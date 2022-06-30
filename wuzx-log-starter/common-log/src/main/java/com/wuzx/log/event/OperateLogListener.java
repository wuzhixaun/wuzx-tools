package com.wuzx.log.event;

import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.model.entity.OperationLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description 异步监听操作日志事件
 *
 * @author wuzhixuan
 * @createTime 2022-06-07 17:27:00
 */
@Slf4j
@AllArgsConstructor
public class OperateLogListener {

    /**
     * RestTemplate
     */
    private final RestTemplate restTemplate;

    /**
     * 日志保存ip
     */
    private final String saveLogIp;


    @Async
    @Order
    @EventListener(OperateLogEvent.class)
    public void saveOperateLog(OperateLogEvent event) {
        Map<String, Object> source = (Map<String, Object>) event.getSource();
        OperationLog operationLog = (OperationLog) source.get(LogConstant.EVENT_LOG);
        saveLog(operationLog);
    }

    public void saveLog(OperationLog operationLog) {
        restTemplate.postForObject(saveLogIp + "/log/operat/info", operationLog, Object.class);
    }
}
