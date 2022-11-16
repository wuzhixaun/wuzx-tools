package com.wuzx.server.config;

import com.wuzx.msg.server.handler.CustomWebSocketHandler;
import com.wuzx.msg.server.handler.PlanTextMessageHandler;
import com.wuzx.msg.server.properties.WebSocketProperties;
import com.wuzx.msg.server.session.DefaultWebSocketSessionStore;
import com.wuzx.msg.server.session.SessionKeyGenerator;
import com.wuzx.msg.server.session.WebSocketSessionStore;
import com.wuzx.msg.server.socket.MapSessionWebSocketHandlerDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;

/**
 *
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 15:58
 **/

@RequiredArgsConstructor
public class WebSocketHandlerConfig {

    private final WebSocketProperties webSocketProperties;

    /**
     * WebSocket session 存储器
     * @return DefaultWebSocketSessionStore
     */
    @Bean
    @ConditionalOnMissingBean
    public WebSocketSessionStore webSocketSessionStore(@Autowired(required = false) SessionKeyGenerator sessionKeyGenerator) {
        return new DefaultWebSocketSessionStore(sessionKeyGenerator);
    }

    @Bean
    @ConditionalOnMissingBean(WebSocketHandler.class)
    public WebSocketHandler webSocketHandler(WebSocketSessionStore webSocketSessionStore,
                                             @Autowired(required = false) PlanTextMessageHandler planTextMessageHandler) {
        CustomWebSocketHandler customWebSocketHandler = new CustomWebSocketHandler(planTextMessageHandler);
        if (webSocketProperties.isMapSession()) {
            return new MapSessionWebSocketHandlerDecorator(customWebSocketHandler, webSocketSessionStore,
                    webSocketProperties.getConcurrent());
        }
        return customWebSocketHandler;
    }
}
