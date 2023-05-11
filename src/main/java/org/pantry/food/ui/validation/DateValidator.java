package org.pantry.food.ui.validation;

import org.pantry.food.util.DateUtil;

/**
 * Validates that a string is non-null, is formatted like a date, and is a valid
 * date
 *
 */
public class DateValidator extends DateFormatValidator {

	@Override
	public Boolean validate(String input) {
		// Must be non-blank and formatted like a date
		Boolean valid = super.validate(input);
		if (!valid) {
			return valid;
		}

		// Must also be a parseable date
		return valid && DateUtil.isDate(input);
	}

}
