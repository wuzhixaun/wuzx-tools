package com.wuzx.auditlog.config;


import com.wuzx.auditlog.properties.AuditProperties;
import com.wuzx.log.event.AuditLogListener;
import com.wuzx.log.model.properties.LogProperties;
import com.wuzx.log.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wuzhixuan
 * @Description 其他必要配置
 * @createTime 2022-06-08 09:49:00
 */
@EnableConfigurationProperties({AuditProperties.class, LogProperties.class})
@ConditionalOnProperty(prefix = AuditProperties.AUDIT_PREFIX, value = "enable", havingValue = "true")
public class OtherAutoConfiguration {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LogProperties logProperties;

    @Bean
    public AuditLogListener auditLogListener() {
        return new AuditLogListener(restTemplate, logProperties.getSaveLogIp());
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
