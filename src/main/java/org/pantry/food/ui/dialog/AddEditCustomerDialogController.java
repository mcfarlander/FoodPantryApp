/*
  Copyright 2011 Dave Johnson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.pantry.food.ui.dialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.pantry.food.Images;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.ValidStatusTracker;
import org.pantry.food.ui.validation.ComboInputValidator;
import org.pantry.food.ui.validation.NotBlankValidator;
import org.pantry.food.ui.validation.NumericValidator;
import org.pantry.food.ui.validation.RegexValidator;
import org.pantry.food.ui.validation.TextInputFocusValidator;
import org.pantry.food.util.DateUtil;
import org.pantry.food.util.NumberUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.StringConverter;

public class AddEditCustomerDialogController implements IModalDialogController<AddEditCustomerDialogInput, Customer> {

	@FXML
	private ComboBox<String> householdIdCbo;
	@FXML
	private TextField personIdText;
	@FXML
	private ComboBox<String> genderCbo;
	@FXML
	private TextField birthdateText;
	@FXML
	private TextField ageText;
	@FXML
	private ComboBox<String> monthRegisteredCbo;
	@FXML
	private CheckBox activeChk;
	@FXML
	private CheckBox newChk;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;
	private ModalDialog<AddEditCustomerDialogInput, Customer> parent;
	private AddEditCustomerDialogInput input;
	private boolean isSaved = false;
	private Customer savedCustomer = null;
	private ValidStatusTracker validStatusTracker;

	@FXML
	private void initialize() {
		// Populate comboboxes with genders and months
		genderCbo.getItems().addAll("Female", "Male");

		monthRegisteredCbo.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
				"Nov", "Dec");

		// Add icons to buttons
		saveBtn.setGraphic(Images.getImageView("accept.png"));
		cancelBtn.setGraphic(Images.getImageView("cancel.png"));

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Close the dialog
				parent.close();
			}

		});

		validStatusTracker = new ValidStatusTracker();
	}

	@Override
	public void setInput(AddEditCustomerDialogInput input) {
		this.input = input;
		Customer customer = new Customer(input.getCustomer());

		householdIdCbo.getItems().add("");

		if (null != input.getHouseholdIds()) {
			householdIdCbo.getItems().addAll(input.getHouseholdIds());
		}

		// Bind all the inputs to their Customer properties
		personIdText.textProperty().bindBidirectional(customer.personIdProperty());
		householdIdCbo.valueProperty().bindBidirectional(customer.householdIdProperty());
		genderCbo.valueProperty().bindBidirectional(customer.genderProperty());
		birthdateText.textProperty().bindBidirectional(customer.birthDateProperty());
		ageText.textProperty().bindBidirectional(customer.ageProperty());

		// The Month Registered combobox displays "Jan"/"Feb"/etc but the actual value
		// is a number, so we have to map between them
		monthRegisteredCbo.setConverter(new MonthConverter());
		monthRegisteredCbo.valueProperty().bindBidirectional(customer.monthRegisteredProperty());
		newChk.selectedProperty().bindBidirectional(customer.newCustomerProperty());
		activeChk.selectedProperty().bindBidirectional(customer.activeProperty());

		// Bind the input validators
		// Person IDs must be non-blank and numeric
		TextInputFocusValidator textValidator = new TextInputFocusValidator(personIdText).add(new NumericValidator())
				.add(new NotBlankValidator());
		personIdText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(ageText).add(new NumericValidator()).add(new NotBlankValidator());
		ageText.focusedProperty().addListener(textValidator);

		// Household IDs can be an empty string or a number
		ComboInputValidator comboValidator = new ComboInputValidator(householdIdCbo).add(new NumericValidator());
		householdIdCbo.getSelectionModel().selectedItemProperty().addListener(comboValidator);

		// Birthdate must comply with typical date format
		textValidator = new TextInputFocusValidator(birthdateText)
				.add(new RegexValidator("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}"));
		birthdateText.focusedProperty().addListener(textValidator);
		// Calculate the person's age when the birthdate changes
		birthdateText.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
				if (wasFocused && !isFocused && DateUtil.isDate(birthdateText.getText())) {
					try {
						int age = calculateAge(DateUtil.toDate(birthdateText.getText()));
						ageText.setText(String.valueOf(age));
					} catch (ParseException e) {
						new Alert(AlertType.WARNING, "Invalid date of birth").show();
					}
				}
			}

			private int calculateAge(Date birthdate) {
				// Create a calendar object with the date of birth
				Calendar dateOfBirth = new GregorianCalendar();
				dateOfBirth.setTime(birthdate);
				// Create a calendar object with today's date
				Calendar today = Calendar.getInstance();
				// Get age based on year
				int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
				// Add the tentative age to the date of birth to get this year's birthday
				dateOfBirth.add(Calendar.YEAR, age);
				// If this year's birthday has not happened yet, subtract one from age
				if (today.before(dateOfBirth)) {
					age--;
				}
				return age;
			}
		});

		validStatusTracker.add(householdIdCbo, personIdText, genderCbo, birthdateText, ageText, monthRegisteredCbo,
				newChk, activeChk);
		saveBtn.disableProperty().bind(validStatusTracker.validProperty().not());
	}

	@Override
	public String getTitle() {
		String type = "Add";
		if (null != input.getCustomer()) {
			type = "Edit";
		}
		return type + " Customer";
	}

	@Override
	public Customer getResponse() {
		return isSaved ? savedCustomer : null;
	}

	@Override
	public boolean isPositiveResponse() {
		return isSaved;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("table_key.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<AddEditCustomerDialogInput, Customer> parent) {
		this.parent = parent;
	}

	class MonthConverter extends StringConverter<String> {

		private Map<String, String> monthToNumber = new HashMap<>();

		MonthConverter() {
			monthToNumber.put("Jan", "1");
			monthToNumber.put("Feb", "2");
			monthToNumber.put("Mar", "3");
			monthToNumber.put("Apr", "4");
			monthToNumber.put("May", "5");
			monthToNumber.put("Jun", "6");
			monthToNumber.put("Jul", "7");
			monthToNumber.put("Aug", "8");
			monthToNumber.put("Sep", "9");
			monthToNumber.put("Oct", "10");
			monthToNumber.put("Nov", "11");
			monthToNumber.put("Dec", "12");
		}

		@Override
		public String toString(String monthName) {
			if (NumberUtil.isNumeric(monthName)) {
				return fromString(monthName);
			}
			return monthName;
		}

		@Override
		public String fromString(String number) {
			for (Map.Entry<String, String> entry : monthToNumber.entrySet()) {
				if (entry.getValue().equals(number)) {
					return entry.getKey();
				}
			}
			return "";
		}

	}

}
