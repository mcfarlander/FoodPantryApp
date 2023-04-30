package org.pantry.food.ui.validation;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class ComboFocusValidator extends TextInputFocusValidator {
	private ComboBox<String> input;

	public ComboFocusValidator(ComboBox<String> input) {
		this.input = input;
	}

	protected String getValue() {
		return input.getValue();
	}

	protected Node toNode() {
		return input;
	}
}
