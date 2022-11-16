package com.wuzx.msg.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知接收者筛选类型
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 16:15
 */
@Getter
@RequiredArgsConstructor
public enum NotifyRecipientFilterTypeEnum {

	/**
	 * 全部
	 */
	ALL(0),
	/**
	 * 指定用户角色
	 */
	SPECIFY_ROLE(1),
	/**
	 * 指定部门
	 */
	SPECIFY_ORGANIZATION(2),
	/**
	 *  指定用户
	 */
	SPECIFY_USER(3),;

	private final int value;

}
