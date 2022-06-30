package com.wuzx.log.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @Description 审计日志
 * @createTime 2022年06月06日 06:06:00
 */
@Data
@Accessors(chain = true)
public class AuditLog {

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
     * 用户名称
     */
    private String userName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 操作的表记录id
     */
    private Object tableId;

    /**
     * 修改之后的值
     */
    private String newValue;

    /**
     * 修改之前的值
     */
    private String oldValue;

    /**
     * 操作
     */
    private String operation;


    /**
     * 创建时间
     */
    private Date createTime;



}
