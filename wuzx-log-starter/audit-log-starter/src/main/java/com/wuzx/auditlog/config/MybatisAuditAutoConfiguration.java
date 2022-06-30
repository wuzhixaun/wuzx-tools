package com.wuzx.auditlog.config;

import com.wuzx.auditlog.interceptor.SQLAuditLogInterceptor;
import com.wuzx.auditlog.properties.AuditProperties;
import com.wuzx.log.model.properties.LogProperties;
import com.wuzx.log.util.LogUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AuditProperties.java
 * @Description 自定注入日志审计插件
 * @createTime 2022年05月24日 16:04:00
 */
@ConditionalOnBean(SqlSessionFactory.class)
@ConditionalOnProperty(prefix = AuditProperties.AUDIT_PREFIX, value = "enable", havingValue = "true")
@EnableConfigurationProperties({AuditProperties.class, LogProperties.class})
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MybatisAuditAutoConfiguration implements InitializingBean {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private AuditProperties properties;

    @Autowired
    private LogProperties logProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        SQLAuditLogInterceptor interceptor = new SQLAuditLogInterceptor(LogUtils.getMethod(logProperties.getUserIdMethod()));
        interceptor.setProperties(this.properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            if (!containsInterceptor(configuration, interceptor)) {
                configuration.addInterceptor(interceptor);
            }
        }
    }

    /**
     * 是否已经存在相同的拦截器
     *
     * @param configuration
     * @param interceptor
     * @return
     */
    private boolean containsInterceptor(org.apache.ibatis.session.Configuration configuration, Interceptor interceptor) {
        try {
            // getInterceptors since 3.2.2
            return configuration.getInterceptors().contains(interceptor);
        } catch (Exception e) {
            return false;
        }
    }


}