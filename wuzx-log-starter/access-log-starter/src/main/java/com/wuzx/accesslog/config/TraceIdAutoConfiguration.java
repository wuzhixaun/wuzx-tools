package com.wuzx.accesslog.config;


import com.wuzx.accesslog.mdc.TraceIdFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AccessLogAutoConfiguration.java
 * @Description TraceId配置
 * @createTime 2022年06月06日 06:06:00
 */
@Slf4j
@ConditionalOnWebApplication
public class TraceIdAutoConfiguration {

	@Bean
	public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistrationBean() {
		FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>(new TraceIdFilter());
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
}
