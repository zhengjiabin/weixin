package weixin.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AccessTokenUtil {
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
	 * 获取凭证
	 * 
	 * @return
	 */
	public static String getAccessToken() {
		long current_time = System.currentTimeMillis();

		long get_access_token_time = Long.valueOf(prop.getProperty("get_access_token_time"));
		int expires_in = Integer.valueOf(prop.getProperty("expires_in"));

		if (current_time - get_access_token_time > expires_in * 1000) {
			prop.setProperty("get_access_token_time", String.valueOf(current_time));
			return loadAccessToken();
		}

		return prop.getProperty("access_token");
	}

	/**
	 * 加载凭证
	 * 
	 * @return
	 */
	private static String loadAccessToken() {
		Map<String, Object> info = loadAccessTokenFromWeixin();
		checkAccessToken(info);
		setAccessToken(info);
		return info.get("access_token").toString();
	}

	/**
	 * 检查获取凭证是否成功
	 * 
	 * @param info
	 */
	private static void checkAccessToken(Map<String, Object> info) {
		Object errcode = info.get("errcode");

		if (errcode != null && Integer.parseInt(errcode.toString()) != 0) {
			Object errmsg = info.get("errmsg");
			throw new RuntimeException("errcode:" + errcode + ",errmsg:" + errmsg);
		}
	}

	/**
	 * 设置获取的凭证信息
	 * 
	 * @param json
	 */
	private static void setAccessToken(Map<String, Object> info) {
		String access_token = info.get("access_token").toString();
		String expires_in = info.get("expires_in").toString();

		prop.setProperty("access_token", access_token);
		prop.setProperty("expires_in", expires_in);
	}

	/**
	 * 远程加载凭证信息
	 * 
	 * @return
	 */
	private static Map<String, Object> loadAccessTokenFromWeixin() {
		String appid = prop.getProperty("appid");
		String secret = prop.getProperty("secret");

		String url = prop.getProperty("getAccessTokenUrl");
		url = url.replace("APPID", appid);
		url = url.replace("APPSECRET", secret);

		WebResource resource = client.resource(url);
		String response = resource.get(String.class);

		return JsonUtil.json2Map(response);
	}

}
