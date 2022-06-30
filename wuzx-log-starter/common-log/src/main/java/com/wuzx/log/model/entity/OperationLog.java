package com.wuzx.log.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @Description 操作日志
 * @createTime 2022年06月06日 06:06:00
 */
@Data
@Accessors(chain = true)
public class OperationLog {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Integer userId;

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
	 * 操作结果
	 */
	private String result;

	/**
	 * 创建者
	 */
	private String operator;

	/**
	 * 创建时间
	 */
	private Date createTime;

}
