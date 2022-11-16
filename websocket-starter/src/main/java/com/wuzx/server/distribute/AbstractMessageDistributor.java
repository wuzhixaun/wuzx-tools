package com.wuzx.server.distribute;

import cn.hutool.core.collection.CollectionUtil;
import com.wuzx.msg.server.message.MessageDO;
import com.wuzx.msg.server.session.WebSocketSessionStore;
import com.wuzx.msg.server.socket.WebSocketMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

/**
 * 消息分发抽象类
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:38
 **/

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMessageDistributor implements MessageDistributor {


    private final WebSocketSessionStore webSocketSessionStore;


    /**
     * 对当前服务中的 websocket 连接做消息推送
     * @param messageDO 消息实体
     */
    protected void doSend(MessageDO messageDO) {
        // 是否广播发送
        Boolean needBroadcast = messageDO.getNeedBroadcast();

        // 获取待发送的 sessionKeys
        Collection<Object> sessionKeys;

        if (needBroadcast != null && needBroadcast) {
            sessionKeys = webSocketSessionStore.getSessionKeys();
        } else {
            sessionKeys = messageDO.getSessionKeys();
        }

        if (CollectionUtil.isEmpty(sessionKeys)) {
            log.warn("发送 websocket 消息，确没有找到对应 sessionKeys, messageDo: {}", messageDO);
            return;
        }

        String messageText = messageDO.getMessageText();
        Boolean onlyOneClientInSameKey = messageDO.getOnlyOneClientInSameKey();

        for (Object sessionKey : sessionKeys) {
            Collection<WebSocketSession> sessions = webSocketSessionStore.getSessions(sessionKey);
            if (CollectionUtil.isEmpty(sessions)) {
                continue;
            }

            // 相同 sessionKey 的客户端只推送一次操作
            if (onlyOneClientInSameKey != null && onlyOneClientInSameKey) {
                WebSocketSession wsSession = CollectionUtil.get(sessions, 0);
                WebSocketMessageSender.send(wsSession, messageText);
                continue;
            }
            for (WebSocketSession wsSession : sessions) {
                WebSocketMessageSender.send(wsSession, messageText);
            }

        }
    }

}
