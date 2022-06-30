package com.wuzx.operate.config;

import com.wuzx.log.event.OperateLogListener;
import com.wuzx.log.model.properties.LogProperties;
import com.wuzx.log.util.LogUtils;
import com.wuzx.log.util.SpringUtil;
import com.wuzx.operate.aspect.OperationLogAspect;
import com.wuzx.operate.handler.CustomOperationLogHandler;
import com.wuzx.operate.handler.OperationLogHandler;
import com.wuzx.operate.properties.OperateLogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AccessLogAutoConfiguration.java
 * @Description 操作日志配置类
 * @createTime 2022年06月06日 06:06:00
 */
@EnableConfigurationProperties({OperateLogProperties.class, LogProperties.class})
@ConditionalOnProperty(prefix = OperateLogProperties.OPERATE_PREFIX, value = "enable", havingValue = "true")
public class OperationLogAutoConfiguration {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LogProperties logProperties;

    /**
     * 注册操作日志Aspect
     * @return OperationLogAspect
     */
    @Bean
    @ConditionalOnBean(OperationLogHandler.class)
    public <T> OperationLogAspect<T> operationLogAspect(OperationLogHandler<T> operationLogHandler) {
        return new OperationLogAspect<>(operationLogHandler);
    }

    @Bean
    public OperationLogHandler operationLogHandler() {
        return new CustomOperationLogHandler(LogUtils.getMethod(logProperties.getUserIdMethod()));
    }

    @Bean
    public OperateLogListener operateLogListener() {
        return new OperateLogListener(restTemplate, logProperties.getSaveLogIp());
    }

    @Bean
    @ConditionalOnMissingBean(SpringUtil.class)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }


    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
