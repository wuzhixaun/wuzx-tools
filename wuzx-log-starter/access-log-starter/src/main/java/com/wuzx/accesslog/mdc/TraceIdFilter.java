package com.wuzx.accesslog.mdc;



import cn.hutool.core.util.IdUtil;
import com.wuzx.log.constant.LogConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AccessLogAutoConfiguration.java
 * @Description 利用 Slf4J 的 MDC 功能，记录 TraceId
 * @createTime 2022年06月06日 06:06:00
 */
public class TraceIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String traceId = MDC.get(LogConstant.TRACE_ID);

		if (StringUtils.isEmpty(traceId)) {
			traceId = IdUtil.objectId();
			MDC.put(LogConstant.TRACE_ID, traceId);
		}
		try {
			// 响应头中添加 traceId 参数，方便排查问题
			response.setHeader(LogConstant.TRACE_ID, traceId);
			filterChain.doFilter(request, response);
		}
		finally {
			MDC.remove(LogConstant.TRACE_ID);
		}
	}

}
