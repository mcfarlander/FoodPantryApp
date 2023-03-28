package org.pantry.food.ui.validation;

/**
 * Validates that a numeric string is numeric and more than -1
 *
 */
public class NotNegativeValidator extends NumericValidator {

	@Override
	public Boolean validate(String input) {
		boolean isValid = super.validate(input);
		return isValid && Integer.parseInt(input.trim()) >= 0;
	}
}
