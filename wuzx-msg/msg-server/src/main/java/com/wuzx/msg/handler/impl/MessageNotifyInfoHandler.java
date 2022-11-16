package com.wuzx.msg.handler.impl;

import com.wuzx.msg.constant.MessageType;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.entity.UserMessage;
import com.wuzx.msg.handler.AbstractNotifyInfoHandler;
import com.wuzx.msg.message.AnnouncementPushMessage;
import com.wuzx.msg.message.CommonMessage;
import com.wuzx.msg.pojo.AnnouncementNotifyInfo;
import com.wuzx.msg.pojo.MessageNotifyInfo;
import com.wuzx.msg.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告通知消息处理器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 14:59
 **/
@Component
public class MessageNotifyInfoHandler extends AbstractNotifyInfoHandler<MessageNotifyInfo, CommonMessage> {


    @Autowired
    private UserMessageService userAnnouncementService;
    @Override
    protected void persistMessage(List<User> userList, MessageNotifyInfo announcementNotifyInfo) {
        List<UserMessage> userMessages = new ArrayList<>(userList.size());
        // 向指定用户推送
        for (User sysUser : userList) {
            Integer userId = sysUser.getId();
            UserMessage userMessage = userAnnouncementService.prodUserAnnouncement(userId,
                    MessageType.MESSAGE_NOTIFICATION, announcementNotifyInfo.getTitle(), announcementNotifyInfo.getContent());
            userMessages.add(userMessage);
        }
        userAnnouncementService.saveBatch(userMessages);
    }

    @Override
    protected CommonMessage createMessage(MessageNotifyInfo messageNotifyInfo) {
        CommonMessage message = new CommonMessage();
        message.setId(messageNotifyInfo.getId());
        message.setTitle(messageNotifyInfo.getTitle());
        message.setContent(messageNotifyInfo.getContent());
        return message;
    }
}
