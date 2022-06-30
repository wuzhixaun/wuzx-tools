package com.wuzx.log.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName ModifyParamMapRequestWrapper.java
 * @Description 修改parameterMap
 * @createTime 2022年06月06日 06:06:00
 */
public class ModifyParamMapRequestWrapper extends HttpServletRequestWrapper {

	private final Map<String, String[]> parameterMap;

	public ModifyParamMapRequestWrapper(HttpServletRequest request, Map<String, String[]> parameterMap) {
		super(request);
		this.parameterMap = parameterMap;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return this.parameterMap;
	}

}