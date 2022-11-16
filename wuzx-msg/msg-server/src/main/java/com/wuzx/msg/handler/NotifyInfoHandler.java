package com.wuzx.msg.handler;

import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;

import java.util.List;

/**
 *  event消息处理接口
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 14:52
 **/
public interface NotifyInfoHandler<T extends NotifyInfo> {


    /**
     * 处理消息
     * @param userList 发送用户列表
     * @param info 消息
     */
    void handle(List<User> userList, T info);

    /**
     * 获取消息类型
     * @return 消息类型
     */
    Class<T> getNotifyClass();
}
