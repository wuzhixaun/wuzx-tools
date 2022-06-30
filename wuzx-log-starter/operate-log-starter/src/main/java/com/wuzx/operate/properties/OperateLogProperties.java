package com.wuzx.operate.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AuditProperties.java
 * @Description 访问日志
 * @createTime 2022年06月06日 06:06:00
 */
@Data
@ConfigurationProperties(prefix = OperateLogProperties.OPERATE_PREFIX)
public class OperateLogProperties {

	public static final String OPERATE_PREFIX = "log.operate";

	/**
	 * 是否开启访问日志
	 */
	private boolean enable;

}
