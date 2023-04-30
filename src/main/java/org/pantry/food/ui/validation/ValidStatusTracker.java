package org.pantry.food.ui.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.pantry.food.util.ValidationStyleUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

/**
 * Tracks the status of a group of form elements based on their style class. If
 * any of the form elements is in an error state (i.e. has the error style
 * class), the entire form is considered invalid.
 */
public class ValidStatusTracker {

	private List<Control> inputs = new ArrayList<>();
	private SimpleBooleanProperty validProperty = new SimpleBooleanProperty(Boolean.TRUE);

	public ValidStatusTracker(Collection<Control> inputs) {
		this.inputs.addAll(inputs);
	}

	public ValidStatusTracker(Control... inputs) {
		add(inputs);
	}

	public SimpleBooleanProperty validProperty() {
		return validProperty;
	}

	public void add(Control... inputs) {
		this.inputs.addAll(Arrays.asList(inputs));
		for (Control control : inputs) {
			if (control instanceof TextField) {
				((TextField) control).focusedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						checkValid();
					}

				});
			} else if (control instanceof ComboBox) {
				((ComboBox) control).focusedProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						checkValid();
					}

				});
			}
		}
	}

	public Boolean isValid() {
		return validProperty.getValue();
	}

	protected Boolean checkValid() {
		Boolean valid = Boolean.TRUE;
		if (null != inputs) {
			for (Control input : inputs) {
				if (input.getStyleClass().contains(ValidationStyleUtil.ERROR_CLASS)) {
					valid = Boolean.FALSE;
				}
			}
		}

		validProperty.setValue(valid);
		return valid;
	}

	// Sets and removes focus from each control to trigger validation listeners
	public Boolean checkAll() {
		Boolean valid = Boolean.TRUE;
		if (null != inputs) {
			for (Control input : inputs) {
				input.requestFocus();
			}
			valid = checkValid();
		}

		return valid;
	}

}
