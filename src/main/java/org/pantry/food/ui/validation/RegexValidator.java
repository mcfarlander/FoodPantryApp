package org.pantry.food.ui.validation;

import java.util.regex.Pattern;

/**
 * Validates that a string is non-null and matches a given regular expression
 *
 */
public class RegexValidator implements StringValidator {

	private final Pattern pattern;

	public RegexValidator(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override
	public Boolean validate(String input) {
		// Must be non-blank
		return null != input && pattern.matcher(input).matches();
	}
}
