package com.wuzx.msg.converter;


import com.wuzx.msg.entity.Announcement;
import com.wuzx.msg.message.CommonMessage;
import com.wuzx.msg.pojo.AnnouncementNotifyInfo;
import com.wuzx.msg.pojo.MessageNotifyInfo;
import com.wuzx.msg.pojo.NotifyInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:18
 */
@Mapper
public interface NotifyInfoConverter {

	NotifyInfoConverter INSTANCE = Mappers.getMapper(NotifyInfoConverter.class);

	/**
	 * 公告转通知实体
	 * @param announcement 公告信息
	 * @return 通知信息
	 */
	AnnouncementNotifyInfo fromAnnouncement(Announcement announcement);

	MessageNotifyInfo fromMessageNotify(MessageNotifyInfo messageNotifyInfo);
}
