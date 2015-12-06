package weixin.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	/**
	 * json格式转map
	 * 
	 * @param <T>
	 * 
	 * @param json
	 * @return
	 */
	public static <T> T json2Object(String json, Class<T> c) {
		if (json == null || json.length() <= 0) {
			return null;
		}

		T t = null;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(json, c);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return t;
	}
}
