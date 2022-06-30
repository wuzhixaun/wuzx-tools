package com.wuzx.log.model.qo;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;


/**
 * @Description 操作日志查询对象
 *
 * @author wuzhixuan
 * @createTime 2022-06-06 06:06:00
 */
@Data
public class OperationLogQO {

    /**
     * 追踪ID
     */
    private String traceId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 日志消息
     */
    private String msg;

    /**
     * 访问IP地址
     */
    private String ip;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 操作状态
     */
    private Integer status;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 登陆时间区间（开始时间）
     */
    @DateTimeFormat(pattern = NORM_DATETIME_PATTERN)
    private LocalDateTime startTime;

    /**
     * 登陆时间区间（结束时间）
     */
    @DateTimeFormat(pattern = NORM_DATETIME_PATTERN)
    private LocalDateTime endTime;

}
