package weixin.util;

import org.junit.Test;

public class AccessTokenUtilTest {
	@Test
	public void testGetAccessToken() {
		String accessToken = AccessTokenUtil.getAccessToken();
		System.out.println(accessToken);
	}
}
