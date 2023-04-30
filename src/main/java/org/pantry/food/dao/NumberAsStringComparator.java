package org.pantry.food.dao;

import java.util.Comparator;

/**
 * Compares strings numerically. Does not do any check to verify strings are
 * numeric.
 *
 */
public class NumberAsStringComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if (null == o1) {
			return -1;
		}
		if (null == o2) {
			return 1;
		}

		// Convert both strings to numbers and compare
		Integer i1 = Integer.valueOf(o1);
		Integer i2 = Integer.valueOf(o2);
		return i1.compareTo(i2);

	}
}