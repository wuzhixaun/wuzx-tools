package com.wuzx.log.event;

import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.model.entity.AccessLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description 异步监听访问日志事件
 *
 * @author wuzhixuan
 * @createTime 2022-06-07 15:22:00
 */
@Slf4j
@AllArgsConstructor
public class AccessLogListener {


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
    @EventListener(AccessLogEvent.class)
    public void saveAccessLog(AccessLogEvent event) {
        Map<String, Object> source = (Map<String, Object>) event.getSource();
        AccessLog logApi = (AccessLog) source.get(LogConstant.EVENT_LOG);
        saveLog(logApi);
    }


    public void saveLog( AccessLog logApi) {
        restTemplate.postForObject(saveLogIp + "/log/access/info", logApi, Object.class);
    }
}
