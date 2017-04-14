package com.example.b2c.net.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static long getTimeStamp(){
		
		return System.currentTimeMillis();
		
	}
	/**定义常量**/
	public static final String DATE_JFP_STR="yyyyMM";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_SMALL_STR_DOT = "yyyy.MM.dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	public static final String DATE_MIDDLE_STR = "MM-dd HH:mm";

	/**
	 * 使用预设格式提取字符串日期
	 * @param strDate 日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate,DATE_FULL_STR);
	}

	/**
	 * 使用用户格式提取字符串日期
	 * @param strDate 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
	 *
	 * @param time
	 * @return
	 */
	public static String timesOne(Long time) {
		SimpleDateFormat sdr = new SimpleDateFormat(DATE_FULL_STR);
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		String times = sdr.format(new Date(time * 1000L));
		return times;

	}
	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 获取给定时间按给定格式显示数据
	 * @return
	 */
	public static String getDateStr(String type,Date date) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(date);
	}

	/**
	 * 获取给定时间按给定格式显示数据
	 * @return
	 */
	public static String getDateStr(String type,Long time) {
		Date date = new  Date(time);
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(date);
	}


	/**
	 * 1 小于
	 * 0 等于
	 * -1 大于
	 * @param data
	 * @return
     */
	public static int compareDate(Date data){
		long sub = data.getTime()-Calendar.getInstance().getTime().getTime();
		int result= 0;
		if(sub == 0){
			result = 0;
		}else if(sub > 0){
			result =  -1;
		}else if(sub < 0){
			result = 1;
		}
        return result;
	}



	

}
