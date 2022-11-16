package com.wuzx.msg.event;

import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@Getter
@RequiredArgsConstructor
public class StationNotifyPushEvent {

	/**
	 * 通知信息
	 */
	private final NotifyInfo notifyInfo;

	/**
	 * 推送用户列表
	 */
	private final List<User> userList;

}
