package com.wuzx.server.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * WebSocketConstants 常量类
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 11:28
 **/
public final class WebSocketConstants {

    /**
     * 存储在 WebSocketSession Attribute 中的 token 属性名
     */
    public static final String TOKEN_ATTR_NAME = "token";

    /**
     * 存储在 WebSocketSession Attribute 中的 用户唯一标识 属性名
     */
    public static final String USER_KEY_ATTR_NAME = "userId";


    @Getter
    @RequiredArgsConstructor
    public enum WebSocketMessageTypeEnum {

        PING("ping"), PONG("pong");

        private final String value;

    }
}
