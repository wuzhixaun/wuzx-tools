package com.wuzx.server.distribute;

import com.wuzx.msg.server.message.MessageDO;

/**
 * 消息分发器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:34
 **/
public interface MessageDistributor {

    /**
     * 消息分发
     * @param messageDO 发送的消息
     */
    void distribute(MessageDO messageDO);
}
