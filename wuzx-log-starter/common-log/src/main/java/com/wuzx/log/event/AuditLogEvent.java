package com.wuzx.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @Description 审计日志事件
 *
 * @author wuzhixuan
 * @createTime 2022-06-07 15:20:00
 */
public class AuditLogEvent extends ApplicationEvent {

    public AuditLogEvent(Map<String, Object> source) {
        super(source);
    }
}
