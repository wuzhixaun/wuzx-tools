package com.wuzx.msg.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.wuzx.msg.constant.UserAnnouncementStateEnum;
import com.wuzx.msg.converter.UserMessageConverter;
import com.wuzx.msg.entity.UserMessage;
import com.wuzx.msg.qo.UserMessageQO;
import com.wuzx.msg.vo.UserMessagePageVO;
import com.yh.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.yh.mybatis.plus.domain.PageParam;
import com.yh.mybatis.plus.mapper.ExtendMapper;
import com.yh.mybatis.plus.toolkit.WrappersX;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
public interface UserMessageMapper extends ExtendMapper<UserMessage> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return 分页结果数据 PageResult
	 */
	default PageInfo<UserMessagePageVO> queryPage(PageParam pageParam, UserMessageQO qo) {
		IPage<UserMessage> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<UserMessage> wrapperX = WrappersX.lambdaAliasQueryX(UserMessage.class)
				.eqIfPresent(UserMessage::getId, qo.getId())
				.eqIfPresent(UserMessage::getUserId, qo.getUserId())
				.eqIfPresent(UserMessage::getMessageType, qo.getMessageType());
		this.selectPage(page, wrapperX);
		IPage<UserMessagePageVO> voPage = page.convert(UserMessageConverter.INSTANCE::poToPageVo);


		final PageInfo<UserMessagePageVO> pageInfo = PageInfo.of(voPage.getRecords());
		pageInfo.setTotal(voPage.getTotal());
		return pageInfo;
	}

	/**
	 * 更新用户信息至已读状态
	 * @param messageId 消息id
	 */
	default void updateToReadState( Long messageId) {
		LambdaUpdateWrapper<UserMessage> wrapper = Wrappers.<UserMessage>lambdaUpdate()
				.set(UserMessage::getState, UserAnnouncementStateEnum.READ.getValue())
				.set(UserMessage::getReadTime, LocalDateTime.now())
				.eq(UserMessage::getId, messageId);
		this.update(null, wrapper);
	}

	default List<UserMessage> getUserMessageList(UserMessageQO userMessageQO) {
		LambdaQueryWrapperX<UserMessage> wrapper =WrappersX.lambdaQueryX(UserMessage.class)
				.eqIfPresent(UserMessage::getState, userMessageQO.getState())
				.eqIfPresent(UserMessage::getUserId, userMessageQO.getUserId())
				.eqIfPresent(UserMessage::getMessageType,userMessageQO.getMessageType())
				.eqIfPresent(UserMessage::getId, userMessageQO.getId());

		return selectList(wrapper);
	}
}