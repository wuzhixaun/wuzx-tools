package com.wuzx.server.socket;

import com.wuzx.msg.server.constant.WebSocketConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName WebSocketInterceptor.java
 * @Description 握手拦截器 WebSocket 握手拦截器 在握手时记录下当前 session 对应的用户Id和token信息
 * @createTime 2022年05月13日 11:38:00
 */
@RequiredArgsConstructor
public class UserAttributeHandshakeInterceptor implements HandshakeInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(UserAttributeHandshakeInterceptor.class);


    /**
     * 握手之前
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        LOGGER.info("WebSocketInterceptor beforeHandshake Handshake ");

        // 转成HttpServletRequest
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String token = servletRequest.getParameter(WebSocketConstants.TOKEN_ATTR_NAME);
        // 需要解析user
        String userId = servletRequest.getParameter(WebSocketConstants.USER_KEY_ATTR_NAME);

        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(userId)) {
            // /将用户id放入socket处理器的会话(WebSocketSession)中
            attributes.put(WebSocketConstants.TOKEN_ATTR_NAME, token);
            attributes.put(WebSocketConstants.USER_KEY_ATTR_NAME, userId);
            LOGGER.info("WebSocketInterceptor beforeHandshake Handshake sucess token {} userId {}", token, userId);
            return true;
        }

        return false;
    }

    /**
     * 握手之后
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        LOGGER.info("WebSocketInterceptor afterHandshake Handshake ");
        // doNothing
    }
}
