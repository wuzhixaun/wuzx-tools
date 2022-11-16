package com.wuzx.server.config;

import com.wuzx.msg.server.handler.JsonMessageHandler;
import com.wuzx.msg.server.handler.PingJsonMessageHandler;
import com.wuzx.msg.server.holder.JsonMessageHandlerInitializer;
import com.wuzx.msg.server.message.JsonWebSocketMessage;
import com.wuzx.msg.server.properties.WebSocketProperties;
import com.wuzx.msg.server.session.SessionKeyGenerator;
import com.wuzx.msg.server.session.UserSessionKeyGenerator;
import com.wuzx.msg.server.socket.SockJsServiceConfigurer;
import com.wuzx.msg.server.socket.UserAttributeHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;

/**
 * websocket配置类
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 11:37
 **/
@Import({ WebSocketHandlerConfig.class, LocalMessageDistributorConfig.class })
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@EnableConfigurationProperties(WebSocketProperties.class)
public class WebSocketAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserAttributeHandshakeInterceptor.class)
    public HandshakeInterceptor authenticationHandshakeInterceptor() {
        return new UserAttributeHandshakeInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(SessionKeyGenerator.class)
    public SessionKeyGenerator userSessionKeyGenerator() {
        return new UserSessionKeyGenerator();
    }

    private final WebSocketProperties webSocketProperties;

    @Bean
    @ConditionalOnMissingBean
    public WebSocketConfigurer webSocketConfigurer(List<HandshakeInterceptor> handshakeInterceptor,
                                                   WebSocketHandler webSocketHandler,
                                                   @Autowired(required = false) SockJsServiceConfigurer sockJsServiceConfigurer) {
        return registry -> {
            WebSocketHandlerRegistration registration = registry
                    .addHandler(webSocketHandler, webSocketProperties.getPath())
                    .addInterceptors(handshakeInterceptor.toArray(new HandshakeInterceptor[0]));

            String[] allowedOrigins = webSocketProperties.getAllowedOrigins();
            if (allowedOrigins != null && allowedOrigins.length > 0) {
                registration.setAllowedOrigins(allowedOrigins);
            }

            String[] allowedOriginPatterns = webSocketProperties.getAllowedOriginPatterns();
            if (allowedOriginPatterns != null && allowedOriginPatterns.length > 0) {
                registration.setAllowedOrigins(allowedOriginPatterns);
            }

            if (webSocketProperties.isWithSockjs()) {
                SockJsServiceRegistration sockJsServiceRegistration = registration.withSockJS();
                if (sockJsServiceConfigurer != null) {
                    sockJsServiceConfigurer.config(sockJsServiceRegistration);
                }
            }
        };
    }


    /**
     * 注册 JsonMessageHandlerInitializer 收集所有的 json 类型消息处理器
     * @param jsonMessageHandlerList json 类型消息处理器
     * @return JsonMessageHandlerInitializer
     */
    @Bean
    @ConditionalOnMissingBean
    public JsonMessageHandlerInitializer jsonMessageHandlerInitializer(
            List<JsonMessageHandler<? extends JsonWebSocketMessage>> jsonMessageHandlerList) {
        return new JsonMessageHandlerInitializer(jsonMessageHandlerList);
    }


    /**
     * 心跳处理器
     * @return PingJsonMessageHandler
     */
    @Bean
    @ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "heartbeat", havingValue = "true",
            matchIfMissing = true)
    public PingJsonMessageHandler pingJsonMessageHandler() {
        return new PingJsonMessageHandler();
    }
}
