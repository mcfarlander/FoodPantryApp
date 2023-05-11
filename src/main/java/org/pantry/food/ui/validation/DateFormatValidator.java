package org.pantry.food.ui.validation;

/**
 * Validates that a string is non-null and is formatted like a date
 *
 */
public class DateFormatValidator extends RegexValidator {

	public DateFormatValidator() {
		super("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}");
	}

}
