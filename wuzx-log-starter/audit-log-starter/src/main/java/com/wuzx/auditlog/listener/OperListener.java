package com.wuzx.auditlog.listener;

import cn.hutool.json.JSONUtil;
import com.wuzx.auditlog.handler.AuditLogTableCreator;
import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.event.AuditLogEvent;
import com.wuzx.log.model.entity.AuditLog;
import com.wuzx.log.util.LogUtils;
import com.wuzx.log.util.SpringUtil;
import org.hibernate.event.spi.PostCommitDeleteEventListener;
import org.hibernate.event.spi.PostCommitInsertEventListener;
import org.hibernate.event.spi.PostCommitUpdateEventListener;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.slf4j.MDC;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName OperListener.java
 * @Description 用户操作JPA监听器
 * @createTime 2022年05月24日 17:45:00
 */
public class OperListener implements PostCommitDeleteEventListener, PostCommitInsertEventListener, PostCommitUpdateEventListener {

    /**
     * 操作id静态方法
     */
    private Method userIdMethod;

    /**
     * 审计log创建器
     */
    private AuditLogTableCreator auditLogTableCreator;

    public OperListener(Method userIdMethod, AuditLogTableCreator auditLogTableCreator) {
        this.userIdMethod = userIdMethod;
        this.auditLogTableCreator = auditLogTableCreator;
    }

    /**
     * 监听修改事件
     */
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        final SingleTableEntityPersister persister = (SingleTableEntityPersister) event.getPersister();
        final String tableName = persister.getTableName();

        // 只有配置了需要监听的表才记录审计日志
        if (!auditLogTableCreator.isAudit(tableName)) {
            return;
        }


        Map<String, Object> oldValue = new HashMap<>();
        Map<String, Object> newValue = new HashMap<>();

        // 判断修改了哪些部分
        LogUtils.getDiff(event.getState(), event.getOldState(), event.getPersister().getPropertyNames(),
                event.getPersister().getPropertyTypes(), newValue, oldValue);

        saveAuditLog(buildLog(tableName, event.getId(), LogConstant.OperationEnum.update, oldValue, newValue));
    }

    /**
     * 监听插入事件
     */
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final SingleTableEntityPersister persister = (SingleTableEntityPersister) event.getPersister();
        final String tableName = persister.getTableName();

        // 只有配置了需要监听的表才记录审计日志
        if (!auditLogTableCreator.isAudit(tableName)) {
            return;
        }

        Map<String, Object> newValue = new HashMap<>();

        // 判断修改了哪些部分
        LogUtils.getDiff(event.getState() , event.getPersister().getPropertyNames(),
                event.getPersister().getPropertyTypes(), newValue);

        saveAuditLog(buildLog(tableName, event.getId(), LogConstant.OperationEnum.insert, null, newValue));
    }

    /**
     * 监听删除事件
     */
    @Override
    public void onPostDelete(PostDeleteEvent event) {

        final SingleTableEntityPersister persister = (SingleTableEntityPersister) event.getPersister();
        final String tableName = persister.getTableName();

        // 只有配置了需要监听的表才记录审计日志
        if (!auditLogTableCreator.isAudit(tableName)) {
            return;
        }
        Map<String, Object> oldValue = new HashMap<>();
        // 判断修改了哪些部分
        LogUtils.getDiff(event.getDeletedState() , event.getPersister().getPropertyNames(),
                event.getPersister().getPropertyTypes(), oldValue);

        saveAuditLog(buildLog(tableName, event.getId(), LogConstant.OperationEnum.delete, oldValue, null));
    }


    /**
     * 组装日志信息
     *
     * @param tableName
     * @param id
     * @param operationEnum
     * @param oldValue
     * @param newValue
     * @return
     */
    private AuditLog buildLog(String tableName, Serializable id, LogConstant.OperationEnum operationEnum, Map<String, Object> oldValue, Map<String, Object> newValue) {
        return new AuditLog()
                .setTraceId(MDC.get(LogConstant.TRACE_ID))
                .setTableName(tableName)
                .setTableId(id)
                .setUserId(LogUtils.getUserId(userIdMethod))
                .setOperation(operationEnum.name())
                .setNewValue(JSONUtil.toJsonStr(newValue))
                .setOldValue(JSONUtil.toJsonStr(oldValue))
                .setCreateTime(new Date());
    }

    /**
     * 日志的添加
     *
     * @param auditLog
     */
    private void saveAuditLog(AuditLog auditLog) {
        Map<String, Object> event = new HashMap<>(16);
        event.put(LogConstant.EVENT_LOG, Arrays.asList(auditLog));
        SpringUtil.publishEvent(new AuditLogEvent(event));
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return true;
    }

    @Override
    public void onPostUpdateCommitFailed(PostUpdateEvent event) {

    }

    @Override
    public void onPostInsertCommitFailed(PostInsertEvent event) {

    }

    @Override
    public void onPostDeleteCommitFailed(PostDeleteEvent event) {

    }


}
