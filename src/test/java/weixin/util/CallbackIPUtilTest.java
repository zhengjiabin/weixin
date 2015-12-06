package weixin.util;

import org.junit.Test;

public class CallbackIPUtilTest {
	@Test
	public void testGetCallbackIP() {
		String[] ip_list = CallbackIPUtil.getCallbackIP();
		System.out.println(ip_list);
	}
}
