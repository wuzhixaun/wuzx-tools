package com.wuzx.auditlog.properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AuditProperties.java
 * @Description 审计
 * @createTime 2022年05月24日 16:04:00
 */
@ConfigurationProperties(prefix = AuditProperties.AUDIT_PREFIX)
public class AuditProperties extends Properties {

    public static final String AUDIT_PREFIX = "log.audit";

    public void setEnable(Boolean enable) {
        setProperty("enable", enable.toString());
    }

    public Boolean getEnable() {
        return Boolean.valueOf(getProperty("enable"));
    }

    public void setSplit(Boolean split) {
        setProperty("split", split.toString());
    }

    public Boolean getSplit() {
        return Boolean.valueOf(getProperty("split"));
    }


    public List<String> getListenTables() {
        List<String> list = new ArrayList<>();

        for (int i = 0; ; i++) {
            final String listenTables = getProperty("listenTables." + i);
            if (StringUtils.isEmpty(listenTables)) {
                return list;
            }
            list.add(StringUtils.deleteWhitespace(listenTables));
        }

    }

    public void setListenTables(String listenTables) {
        setProperty("listenTables", listenTables);
    }

}
