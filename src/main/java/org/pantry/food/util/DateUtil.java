package org.pantry.food.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public static boolean isDate(String str) {
		if (str == null) {
			return false;
		}
		try {
			toDate(str);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

	public static Date toDate(String str) throws ParseException {
		return dateFormat.parse(str);
	}
}
