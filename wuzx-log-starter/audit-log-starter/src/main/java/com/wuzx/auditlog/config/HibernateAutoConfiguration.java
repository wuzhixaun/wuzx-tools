package com.wuzx.auditlog.config;

import com.wuzx.auditlog.handler.AuditLogTableCreator;
import com.wuzx.auditlog.listener.OperListener;
import com.wuzx.auditlog.properties.AuditProperties;
import com.wuzx.log.model.properties.LogProperties;
import com.wuzx.log.util.LogUtils;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName HibernateConfiguration.java
 * @Description jpa 自动配置配置类
 * @createTime 2022年05月24日 17:44:00
 */

@EnableConfigurationProperties(AuditProperties.class)
@ConditionalOnProperty(prefix = AuditProperties.AUDIT_PREFIX, value = "enable", havingValue = "true")
public class HibernateAutoConfiguration {


    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private OperListener operListener;

    @PostConstruct
    public void registerListeners() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(operListener);//对实体保存的监听
        registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(operListener);//对实体修改的监听
        registry.getEventListenerGroup(EventType.POST_COMMIT_DELETE).appendListener(operListener);//对实体删除的监听
    }


    @Bean
    public OperListener operListener(AuditProperties properties, LogProperties logProperties) {
        Boolean splitEnableOption = Boolean.valueOf(properties.getProperty("split"));
        final List<String> listenTables = properties.getListenTables();

        final AuditLogTableCreator auditLogTableCreator = new AuditLogTableCreator(splitEnableOption, listenTables);

        // 指定用于获取当前登录用户ID的方法，#号之前是类的全限定名称，#号之后是静态方法的名称，返回int类型，如不需要记录用户ID可删除此配置项
        final Method userIdMethod = LogUtils.getMethod(logProperties.getUserIdMethod());
        return new OperListener(userIdMethod, auditLogTableCreator);
    }
}
