package com.servitization.session.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONUtil {
	
	/**
	 * 将jsonp格式的数据还原json
	 * 
	 * @param jsonp
	 * @return
	 */
	public static String changeJsonp2Json(String jsonp) {
		String s = "";
		Pattern pattern = Pattern.compile("(?<=\\()(.*)(?=\\))");
		Matcher matcher = pattern.matcher(jsonp);
		if (matcher.find()) {
			s = matcher.group();
		}
		if (s.length() == 0) {
			s = jsonp;
		}
		return s;
	}
}
