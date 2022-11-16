package com.wuzx.msg.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户公告状态
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Getter
@AllArgsConstructor
public enum UserAnnouncementStateEnum {

	/**
	 * 未读
	 */
	UNREAD(0),
	/**
	 * 已读
	 */
	READ(1);

	private final int value;

}
