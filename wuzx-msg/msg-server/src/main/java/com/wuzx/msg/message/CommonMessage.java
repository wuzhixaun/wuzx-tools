package com.wuzx.msg.message;

import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.server.message.JsonWebSocketMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/11/14 15:51
 **/
@Getter
@Setter
public class CommonMessage extends JsonWebSocketMessage {

    public CommonMessage() {
        super("common-msg");
    }

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


}
