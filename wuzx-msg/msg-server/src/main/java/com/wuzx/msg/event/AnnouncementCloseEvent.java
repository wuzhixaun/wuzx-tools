package com.wuzx.msg.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 公告关闭事件
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@Getter
@ToString
@AllArgsConstructor
public class AnnouncementCloseEvent {

	/**
	 * ID
	 */
	private final Long id;

}
