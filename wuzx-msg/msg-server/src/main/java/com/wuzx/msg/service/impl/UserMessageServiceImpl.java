package com.wuzx.msg.service.impl;


import com.github.pagehelper.PageInfo;
import com.wuzx.msg.constant.MessageType;
import com.wuzx.msg.constant.UserAnnouncementStateEnum;
import com.wuzx.msg.converter.NotifyInfoConverter;
import com.wuzx.msg.entity.UserMessage;
import com.wuzx.msg.event.NotifyPublishEvent;
import com.wuzx.msg.mapper.UserMessageMapper;
import com.wuzx.msg.pojo.MessageNotifyInfo;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.qo.UserMessageQO;
import com.wuzx.msg.service.UserMessageService;
import com.wuzx.msg.vo.UserMessagePageVO;
import com.yh.mybatis.plus.domain.PageParam;
import com.yh.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Service
@RequiredArgsConstructor
public class UserMessageServiceImpl extends ExtendServiceImpl<UserMessageMapper, UserMessage>
		implements UserMessageService {

	private final ApplicationEventPublisher publisher;
	/**
	 *
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<UserAnnouncementVO> 分页数据
	 */
	@Override
	public PageInfo<UserMessagePageVO> queryPage(PageParam pageParam, UserMessageQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 根据用户ID和公告id初始化一个新的用户公告关联对象
	 *
	 * @param userId      用户ID
	 * @param messageType 消息类型
	 * @return UserAnnouncement
	 */
	@Override
	public UserMessage prodUserAnnouncement(Integer userId, MessageType messageType, String title, String content) {
		UserMessage userMessage = new UserMessage();
		userMessage.setUserId(userId);
		userMessage.setMessageType(messageType.getValue());
		userMessage.setCreateTime(new Date());
		userMessage.setState(UserAnnouncementStateEnum.UNREAD.getValue());
		userMessage.setTitle(title);
		userMessage.setContent(content);
		return userMessage;
	}

	/**
	 * 对用户消息进行已读标记
	 * @param messageId 消息id
	 */
	@Override
	public void readAnnouncement(Long messageId) {
		baseMapper.updateToReadState(messageId);
	}


	@Override
	public List<UserMessage> getUserMessageList(UserMessageQO userMessageQO) {
		return baseMapper.getUserMessageList(userMessageQO);
	}


	@Override
	public void generateUserMessage(MessageNotifyInfo messageNotifyInfo) {
		NotifyInfo notifyInfo = NotifyInfoConverter.INSTANCE.fromMessageNotify(messageNotifyInfo);
		publisher.publishEvent(new NotifyPublishEvent(notifyInfo));
	}
}
