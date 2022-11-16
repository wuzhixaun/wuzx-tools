package com.wuzx.msg.push;

import cn.hutool.core.util.StrUtil;
import com.wuzx.msg.constant.NotifyChannelEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.utils.HtmlUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 短信通知发布
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:34
 */
public class SmsNotifyPusher implements NotifyPusher {

	/**
	 * 当前发布者对应的接收方式
	 * @see NotifyChannelEnum
	 * @return 推送方式
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.SMS.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<User> userList) {
		List<String> phoneList = userList.stream().map(User::getPhone).filter(StrUtil::isNotBlank)
				.collect(Collectors.toList());
		// 短信文本去除 html 标签
		String content = HtmlUtils.toText(notifyInfo.getContent());
		// TODO 对接短信发送平台
		System.out.println("短信推送");
	}

}
