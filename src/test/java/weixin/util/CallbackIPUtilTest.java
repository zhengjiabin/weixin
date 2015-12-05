package weixin.util;

import org.junit.Test;

public class CallbackIPUtilTest {
	@Test
	public void testGetCallbackIP() {
		String access_token = AccessTokenUtil.getAccessToken();
		System.out.println(access_token);

		String ip_list = CallbackIPUtil.getCallbackIP(access_token);
		System.out.println(ip_list);
	}
}
