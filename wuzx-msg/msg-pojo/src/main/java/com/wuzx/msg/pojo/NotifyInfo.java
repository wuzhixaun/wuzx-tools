package com.wuzx.msg.pojo;


import com.wuzx.msg.constant.MessageType;
import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 16:15
 */
public interface NotifyInfo {

	/**
	 * 标题
	 * @return String 当前通知标题
	 */
	String getTitle();

	/**
	 * 内容
	 * @return String 当前通知内容
	 */
	String getContent();

	/**
	 * 消息类型
	 * @see MessageType
	 * @return
	 */
	Integer getMessageType();

	/**
	 * 接收人筛选方式
	 * @see NotifyRecipientFilterTypeEnum
	 * @return Integer 接收人筛选方式
	 */
	Integer getRecipientFilterType();

	/**
	 * 对应接收人筛选方式的条件信息
	 * @return List<Object>
	 */
	List<Object> getRecipientFilterCondition();

	/**
	 * 接收方式，值与通知渠道一一对应
	 * @see NotifyChannelEnum
	 * @return List<Integer>
	 */
	List<Integer> getReceiveMode();

}
