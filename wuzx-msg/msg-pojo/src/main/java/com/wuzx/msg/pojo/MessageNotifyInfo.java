package com.wuzx.msg.pojo;

import com.wuzx.msg.constant.MessageType;
import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 普通消息
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/11/14 15:32
 **/
@Data
@Accessors(chain = true)
@Api(tags = "普通消息")
public class MessageNotifyInfo implements NotifyInfo {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     * 标题
     */
    @ApiModelProperty(value  = "标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value  = "内容")
    private String content;

    /**
     * 接收人筛选方式
     * @see NotifyRecipientFilterTypeEnum
     */
    @ApiModelProperty(value  = "接收人筛选方式")
    private Integer recipientFilterType;

    /**
     * 对应接收人筛选方式的条件信息
     */
    @ApiModelProperty(value  = "对应接收人筛选方式的条件信息")
    private List<Object> recipientFilterCondition;

    /**
     * 接收方式，值与通知渠道一一对应
     * @see NotifyChannelEnum
     */
    @ApiModelProperty(value  = "接收方式")
    private List<Integer> receiveMode;


    @Override
    public Integer getMessageType() {
        return MessageType.MESSAGE_NOTIFICATION.getValue();
    }


}
