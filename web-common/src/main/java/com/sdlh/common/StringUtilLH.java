package com.sdlh.common;

public class StringUtilLH {
	public static boolean isEmpty(String src) {
		if(src==null || "".equals(src.trim()) || "null".equalsIgnoreCase(src)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String src) {
		if(src!=null && !"".equals(src.trim()) && !"null".equalsIgnoreCase(src)) {
			return true;
		}
		return false;
	}
}
