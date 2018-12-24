package com.sdlh.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtilLH {
	static Logger logger = LoggerFactory.getLogger(DateUtilLH.class);
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取当前日期，格式：yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDate() {
		return dateFormat.format(new Date());
	}
	
	public static String getCurrentTime() {
		return timeFormat.format(new Date());
	}
	/**
	 * 获取当前日期
	 * @param format 日期格式
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(new Date());
	}
	
	/**
	 * 日期转换为字符串格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDate2Str(Date date,String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
	/**
	 * 日期转换为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDate2Str(Date date,SimpleDateFormat format) {
		return format.format(date);
	}
	
	public static Date convertStr2Date(String dateStr,String format) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.parse(dateStr);
		}catch(Exception e){
			logger.error("字符转日期异常", e);
			return null;
		}
	}
	/**
	 * 将一种日期字符串格式转换为另一种格式
	 * @param dateStr 日期字符串
	 * @param fromFormat 原日期格式
	 * @param toFormat 新日期格式
	 * @return
	 */
	public static String convertStr2AnotherDateStr(String dateStr,String fromFormat,String toFormat) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat(fromFormat);
			SimpleDateFormat toSF = new SimpleDateFormat(toFormat);
			return toSF.format(sf.parse(dateStr));
		}catch(Exception e){
			logger.error("字符转日期异常", e);
			return null;
		}
	}
	
	public static Date convertStr2Date(String dateStr,SimpleDateFormat format) {
		try{
			return format.parse(dateStr);
		}catch(Exception e){
			logger.error("字符转日期异常", e);
			return null;
		}
	}
	/**
	 * 给日期增加一段时间
	 * @param srcDate
	 * @param field  增加域
	 * @param amount 增加数值
	 * @return
	 */
	public static Date addTimeToDate(Date srcDate,int field,int amount) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(srcDate);
		ca.add(field, amount);
		return ca.getTime();
	}

	/**
	 * 给字符串日期增加一段时间
	 * @param srcDateStr 原日期字符串
	 * @param srcFormatStr 原日期格式
	 * @param destFormatStr 转换后日期格式
	 * @param field 增加域 年、月、日  参考  Calendar.Field
	 * @param amount 增加数值
	 * @return
	 */
	public static String addTimeToDate(String srcDateStr,String srcFormatStr,String destFormatStr,int field,int amount) {
		try {
			SimpleDateFormat srcFormat = new SimpleDateFormat(srcFormatStr);
			Date srcDate = srcFormat.parse(srcFormatStr);
			Calendar ca = Calendar.getInstance();
			ca.setTime(srcDate);
			ca.add(field, amount);
			SimpleDateFormat destFormat = new SimpleDateFormat(destFormatStr);
			return destFormat.format(ca.getTime());
		}catch(Exception e) {
			logger.error("日期转换异常：srcDateStr："+srcDateStr+";srcFormatStr:"+srcFormatStr);
			return null;
		}
	}
}
