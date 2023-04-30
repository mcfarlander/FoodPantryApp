package org.pantry.food.util;

import java.util.function.Function;

import javafx.scene.Node;

/**
 * Puts a red border around input elements if they fail validation. Removes
 * border if they pass validation.
 */
public class ValidationStyleUtil {
	public static final String ERROR_CLASS = "error";

	public static Boolean validate(Node input, Function<Void, Boolean> validationFn) {
		input.getStyleClass().remove(ERROR_CLASS);

		Boolean isValid = validationFn.apply(null);
		if (!isValid) {
			input.getStyleClass().add(ERROR_CLASS);
		}
		return isValid;
	}
}
