package weixin.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

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
	 * @param access_token
	 * @return
	 */
	public static String getCallbackIP(String access_token) {
		Map<String, Object> info = loadCallbackIPFromWeixin(access_token);
		checkCallbackIP(info);

		String ip_list = info.get("ip_list").toString();
		prop.setProperty("ip_list", ip_list);
		return ip_list;
	}

	/**
	 * 检查获取微信服务器ip是否成功
	 * 
	 * @param info
	 */
	private static void checkCallbackIP(Map<String, Object> info) {
		Object errcode = info.get("errcode");

		if (errcode != null && Integer.parseInt(errcode.toString()) != 0) {
			Object errmsg = info.get("errmsg");
			throw new RuntimeException("errcode:" + errcode + ",errmsg:" + errmsg);
		}
	}

	/**
	 * 远程加载微信服务器ip信息
	 * 
	 * @param access_token
	 * @return
	 */
	private static Map<String, Object> loadCallbackIPFromWeixin(String access_token) {
		String url = prop.getProperty("getCallbackIPUrl");
		url = url.replace("ACCESS_TOKEN", access_token);

		WebResource resource = client.resource(url);
		String response = resource.get(String.class);

		return JsonUtil.json2Map(response);
	}
}
