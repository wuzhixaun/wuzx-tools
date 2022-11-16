package com.wuzx.msg.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知接收方式
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 16:15
 */
@Getter
@RequiredArgsConstructor
public enum NotifyChannelEnum {

	// 站内
	STATION(0),
	// 邮件
	MAIL(1),
	// 短信
	SMS(2),
	;


	private final int value;

}
