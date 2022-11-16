package com.wuzx.msg.handler;

import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息处理代理
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 14:55
 **/
@Slf4j
@Component
@AllArgsConstructor
public class NotifyInfoDelegateHandler<T extends NotifyInfo> {
    private final List<NotifyInfoHandler<T>> notifyInfoHandlers;

    private Map<Class<?>, NotifyInfoHandler<T>> handlerMap;

    @PostConstruct
    public void init() {
        handlerMap = new HashMap<>(notifyInfoHandlers.size());
        for (NotifyInfoHandler<T> handler : notifyInfoHandlers) {
            handlerMap.put(handler.getNotifyClass(), handler);
        }
    }


    /**
     * 代理方法
     * @param userList 发送用户列表
     * @param info 消息
     */
    public void handle(List<User> userList, T info) {
        Assert.notNull(info, "event message cant be null!");
        NotifyInfoHandler<T> notifyInfoHandler = handlerMap.get(info.getClass());
        if (notifyInfoHandler == null) {
            log.warn("no notifyHandler bean for class:{},please check!", info.getClass().getName());
            return;
        }
        notifyInfoHandler.handle(userList, info);
    }

}
