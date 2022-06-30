package com.wuzx.accesslog.handler;

import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;

import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.event.AccessLogEvent;
import com.wuzx.log.model.entity.AccessLog;
import com.wuzx.log.util.IpUtils;
import com.wuzx.log.util.LogUtils;
import com.wuzx.log.util.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AuditProperties.java
 * @Description 访问日志
 * @createTime 2022年06月06日 06:06:00
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAccessLogHandler implements AccessLogHandler<AccessLog> {

    private static final String APPLICATION_JSON = "application/json";


    /**
     * 获取用户id方法
     */
    private final Method userIdMethod;

    /**
     * 生产一个日志
     * @return accessLog
     * @param request 请求信息
     * @param response 响应信息
     * @param time 执行时长
     * @param myThrowable 异常信息
     */
    @Override
    public AccessLog buildLog(HttpServletRequest request, HttpServletResponse response, Long time,
                              Throwable myThrowable) {
        Object matchingPatternAttr = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String matchingPattern = matchingPatternAttr == null ? "" : String.valueOf(matchingPatternAttr);
        // @formatter:off
        String uri = URLUtil.getPath(request.getRequestURI());
        AccessLog accessLog = new AccessLog()
                .setTraceId(MDC.get(LogConstant.TRACE_ID))
                .setUserId(LogUtils.getUserId(userIdMethod))
                .setCreateTime(new Date())
                .setExecutTime(time)
                .setIp(IpUtils.getIpAddr(request))
                .setMethod(request.getMethod())
                .setUserAgent(request.getHeader("user-agent"))
                .setUri(uri)
                .setMatchingPattern(matchingPattern)
                .setErrorMsg(Optional.ofNullable(myThrowable).map(Throwable::getMessage).orElse(""))
                .setHttpStatus(response.getStatus());
        // @formatter:on

        // 参数获取
        String params = getParams(request);
        accessLog.setReqParams(params);

        // 非文件上传请求，记录body，用户改密时不记录body
        // TODO 使用注解控制此次请求是否记录body，更方便个性化定制
        if (!LogUtils.isMultipartContent(request)) {
            accessLog.setReqBody(LogUtils.getRequestBody(request));
        }

        // 只记录响应头为 application/json 的返回数据
        // 后台日志对于分页数据请求，不记录返回值
        if (!uri.endsWith("/page") && !uri.endsWith("/list")
                && !HttpMethod.GET.name().equals(request.getMethod())
                && response.getContentType() != null
                && response.getContentType().contains(APPLICATION_JSON)) {
            accessLog.setResult(LogUtils.getResponseBody(request, response));
        }

        return accessLog;
    }

    /**
     * 获取参数信息
     * @param request 请求信息
     * @return 请求参数
     */
    public String getParams(HttpServletRequest request) {
        String params;
        try {
            Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
            params = JSONUtil.toJsonStr(parameterMap);
        }
        catch (Exception e) {
            params = "记录参数异常";
            log.error("[prodLog]，参数获取序列化异常", e);
        }
        return params;
    }

    /**
     * 记录日志
     * @param accessLog 访问日志
     */
    @Override
    public void saveLog(AccessLog accessLog) {
        Map<String, Object> event = new HashMap<>(16);
        event.put(LogConstant.EVENT_LOG, accessLog);
        SpringUtil.publishEvent(new AccessLogEvent(event));
    }

}
