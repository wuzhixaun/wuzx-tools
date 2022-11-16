package com.wuzx.server.constant;

/**
 * websocket 消息分发器类型常量类
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:38
 */
public final class MessageDistributorTypeConstants {

	private MessageDistributorTypeConstants() {
	}

	/**
	 * 本地
	 */
	public static final String LOCAL = "local";

	/**
	 * 基于 Redis PUB/SUB
	 */
	public static final String REDIS = "redis";

	/**
	 * 自定义
	 */
	public static final String CUSTOM = "custom";

}
