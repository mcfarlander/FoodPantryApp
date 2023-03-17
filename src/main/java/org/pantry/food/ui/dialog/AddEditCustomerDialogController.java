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

import java.util.HashMap;
import java.util.Map;

import org.pantry.food.Images;
import org.pantry.food.model.Customer;
import org.pantry.food.util.NumberUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.StringConverter;

public class AddEditCustomerDialogController implements IModalDialogController<AddEditCustomerDialogInput, Void> {

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
	private ModalDialog<AddEditCustomerDialogInput, Void> parent;
	private AddEditCustomerDialogInput input;

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
	}

	@Override
	public void setInput(AddEditCustomerDialogInput input) {
		this.input = input;
		Customer customer = new Customer(input.getCustomer());

		householdIdCbo.getItems().add("");

		if (null != input.getHouseholdIds()) {
			householdIdCbo.getItems().addAll(input.getHouseholdIds());
		}

		personIdText.textProperty().bindBidirectional(customer.personIdProperty());
		householdIdCbo.valueProperty().bindBidirectional(customer.householdIdProperty());
		genderCbo.valueProperty().bindBidirectional(customer.genderProperty());
		birthdateText.textProperty().bindBidirectional(customer.birthDateProperty());
		ageText.textProperty().bindBidirectional(customer.ageProperty());
		monthRegisteredCbo.setConverter(new MonthConverter());
		monthRegisteredCbo.valueProperty().bindBidirectional(customer.monthRegisteredProperty());
		newChk.selectedProperty().bindBidirectional(customer.newCustomerProperty());
		activeChk.selectedProperty().bindBidirectional(customer.activeProperty());
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
	public Void getResponse() {
		return null;
	}

	@Override
	public boolean isPositiveResponse() {
		return true;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("table_key.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<AddEditCustomerDialogInput, Void> parent) {
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
