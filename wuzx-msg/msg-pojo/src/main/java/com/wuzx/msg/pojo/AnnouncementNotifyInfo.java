package com.wuzx.msg.pojo;


import com.wuzx.msg.constant.MessageType;
import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 公告通知信息
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Data
@Accessors(chain = true)
@Api(tags = "公告通知信息")
public class AnnouncementNotifyInfo implements NotifyInfo {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "公告ID")
	private Long id;

	/**
	 * 标题
	 */
	@ApiModelProperty(value  = "标题")
	private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty(value  = "内容")
	private String content;

	/**
	 * 接收人筛选方式
	 * @see NotifyRecipientFilterTypeEnum
	 */
	@ApiModelProperty(value  = "接收人筛选方式")
	private Integer recipientFilterType;

	/**
	 * 对应接收人筛选方式的条件信息
	 */
	@ApiModelProperty(value  = "对应接收人筛选方式的条件信息")
	private List<Object> recipientFilterCondition;

	/**
	 * 接收方式，值与通知渠道一一对应
	 * @see NotifyChannelEnum
	 */
	@ApiModelProperty(value  = "接收方式")
	private List<Integer> receiveMode;

	/**
	 * 永久有效的
	 */
	@ApiModelProperty(value  = "永久有效的")
	private Integer immortal;

	/**
	 * 截止日期
	 */
	@ApiModelProperty(value  = "截止日期")
	private Date deadline;


	@Override
	public Integer getMessageType() {
		return MessageType.ANNOUNCEMENT.getValue();
	}
}
