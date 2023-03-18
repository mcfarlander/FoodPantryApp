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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	private TextField commentsText;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<AddEditCustomerDialogInput, Customer> parent;
	private AddEditCustomerDialogInput input;
	private boolean isSaved = false;
	private Customer savedCustomer = null;
	private ValidStatusTracker validStatusTracker;
	private List<String> monthNames = new ArrayList<>(
			Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));

	@FXML
	private void initialize() {
		// Populate comboboxes with genders and months
		genderCbo.getItems().addAll("Female", "Male");

		monthRegisteredCbo.getItems().addAll(monthNames);

		// Add icons to buttons
		saveBtn.setGraphic(Images.getImageView("accept.png"));
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Attempt to save the new record
//				
//				this.customer.setHouseholdId(Integer.parseInt(this.cboHouseholdId.getSelectedItem().toString()));
//		        this.customer.setPersonId(Integer.parseInt(this.txtPersonId.getText()));
//		        this.customer.setGender(this.cboGender.getSelectedItem().toString());
//		        this.customer.setBirthDate(this.txtBirthDate.getText());
//		        this.customer.setAge(Integer.parseInt(txtAge.getText()));
//		        this.customer.setMonthRegistered(this.cboMonthRegistered.getSelectedIndex() + 1);
//		        this.customer.setNewCustomer(this.chkNew.isSelected());
//		        this.customer.setComments(txtComments.getText());
//		        this.customer.setActive(chkActive.isSelected());
			}

		});

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
		boolean isNew = null == input.getCustomer();
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
		monthRegisteredCbo.setConverter(new MonthConverter(monthNames));
		monthRegisteredCbo.valueProperty().bindBidirectional(customer.monthRegisteredProperty());

		newChk.selectedProperty().bindBidirectional(customer.newCustomerProperty());
		activeChk.selectedProperty().bindBidirectional(customer.activeProperty());

		commentsText.textProperty().bindBidirectional(customer.commentsProperty());

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
				.add(new RegexValidator("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}"));
		birthdateText.focusedProperty().addListener(textValidator);
		// Calculate the person's age when the birthdate changes
		birthdateText.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
				if (wasFocused && !isFocused && DateUtil.isDate(birthdateText.getText())) {
					try {
						LocalDate birthdate = DateUtil.toDate(birthdateText.getText());
						int age = calculateAge(birthdate);

						if (age < 0 || age > 120) {
							// Try to reconcile the date by adding 1900 to the year. If the year is
							// presented in a 2-digit format, Java assumes it's a 2-digit representation of
							// a year sub-100 AD. But the user probably means a year within the 20th
							// century.
							birthdate = birthdate.plusYears(1900);
							age = calculateAge(birthdate);
							if (age < 0 || age > 120) {
								new Alert(AlertType.WARNING, "Incorrect birth year").show();
							} else {
								ageText.setText(String.valueOf(age));
							}
						} else {
							ageText.setText(String.valueOf(age));
						}
					} catch (ParseException e) {
						new Alert(AlertType.WARNING, "Invalid date of birth").show();
					}
				}
			}

			private int calculateAge(LocalDate birthdate) {
				return (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());
			}
		});

		// If this is a customer add, pre-select the New, Active, and Month Registered
		// values
		if (isNew) {
			newChk.setSelected(true);
			activeChk.setSelected(true);
			monthRegisteredCbo.getSelectionModel().select(String.valueOf(DateUtil.getCurrentMonth()));
		}

		// Month Registered must be selected
		comboValidator = new ComboInputValidator(monthRegisteredCbo).add(new NotBlankValidator());
		monthRegisteredCbo.getSelectionModel().selectedItemProperty().addListener(comboValidator);

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

	/**
	 * Converts a month name/abbreviation to its corresponding number
	 */
	class MonthConverter extends StringConverter<String> {

		private Map<String, String> monthToNumber = new HashMap<>();

		MonthConverter(List<String> monthNames) {
			int count = 1;
			for (String name : monthNames) {
				monthToNumber.put(name, String.valueOf(count++));
			}
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
