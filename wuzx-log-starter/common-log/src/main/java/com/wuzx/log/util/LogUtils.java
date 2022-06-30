package com.wuzx.log.util;


import cn.hutool.core.comparator.CompareUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.Type;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName LogUtils.java
 * @Description LogUtils
 * @createTime 2022年06月06日 06:06:00
 */
@Slf4j
@UtilityClass
public class LogUtils {

	/**
	 * 获取请求体
	 * @param request 请求体
	 * @return requestBody
	 */
	public String getRequestBody(HttpServletRequest request) {
		String body = null;
		if (!request.getMethod().equals(HttpMethod.GET.name())) {
			try {
				BufferedReader reader = request.getReader();
				if (reader != null) {
					body = reader.lines().collect(Collectors.joining(System.lineSeparator()));
				}
			}
			catch (Exception e) {
				log.error("读取请求体异常：", e);
			}
		}
		return body;
	}

	/**
	 * 获取响应体
	 * @param response 响应信息
	 * @return responseBody 响应体
	 */
	public String getResponseBody(HttpServletResponse response) {
		HttpServletRequest request = WebUtils.getRequest();
		return getResponseBody(request, response);
	}

	/**
	 * 获取响应体 防止在 {@link org.springframework.web.context.request.RequestContextHolder}
	 * 设置内容之前或清空内容之后使用，从而导致获取不到响应体的问题
	 * @param request 请求信息
	 * @param response 响应信息
	 * @return responseBody 响应体
	 */
	public String getResponseBody(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (response instanceof ContentCachingResponseWrapper) {
				ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
				// 获取响应体
				byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
				return new String(contentAsByteArray, StandardCharsets.UTF_8);
			}
			log.warn("对于未包装的响应体，默认不进行读取请求体，请求 uri: [{}]", request.getRequestURI());
		}
		catch (Exception exception) {
			log.error("获取响应体信息失败，请求 uri: [{}]", request.getRequestURI());
		}
		return "";
	}

	/**
	 * 判断是否是multipart/form-data请求
	 * @param request 请求信息
	 * @return 是否是multipart/form-data请求
	 */
	public boolean isMultipartContent(HttpServletRequest request) {
		// 获取Content-Type
		String contentType = request.getContentType();
		return (contentType != null) && (contentType.toLowerCase().startsWith("multipart/"));
	}


	/**
	 * 获取操作人id
	 * @param userIdMethod
	 * @return
	 */
	public static Integer getUserId(Method userIdMethod) {
		if (null == userIdMethod) {
			return -1;
		}
		try {
			return (Integer) userIdMethod.invoke(null, null);
		} catch (RuntimeException | IllegalAccessException | InvocationTargetException e) {
			// do nothing.
		}

		return -1;
	}


	/**
	 * 获取操作用户id
	 * @param userIdMethod
	 * @return
	 */
	public static Method getMethod(String userIdMethod) {
		Method clerkIdMethod = null;
		// 指定用于获取当前登录用户ID的方法，#号之前是类的全限定名称，#号之后是静态方法的名称，返回int类型，如不需要记录用户ID可删除此配置项
		if (StringUtils.isBlank(userIdMethod)) {
			return clerkIdMethod;
		}
		try {
			String[] classAndMethodName = userIdMethod.split("#");
			if (classAndMethodName.length == 2) {
				Class clerkClazz = Class.forName(classAndMethodName[0]);
				clerkIdMethod = clerkClazz.getMethod(classAndMethodName[1]);
			}

		} catch (ClassNotFoundException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		return clerkIdMethod;
	}


	/**
	 * 获取不同的值
	 * @param newEntity
	 * @param oldEntity
	 * @param names
	 * @param types
	 * @param newValue
	 * @param oldValue
	 */
	public static void getDiff(Object[] newEntity, Object[] oldEntity, String[] names, Type[] types, Map<String,Object> newValue, Map<String,Object> oldValue) {
		StringBuffer result = new StringBuffer(); //各参数数组均按序传进来的，按index取值即可
		for (int col = 0; col < oldEntity.length; col++) {
			//外键忽略处理
			if (types[col].isAssociationType()) {
				continue;
			}

			// 相等就不处理
			if ((oldEntity[col] == newEntity[col])) {
				continue;
			}

			//如不相等，则加入字符串中
			if (!compare(newEntity[col], oldEntity[col])) {
				oldValue.put(names[col], oldEntity[col]);
				newValue.put(names[col], newEntity[col]);
			}
		}

	}


	/**
	 * 对象值比较
	 * @param newValue
	 * @param oldValue
	 * @return
	 */
	public static boolean compare(Object newValue, Object oldValue) {

		if (newValue instanceof BigDecimal) {
			return CompareUtil.compare((BigDecimal) newValue, (BigDecimal) oldValue) == 0;
		} else if (newValue instanceof Date) {
			return CompareUtil.compare((Date) newValue, (Date) oldValue) == 0;
		}


		if (oldValue != null && !oldValue.equals(newValue)
				|| oldValue == null && newValue != null) {
			return false;
		}

		return true;
	}


	/**
	 * 获取不同的值
	 * @param newEntity
	 * @param names
	 * @param types
	 * @param newValue
	 */
	public static void getDiff(Object[] newEntity,  String[] names, Type[] types, Map<String,Object> newValue) {
		for (int col = 0; col < newEntity.length; col++) {
			//外键忽略处理
			if (types[col].isAssociationType()) {
				continue;
			}

			//如不相等，则加入字符串中
			if (newEntity[col] != null ) {
				newValue.put(names[col], newEntity[col]);
			}
		}

	}

}
