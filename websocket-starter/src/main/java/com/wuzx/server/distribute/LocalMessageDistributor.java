package com.wuzx.server.distribute;


import com.wuzx.msg.server.message.MessageDO;
import com.wuzx.msg.server.session.WebSocketSessionStore;

/**
 * 本地消息分发，直接进行发送
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:34
 */
public class LocalMessageDistributor extends AbstractMessageDistributor {

	public LocalMessageDistributor(WebSocketSessionStore webSocketSessionStore) {
		super(webSocketSessionStore);
	}

	/**
	 * 消息分发
	 * @param messageDO 发送的消息
	 */
	@Override
	public void distribute(MessageDO messageDO) {
		doSend(messageDO);
	}

}
