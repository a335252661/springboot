package com.example.untils;

import com.alibaba.fastjson.JSON;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);
	
	private static ObjectMapper objectMapper = null;
	/**
	 * 支持转换 ‘yyyy-MM-dd HH:mm:ss’ 日期格式
	 * @author guang
	 * getInstance
	 * @return
	 */
	public static ObjectMapper getObjectMapperForDateFormat(){
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			//设置转换日期格式
			objectMapper.setDateFormat(new JacksonDateFormat());
			objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		return objectMapper;
	}
	
	/**
	 * 用于实体类的toString方法 【json string】
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("转JSON异常", e);
		}
		return json;
	}

	/**
	 * JSON转Map
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> json2Map(String jsonStr){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> map = null;
		try {
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);  
			map = objectMapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			logger.error("JSON转Map异常", e);
		}
		return map;
	}
	
	/**
	 * JSON转 指定对象
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	public static <T> T json2Object(String jsonStr,Class<T> valueType){
		ObjectMapper objectMapper = getObjectMapperForDateFormat();
		T  object = null;
		try {
			object = objectMapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			logger.error("JSON转object异常", e);
		}
		return object;
	}
	
	
	/**
	 * JSON转 指定对象
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> json2List(String jsonStr,Class<T> valueType){
		ObjectMapper objectMapper = getObjectMapperForDateFormat();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, valueType); 
		List<T> list = null;
		try {
			list = (List<T>)objectMapper.readValue(jsonStr, javaType); 
		} catch (Exception e) {
			logger.error("JSON转list异常", e);
		}
		return list;
	}
	
	public static String toJSONStringByfastjson(Object object) {
		return JSON.toJSONString(object);
	}
	
	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}
	
}
