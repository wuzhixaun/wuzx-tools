package com.wuzx.msg.event;


import com.wuzx.msg.pojo.NotifyInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 通知发布事件
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@Getter
public class NotifyPublishEvent extends ApplicationEvent {

	/**
	 * 通知信息
	 */
	private final NotifyInfo notifyInfo;

	public NotifyPublishEvent(NotifyInfo notifyInfo) {
		super(notifyInfo);
		this.notifyInfo = notifyInfo;
	}

}
