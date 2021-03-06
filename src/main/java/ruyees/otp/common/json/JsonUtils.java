package ruyees.otp.common.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

	private static ObjectMapper objectMapper = null;

	/**
	 * 把对象转化为json
	 * 
	 * @param entity
	 * @return
	 */
	public static String writeObjectToJson(Object entity) {
		try {
			if (objectMapper == null)
				objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(entity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把json字符串转化为Object
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T readObjectByJson(String json, Class<T> clazz) {
		try {
			if (objectMapper == null) {
				objectMapper = new ObjectMapper();
			}
			return objectMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			log.error("json==" + json + ",error(JsonParseException):"
					+ e.getMessage());
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			log.error("json==" + json + ",error(JsonMappingExcption):"
					+ e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("json==" + json + ",error(IOException):" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
