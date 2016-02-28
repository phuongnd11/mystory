package com.inspireon.mystory.common.util;


public class StringUtils {

	/**
	 * Gets a substring from the specified String 
	 * StringUtils.substring("My Love Story", 7) = "My Love"
	 * StringUtils.substring("My Love Story", 6) = "My"
	 * 
	 * @param date
	 * @return string
	 */
	public static final String substring(final String str, final int maxLen) {
		
		if (str.length() <= maxLen)
	         return str;
	  
	     int endIdx = maxLen;
	     while (endIdx > 0 && str.charAt(endIdx) != ' ')
	         endIdx--;
	  
	     return str.substring(0, endIdx);
	}
}
