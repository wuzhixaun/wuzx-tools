package com.wuzx.operate.annotation;


import com.wuzx.log.enums.OperationTypeEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName CreateOperationLogging.java
 * @Description 修改操作
 * @createTime 2022年06月06日 06:06:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@OperationLogging(type = OperationTypeEnum.UPDATE)
public @interface UpdateOperationLogging {

	/**
	 * 日志信息
	 * @return 日志描述信息
	 */
	@AliasFor(annotation = OperationLogging.class)
	String msg();

	/**
	 * 是否保存方法入参
	 * @return boolean
	 */
	@AliasFor(annotation = OperationLogging.class)
	boolean recordParams() default true;

	/**
	 * 是否保存方法返回值
	 * @return boolean
	 */
	@AliasFor(annotation = OperationLogging.class)
	boolean recordResult() default true;

}
