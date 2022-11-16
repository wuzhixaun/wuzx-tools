package com.wuzx.server.holder;



import com.wuzx.msg.server.handler.JsonMessageHandler;
import com.wuzx.msg.server.message.JsonWebSocketMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
public final class JsonMessageHandlerHolder {

	private JsonMessageHandlerHolder() {
	}

	private static final Map<String, JsonMessageHandler<JsonWebSocketMessage>> MESSAGE_HANDLER_MAP = new ConcurrentHashMap<>();

	public static JsonMessageHandler<JsonWebSocketMessage> getHandler(String type) {
		return MESSAGE_HANDLER_MAP.get(type);
	}

	public static void addHandler(JsonMessageHandler<JsonWebSocketMessage> jsonMessageHandler) {
		MESSAGE_HANDLER_MAP.put(jsonMessageHandler.type(), jsonMessageHandler);
	}

}
