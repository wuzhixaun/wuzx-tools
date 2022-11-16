package com.wuzx.msg.push;


import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;

import java.util.List;

/**
 * 通知发布者
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 18:18
 */
public interface NotifyPusher {

	/**
	 * 当前发布者对应的推送渠道
	 * @see NotifyChannelEnum
	 * @return 推送方式对应的标识
	 */
	Integer notifyChannel();

	/**
	 * 推送通知
	 * @param notifyInfo 通知信息
	 * @param userList 用户列表
	 */
	void push(NotifyInfo notifyInfo, List<User> userList);

}
