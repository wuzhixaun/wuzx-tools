package com.wuzx.server.message;


import com.wuzx.msg.server.constant.WebSocketConstants;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
public class PingJsonWebSocketMessage extends JsonWebSocketMessage {

	public PingJsonWebSocketMessage() {
		super(WebSocketConstants.WebSocketMessageTypeEnum.PING.getValue());
	}

}
