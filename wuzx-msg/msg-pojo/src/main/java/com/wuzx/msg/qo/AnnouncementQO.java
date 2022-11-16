package com.wuzx.msg.qo;

import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告信息 查询对象
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 16:16
 */
@Data
@ApiModel(description = "公告信息查询对象")
public class AnnouncementQO  {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 接收人筛选方式
     *
     * @see NotifyRecipientFilterTypeEnum
     */
    @ApiModelProperty(value = "接收人筛选方式")
    private Integer recipientFilterType;

    @ApiModelProperty(value = "状态")
    private Integer[] status;

}