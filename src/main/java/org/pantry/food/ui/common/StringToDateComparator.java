package org.pantry.food.ui.common;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;

import org.pantry.food.util.DateUtil;

/**
 * Compares date strings as dates
 */
public class StringToDateComparator implements Comparator {

	private static StringToDateComparator INSTANCE;

	public static StringToDateComparator getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new StringToDateComparator();
		}
		return INSTANCE;
	}

	@Override
	public int compare(Object o1, Object o2) {
		if (null == o1) {
			return 1;
		}
		if (null == o2) {
			return -1;
		}
		try {
			LocalDate d1 = DateUtil.toDate(o1.toString());
			LocalDate d2 = DateUtil.toDate(o2.toString());
			return d1.compareTo(d2);
		} catch (ParseException e) {
			return -1;
		}
	}

}
