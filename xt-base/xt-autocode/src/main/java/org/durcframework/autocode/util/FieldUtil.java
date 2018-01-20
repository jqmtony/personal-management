package org.durcframework.autocode.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldUtil {

	/**
	 * remove the underline. e.x. table_name change to: tableName
	 * 
	 * @param field
	 * @return
	 */
	public static String underlineFilter(String field) {
		if (StringUtils.hasText(field)) {
			StringBuilder sb = new StringBuilder(field);
			while (sb.indexOf("_") > -1) {
				int index = sb.indexOf("_");
				String upperLetter = sb.substring(index + 1, index + 2)
						.toUpperCase();
				sb.replace(index + 1, index + 2, upperLetter);
				sb.deleteCharAt(index);
			}
			return sb.toString();
		}
		return "";
	}
	
	public static String dotFilter(String field){
		if (StringUtils.hasText(field)) {
			if(field.indexOf(".") > -1) {
				return field.split("\\.")[1];
			}
		}
		return field;
	}

	/**
	 * 将第一个字母转换成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str) {
		if (StringUtils.hasText(str)) {
			String firstUpper = str.substring(0, 1).toUpperCase();
			str = firstUpper + str.substring(1, str.length());
		}
		return str;
	}
	
	/**
	 * 将第一个字母转换成小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerFirstLetter(String str) {
		if (StringUtils.hasText(str)) {
			String firstLower = str.substring(0, 1).toLowerCase();
			str = firstLower + str.substring(1, str.length());
		}
		return str;
	}


	/**
	 * 将一个字符串先转成小写后再根据下划线转成驼峰命名风格
	 * @param name
	 * @return
     */
	public static String getFunctionName(String name){
		name=name.toLowerCase();//强字符串转为小写
		Pattern p = Pattern.compile("_[a-z]");
		Matcher m = p.matcher(name);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String firstChar =  m.group().substring(1,2);
			m.appendReplacement(sb, firstChar.toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	
	public static void main(String[] args) {
		System.out.println(underlineFilter("table_name"));
		System.out.println(underlineFilter("tableName"));
		System.out.println(underlineFilter("username"));
	}
	
}
