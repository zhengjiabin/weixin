package weixin.util;

import java.io.IOException;
import java.util.Properties;

import weixin.bean.CallbackIP;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CallbackIPUtil {
	private static Client client;
	private static Properties prop;

	static {
		prop = new Properties();
		try {
			prop.load(AccessTokenUtil.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ClientConfig config = new DefaultClientConfig();
		client = Client.create(config);
	}

	/**
	 * 获取微信服务器ip地址
	 * 
	 * @return
	 */
	public static String[] getCallbackIP() {
		String access_token = AccessTokenUtil.getAccessToken();
		CallbackIP info = loadCallbackIPFromWeixin(access_token);
		checkCallbackIP(info);

		return info.getIp_list();
	}

	/**
	 * 检查获取微信服务器ip是否成功
	 * 
	 * @param info
	 */
	private static void checkCallbackIP(CallbackIP info) {
		int errcode = info.getErrcode();

		if (errcode != 0) {
			String errmsg = info.getErrmsg();
			throw new RuntimeException("errcode:" + errcode + ",errmsg:" + errmsg);
		}
	}

	/**
	 * 远程加载微信服务器ip信息
	 * 
	 * @param access_token
	 * @return
	 */
	private static CallbackIP loadCallbackIPFromWeixin(String access_token) {
		String url = prop.getProperty("getCallbackIPUrl");
		url = url.replace("ACCESS_TOKEN", access_token);

		WebResource resource = client.resource(url);
		String response = resource.get(String.class);

		return JsonUtil.json2Object(response, CallbackIP.class);
	}
}
