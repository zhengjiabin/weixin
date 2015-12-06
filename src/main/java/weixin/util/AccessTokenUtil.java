package weixin.util;

import java.io.IOException;
import java.util.Properties;

import weixin.bean.AccessToken;

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

		if (isNeedLoadAccessTokenFromWeixin(current_time)) {
			return loadAccessToken(current_time);
		}

		return prop.getProperty("access_token");
	}

	/**
	 * 加载凭证
	 * 
	 * @param current_time
	 * @return
	 */
	private static synchronized String loadAccessToken(long current_time) {
		if (isNeedLoadAccessTokenFromWeixin(current_time)) {
			AccessToken info = loadAccessTokenFromWeixin();
			checkAccessToken(info);
			setAccessToken(info);

			prop.setProperty("get_access_token_time", String.valueOf(current_time));
		}

		return prop.getProperty("access_token");
	}

	/**
	 * 检查获取凭证是否成功
	 * 
	 * @param info
	 */
	private static void checkAccessToken(AccessToken info) {
		int errcode = info.getErrcode();

		if (errcode != 0) {
			String errmsg = info.getErrmsg();
			throw new RuntimeException("errcode:" + errcode + ",errmsg:" + errmsg);
		}
	}

	/**
	 * 设置获取的凭证信息
	 * 
	 * @param json
	 */
	private static void setAccessToken(AccessToken info) {
		String access_token = info.getAccess_token();
		int expires_in = info.getExpires_in();

		prop.setProperty("access_token", access_token);
		prop.setProperty("expires_in", String.valueOf(expires_in));
	}

	/**
	 * 远程加载凭证信息
	 * 
	 * @return
	 */
	private static AccessToken loadAccessTokenFromWeixin() {
		String appid = prop.getProperty("appid");
		String secret = prop.getProperty("secret");

		String url = prop.getProperty("getAccessTokenUrl");
		url = url.replace("APPID", appid);
		url = url.replace("APPSECRET", secret);

		WebResource resource = client.resource(url);
		String response = resource.get(String.class);

		return JsonUtil.json2Object(response, AccessToken.class);
	}

	/**
	 * 是否需要
	 * 
	 * @param current_time
	 * @return
	 */
	private static boolean isNeedLoadAccessTokenFromWeixin(long current_time) {
		long get_access_token_time = Long.valueOf(prop.getProperty("get_access_token_time"));
		int expires_in = Integer.valueOf(prop.getProperty("expires_in"));

		return current_time - get_access_token_time > expires_in * 1000;
	}
}
