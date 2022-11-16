
package com.wuzx.weixin.qyweixin.sdk.jfinal;

import com.yh.qyweixin.sdk.api.ApiConfigKit;
import com.yh.qyweixin.sdk.kit.MsgEncryptKit;
import com.yh.qyweixin.sdk.kit.SignatureCheckKit;
import com.yh.qyweixin.sdk.msg.InMsgParser;
import com.yh.qyweixin.sdk.msg.in.InImageMsg;
import com.yh.qyweixin.sdk.msg.in.InLinkMsg;
import com.yh.qyweixin.sdk.msg.in.InLocationMsg;
import com.yh.qyweixin.sdk.msg.in.InMsg;
import com.yh.qyweixin.sdk.msg.in.InNotDefinedEvent;
import com.yh.qyweixin.sdk.msg.in.InNotDefinedMsg;
import com.yh.qyweixin.sdk.msg.in.InShortVideoMsg;
import com.yh.qyweixin.sdk.msg.in.InTextMsg;
import com.yh.qyweixin.sdk.msg.in.InVideoMsg;
import com.yh.qyweixin.sdk.msg.in.InVoiceMsg;
import com.yh.qyweixin.sdk.msg.in.event.InEnterAgentEvent;
import com.yh.qyweixin.sdk.msg.in.event.InFollowEvent;
import com.yh.qyweixin.sdk.msg.in.event.InJobEvent;
import com.yh.qyweixin.sdk.msg.in.event.InLocationEvent;
import com.yh.qyweixin.sdk.msg.in.event.InMenuEvent;
import com.yh.qyweixin.sdk.msg.in.event.InQrCodeEvent;
import com.yh.qyweixin.sdk.msg.out.OutMsg;
import com.yh.qyweixin.sdk.msg.out.OutTextMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 接收微信服务器消息，自动解析成 InMsg 并分发到相应的处理方法
 */
@Slf4j
public abstract class MsgController {

	private String inMsgXml = null;		// 本次请求 xml数据
	private InMsg inMsg = null;			// 本次请求 xml 解析后的 InMsg 对象
	
