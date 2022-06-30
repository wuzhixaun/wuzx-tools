package com.wuzx.operate.annotation;




import com.wuzx.log.enums.OperationTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName CreateOperationLogging.java
 * @Description 日志操作
 * @createTime 2022年06月06日 06:06:00
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogging {

	/**
	 * 日志信息
	 * @return 日志描述信息
	 */
	String msg() default "";

	/**
	 * 日志操作类型
	 * @return 日志操作类型枚举
	 */
	OperationTypeEnum type();

	/**
	 * 是否保存方法入参
	 * @return boolean
	 */
	boolean recordParams() default true;

	/**
	 * 是否保存方法返回值
	 * @return boolean
	 */
	boolean recordResult() default true;

}
