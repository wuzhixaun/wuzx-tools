package com.wuzx.weixin.test;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yh.qyweixin.sdk.api.AgentApi;
import com.yh.qyweixin.sdk.api.ApiConfig;
import com.yh.qyweixin.sdk.api.ApiConfigKit;
import com.yh.qyweixin.sdk.api.ApiResult;
import com.yh.qyweixin.sdk.api.ChatApi;
import com.yh.qyweixin.sdk.api.ChatApi.ChatUrl;
import com.yh.qyweixin.sdk.api.ConBatchApi;
import com.yh.qyweixin.sdk.api.ConDepartmentApi;
import com.yh.qyweixin.sdk.api.ConTagApi;
import com.yh.qyweixin.sdk.api.ConUserApi;
import com.yh.qyweixin.sdk.api.OAuthApi;
import com.yh.qyweixin.sdk.api.SendMessageApi;
import com.yh.qyweixin.sdk.api.media.MediaApi;
import com.yh.qyweixin.sdk.api.media.MediaApi.MediaType;
import com.yh.qyweixin.sdk.api.media.MediaFile;
import com.yh.qyweixin.sdk.menu.MenuManager;
import com.yh.qyweixin.sdk.msg.chat.ChatReceiver;
import com.yh.qyweixin.sdk.msg.chat.ChatReceiver.ChatType;
import com.yh.qyweixin.sdk.msg.chat.TextChat;
import com.yh.qyweixin.sdk.msg.chat.TextChatMsg;
import com.yh.qyweixin.sdk.msg.send.Article;
import com.yh.qyweixin.sdk.msg.send.News;
import com.yh.qyweixin.sdk.msg.send.QiYeFileMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeImageMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeNewsMsg;
import com.yh.qyweixin.sdk.msg.send.QiYeTextMsg;
import com.yh.qyweixin.sdk.msg.send.Text;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class QyWeixinApiController {

	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
	 * ApiConfig 属性值
	 */

	@Before
	public void getApiConfig() {
		ApiConfig ac = new ApiConfig();
		ac.setAgentId("1000087");
		// 配置微信 API 相关常量
		ac.setCorpId("ww8db3ee8099f5fd73");
		ac.setCorpSecret("kkZWkMzUuAN6HEjWtIZh2XgKOZ6IKAmqFhxudKqOxmk");

		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage( false);
		ac.setEncodingAesKey("setting it in config file");
		ApiConfigKit.putApiConfig(ac);
	}

	/**
	 * 发送文本消息
	 */
	@Test
	public void sendTextMssage() {

		QiYeTextMsg text = new QiYeTextMsg();
		text.setAgentid("1000087");
		text.setText(new Text("测试消息"));
		text.setSafe("1");
		text.setTouser("wuzhixuan");
		ApiResult sendTextMsg = SendMessageApi.sendTextMsg(text);
		System.out.println(sendTextMsg);
	}
	/**
	 * 图文混排的消息
	 */
	@Test
	public void sendNewsMessage() {
		QiYeNewsMsg qiYeNewsMsg = new QiYeNewsMsg();
		qiYeNewsMsg.setAgentid("1000087");
		qiYeNewsMsg.setArticleCount(1);
		qiYeNewsMsg.setSafe("0");
		qiYeNewsMsg.setTouser("@all");

		News news = new News();
		List<Article> articles = new ArrayList<Article>();
		Article article = new Article();
		article.setTitle("刘祥凡叼毛码");
		article.setDescription("刘祥凡叼毛码");
		article.setPicurl(
				"https://mmbiz.qlogo.cn/mmbiz/ibHRiaZ9MRcUpjHhhNQzCl9zGicPBWibh1ndW6Mj27ibCREGGVa9mag0iatwDJ1fSPhsib2LiaBVVenAU8ibqW1kGeka9HQ/0?wx_fmt=png");
		article.setUrl(
				"http://mp.weixin.qq.com/s?__biz=MzA4MDA2OTA0Mg==&mid=400919708&idx=1&sn=c35cf7fe2c77f19f4c3edcdb9607925f#rd");
		articles.add(article);
		news.setArticles(articles);
		qiYeNewsMsg.setNews(news);

		ApiResult sendTextMsg = SendMessageApi.sendNewsMsg(qiYeNewsMsg);
		System.out.println(sendTextMsg);
	}

	/**
	 * 发送图片
	 */
	@Test
	public void sendImage() {
		QiYeImageMsg image = new QiYeImageMsg();
		image.setAgentid("1000087");
		image.setSafe("0");
		image.setTouser("wuzhixuan");
		image.setMedia_id("1dpf5yPTQLfUF3GoT4N43ktZ5d9ulkgURwTE-GkrqHTix7gco2B8dhU1MZGd5IyqY");
		ApiResult apiResult = SendMessageApi.sendImageMsg(image);
		System.out.println(apiResult);
	}

	/**
	 * 发送文件
	 */
	public void sendFile() {
		QiYeFileMsg file = new QiYeFileMsg();
		file.setAgentid("1000087");
		file.setMedia_id("1g45y7tvRx9dyk3jnaiMl5XR48dBcrPkl3SxfNJYC4mf3AYb6yLqs_dG1mK1mXVEzQ5zOprkWoF01x2uP290E2g");
		file.setSafe("0");
		file.setTouser("@all");
		ApiResult apiResult = SendMessageApi.sendFileMsg(file);
	}

	/**
	 * 获取菜单
	 */

	/**
	 * 删除菜单
	 */

	/**
	 * 创建菜单
	 */
	public void creatMenuApi() {
		String json = JSONUtil.toJsonStr(MenuManager.getMenu());
		log.error(json);
		System.out.println(json);
	}

	/**
	 * 获取指定部分列表
	 * {"errcode":0,"errmsg":"ok","department":[{"id":1,"name":"企业号体验34797078",
	 * "parentid":0,"order":200},{"id":2,"name":"研发","parentid":1,"order":200}]}
	 */
	public void getDepartment() {
		ApiResult apiResult = ConDepartmentApi.getDepartment("1");
	}

	/**
	 * 创建部门
	 */
	public void createDepartment() {
		String data = "{\"name\": \"XX研发中心\",\"" + "parentid\": \"1\",\"" + "order\": \"2\",\"" + "id\": \"3\"}";
		ApiResult apiResult = ConDepartmentApi.createDepartment(data);

		log.error(data);
	}

	/**
	 * 更新部门
	 */
	public void updateDepartment() {
		String data = "{\"name\": \"北京研发中心\",\"" + "parentid\": \"1\",\"" + "order\": \"1\",\"" + "id\": \"5\"}";
		ApiResult apiResult = ConDepartmentApi.updateDepartment(data);

		log.error(data);
	}

	/**
	 * 删除部门
	 */
	public void deleteDepartment() {
		ApiResult apiResult = ConDepartmentApi.deleteDepartment("6");
	}

	/**
	 * 获取成员
	 */
	public void getUser() {
		ApiResult apiResult = ConUserApi.getUser("Javen68");
	}

	/**
	 * 创建成员
	 */
	public void createUser() {
		String json = "{\"userid\": \"Javen205\"," + "\"name\": \"Javen205\"," + "\"department\": [3],"
				+ "\"position\": \"产品经理\"," + "\"mobile\": \"\"," + "\"gender\": \"1\"," + "\"email\": \"\","
				+ "\"weixinid\": \"Javen205\","
				// +"\"avatar_mediaid\":
				// \"2-G6nrLmr5EC3MNb_-zL1dDdzkd0p7cNliYu9V5w7o8K0\","
				+ "\"extattr\": {\"attrs\":[{\"name\":\"爱好\",\"value\":\"旅游\"},{\"name\":\"卡号\",\"value\":\"1234567234\"}]}}";
		System.out.println(json);
		ApiResult apiResult = ConUserApi.createUser(json);
	}

	/**
	 * 更新成员
	 */
	public void updateUser() {
		String json = "{\"userid\": \"Javen205\"," + "\"name\": \"Javen205_test\"," + "\"department\": [3],"
				+ "\"position\": \"产品经理\"," + "\"mobile\": \"\"," + "\"gender\": \"1\","
				+ "\"email\": \"342796937@qq.com\"," + "\"weixinid\": \"Javen205\","
				// +"\"avatar_mediaid\":
				// \"2-G6nrLmr5EC3MNb_-zL1dDdzkd0p7cNliYu9V5w7o8K0\","
				+ "\"extattr\": {\"attrs\":[{\"name\":\"爱好\",\"value\":\"旅游\"},{\"name\":\"卡号\",\"value\":\"1234567234\"}]}}";
		System.out.println(json);
		ApiResult apiResult = ConUserApi.updateUser(json);
	}

	/**
	 * 删除成员
	 */
	public void deleteUser() {
		ApiResult apiResult = ConUserApi.deleteUser("lisi");
	}

	/**
	 * 批量删除成员
	 */
	public void batchDeleteUser() {
		String data = "{" + "\"useridlist\": [\"zhangsan\", \"lisi\"]\"}";
		ApiResult apiResult = ConUserApi.batchDeleteUser(data);
	}

	/**
	 * 获取部门成员
	 */
	@Test
	public void getDepartmentUserSimpleList() {
		ApiResult apiResult = ConUserApi.getDepartmentUserSimpleList("1", "1", "0");
		System.out.println(apiResult);
	}

	/**
	 * 获取部门成员(详情)
	 */
	public void getDepartmentUserList() {
		ApiResult apiResult = ConUserApi.getDepartmentUserList("1", "1", "0");
		System.out.println(apiResult.getJson());
		if (apiResult.isSucceed()) {
			JSONObject object = JSON.parseObject(apiResult.getJson());
			JSONArray jsonArray = object.getJSONArray("userlist");
			if (null != jsonArray && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject userObject = jsonArray.getJSONObject(i);
//					User user = JSON.toJavaObject(userObject, User.class);
//					System.out.println(user.toString());
				}
			}

		}

	}

	/**
	 * 邀请成员关注 测试貌似只能使用邮箱要求
	 */
	public void inviteUser() {
		String data = "{\"userid\":\"Javen205\"}";
		System.out.println(data);
		ApiResult apiResult = ConUserApi.inviteUser(data);
	}

	/**
	 * 创建标签
	 */
	public void createTag() {
		String data = "{\"tagname\": \"UI\",\"tagid\": id}";
		System.out.println(data);
		ApiResult apiResult = ConTagApi.createTag(data);
	}

	/**
	 * 更新标签名字
	 */
	public void updateTag() {
		String data = "{\"tagname\": \"UI-test\",\"tagid\": 1}";
		System.out.println(data);
		ApiResult apiResult = ConTagApi.updateTag(data);
	}

	/**
	 * 删除标签
	 */
	public void deleteTag() {
		ApiResult apiResult = ConTagApi.deleteTag("1");
	}

	/**
	 * 获取标签成员
	 */
	public void getTagUser() {
		ApiResult apiResult = ConTagApi.getTagUser("1");
	}

	/**
	 * 增加标签成员
	 */
	public void addTagUsers() {
		String data = "{\"tagid\": \"1\"," + "\"userlist\":[ \"Javen\",\"lisi\"]," + "\"partylist\": []}";
		System.out.println(data);
		ApiResult apiResult = ConTagApi.addTagUsers(data);
	}

	/**
	 * 删除标签成员
	 */
	public void delTagUser() {
		String data = "{\"tagid\": \"1\"," + "\"userlist\":[ \"Javen\",\"lisi\"]," + "\"partylist\": []}";
		System.out.println(data);
		ApiResult apiResult = ConTagApi.deleteTagUsers(data);
	}

	/**
	 * 获取标签列表
	 */
	public void getTagList() {
		ApiResult apiResult = ConTagApi.getTagList();
	}

	/**
	 * 邀请成员关注
	 */
	public void inviteUsers() {
		String data = "{" + "\"touser\":\"lisi|Javen\"," + "\"toparty\":\"3\"," + "\"totag\":\"1\"," + "\"callback\":"
				+ "{" + " 	\"url\": \"http://javen.ngrok.natapp.cn/qymsg\"," + " 	\"token\": \"Javen\","
				+ " 	\"encodingaeskey\": \"sPqS4op3rKjOT7XbWJkDr5Kqq6v6oL3enZ8oY6hrK8b\"" + "}" + "	}";
		System.out.println(data);
		ApiResult apiResult = ConBatchApi.inviteUsers(data);
	}

	/**
	 * 获取异步任务结果
	 */
	public void batchGetResult() {
		ApiResult apiResult = ConBatchApi.batchGetResult("DJuuczAtDK_5ryoQ0_e8YwzG-IosiijUjVYk3EjAuhQ");
	}

	public void uploadFile() {
		ApiResult apiResult = MediaApi.uploadMedia(MediaType.FILE,
				new File("/Users/Javen/Documents/batch_user_sample.csv"));
	}

	/**
	 * 上传临时素材
	 * {"errcode":0,"errmsg":"ok","type":"image","media_id":
	 * "1dpf5yPTQLfUF3GoT4N43ktZ5d9ulkgURwTE-GkrqHTix7gco2B8dhU1MZGd5IyqY",
	 * "created_at":"1500694345"}
	 */
	public void uploadImage() {
		ApiResult apiResult = MediaApi.uploadMedia(MediaType.IMAGE, new File("E:\\Users\\admin\\Desktop\\v2-r.jpg"));
		String json = apiResult.getJson();
		ApiResult sendImageMsg = null;
		if (apiResult.isSucceed()) {
			String mediaId = JSON.parseObject(json).getString("media_id");
			QiYeImageMsg image = new QiYeImageMsg(mediaId);
			image.setAgentid("16");
			image.setSafe("0");
			image.setTouser("wuzhixuan");
			sendImageMsg = SendMessageApi.sendImageMsg(image);
		}

	}

	/**
	 * 获取临时素材
	 */
	public void getMedia() {
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			MediaFile media = MediaApi.getMedia("1dpf5yPTQLfUF3GoT4N43ktZ5d9ulkgURwTE-GkrqHTix7gco2B8dhU1MZGd5IyqY");
			BufferedInputStream fileStream = media.getFileStream();
			fileOutputStream = new FileOutputStream("/Users/Javen/Documents/pic/test/" + media.getFullName());
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			int bytesRead = 0;
			byte[] buff = new byte[1024];
			while (-1 != (bytesRead = fileStream.read(buff, 0, buff.length))) {
				bufferedOutputStream.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedOutputStream.flush();
				fileOutputStream.close();
				bufferedOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 上传永久素材
	 */
	public void addMaterial() {
		ApiResult apiResult = MediaApi.addMaterial(new File("/Users/Javen/Documents/pic/11.jpeg"), "16",
				MediaType.IMAGE);
	}

	public void getMediaDate() {
		try {
			MediaFile mediaFile = MediaApi.getMedia(
					"1jwHxr9qU40IcyOMPoeQDtBHa9pvqJZGofzUhA1Yhd2AWxRCjrgyGZjNSKwZvKsqSDWeoWmNF3tkc05aBbXVmsg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getMaterialCount() {
		ApiResult apiResult = MediaApi.getMaterialCount("22");
	}

	public void batchGetMaterial() {
		ApiResult apiResult = MediaApi.batchGetMaterial(MediaType.IMAGE, 0, 20, 22);
	}

	public void updateSyncUser() {
		String data = "{"
				+ "\"media_id\":\"1g45y7tvRx9dyk3jnaiMl5XR48dBcrPkl3SxfNJYC4mf3AYb6yLqs_dG1mK1mXVEzQ5zOprkWoF01x2uP290E2g\","
				+ "\"callback\":" + "{" + " 	\"url\": \"http://javen.ngrok.natapp.cn/qymsg\","
				+ " 	\"token\": \"Javen\"," + " 	\"encodingaeskey\": \"sPqS4op3rKjOT7XbWJkDr5Kqq6v6oL3enZ8oY6hrK8b\""
				+ "}" + "	}";
		System.out.println(data);
		ApiResult apiResult = ConBatchApi.updateSyncUser(data);
	}

	/**
	 * 获取企业号应用
	 */
	public void getAgent() {
		ApiResult apiResult = AgentApi.getAgent("22");
	}

	/**
	 * 设置企业号应用
	 */
	public void setAgent() {
		String data = "{" + "\"agentid\": \"22\"," + "\"report_location_flag\": \"1\"," +
				// "\"logo_mediaid\": \"xxxxx\","+
				"\"name\": \"智慧云端日记\"," + "\"description\": \"企业号测试应用\","
				+ "\"redirect_domain\": \"javen.ngrok.natapp.cn\"," + "\"isreportuser\":1," + "\"isreportenter\":1}";
		System.out.println(data);
		ApiResult apiResult = AgentApi.setAgent(data);
	}

	/**
	 * 获取应用概况列表
	 */
	public void getListAgent() {
		ApiResult apiResult = AgentApi.getListAgent();
	}

	/**
	 * 如果用户未关注将无法转化 openid转换成userid接口
	 */
	public void toUserId() {
		String json = "{\"openid\":\"oD3e5jpSC3C8Qq5uon_SEeRwc9AA\"}";
		System.out.println("json..." + json);
		ApiResult apiResult = OAuthApi.ToUserId(json);
	}

	public void sendTextChat() {
		TextChat textChat = new TextChat();

		ChatReceiver receiver = new ChatReceiver();
		receiver.setType(ChatType.single);
		receiver.setId("Javen");

		textChat.setReceiver(receiver);
		textChat.setSender("Javen");
		textChat.setText(new TextChatMsg("企业会话消息测试....."));
		String data = JSON.toJSONString(textChat);
		System.out.println("data:" + data);
		ApiResult apiResult = ChatApi.Chat(ChatUrl.sendUrl, data);
	}
}
