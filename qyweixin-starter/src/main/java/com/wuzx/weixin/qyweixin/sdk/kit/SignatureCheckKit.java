
package com.wuzx.weixin.qyweixin.sdk.kit;


import com.yh.qq.weixin.mp.aes.AesException;
import com.yh.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.yh.qyweixin.sdk.api.ApiConfigKit;
import com.yh.qyweixin.sdk.utils.XmlHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Slf4j
public class SignatureCheckKit {
	public static final SignatureCheckKit me = new SignatureCheckKit();

	public boolean checkSignature(String msgSignature, String timestamp, String nonce) {
		String TOKEN = ApiConfigKit.getApiConfig().getToken();
		String array[] = {TOKEN, timestamp, nonce};
		Arrays.sort(array);
		String tempStr = new StringBuilder().append(array[0] + array[1] + array[2]).toString();
//		tempStr = HashKit.sha1(tempStr);
		return tempStr.equalsIgnoreCase(msgSignature);
	}
	public boolean checkSignature(String msgSignature, String timestamp, String nonce,String content) {
		String TOKEN = ApiConfigKit.getApiConfig().getToken();
		String array[] = {TOKEN, timestamp, nonce, content};
		Arrays.sort(array);
		String tempStr = new StringBuilder().append(array[0] + array[1] + array[2] + array[3]).toString();
//		tempStr = HashKit.sha1(tempStr);
		return tempStr.equalsIgnoreCase(msgSignature);
	}
	
	
	public String VerifyURL(String msgSignature, String timeStamp, String nonce, String echoStr){
		String result =null;
		try {
			String token = ApiConfigKit.getApiConfig().getToken();
			String corpId = ApiConfigKit.getApiConfig().getCorpId();
			String encodingAesKey = ApiConfigKit.getApiConfig().getEncodingAesKey();
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token,encodingAesKey,corpId);
			result = wxcpt.verifyUrl(msgSignature, timeStamp, nonce,echoStr);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	/**
	 * 检测签名
	 */
	public boolean checkSignatures(String msgSignature, String timeStamp, String nonce,String encryptMessage) {
		
		if (StringUtils.isBlank(msgSignature) || StringUtils.isBlank(timeStamp) || StringUtils.isBlank(nonce)) {
			return false;
		}
		if (SignatureCheckKit.me.checkSignature(msgSignature, timeStamp, nonce ,encryptMessage)) {
			return true;
		}
		else {
			log.error("check signature failure: " +
					" signature = " + msgSignature +
					" timestamp = " + timeStamp +
					" nonce = " + nonce+
					" content = " + encryptMessage);
			
			return false;
		}
	}
	

	
	public String getEncrypt(String xml){
		XmlHelper xmlHelper = XmlHelper.of(xml);
		String content=xmlHelper.getString("//Encrypt");
		return content;
	}
}



