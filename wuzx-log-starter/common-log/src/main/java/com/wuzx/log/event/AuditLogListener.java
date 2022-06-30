package com.wuzx.log.event;


import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.model.entity.AuditLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Description 异步监听审计日志事件
 *
 * @author wuzhixuan
 * @createTime 2022-06-07 15:22:00
 */
@Slf4j
@AllArgsConstructor
public class AuditLogListener {


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
    @EventListener(AuditLogEvent.class)
    public void saveAuditLog(AuditLogEvent event) {
        Map<String, Object> source = (Map<String, Object>) event.getSource();
        List<AuditLog> logs = (List<AuditLog>) source.get(LogConstant.EVENT_LOG);
        saveLog(logs);
    }


    public void saveLog(List<AuditLog> logs) {
        restTemplate.postForObject(saveLogIp + "/log/audit/info", logs, Object.class);
    }
}
