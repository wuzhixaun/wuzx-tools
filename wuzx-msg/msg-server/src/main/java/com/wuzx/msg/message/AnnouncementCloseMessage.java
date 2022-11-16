package com.wuzx.msg.message;

import com.wuzx.msg.server.message.JsonWebSocketMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hccake 2021/1/7
 * @version 1.0
 */
@Getter
@Setter
public class AnnouncementCloseMessage extends JsonWebSocketMessage {

	public AnnouncementCloseMessage() {
		super("announcement-close");
	}

	/**
	 * ID
	 */
	private Long id;

}
