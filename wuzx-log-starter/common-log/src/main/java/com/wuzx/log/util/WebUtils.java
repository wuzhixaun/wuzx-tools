package com.wuzx.log.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName WebUtils.java
 * @Description WebUtils
 * @createTime 2022年06月06日 06:06:00
 */
@Slf4j
@UtilityClass
public class WebUtils extends org.springframework.web.util.WebUtils {

	/**
	 * 获取 ServletRequestAttributes
	 * @return {ServletRequestAttributes}
	 */
	public ServletRequestAttributes getServletRequestAttributes() {
		return (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
	}

	/**
	 * 获取 HttpServletRequest
	 * @return {HttpServletRequest}
	 */
	public HttpServletRequest getRequest() {
		return getServletRequestAttributes().getRequest();
	}

	/**
	 * 获取 HttpServletResponse
	 * @return {HttpServletResponse}
	 */
	public HttpServletResponse getResponse() {
		return getServletRequestAttributes().getResponse();
	}

}
