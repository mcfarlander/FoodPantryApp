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

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.model.Visit;
import org.pantry.food.ui.validation.ComboInputValidator;
import org.pantry.food.ui.validation.DateValidator;
import org.pantry.food.ui.validation.NotNegativeValidator;
import org.pantry.food.ui.validation.RegexValidator;
import org.pantry.food.ui.validation.TextInputFocusValidator;
import org.pantry.food.ui.validation.ValidStatusTracker;
import org.pantry.food.util.DateUtil;

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

public class AddEditVisitDialogController implements IModalDialogController<AddEditVisitDialogInput, Visit> {
	private static final Logger log = LogManager.getLogger(AddEditVisitDialogController.class.getName());

	@FXML
	private ComboBox<String> householdIdCbo;
	@FXML
	private CheckBox newChk;

	@FXML
	private TextField numAdultsText;
	@FXML
	private TextField numKidsText;
	@FXML
	private TextField numSeniorsText;

	@FXML
	private CheckBox workingIncomeChk;
	@FXML
	private CheckBox noIncomeChk;
	@FXML
	private CheckBox otherIncomeChk;

	@FXML
	private TextField visitDateText;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<AddEditVisitDialogInput, Visit> parent;
	private AddEditVisitDialogInput input;
	private boolean isSaved = false;
	private ValidStatusTracker validStatusTracker;
	private Visit savedVisit = null;

	@FXML
	private void initialize() {
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!validStatusTracker.checkAll()) {
					return;
				}

				// We can't calculate week number on focus lost because the user doesn't have to
				// focus on the visit date box
				calculateAndSetWeekNumber();

				boolean isNew = savedVisit.getId() <= 0;
				log.info("Attempting to save {} visit record {}", isNew ? "new" : "existing",
						isNew ? savedVisit.getId() : "");

				// Attempt to save the record
				// We only ever use the CustomersDao in this handler, so no reason to make it
				// available to the rest of this class
				VisitsDao dao = ApplicationContext.getVisitsDao();
				if (isNew) {
					savedVisit.setId(dao.getNextId());
					savedVisit.setActive(true);
					dao.add(savedVisit);
				} else {
					dao.edit(savedVisit);
				}

				try {
					dao.persist();
					isSaved = true;
					parent.close();
				} catch (IOException e) {
					String message = "Could not " + (isNew ? "add" : "edit") + " record\r\n" + e.getMessage();
					log.error(message, e);
					new Alert(AlertType.WARNING, message).show();
				}
			}

		});

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				log.info("User canceled add/edit visit record");
				// Close the dialog
				parent.close();
			}

		});

		validStatusTracker = new ValidStatusTracker();
	}

	@Override
	public void setInput(AddEditVisitDialogInput input) {
		this.input = input;
		boolean isNew = null == input.getVisit();
		log.info("Opening dialog to {} a visit record", isNew ? "add" : "edit");
		savedVisit = new Visit(input.getVisit());

		householdIdCbo.getItems().add("");
		if (null != input.getHouseholdIds()) {
			householdIdCbo.getItems().addAll(input.getHouseholdIds());
		}

		// Bind all the inputs to their Visit properties
		householdIdCbo.valueProperty().bindBidirectional(savedVisit.householdIdProperty());
		newChk.selectedProperty().bindBidirectional(savedVisit.newCustomerProperty());

		numAdultsText.textProperty().bindBidirectional(savedVisit.numberAdultsProperty());
		numKidsText.textProperty().bindBidirectional(savedVisit.numberKidsProperty());
		numSeniorsText.textProperty().bindBidirectional(savedVisit.numberSeniorsProperty());

		workingIncomeChk.selectedProperty().bindBidirectional(savedVisit.workingIncomeProperty());
		otherIncomeChk.selectedProperty().bindBidirectional(savedVisit.otherIncomeProperty());
		noIncomeChk.selectedProperty().bindBidirectional(savedVisit.noIncomeProperty());

		visitDateText.textProperty().bindBidirectional(savedVisit.dateProperty());

		// Bind the input validators
		// Note: By Sue's request, some visits can get entered without a customer
		// number
		log.debug("Binding input validators");
		ComboInputValidator comboValidator = new ComboInputValidator(householdIdCbo)
				.add(new RegexValidator("[0-9]{1,}"));
		householdIdCbo.getSelectionModel().selectedItemProperty().addListener(comboValidator);

		TextInputFocusValidator textValidator = new TextInputFocusValidator(numAdultsText)
				.add(new NotNegativeValidator());
		numAdultsText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(numKidsText).add(new NotNegativeValidator());
		numKidsText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(numSeniorsText).add(new NotNegativeValidator());
		numSeniorsText.focusedProperty().addListener(textValidator);

		// Visit Date must comply with typical date format
		textValidator = new TextInputFocusValidator(visitDateText).add(new DateValidator());
		visitDateText.focusedProperty().addListener(textValidator);
		// Calculate the week number when the visit date changes
		visitDateText.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
				if (wasFocused && !isFocused && DateUtil.isDate(visitDateText.getText())) {

				}
			}

		});

		// If this is a visit add, pre-select the New and Visit Date values
		if (isNew) {
			newChk.setSelected(true);
			visitDateText.setText(DateUtil.getCurrentDateStringFourDigitYear());
		}

		validStatusTracker.add(householdIdCbo, newChk, numAdultsText, numKidsText, numSeniorsText, visitDateText);
		saveBtn.disableProperty().bind(validStatusTracker.validProperty().not());
	}

	@Override
	public String getTitle() {
		String type = "Add";
		if (null != input.getVisit()) {
			type = "Edit";
		}
		return type + " Visit";
	}

	@Override
	public Visit getResponse() {
		return isSaved ? savedVisit : null;
	}

	@Override
	public boolean isPositiveResponse() {
		return isSaved;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("cart.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<AddEditVisitDialogInput, Visit> parent) {
		this.parent = parent;
	}

	private void calculateAndSetWeekNumber() {
		String dateStr = visitDateText.getText();
		log.trace("Calculating week number, input is {}", dateStr);

		try {
			// Find the week number for this date
			LocalDate visitDate = DateUtil.toDate(dateStr);
			// Two-digit years are considered valid, but are the year 23 AD
			if (visitDate.getYear() < 2000) {
				visitDate = visitDate.plusYears(2000);
				visitDateText.setText(DateUtil.formatDateFourDigitYear(visitDate));
			}
			int weekNumber = visitDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
			savedVisit.setWeekNumber(weekNumber);
		} catch (ParseException e) {
			log.error("Invalid visit date - date {} could not be parsed", dateStr, e);
			new Alert(AlertType.WARNING, "Invalid visit date").show();
		}
	}

}
