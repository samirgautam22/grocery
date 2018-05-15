package com.project.grocery.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
public class DateUtil {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static Date getTokenExpireDate(Date current) {

		Calendar c = Calendar.getInstance();
		c.setTime(current);

		c.add(Calendar.YEAR, 0);
		c.add(Calendar.MONTH, 0);
		c.add(Calendar.DATE, 1);
		c.add(Calendar.HOUR, 0);
		c.add(Calendar.MINUTE, 0);
		c.add(Calendar.SECOND, 0);

		Date currentDatePlusOne = c.getTime();
		System.out.println(dateFormat.format(currentDatePlusOne));
		return currentDatePlusOne;

	}

	public static boolean compareDate(Date createdDate, Date expireDate) {

		if (expireDate.compareTo(createdDate) < 0) {
			return false;

		}
		if (expireDate.compareTo(createdDate) == 0) {

			return true;
		}
		if (expireDate.compareTo(createdDate) > 0) {
			return true;
		}
		return false;
	}

}
