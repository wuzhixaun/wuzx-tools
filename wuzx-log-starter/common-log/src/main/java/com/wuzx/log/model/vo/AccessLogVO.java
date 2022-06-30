package com.wuzx.log.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 访问日志展示对象
 *
 * @author wuzhixuan
 * @createTime 2022-06-06 06:06:00
 */
@Data
public class AccessLogVO {

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
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

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
     * 请求映射地址
     */
    private String matchingPattern;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 请求参数
     */
    private String reqParams;

    /**
     * 请求body
     */
    private String reqBody;

    /**
     * 响应状态码
     */
    private Integer httpStatus;

    /**
     * 响应信息
     */
    private String result;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 执行时长
     */
    private Long time;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
