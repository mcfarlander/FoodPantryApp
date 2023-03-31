package org.pantry.food.ui.validation;

import java.util.ArrayList;
import java.util.List;

import org.pantry.food.util.ValidationStyleUtil;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBoxBase;

public class ComboInputValidator extends TextInputValidator {
	private List<StringValidator> validators = new ArrayList<>();
	private ComboBoxBase<?> input;

	public ComboInputValidator(ComboBoxBase<?> input) {
		this.input = input;
	}

	public ComboInputValidator add(StringValidator validator) {
		validators.add(validator);
		return this;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		// Validate input when selected item is changed
		if (null != newValue) {
			// Remove any error styling
			ValidationStyleUtil.validate(input, (unused) -> {
				Object valueObj = input.getValue();
				if (null == valueObj) {
					return Boolean.FALSE;
				}

				String value = valueObj.toString();
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
