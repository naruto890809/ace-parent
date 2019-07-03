package com.ace.framework.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * json和java对象的转换工具类，依赖于jackson
 * 
 * @author 杨涛
 * @version 1.0
 */
public class JsonUtil {
	/**
	 * 默认的时间序列化格式
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	/**
	 * 从对象转换到json字符串
	 * 
	 * @param obj
	 *            对象
	 * @param ignoreNull
	 *            是否可以忽略空值
	 * @return json字符串
	 */
	public static String obj2Json(Object obj, boolean ignoreNull) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// 忽略空值输出
			if (ignoreNull) {
				mapper.getSerializationConfig().setSerializationInclusion(
						Inclusion.NON_NULL);
			}
			mapper.getSerializationConfig().setDateFormat(sdf);
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 从josn字符串反序列化成java对象
	 * 
	 * @param <X>
	 *            java对象的类型
	 * @param jsonStr
	 *            json字符串
	 * @param x
	 *            java对象的类
	 * @return jave对象
	 */
	public static <X> X json2Obj(String jsonStr, Class<X> x) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonStr, x);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
