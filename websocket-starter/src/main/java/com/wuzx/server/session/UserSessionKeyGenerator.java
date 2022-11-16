package com.wuzx.server.session;

import com.wuzx.msg.server.constant.WebSocketConstants;
import com.wuzx.msg.server.socket.UserAttributeHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

/**
 * id生成器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 11:31
 **/
@RequiredArgsConstructor
public class UserSessionKeyGenerator implements SessionKeyGenerator {

    /**
     * 获取当前session的唯一标识，用户的唯一标识已经通过
     * @see UserAttributeHandshakeInterceptor 存储在当前 session 的属性中
     * @param webSocketSession 当前session
     * @return session唯一标识
     */

    @Override
    public Object sessionKey(WebSocketSession webSocketSession) {
        return webSocketSession.getAttributes().get(WebSocketConstants.USER_KEY_ATTR_NAME);
    }
}
