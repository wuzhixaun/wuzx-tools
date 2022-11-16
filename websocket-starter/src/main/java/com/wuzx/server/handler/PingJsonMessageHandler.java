package com.wuzx.server.handler;

import com.wuzx.msg.server.constant.WebSocketConstants;
import com.wuzx.msg.server.message.JsonWebSocketMessage;
import com.wuzx.msg.server.message.PingJsonWebSocketMessage;
import com.wuzx.msg.server.message.PongJsonWebSocketMessage;
import com.wuzx.msg.server.socket.WebSocketMessageSender;
import org.springframework.web.socket.WebSocketSession;

/**
 * 心跳处理，接收到客户端的ping时，立刻回复一个pong
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
public class PingJsonMessageHandler implements JsonMessageHandler<PingJsonWebSocketMessage> {

	@Override
	public void handle(WebSocketSession session, PingJsonWebSocketMessage message) {
		JsonWebSocketMessage pongJsonWebSocketMessage = new PongJsonWebSocketMessage();
		WebSocketMessageSender.send(session, pongJsonWebSocketMessage);
	}

	@Override
	public String type() {
		return WebSocketConstants.WebSocketMessageTypeEnum.PING.getValue();
	}

	@Override
	public Class<PingJsonWebSocketMessage> getMessageClass() {
		return PingJsonWebSocketMessage.class;
	}

}
