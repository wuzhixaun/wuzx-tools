package com.wuzx.msg.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Data
@ApiModel(description = "用户公告分页VO")
public class UserMessagePageVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型")
    private Integer messageType;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    /**
     * 状态，已读(1)|未读(0)
     */
    @ApiModelProperty(value = "状态，已读(1)|未读(0)")
    private Integer state;

    /**
     * 阅读时间
     */
    @ApiModelProperty(value = "阅读时间")
    private Date readTime;

    /**
     * 拉取时间
     */
    @ApiModelProperty(value = "拉取时间")
    private Date createTime;

}