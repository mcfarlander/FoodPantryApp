package org.pantry.food.ui.validation;

import java.util.ArrayList;
import java.util.List;

import org.pantry.food.util.ValidationStyleUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;

public class TextInputValidator implements ChangeListener<String> {
	private List<StringValidator> validators = new ArrayList<>();
	private TextInputControl input;

	public TextInputValidator() {
	}

	public TextInputValidator(TextInputControl input) {
		this.input = input;
	}

	public TextInputValidator add(StringValidator validator) {
		validators.add(validator);
		return this;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		ValidationStyleUtil.validate(input, (unused) -> {
			String value = newValue;
			if (null == newValue) {
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
