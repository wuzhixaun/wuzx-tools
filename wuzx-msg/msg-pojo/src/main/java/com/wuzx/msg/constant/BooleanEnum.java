package com.wuzx.msg.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 21:52
 */
@Getter
@AllArgsConstructor
public enum BooleanEnum {

	/**
	 * 是
	 */
	TRUE(1),
	/**
	 * 否
	 */
	FALSE(0);

	private final int value;

}
