package org.pantry.food.ui.validation;

import java.util.ArrayList;
import java.util.List;

import org.pantry.food.util.ValidationStyleUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;

public class TextInputFocusValidator implements ChangeListener<Boolean> {
	private List<StringValidator> validators = new ArrayList<>();
	private TextInputControl input;

	public TextInputFocusValidator(TextInputControl input) {
		this.input = input;
	}

	public TextInputFocusValidator add(StringValidator validator) {
		validators.add(validator);
		return this;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
		// Validate input when focus is lost
		if (wasFocused && !isFocused) {
			// User has now focused on this element, remove any error styling
			ValidationStyleUtil.validate(input, (unused) -> {
				String value = input.getText();
				if (null == value) {
					value = "";
				}

				Boolean isValid = Boolean.TRUE;
				for (StringValidator validator : validators) {
					isValid = validator.validate(value);
					if (!isValid) {
						break;
					}
				}
				return isValid;
			});
		}
	}
}
