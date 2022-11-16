package com.wuzx.msg.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.server.distribute.MessageDistributor;
import com.wuzx.msg.server.message.JsonWebSocketMessage;
import com.wuzx.msg.server.message.MessageDO;
import javassist.expr.Cast;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 14:56
 **/
public abstract class AbstractNotifyInfoHandler<T extends NotifyInfo, M extends JsonWebSocketMessage>
        implements NotifyInfoHandler<T> {


    @Autowired
    private MessageDistributor messageDistributor;

    protected final Class<T> clz;

    protected AbstractNotifyInfoHandler() {
        Type superClass = getClass().getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) superClass;
        clz = (Class<T>) type.getActualTypeArguments()[0];
    }


    @Override
    public void handle(List<User> userList, T notifyInfo) {
        M message = createMessage(notifyInfo);
        String msg = JSONUtil.toJsonStr(message);
        List<Object> sessionKeys = userList.stream().map(user -> String.valueOf(user.getId())).collect(Collectors.toList());
        persistMessage(userList, notifyInfo);
        MessageDO messageDO = new MessageDO().setMessageText(msg).setSessionKeys(sessionKeys)
                .setNeedBroadcast(CollUtil.isEmpty(sessionKeys));
        messageDistributor.distribute(messageDO);
    }

    @Override
    public Class<T> getNotifyClass() {
        return this.clz;
    }


    /**
     * 持久化通知
     * @param userList 通知用户列表
     * @param notifyInfo 消息内容
     */
    protected abstract void persistMessage(List<User> userList, T notifyInfo);

    /**
     * 产生推送消息
     * @param notifyInfo 消息内容
     * @return 分发消息
     */
    protected abstract M createMessage(T notifyInfo);
}

