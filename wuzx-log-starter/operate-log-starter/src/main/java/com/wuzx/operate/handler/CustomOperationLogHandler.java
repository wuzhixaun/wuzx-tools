package com.wuzx.operate.handler;

import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.enums.LogStatusEnum;
import com.wuzx.log.event.OperateLogEvent;
import com.wuzx.log.model.entity.OperationLog;
import com.wuzx.log.util.IpUtils;
import com.wuzx.log.util.LogUtils;
import com.wuzx.log.util.SpringUtil;
import com.wuzx.log.util.WebUtils;
import com.wuzx.operate.annotation.OperationLogging;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/5/25 20:38
 */
@RequiredArgsConstructor
public class CustomOperationLogHandler extends AbstractOperationLogHandler<OperationLog> {

    /**
     * 获取用户id方法
     */
    private final Method userIdMethod;

    @Override
    public OperationLog buildLog(OperationLogging operationLogging, ProceedingJoinPoint joinPoint) {
        // 获取 Request
        HttpServletRequest request = WebUtils.getRequest();

        // @formatter:off
        OperationLog operationLog = new OperationLog()
                .setCreateTime(new Date())
                .setIp(IpUtils.getIpAddr(request))
                .setMethod(request.getMethod())
                .setUserAgent(request.getHeader(HttpHeaders.USER_AGENT))
                .setUri(URLUtil.getPath(request.getRequestURI()))
                .setType(operationLogging.type().getValue())
                .setMsg(operationLogging.msg())
                .setTraceId(MDC.get(LogConstant.TRACE_ID))
                .setUserId(LogUtils.getUserId(userIdMethod));

        // 请求参数
        if (operationLogging.recordParams()) {
            operationLog.setParams(getParams(joinPoint));
        }

        return operationLog;
    }

    @Override
    public OperationLog recordExecutionInfo(OperationLog operationLog, ProceedingJoinPoint joinPoint,
                                            long executionTime, Throwable throwable, boolean isSaveResult, Object result) {
        // 执行时长
        operationLog.setTime(executionTime);
        // 执行状态
        LogStatusEnum logStatusEnum = throwable == null ? LogStatusEnum.SUCCESS : LogStatusEnum.FAIL;
        operationLog.setStatus(logStatusEnum.getValue());
        // 执行结果
        if (isSaveResult) {
            Optional.ofNullable(result).ifPresent(x -> operationLog.setResult(JSONUtil.toJsonStr(result)));
        }
        return operationLog;
    }

    @Override
    public void handleLog(OperationLog operationLog) {
        Map<String, Object> event = new HashMap<>(16);
        event.put(LogConstant.EVENT_LOG, operationLog);
        SpringUtil.publishEvent(new OperateLogEvent(event));
    }

}
