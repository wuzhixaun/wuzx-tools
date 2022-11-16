
package com.wuzx.weixin.qyweixin.sdk.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.yh.qyweixin.sdk.kit.ParaMap;


/**
 * menu api
 */
public class MenuApi {
	
	private static String getMenu = "https://qyapi.weixin.qq.com/cgi-bin/menu/get";
	private static String createMenu = "https://qyapi.weixin.qq.com/cgi-bin/menu/create";
	private static String deleteMenu ="https://qyapi.weixin.qq.com/cgi-bin/menu/delete";
	/**
	 * 查询菜单
	 */
	public static ApiResult getMenu(String agentid) {
		ParaMap pm=ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr()).put("agentid", agentid);
		String jsonResult = HttpUtil.get(getMenu,pm.getData());
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 创建菜单
	 */
	public static ApiResult createMenu(String jsonStr,String agentid) {
		String jsonResult = HttpRequest.post(createMenu)
				.header("access_token", AccessTokenApi.getAccessTokenStr())
				.header("agentid", agentid)
				.body(jsonStr)
				.execute().body();

		return new ApiResult(jsonResult);
	}
	/**
	 * 删除菜单
	 */
	public static ApiResult deleteMenu(String agentid){
		ParaMap pm=ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr()).put("agentid", agentid);
		String jsonResult = HttpUtil.get(deleteMenu ,pm.getData());
		return new ApiResult(jsonResult);
	}
}


