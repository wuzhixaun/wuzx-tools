package com.wuzx.server.socket;

import com.wuzx.msg.server.handler.ConcurrentWebSocketSessionOptions;
import com.wuzx.msg.server.session.WebSocketSessionStore;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * WebSocketHandler 装饰器，该装饰器主要用于在开启和关闭连接时，进行session的映射存储与释放
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 15:30
 **/
public class MapSessionWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    private final WebSocketSessionStore webSocketSessionStore;

    private final ConcurrentWebSocketSessionOptions concurrentWebSocketSessionOptions;

    public MapSessionWebSocketHandlerDecorator(WebSocketHandler delegate, WebSocketSessionStore webSocketSessionStore,
                                               ConcurrentWebSocketSessionOptions concurrentWebSocketSessionOptions) {
        super(delegate);
        this.webSocketSessionStore = webSocketSessionStore;
        this.concurrentWebSocketSessionOptions = concurrentWebSocketSessionOptions;
    }

    /**
     * websocket 连接时执行的动作
     * @param wsSession websocket session 对象
     * @throws Exception 异常对象
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession wsSession) throws Exception {
        // 包装一层，防止并发发送出现问题
        if (Boolean.TRUE.equals(concurrentWebSocketSessionOptions.isEnable())) {
            wsSession = new ConcurrentWebSocketSessionDecorator(wsSession,
                    concurrentWebSocketSessionOptions.getSendTimeLimit(),
                    concurrentWebSocketSessionOptions.getBufferSizeLimit(),
                    concurrentWebSocketSessionOptions.getOverflowStrategy());
        }
        webSocketSessionStore.addSession(wsSession);
    }


    /**
     * websocket 关闭连接时执行的动作
     * @param wsSession websocket session 对象
     * @param closeStatus 关闭状态对象
     * @throws Exception 异常对象
     */
    @Override
    public void afterConnectionClosed(WebSocketSession wsSession, CloseStatus closeStatus) throws Exception {
        webSocketSessionStore.removeSession(wsSession);
    }
}
