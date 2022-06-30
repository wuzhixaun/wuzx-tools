package com.wuzx.log.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuzhixuan
 * @Description TODO
 * @createTime 2022-06-08 17:01:00
 */
@Data
@ConfigurationProperties(prefix = LogProperties.LOG_PREFIX)
public class LogProperties {
    public static final String LOG_PREFIX = "log";
    /**
     * 保存日志服务地址
     */
    private String saveLogIp;


    /**
     * 获取用户id的方法，#分割
     */
    private String userIdMethod;

}
