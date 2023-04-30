package org.pantry.food.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.text.WordUtils;

public class DateUtil {
	private static DateTimeFormatter dateFormatInput = DateTimeFormatter.ofPattern("M/d/y");
	private static DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("MM/dd/yy");
	private static DateTimeFormatter dateFormatOutputFourDigitYear = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static DateTimeFormatter dateFormatDatestamp = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	// Reusable but immutable LocalDate instance so we don't have to create a new
	// one every time we call getMonthName
	private static final LocalDate getMonthNameDate = LocalDate.now();

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

	public static String formatDate(LocalDate date) {
		return dateFormatOutput.format(date);
	}

	public static String formatDateFourDigitYear(LocalDate date) {
		return dateFormatOutputFourDigitYear.format(date);
	}

	public static String getCurrentDateString() {
		return dateFormatOutput.format(LocalDate.now());
	}

	public static String getCurrentDateStringFourDigitYear() {
		return dateFormatOutputFourDigitYear.format(LocalDate.now());
	}

	public static String getDatestamp() {
		return dateFormatDatestamp.format(LocalDate.now());
	}

	public static int getCurrentMonth() {
		return LocalDate.now().getMonthValue();
	}

	/**
	 * Converts a month ID (e.g. 1 = Jan, 2 = Feb, etc) to its 3-character name
	 * 
	 * @param id month ID (1 - 12)
	 * @return
	 */
	public static String getMonthName(int id) {
		return WordUtils.capitalizeFully(getMonthNameDate.withMonth(id).getMonth().toString()).substring(0, 3);
	}

}
