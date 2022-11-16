package com.wuzx.server.holder;


import com.wuzx.msg.server.handler.JsonMessageHandler;
import com.wuzx.msg.server.message.JsonWebSocketMessage;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * JsonMessageHandler 初始化器
 * <p/>
 * 将所有的 jsonMessageHandler 收集到 JsonMessageHandlerHolder 中
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@RequiredArgsConstructor
public class JsonMessageHandlerInitializer {

	private final List<JsonMessageHandler<? extends JsonWebSocketMessage>> jsonMessageHandlerList;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initJsonMessageHandlerHolder() {
		for (JsonMessageHandler<? extends JsonWebSocketMessage> jsonMessageHandler : jsonMessageHandlerList) {
			JsonMessageHandlerHolder.addHandler((JsonMessageHandler<JsonWebSocketMessage>) jsonMessageHandler);
		}
	}

}