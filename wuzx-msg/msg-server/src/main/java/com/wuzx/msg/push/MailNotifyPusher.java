package com.wuzx.msg.push;

import cn.hutool.core.util.StrUtil;
import com.yh.mail.model.MailDetails;
import com.yh.mail.sender.MailSender;
import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 邮箱推送
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 11:45
 **/
@Component
@RequiredArgsConstructor
public class MailNotifyPusher implements NotifyPusher {

    private final MailSender mailSender;
    /**
     * 当前发布者的推送方式
     * @see NotifyChannelEnum
     * @return 推送方式
     */
    @Override
    public Integer notifyChannel() {
        return NotifyChannelEnum.MAIL.getValue();
    }

    @Override
    public void push(NotifyInfo notifyInfo, List<User> userList) {
        String[] emails = userList.stream().map(User::getEmail).filter(StrUtil::isNotBlank).toArray(String[]::new);
        // 密送群发，不展示其他收件人
        MailDetails mailDetails = new MailDetails();
        mailDetails.setShowHtml(true);
        mailDetails.setSubject(notifyInfo.getTitle());
        mailDetails.setContent(notifyInfo.getContent());
        mailDetails.setBcc(emails);
        mailSender.sendMail(mailDetails);
    }
}
