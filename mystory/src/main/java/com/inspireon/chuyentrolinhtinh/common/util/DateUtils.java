package com.inspireon.chuyentrolinhtinh.common.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtils {

	public static final String DATE_PATTERN = "dd-MM-yyyy";
	
	public static final String TIME_PATTERN = "HH:mm:ss";
	
	public static final String DATE_TIME_PATTERN = "dd-MMM-yyyy HH:mm";
	
	public static final String MONTH_PATTERN = "yyyyMM";
	
	public static final String DAY_PATTERN = "yyyyMMdd";
	
	//Time Regex	
	public static final String SHORT_TIME_PATTERN = "HH:mm"; //"14:05"
	public static final String SHORT_DATE_PATTERN = "dd/MM/yyyy"; //"3/9/2008"
	public static final String MONTH_DAY_PATTERN = "MMM dd"; //"March 09"

	
	private static final int SECOND_MILLIS = 1000;
	private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
	private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
	private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

	

	public static final String getTimeAgo(long time, String justNow, String aMinuteAgo, 
			String minutesAgo, String anHourAgo, String hoursAgo, String yesterday, String daysAgo) {
		
	    if (time < 1000000000000L) {
	        // if timestamp given in seconds, convert to millis
	        time *= 1000;
	    }

	    long now = System.currentTimeMillis();
	    if (time > now || time <= 0) {
	        return null;
	    }

	    // TODO: localize
	    final long diff = now - time;
	    if (diff < MINUTE_MILLIS) {
	        return justNow;
	    } else if (diff < 2 * MINUTE_MILLIS) {
	        return aMinuteAgo;
	    } else if (diff < 50 * MINUTE_MILLIS) {
	        return diff / MINUTE_MILLIS + " " + minutesAgo;
	    } else if (diff < 90 * MINUTE_MILLIS) {
	        return anHourAgo;
	    } else if (diff < 24 * HOUR_MILLIS) {
	        return diff / HOUR_MILLIS + " " + hoursAgo;
	    } else if (diff < 48 * HOUR_MILLIS) {
	        return yesterday;
	    } else {
	        return diff / DAY_MILLIS + " " +daysAgo;
	    }
	}
	
	/**
	 * Convert date to string with default pattern "dd-MM-yyyy"
	 * 
	 * @param date
	 * @return string
	 * @author Tung 
	 */
	public static final String dateToString(final Date date) {
		return dateToString(date, DATE_PATTERN);
	}
	
	/**
	 * Convert date to string with Vietnamese date time pattern "HH:mm:ss ngày dd/MM/yyyy"
	 * 
	 * @param date
	 * @return string
	 * @author Tung 
	 */
	public static final String dateToViString(final Date date) {
		return dateToString(date, TIME_PATTERN) + " ngày " + dateToString(date, DATE_PATTERN);
	}
	
	/**
	 * Convert date to month string with format "yyyyMM"
	 * 
	 * @param date
	 * @return string
	 * @author Tung 
	 */
	public static final String dateToMonthFormat(final Date date) {
		return dateToString(date, MONTH_PATTERN);
	}
	
	/**
	 * Convert date to day string with format "yyyyMMdd"
	 * 
	 * @param date
	 * @return string
	 * @author Tung 
	 */
	public static final String dateToDayFormat(final Date date) {
		return dateToString(date, DAY_PATTERN);
	}
	
	/**
	 * Convert date to string with customized pattern.
	 * 
	 * @param date
	 * @param pattern
	 * @author Tung
	 * 
	 * @return string
	 */
	public static final String dateToString(final Date date, final String pattern) {
		String s = StringUtils.EMPTY;
		
		if(date != null) {
			s = DateFormatUtils.format(date, pattern);
		}
		
		return s;
	}	
	
	public static final Date substractDate(Date originalDate, long numberOfDays) {
		return new Date(originalDate.getTime() - numberOfDays * 24 * 3600 * 1000);
	}
	
	public static String dateToMailString(final Date date) {
		if (date == null) return StringUtils.EMPTY;
		
		// get today
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date today = c.getTime();	
		
		//get begining year
		c.set(Calendar.DAY_OF_YEAR, 1);		
		Date year = c.getTime();
		
		if (date.after(today)) {
			return dateToString(date, SHORT_TIME_PATTERN);
		} else {
			if (date.after(year)) {
				return dateToString(date, MONTH_DAY_PATTERN);
			} else {
				return dateToString(date, SHORT_DATE_PATTERN);
			}
		}
	}
	
	public static String getCurrentYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}
	
	public static String getCurrentMonth() {
		return String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
	}
	
	public static String getCurrentDate() {
		return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
	}
}
