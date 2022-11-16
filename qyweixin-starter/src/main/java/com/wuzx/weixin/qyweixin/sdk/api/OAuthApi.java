/**
 * Copyright (c) 2011-2015, Javen  (javenlife@126.com).
 *
 * Licensed under the Apache License, Version 1.0 (the "License");
 */
package com.wuzx.weixin.qyweixin.sdk.api;

import cn.hutool.json.JSONUtil;
import com.yh.qyweixin.sdk.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author Javen
 * 2015年12月27日
 */
public class OAuthApi {
	private static String getCodeUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
	private static String getUserInfoUrl="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=";
	/**
	 * userid转换成openid接口
	 */
	private static String toOpenIdUrl="https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=";
	/**
	 * openid转换成userid接口
	 */
	private static String toUserIdUrl="https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=";
	
	private static String getuserdetailURl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=";
	/**
	 * 获取企业授权codeUrl
	 * @param redirectUri
	 * @param state
	 * @return
	 */
	public static String getAuthorizeURL(String redirectUri,String corpId,String state,String agentId,boolean isUserInfo){
		String scope = "snsapi_base";
		if (isUserInfo) {
			scope =  "snsapi_privateinfo";
		}
		
		StringBuffer sbf = new StringBuffer();
		sbf.append(getCodeUrl).append(corpId)
		.append("&redirect_uri=").append(redirectUri)
		.append("&response_type=code")
		.append("&agentid=").append(agentId)
		.append("&scope=").append(scope);
		if (StringUtils.isNotBlank(state)) {
			sbf.append("&state=").append(state);
		}
		sbf.append("#wechat_redirect");
		
		return sbf.toString();
	}
	/**
	 * 根据code获取成员信息
	 * @param code
	 * @return
	 */
	public static ApiResult getUserInfoByCode(String code){
		
		String jsonResult = HttpUtils.get(getUserInfoUrl + AccessTokenApi.getAccessTokenStr() + "&code="+code);
		return new ApiResult(jsonResult);
	}
	
	
	/**
	 * 根据user_ticket获取成员详情
	 * @param user_ticket 
	 * @return
	 */
	public static ApiResult getUserInfo(String user_ticket){
		String jsonResult = HttpUtils.post(getuserdetailURl + AccessTokenApi.getAccessTokenStr(),user_ticket);
		return new ApiResult(jsonResult);
	}
	
	/***
	 *  userid转换成openid
	 * @param userid
	 * @param agentid
	 * @return
	 */
	public static ApiResult ToOpenId(String userid,String agentid){

		HashMap<String, String> body = new HashMap<>();
		body.put("userid", userid);
		body.put("agentid", agentid);

		String jsonResult=HttpUtils.post(toOpenIdUrl + AccessTokenApi.getAccessTokenStr(), JSONUtil.toJsonStr(body));
		return new ApiResult(jsonResult);
	}

	/**
	 * openid转换成userid
	 * @param openid
	 * @return
	 */
	public static ApiResult ToUserId(String openid){

		HashMap<String, String> body = new HashMap<>();
		body.put("openid", openid);
		String jsonResult=HttpUtils.post(toUserIdUrl + AccessTokenApi.getAccessTokenStr(),  JSONUtil.toJsonStr(body));
		return new ApiResult(jsonResult);
	}

}
