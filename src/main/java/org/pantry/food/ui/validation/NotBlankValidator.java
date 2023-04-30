package org.pantry.food.ui.validation;

/**
 * Validates that a string is non-null and not empty
 *
 */
public class NotBlankValidator implements StringValidator {

	@Override
	public Boolean validate(String input) {
		// Must be non-blank
		return null != input && input.trim().length() > 0;
	}
}
