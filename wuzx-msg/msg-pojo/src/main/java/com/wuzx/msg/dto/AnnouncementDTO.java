package com.wuzx.msg.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuzx.msg.constant.NotifyChannelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 公告信息
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Data
@ApiModel(description = "公告信息")
public class AnnouncementDTO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 接收人筛选方式，1：全部 2：用户角色 3：组织机构 4：用户类型 5：自定义用户
     */
    @ApiModelProperty(value = "接收人范围")
    private Integer recipientFilterType;

    /**
     * 对应接收人筛选方式的条件信息，多个用逗号分割。如角色标识，组织ID，用户类型，用户ID等
     */
    @ApiModelProperty(value = "对应接收人筛选方式的条件信息。如角色标识，组织ID，用户类型，用户ID等")
    private List<Object> recipientFilterCondition;

    /**
     * 接收方式，值与通知渠道一一对应
     *
     * @see NotifyChannelEnum
     */
    @ApiModelProperty(value = "接收方式")
    private List<Integer> receiveMode;

    /**
     * 永久有效的
     *
     * @see
     */
    @ApiModelProperty(value = "永久有效的")
    private Integer immortal;

    /**
     * 截止日期
     */
    @ApiModelProperty(value = "截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deadline;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

}
