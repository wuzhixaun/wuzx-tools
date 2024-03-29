/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.wuzx.weixin.qyweixin.sdk.msg.in;

import java.io.Serializable;

/**
 * <pre>
    接收消息，以下是接收文本消息的例子
    接收文本消息
 &lt;xml&gt;
 &lt;ToUserName&gt;&lt;![CDATA[toUser]]&gt;&lt;/ToUserName&gt;
 &lt;FromUserName&gt;&lt;![CDATA[fromUser]]&gt;&lt;/FromUserName&gt;
 &lt;CreateTime&gt;1348831860&lt;/CreateTime&gt;
 &lt;MsgType&gt;&lt;![CDATA[text]]&gt;&lt;/MsgType&gt;
 &lt;Content&gt;&lt;![CDATA[this is a test]]&gt;&lt;/Content&gt;
 &lt;MsgId&gt;1234567890123456&lt;/MsgId&gt;
  &lt;AgentID&gt;1&lt;/AgentID&gt;
 &lt;/xml&gt;
 </pre>
 */
@SuppressWarnings("serial")
public abstract class InMsg implements Serializable {

	// 企业号CorpID
	protected String toUserName;
	
	// 成员UserID
	protected String fromUserName;
	
	// 消息创建时间 （整型）
	protected Integer createTime;
	
	
	/**
	 * 消息类型
	 * 1：text 文本消息
	 * 2：image 图片消息
	 * 3：voice 语音消息
	 * 4：video 视频消息
	 * 5：location 地址位置消息
	 * 6：link 链接消息
	 * 7：event 事件
	 */
	protected String msgType;
	//企业应用的id，整型。可在应用的设置页面查看
	protected String agentId;
	
	public InMsg(String toUserName, String fromUserName, Integer createTime, String msgType,String agentId) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.agentId = agentId;
	}
	
	public String getToUserName() {
		return toUserName;
	}
	
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	public String getFromUserName() {
		return fromUserName;
	}
	
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public Integer getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	
	public String getMsgType() {
		return msgType;
	}
	
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getAgentID() {
		return agentId;
	}

	public void setAgentID(String agentID) {
		this.agentId = agentID;
	}
}
