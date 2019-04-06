package com.sdlh.common;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper om = new ObjectMapper();
	
	public static String convertObjectToJsonStr(Object target) {
		String result = null;
		try {
			result = om.writeValueAsString(target);
		}catch(Exception e) {
			logger.error("json convert exception", e);
		}
		return result;
	}
	
	public static Map<String,String> convertStrToMap(String src) {
		Map<String, String> result =null;
		try {
			result = om.readValue(src, new TypeReference<Map<String,String>>() {
	        });
		}catch(Exception e) {
			logger.error("str covert to map exception", e);
		}
		return result;
	}
	
	public static JsonNode convertStrToJson(String src) {
		try {
			return om.readTree(src);
		}catch(Exception e){
			logger.error("str convert to json exception", e);
			return null;
		}
	}
	
	public static ArrayNode convertStrToJsonArray(String src) {
		try {
			JsonNode node = om.readTree(src);
			if(node.isArray()) {
				return (ArrayNode)node;
			}
			return null;
		}catch(Exception e){
			logger.error("str convert to json exception", e);
			return null;
		}
	}
	
	public static JsonNode convertObjToJson(Object obj){
		try {
			return convertStrToJson(convertObjectToJsonStr(obj));
		}catch(Exception e){
			logger.error("obj convert to json exception", e);
			return null;
		}
	}
	public static void main(String[] args) {
		try{
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> item = new HashMap<String,Object>();
			item.put("feeName", "停车费");
			item.put("feeAmount", 12.2);
			list.add(item);
			
			Map<String,Object> item1 = new HashMap<String,Object>();
			item1.put("feeName", "过路费");
			item1.put("feeAmount", 14.2);
			list.add(item1);
			String jsonStr = convertObjectToJsonStr(list);
			System.out.println(URLEncoder.encode(jsonStr, "UTF-8"));
			System.out.println(jsonStr);
			ArrayNode node = convertStrToJsonArray(jsonStr);
			for(int i=0;i<node.size();i++) {
				JsonNode nodeItem = node.get(i);
				System.out.println(nodeItem.get("feeName"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
