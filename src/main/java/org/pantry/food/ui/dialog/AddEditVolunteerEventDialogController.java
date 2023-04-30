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
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerEvent;
import org.pantry.food.ui.validation.ComboFocusValidator;
import org.pantry.food.ui.validation.DateValidator;
import org.pantry.food.ui.validation.NotBlankValidator;
import org.pantry.food.ui.validation.NotNegativeValidator;
import org.pantry.food.ui.validation.TextInputFocusValidator;
import org.pantry.food.ui.validation.ValidStatusTracker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class AddEditVolunteerEventDialogController
		implements IModalDialogController<AddEditVolunteerEventDialogInput, VolunteerEvent> {
	private static final Logger log = LogManager.getLogger(AddEditVolunteerEventDialogController.class.getName());

	@FXML
	private Label eventIdLabel;
	@FXML
	private TextField eventNameText;
	@FXML
	private Label copiedIconLabel;
	@FXML
	private Button copyBtn;
	@FXML
	private Button pasteBtn;
	@FXML
	private ComboBox<String> volunteerCbo;
	@FXML
	private TextField volunteerHoursText;
	@FXML
	private TextField notesText;
	@FXML
	private TextField dateText;

	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<AddEditVolunteerEventDialogInput, VolunteerEvent> parent;
	private AddEditVolunteerEventDialogInput input;
	private boolean isSaved = false;
	private ValidStatusTracker validStatusTracker;
	private VolunteerEvent savedEvent = null;

	@FXML
	private void initialize() {
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!validStatusTracker.checkAll()) {
					return;
				}

				boolean isNew = savedEvent.getVolunteerEventId() <= 0;
				log.info("Attempting to save {} volunteer record {}", isNew ? "new" : "existing",
						isNew ? savedEvent.getVolunteerEventId() : "");

				// Attempt to save the record
				// We only ever use the CustomersDao in this handler, so no reason to make it
				// available to the rest of this class
				VolunteerEventsDao dao = ApplicationContext.getVolunteerEventsDao();
				if (isNew) {
					savedEvent.setVolunteerEventId(dao.getNextId());
					dao.add(savedEvent);
				} else {
					dao.edit(savedEvent);
				}

				try {
					dao.persist();
					isSaved = true;
					parent.close();
				} catch (IOException e) {
					String message = "Could not " + (isNew ? "add" : "edit") + " volunteer record\r\n" + e.getMessage();
					log.error(message, e);
					new Alert(AlertType.WARNING, message).show();
				}
			}

		});

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				log.info("User canceled add/edit volunteer record");
				// Close the dialog
				parent.close();
			}

		});

		validStatusTracker = new ValidStatusTracker();
	}

	@Override
	public void setInput(AddEditVolunteerEventDialogInput input) {
		this.input = input;
		boolean isNew = null == input.getEvent();
		log.info("Opening dialog to {} a volunteer record", isNew ? "add" : "edit");
		savedEvent = new VolunteerEvent(input.getEvent());

		if (null == input.getVolunteers()) {
			volunteerCbo.getItems().add("");
		} else {
			List<String> volunteers = new ArrayList<>();
			for (Volunteer volunteer : input.getVolunteers()) {
				volunteers.add(volunteer.getName());
			}
			volunteerCbo.getItems().addAll(volunteers);
		}
		volunteerCbo.getSelectionModel().select(0);

		// Bind all the inputs to their VolunteerEvent properties
		eventIdLabel.textProperty().bind(savedEvent.idProperty());
		volunteerCbo.valueProperty().bindBidirectional(savedEvent.volunteerNameProperty());

		eventNameText.textProperty().bindBidirectional(savedEvent.nameProperty());
		volunteerHoursText.textProperty().bindBidirectional(savedEvent.hoursProperty());
		dateText.textProperty().bindBidirectional(savedEvent.dateProperty());
		notesText.textProperty().bindBidirectional(savedEvent.notesProperty());

		// Bind the input validators
		log.debug("Binding input validators");
		// Volunteer name is required
		TextInputFocusValidator textValidator = new ComboFocusValidator(volunteerCbo).add(new NotBlankValidator());
		volunteerCbo.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(eventNameText).add(new NotBlankValidator());
		eventNameText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(volunteerHoursText).add(new NotBlankValidator())
				.add(new NotNegativeValidator());
		volunteerHoursText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(dateText).add(new DateValidator());
		dateText.focusedProperty().addListener(textValidator);

		validStatusTracker.add(eventNameText, volunteerCbo, volunteerHoursText, dateText);
		saveBtn.disableProperty().bind(validStatusTracker.validProperty().not());

		// Set up the copy/paste button functionality
		// The icon label is hidden by default
		copiedIconLabel.setGraphic(Images.getImageView("tag_green.png"));

		copyBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String eventName = eventNameText.getText();
				if (null == eventName) {
					eventName = "";
				}
				ApplicationContext.setCopiedEventName(eventName);
				copiedIconLabel.setVisible(true);
			}

		});

		pasteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				eventNameText.setText(ApplicationContext.getCopiedEventName());
				eventNameText.requestFocus();
			}

		});
	}

	@Override
	public String getTitle() {
		String type = "Add";
		if (null != input.getEvent()) {
			type = "Edit";
		}
		return type + " Volunteer";
	}

	@Override
	public VolunteerEvent getResponse() {
		return isSaved ? savedEvent : null;
	}

	@Override
	public boolean isPositiveResponse() {
		return isSaved;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("user.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<AddEditVolunteerEventDialogInput, VolunteerEvent> parent) {
		this.parent = parent;
	}

}
