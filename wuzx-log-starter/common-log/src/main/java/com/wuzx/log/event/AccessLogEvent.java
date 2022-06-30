package com.wuzx.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @Description 访问日志事件
 *
 * @author wuzhixuan
 * @createTime 2022-06-07 15:20:00
 */
public class AccessLogEvent extends ApplicationEvent {

    public AccessLogEvent(Map<String, Object> source) {
        super(source);
    }
}
