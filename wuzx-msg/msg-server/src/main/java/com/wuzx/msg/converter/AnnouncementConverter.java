package com.wuzx.msg.converter;

import com.wuzx.msg.dto.AnnouncementDTO;
import com.wuzx.msg.entity.Announcement;
import com.wuzx.msg.vo.AnnouncementPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:18
 */
@Mapper
public interface AnnouncementConverter {

	AnnouncementConverter INSTANCE = Mappers.getMapper(AnnouncementConverter.class);

	/**
	 * PO 转 PageVO
	 * @param announcement 公告表
	 * @return AnnouncementPageVO 公告表PageVO
	 */
	AnnouncementPageVO poToPageVo(Announcement announcement);

	/**
	 * AnnouncementDTO 转 Announcement实体
	 * @param dto AnnouncementDTO
	 * @return Announcement
	 */
	@Mapping(target = "updateTime", ignore = true)
	@Mapping(target = "createTime", ignore = true)
	@Mapping(target = "createBy", ignore = true)
	Announcement dtoToPo(AnnouncementDTO dto);

}
