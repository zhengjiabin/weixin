package weixin.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	/**
	 * json格式转map
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String json) {
		if (json == null || json.length() <= 0) {
			return null;
		}

		Map<String, Object> info = null;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			info = objectMapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return info;
	}
}
