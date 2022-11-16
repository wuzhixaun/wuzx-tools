package com.wuzx.msg.listener;

import com.wuzx.msg.event.NotifyPublishEvent;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.push.NotifyPushExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 通知发布事件监听器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 14:32
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyPublishEventListener {

    private final NotifyPushExecutor notifyPushExecutor;

    /**
     * 通知发布事件
     * @param event the NotifyPublishEvent
     */
    @Async
    @EventListener(NotifyPublishEvent.class)
    public void onNotifyPublishEvent(NotifyPublishEvent event) {
        NotifyInfo notifyInfo = event.getNotifyInfo();
        // 推送通知
        notifyPushExecutor.push(notifyInfo);
    }
}
