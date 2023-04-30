package org.pantry.food.ui.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Evaluates a validator if a callback function returns true
 *
 */
public class ConditionalValidator implements StringValidator {

	private List<StringValidator> validators = new ArrayList<>();
	private Function<String, Boolean> evaluator;

	public ConditionalValidator(Function<String, Boolean> evaluator) {
		this.evaluator = evaluator;
	}

	public ConditionalValidator add(StringValidator validator) {
		validators.add(validator);
		return this;
	}

	@Override
	public Boolean validate(String input) {
		// Only apply validators if conditional evaluator returns true
		if (!evaluator.apply(input)) {
			return true;
		}

		// Flip through all dependend validators
		for (StringValidator validator : validators) {
			if (!validator.validate(input)) {
				return false;
			}
		}

		return true;
	}
}
