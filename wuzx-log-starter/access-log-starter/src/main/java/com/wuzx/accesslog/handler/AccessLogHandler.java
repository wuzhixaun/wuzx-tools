package com.wuzx.accesslog.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AuditProperties.java
 * @Description 访问日志处理器
 * @createTime 2022年06月06日 06:06:00
 */
public interface AccessLogHandler<T> {

	/**
	 * 记录日志
	 * @param request 请求信息
	 * @param response 响应信息
	 * @param executionTime 执行时长
	 * @param throwable 异常
	 */
	default void handleLog(HttpServletRequest request, HttpServletResponse response, Long executionTime,
			Throwable throwable) {
		T log = buildLog(request, response, executionTime, throwable);
		saveLog(log);
	}

	/**
	 * 生产一个日志
	 * @return accessLog
	 * @param request 请求信息
	 * @param response 响应信息
	 * @param executionTime 执行时长
	 * @param throwable 异常
	 */
	T buildLog(HttpServletRequest request, HttpServletResponse response, Long executionTime, Throwable throwable);

	/**
	 * 保存日志 落库/或输出到文件等
	 * @param accessLog 访问日志
	 */
	void saveLog(T accessLog);

}
