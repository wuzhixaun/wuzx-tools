package com.wuzx.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName LogStatusEnum.java
 * @Description 操作状态枚举
 * @createTime 2022年06月06日 06:06:00
 */
@Getter
@AllArgsConstructor
public enum LogStatusEnum {

	/**
	 * 成功
	 */
	SUCCESS(1),
	/**
	 * 失败
	 */
	FAIL(0);

	private final int value;

}
