package com.wuzx.log.model.vo;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * @Description 操作日志视图对象
 *
 * @author wuzhixuan
 * @createTime 2022-06-06 06:06:00
 */
@Data
public class OperationLogVO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 追踪ID
     */
    private String traceId;

    /**
     * 日志消息
     */
    private String msg;

    /**
     * 访问IP地址
     */
    private String ip;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 操作状态
     */
    private Integer status;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 执行时长
     */
    private Long time;

    /**
     * 创建者
     */
    private String operator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
