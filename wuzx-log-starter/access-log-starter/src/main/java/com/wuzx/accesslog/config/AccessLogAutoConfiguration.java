package com.wuzx.accesslog.config;



import com.wuzx.accesslog.filter.AccessLogFilter;
import com.wuzx.accesslog.handler.AccessLogHandler;
import com.wuzx.accesslog.handler.CustomAccessLogHandler;
import com.wuzx.accesslog.properties.AccessLogProperties;
import com.wuzx.log.event.AccessLogListener;
import com.wuzx.log.model.properties.LogProperties;
import com.wuzx.log.util.LogUtils;
import com.wuzx.log.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AccessLogAutoConfiguration.java
 * @Description 访问日志配置类
 * @createTime 2022年06月06日 06:06:00
 */
@Slf4j
@EnableConfigurationProperties({AccessLogProperties.class, LogProperties.class})
@ConditionalOnProperty(prefix = AccessLogProperties.ACCESS_PREFIX, value = "enable", havingValue = "true")
public class AccessLogAutoConfiguration {

    @Autowired
    private AccessLogHandler<?> accessLogHandler;
    @Autowired
    private RestTemplate restTemplate;


    @Bean
    public FilterRegistrationBean<AccessLogFilter> accessLogFilterRegistrationBean(AccessLogProperties accessLogProperties) {
        log.info("----------------------access log 记录拦截器已开启----------------------");
        FilterRegistrationBean<AccessLogFilter> registrationBean = new FilterRegistrationBean<>(
                new AccessLogFilter(accessLogHandler, accessLogProperties.getIgnoreUrlPatterns()));
        registrationBean.setOrder(-10);
        return registrationBean;
    }


    @Bean
    public AccessLogHandler accessLogHandler(LogProperties logProperties) {
        return new CustomAccessLogHandler(LogUtils.getMethod(logProperties.getUserIdMethod()));
    }

    @Bean
    public AccessLogListener accessLogListener(LogProperties logProperties) {
        return new AccessLogListener(restTemplate, logProperties.getSaveLogIp());
    }

    @Bean
    @ConditionalOnMissingBean(SpringUtil.class)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }


    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
