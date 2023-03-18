package org.pantry.food.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	private static DateTimeFormatter dateFormatInput = DateTimeFormatter.ofPattern("M/d/y");
	private static DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("MM/dd/yy");

	public static boolean isDate(String str) {
		if (str == null) {
			return false;
		}
		try {
			LocalDate.parse(str, dateFormatInput);
			toDate(str);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

	public static LocalDate toDate(String str) throws ParseException {
		return LocalDate.parse(str, dateFormatInput);
	}

	public static String getCurrentDateString() {
		return dateFormatOutput.format(LocalDate.now());
	}

	public static int getCurrentMonth() {
		return LocalDate.now().getMonthValue();
	}
}
