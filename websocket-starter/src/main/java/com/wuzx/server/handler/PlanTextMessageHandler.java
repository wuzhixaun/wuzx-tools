package com.wuzx.server.handler;


import com.wuzx.msg.server.message.JsonWebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 普通文本类型（非指定json类型）的消息处理器 即消息不满足于我们定义的Json类型消息时，所使用的处理器
 *
 * @see JsonWebSocketMessage
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
public interface PlanTextMessageHandler {

	/**
	 * 普通文本消息处理
	 * @param session 当前接收消息的session
	 * @param message 文本消息
	 */
	void handle(WebSocketSession session, String message);

}
