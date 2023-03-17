package org.pantry.food.ui.validation;

import org.pantry.food.util.NumberUtil;

/**
 * Validates that a string is non-null and numeric
 *
 */
public class NumericValidator implements StringValidator {

	@Override
	public Boolean validate(String input) {
		// Must be numeric
		return null != input && NumberUtil.isNumeric(input);
	}
}
