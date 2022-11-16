package com.wuzx.msg.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户公告表 查询对象
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Data
@ApiModel(description = "用户消息查询对象")
public class UserMessageQO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;


    /**
     * userId
     */
    @ApiModelProperty(value = "userId")
    private Long userId;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    private Integer messageType;


    /**
     * 状态，已读(1)|未读(0)
     */
    @ApiModelProperty(value = "状态，已读(1)|未读(0)")
    private Integer state;

}