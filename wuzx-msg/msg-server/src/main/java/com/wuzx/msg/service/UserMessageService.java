package com.wuzx.msg.service;

import com.github.pagehelper.PageInfo;
import com.wuzx.msg.constant.MessageType;
import com.wuzx.msg.entity.UserMessage;
import com.wuzx.msg.pojo.MessageNotifyInfo;
import com.wuzx.msg.qo.UserMessageQO;
import com.wuzx.msg.vo.UserMessagePageVO;
import com.yh.mybatis.plus.domain.PageParam;
import com.yh.mybatis.plus.service.ExtendService;

import java.util.List;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
public interface UserMessageService extends ExtendService<UserMessage> {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<AnnouncementVO> 分页数据
	 */
	PageInfo<UserMessagePageVO> queryPage(PageParam pageParam, UserMessageQO qo);

	/**
	 * 根据用户ID和公告id初始化一个新的用户公告关联对象
	 *
	 * @param userId      用户ID
	 * @param messageType 消息类型
	 * @return UserAnnouncement
	 */
	UserMessage prodUserAnnouncement(Integer userId, MessageType messageType, String title, String content);

	/**
	 * 对用户消息进行已读标记
	 * @param messageId 消息id
	 */
	void readAnnouncement(Long messageId);

	/**
	 * 获取用户消息列表
	 * @param userMessageQO
	 * @return
	 */
	List<UserMessage> getUserMessageList(UserMessageQO userMessageQO);

	/**
	 * 生成站内消息
	 * @param messageNotifyInfo
	 */
	void generateUserMessage(MessageNotifyInfo messageNotifyInfo);
}