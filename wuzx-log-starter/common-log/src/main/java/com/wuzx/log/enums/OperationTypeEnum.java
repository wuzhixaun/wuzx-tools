package com.wuzx.log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName OperationTypeEnum.java
 * @Description 操作类型
 * @createTime 2022年06月06日 06:06:00
 */
@Getter
@RequiredArgsConstructor
public enum OperationTypeEnum {

	/**
	 * 其他操作
	 */
	OTHER(0),

	/**
	 * 导入操作
	 */
	IMPORT(1),

	/**
	 * 导出操作
	 */
	EXPORT(2),

	/**
	 * 查看操作，主要用于敏感数据查询记录
	 */
	READ(3),

	/**
	 * 新建操作
	 */
	CREATE(4),

	/**
	 * 修改操作
	 */
	UPDATE(5),

	/**
	 * 删除操作
	 */
	DELETE(6);

	private final Integer value;

}
