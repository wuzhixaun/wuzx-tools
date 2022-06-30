package com.wuzx.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wuzhixuan
 * @Description 操作日志事件
 * @createTime 2022-06-07 15:20:00
 */
public class OperateLogEvent extends ApplicationEvent {

    public OperateLogEvent(Object source) {
        super(source);
    }
}
