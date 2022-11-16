package com.wuzx.msg.converter;


import com.wuzx.msg.entity.UserMessage;
import com.wuzx.msg.vo.UserMessagePageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:18
 */
@Mapper
public interface UserMessageConverter {

	UserMessageConverter INSTANCE = Mappers.getMapper(UserMessageConverter.class);

	/**
	 * PO 转 PageVO
	 * @param userMessage 用户公告表
	 * @return UserAnnouncementPageVO 用户公告表PageVO
	 */
	UserMessagePageVO poToPageVo(UserMessage userMessage);

}
