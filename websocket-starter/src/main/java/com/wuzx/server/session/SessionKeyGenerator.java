package com.wuzx.server.session;

import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocketSession 唯一标识生成器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 11:25
 **/
public interface SessionKeyGenerator {

    /**
     * 获取当前session的唯一标识
     *
     * @param webSocketSession 当前session
     * @return session唯一标识
     */
    Object sessionKey(WebSocketSession webSocketSession);
}
