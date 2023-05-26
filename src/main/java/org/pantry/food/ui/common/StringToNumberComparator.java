package org.pantry.food.ui.common;

import java.util.Comparator;

/**
 * Compares strings numerically
 */
public class StringToNumberComparator implements Comparator {

	private static StringToNumberComparator INSTANCE;

	public static StringToNumberComparator getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new StringToNumberComparator();
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
			Double n1 = Double.parseDouble(o1.toString());
			Double n2 = Double.parseDouble(o2.toString());
			return n1.compareTo(n2);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

}
