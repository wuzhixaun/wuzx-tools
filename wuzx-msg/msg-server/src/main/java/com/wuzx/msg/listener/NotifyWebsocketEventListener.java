package com.wuzx.msg.listener;

import cn.hutool.json.JSONUtil;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.event.AnnouncementCloseEvent;
import com.wuzx.msg.event.StationNotifyPushEvent;
import com.wuzx.msg.handler.NotifyInfoDelegateHandler;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.server.distribute.MessageDistributor;
import com.wuzx.msg.message.AnnouncementCloseMessage;
import com.wuzx.msg.server.message.MessageDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 14:32
 */
@Slf4j
@RequiredArgsConstructor
public class NotifyWebsocketEventListener {

	private final MessageDistributor messageDistributor;

	private final NotifyInfoDelegateHandler<? super NotifyInfo> notifyInfoDelegateHandler;

	/**
	 * 公告关闭事件监听
	 * @param event the AnnouncementCloseEvent
	 */
	@Async
	@EventListener(AnnouncementCloseEvent.class)
	public void onAnnouncementCloseEvent(AnnouncementCloseEvent event) {
		// 构建公告关闭的消息体
		AnnouncementCloseMessage message = new AnnouncementCloseMessage();
		message.setId(event.getId());
		String msg = JSONUtil.toJsonStr(message);

		// 广播公告关闭信息
		MessageDO messageDO = new MessageDO().setMessageText(msg).setNeedBroadcast(true);
		messageDistributor.distribute(messageDO);
	}

	/**
	 * 站内通知推送事件
	 * @param event the StationNotifyPushEvent
	 */
	@Async
	@EventListener(StationNotifyPushEvent.class)
	public void onAnnouncementPublishEvent(StationNotifyPushEvent event) {
		NotifyInfo notifyInfo = event.getNotifyInfo();
		List<User> userList = event.getUserList();
		notifyInfoDelegateHandler.handle(userList, notifyInfo);
	}

}
