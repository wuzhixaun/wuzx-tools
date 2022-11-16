package com.wuzx.msg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yh.mybatis.plus.alias.TableAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 16:15
 */
@Data
@TableAlias(UserMessage.TABLE_ALIAS)
@TableName("notify_user_message")
@Schema(title = "用户消息")
@ApiModel(description = "用户个人消息")
public class UserMessage {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_ALIAS = "ua";

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
     * 标题
     */
    @Schema(title = "标题")
    private String title;

    /**
     * 内容
     */
    @Schema(title = "内容")
    private String content;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "阅读时间")
    private Date readTime;

    /**
     * 拉取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "拉取时间")
    private Date createTime;

}
