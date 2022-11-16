package com.wuzx.server.message;

/**
 * <p>
 * BallCat 自定义的 Json 类型的消息
 * </p>
 *
 * <ul>
 * <li>要求消息内容必须是一个 Json 对象
 * <li>Json 对象中必须有一个属性 type
 * <ul/>
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
public abstract class JsonWebSocketMessage {

	public static final String TYPE_FIELD = "type";

	private final String type;

	protected JsonWebSocketMessage(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
