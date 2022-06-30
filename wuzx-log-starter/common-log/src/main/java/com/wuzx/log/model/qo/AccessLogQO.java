package com.wuzx.log.model.qo;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName ModifyParamMapRequestWrapper.java
 * @Description 后台访问日志查询对象
 * @createTime 2022年06月06日 06:06:00
 */
@Data
public class AccessLogQO {

    private static final long serialVersionUID = 1L;

    /**
     * 追踪ID
     */
    private String traceId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 访问IP地址
     */
    private String ip;

    /**
     * 请求Uri
     */
    private String uri;

    /**
     * 请求映射地址
     */
    private String matchingPattern;

    /**
     * 响应状态码
     */
    private Integer httpStatus;

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


    private int pageNo;

    private int pageSize;

}
