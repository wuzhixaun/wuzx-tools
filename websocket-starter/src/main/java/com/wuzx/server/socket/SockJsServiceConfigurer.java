package com.wuzx.server.socket;

import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;

/**
 * SockJsService 配置类
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 15:30
 */
public interface SockJsServiceConfigurer {

	/**
	 * 配置 sockjs 相关
	 * @param sockJsServiceRegistration sockJsService 注册类
	 */
	void config(SockJsServiceRegistration sockJsServiceRegistration);

}