	/**
	 * weixin 公众号服务器调用唯一入口，即在开发者中心输入的 URL 必须要指向此 action
	 */
	public void index() {
		// 开发模式输出微信服务发送过来的  xml 消息
		if (ApiConfigKit.isDevMode()) {
			System.out.println("接收消息:");
			System.out.println(getInMsgXml());
		}
		
		// 解析消息并根据消息类型分发到相应的处理方法
		InMsg msg = getInMsg();
		if (msg instanceof InTextMsg)
			processInTextMsg((InTextMsg) msg);
		else if (msg instanceof InLinkMsg)
			processInLinkMsg((InLinkMsg) msg);
		else if (msg instanceof InImageMsg)
			processInImageMsg((InImageMsg) msg);
		else if (msg instanceof InVoiceMsg)
			processInVoiceMsg((InVoiceMsg) msg);
		else if (msg instanceof InVideoMsg)
			processInVideoMsg((InVideoMsg) msg);
		else if (msg instanceof InShortVideoMsg)   //支持小视频
			processInShortVideoMsg((InShortVideoMsg) msg);
		else if (msg instanceof InLocationMsg)
			processInLocationMsg((InLocationMsg) msg);
		else if (msg instanceof InFollowEvent)
			processInFollowEvent((InFollowEvent) msg);
		else if (msg instanceof InLocationEvent)
			processInLocationEvent((InLocationEvent) msg);
		else if (msg instanceof InMenuEvent)
			processInMenuEvent((InMenuEvent) msg);
		else if (msg instanceof InEnterAgentEvent)
			processInEnterAgentEvent((InEnterAgentEvent) msg);
		else if (msg instanceof InQrCodeEvent)
			processInQrCodeEvent((InQrCodeEvent) msg);
		else if (msg instanceof InJobEvent)
			processInJobEvent((InJobEvent) msg);
		
		else if (msg instanceof InNotDefinedMsg) {
            log.error("未能识别的消息类型。 消息 xml 内容为：\n" + getInMsgXml());
            processIsNotDefinedMsg((InNotDefinedMsg) msg);
        }
		else if (msg instanceof InNotDefinedEvent) {
	            log.error("未能识别的事件类型。 消息 xml 内容为：\n" + getInMsgXml());
	            processIsNotDefinedEvent((InNotDefinedEvent) msg);
	    }
	}
	/**
	 * 在接收到微信服务器的 InMsg 消息后后响应 OutMsg 消息
	 */
	public void render(OutMsg outMsg) {
		 String outMsgXml = outMsg.toXml();
		// 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
		if (ApiConfigKit.isDevMode()) {
			System.out.println("发送消息:");
			System.out.println(outMsgXml);
			System.out.println("--------------------------------------------------------------------------------\n");
		}
		
		// 是否需要加密消息
		if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
			outMsgXml = MsgEncryptKit.encrypt(outMsgXml, "timestamp", "nonce");
		}

	}
	
	public void renderOutTextMsg(String content) {
		OutTextMsg outMsg= new OutTextMsg(getInMsg());
		outMsg.setContent(content);
		render(outMsg);
	}

	public String getInMsgXml() {
		if (inMsgXml == null) {
			inMsgXml = null;
			
			String msg_signature = "msg_signature";
			String timestamp = "timestamp";
			String nonce = "nonce";
			String encrypt = SignatureCheckKit.me.getEncrypt(inMsgXml);
			//签名检测
			if (SignatureCheckKit.me.checkSignatures(msg_signature,timestamp,nonce,encrypt)) {
				// 是否需要解密消息
				if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
					inMsgXml = MsgEncryptKit.decrypt(inMsgXml, timestamp, nonce, msg_signature);
				}else {
					throw new RuntimeException("微信企业号、企业微信必须使用加密模式...");
				}
			}else {
				throw new RuntimeException("签名验证失败，请确定是微信服务器在发送消息过来");
			}
		}
		if (StringUtils.isBlank(inMsgXml)) {
            throw new RuntimeException("请不要在浏览器中请求该连接,调试请在本地做端口映射。参考资料:http://blog.csdn.net/zyw_java/article/details/70341106");
        }
		return inMsgXml;
	}
	

	public InMsg getInMsg() {
		if (inMsg == null)
			inMsg = InMsgParser.parse(getInMsgXml()); 
		return inMsg;
	}
	
	// 处理接收到的文本消息
	protected abstract void processInTextMsg(InTextMsg inTextMsg);
	// 处理接收到的链接消息
	protected abstract void processInLinkMsg(InLinkMsg inLinkMsg);
	
	// 处理接收到的图片消息
	protected abstract void processInImageMsg(InImageMsg inImageMsg);
	
	// 处理接收到的语音消息
	protected abstract void processInVoiceMsg(InVoiceMsg inVoiceMsg);
	
	// 处理接收到的视频消息
	protected abstract void processInVideoMsg(InVideoMsg inVideoMsg);

	// 处理接收到的视频消息
	protected abstract void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg);
	
	// 处理接收到的地址位置消息
	protected abstract void processInLocationMsg(InLocationMsg inLocationMsg);
	
	// 处理接收到的关注/取消关注事件
	protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);
	
	// 处理接收到的上报地理位置事件
	protected abstract void processInLocationEvent(InLocationEvent inLocationEvent);

	// 处理接收到的自定义菜单事件
	protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);
		
	// 处理接收到的扫描带参数二维码事件
	protected abstract void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent);
	
	// 处理接收到的成员进入应用的事件
	protected abstract void processInEnterAgentEvent(InEnterAgentEvent inAgentEvent);
	
	// 处理接收到的异步任务完成事件事件
	protected abstract void processInJobEvent(InJobEvent inJobEvent);
	
	 /**
     * 没有找到对应的消息
     * @param inNotDefinedMsg 没有对应消息
     */
    protected abstract void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg);

    /**
     * 没有找到对应的事件消息
     * @param inNotDefinedEvent 没有对应的事件消息
     */
    protected abstract void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent);

}