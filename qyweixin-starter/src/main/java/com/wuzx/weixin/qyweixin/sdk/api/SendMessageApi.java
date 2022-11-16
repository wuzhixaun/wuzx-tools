/**
 * Copyright (c) 2011-2015, Javen  (javenlife@126.com).
 *
 * Licensed under the Apache License, Version 1.0 (the "License");
 */
package com.wuzx.weixin.qyweixin.sdk.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yh.qyweixin.sdk.msg.send.QiYeFileMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeImageMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeMpNewsMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeNewsMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeTextMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeVideoMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeVoiceMsg;

/**
 * @author Javen
 * 2015年12月12日
 * 发送消息接口
 * Https请求方式: POST
  https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN
 * 
 */
public class SendMessageApi {
	
	private static final String url="https://qyapi.weixin.qq.com/cgi-bin/message/send";
	
	/**
	 * 文本消息
	 * @param text
	 * @return
	 */
	public static ApiResult sendTextMsg(QiYeTextMsg text) {
		String jsonStr = JSONUtil.toJsonStr(text);
		return sendMessage(jsonStr);
	}
	
	/**
	 * 图片消息
	 * @param image
	 * @return
	 */
	public static ApiResult sendImageMsg(QiYeImageMsg image) {
		String jsonStr = JSONUtil.toJsonStr(image);
		return sendMessage(jsonStr);
	}
	
	/**
	 * 语音消息
	 * @param voice
	 * @return
	 */
	public static ApiResult sendVoiceMsg(QiYeVoiceMsg voice) {
		String jsonStr = JSONUtil.toJsonStr(voice);
		return sendMessage(jsonStr);
	}
	
	/**
	 * 视频消息
	 * @param video
	 * @return
	 */
	public static ApiResult sendVideoMsg(QiYeVideoMsg video) {
		String jsonStr = JSONUtil.toJsonStr(video);
		return sendMessage(jsonStr);
	}
	
	/**
	 * （图文混排的消息
	 * @param news
	 * @return
	 */
	public static ApiResult sendNewsMsg(QiYeNewsMsg news) {
		String jsonStr = JSONUtil.toJsonStr(news);
		return sendMessage(jsonStr);
	}
	
	/**
	 * 多图文混排的消息
	 * @param mpNews
	 * @return
	 */
	public static ApiResult sendMpNewsMsg(QiYeMpNewsMsg mpNews) {
		String jsonStr = JSONUtil.toJsonStr(mpNews);
		return sendMessage(jsonStr);
	}
	

	/**
	 * 文件消息
	 * @param file
	 * @return
	 */
	public static ApiResult sendFileMsg(QiYeFileMsg file) {
		String jsonStr = JSONUtil.toJsonStr(file);
		return sendMessage(jsonStr);
	}
	
	public static ApiResult sendMessage(String jsonStr) {
		final HttpRequest request = HttpRequest.post(url + "?access_token=" + AccessTokenApi.getAccessTokenStr())
				.body(jsonStr);
		return new ApiResult(request.execute().body());

	}
	
	
}
