package com.wuzx.server.exception;


/**
 * 错误的 json 消息
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
public class ErrorJsonMessageException extends RuntimeException {

	public ErrorJsonMessageException(String message) {
		super(message);
	}

}