package com.wuzx.msg.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 消息类型
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/27 10:00
 **/
@Getter
@AllArgsConstructor
public enum MessageType {

    ANNOUNCEMENT(0, "公告"),
    MESSAGE_NOTIFICATION(1, "消息通知"),
    ;



    private final int value;
    private final String name;
}
