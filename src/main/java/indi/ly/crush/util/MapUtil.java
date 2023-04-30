package indi.ly.crush.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h2>{@link Map} 工具</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class MapUtil {
	
	public static Map<String, Object> ok(Integer code, String message) {
		return ok(code, message, null);
	}
	
	public static <T> Map<String, Object> ok(Integer code, String message, T data) {
		return create(true,code,message,data);
	}
	
	public static Map<String, Object> error(Integer code, String message) {
		return error(code,message, null);
	}
	
	public static <T> Map<String, Object> error(Integer code, String message, T data) {
		return create(false,code,message,data);
	}
	
	private static <T> Map<String, Object> create(Boolean success, Integer code, String message, T data) {
		Map<String, Object> responseResult = new LinkedHashMap<>(4);
		responseResult.put("success", success);
		responseResult.put("code", code);
		responseResult.put("message", message);
		if (data != null) {
			responseResult.put("data", data);
		}
		return responseResult;
	}
}
