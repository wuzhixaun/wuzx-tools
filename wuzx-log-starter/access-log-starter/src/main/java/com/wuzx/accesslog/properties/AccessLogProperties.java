package com.wuzx.accesslog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AuditProperties.java
 * @Description 访问日志
 * @createTime 2022年06月06日 06:06:00
 */
@Data
@ConfigurationProperties(prefix = AccessLogProperties.ACCESS_PREFIX)
public class AccessLogProperties {

	public static final String ACCESS_PREFIX = "log.access";
	/**
	 * 忽略的Url匹配规则，Ant风格
	 */
	private List<String> ignoreUrlPatterns = Arrays.asList("/actuator/**","/swagger-ui/**","/user/login");

	/**
	 * 是否开启访问日志
	 */
	private boolean enable;

}
