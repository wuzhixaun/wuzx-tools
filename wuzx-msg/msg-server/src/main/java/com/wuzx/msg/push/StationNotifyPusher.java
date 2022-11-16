package com.wuzx.msg.push;


import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.event.StationNotifyPushEvent;
import com.wuzx.msg.pojo.NotifyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息通知站内推送
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:34
 */
@Component
@RequiredArgsConstructor
public class StationNotifyPusher implements NotifyPusher {

	private final ApplicationEventPublisher publisher;

	/**
	 * 当前发布者对应的接收方式
	 * @see NotifyChannelEnum
	 * @return 推送方式
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.STATION.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<User> userList) {
		// 发布事件，监听者进行实际的 websocket 推送
		publisher.publishEvent(new StationNotifyPushEvent(notifyInfo, userList));
	}

}
