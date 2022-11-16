package com.wuzx.msg.message;


import com.wuzx.msg.server.message.JsonWebSocketMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 公告发布消息
 *
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Getter
@Setter
public class AnnouncementPushMessage extends JsonWebSocketMessage {

	public AnnouncementPushMessage() {
		super("announcement-push");
	}

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 永久有效的
	 * @see
	 */
	private Integer immortal;

	/**
	 * 截止日期
	 */
	private Date deadline;

}
