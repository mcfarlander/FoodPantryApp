package org.pantry.food.ui.validation;

import org.pantry.food.util.NumberUtil;

/**
 * Validates that a string is null or numeric
 *
 */
public class NumericValidator implements StringValidator {

	@Override
	public Boolean validate(String input) {
		// Must be numeric if non-null
		return null == input || NumberUtil.isNumeric(input);
	}
}
