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

import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerType;
import org.pantry.food.ui.ValidStatusTracker;
import org.pantry.food.ui.validation.ComboInputValidator;
import org.pantry.food.ui.validation.NotBlankValidator;
import org.pantry.food.ui.validation.TextInputFocusValidator;

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

public class AddEditVolunteerDialogController
		implements IModalDialogController<AddEditVolunteerDialogInput, Volunteer> {
	private static final Logger log = LogManager.getLogger(AddEditVolunteerDialogController.class.getName());

	@FXML
	private Label volunteerIdLabel;
	@FXML
	private ComboBox<String> typeCbo;

	@FXML
	private TextField nameText;
	@FXML
	private TextField phoneText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField notesText;

	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<AddEditVolunteerDialogInput, Volunteer> parent;
	private AddEditVolunteerDialogInput input;
	private boolean isSaved = false;
	private ValidStatusTracker validStatusTracker;
	private Volunteer savedVolunteer = null;

	@FXML
	private void initialize() {
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!validStatusTracker.checkAll()) {
					return;
				}

				boolean isNew = savedVolunteer.getVolunteerId() <= 0;
				log.info("Attempting to save {} volunteer record {}", isNew ? "new" : "existing",
						isNew ? savedVolunteer.getVolunteerId() : "");

				// Attempt to save the record
				// We only ever use the CustomersDao in this handler, so no reason to make it
				// available to the rest of this class
				VolunteersDao dao = ApplicationContext.getVolunteersDao();
				if (isNew) {
					savedVolunteer.setVolunteerId(dao.getNextId());
					dao.add(savedVolunteer);
				} else {
					dao.edit(savedVolunteer);
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
	public void setInput(AddEditVolunteerDialogInput input) {
		this.input = input;
		boolean isNew = null == input.getVolunteer();
		log.info("Opening dialog to {} a volunteer record", isNew ? "add" : "edit");
		savedVolunteer = new Volunteer(input.getVolunteer());

		for (VolunteerType vt : VolunteerType.values()) {
			typeCbo.getItems().add(WordUtils.capitalizeFully(vt.name()));
		}

		// Bind all the inputs to their Volunteer properties
		volunteerIdLabel.textProperty().bind(savedVolunteer.volunteerIdProperty());
		typeCbo.valueProperty().bindBidirectional(savedVolunteer.typeProperty());

		nameText.textProperty().bindBidirectional(savedVolunteer.nameProperty());
		phoneText.textProperty().bindBidirectional(savedVolunteer.phoneProperty());
		emailText.textProperty().bindBidirectional(savedVolunteer.emailProperty());
		notesText.textProperty().bindBidirectional(savedVolunteer.noteProperty());

		// Bind the input validators
		log.debug("Binding input validators");
		// Only Type and Name are required
		ComboInputValidator comboValidator = new ComboInputValidator(typeCbo).add(new NotBlankValidator());
		typeCbo.getSelectionModel().selectedItemProperty().addListener(comboValidator);

		TextInputFocusValidator textValidator = new TextInputFocusValidator(nameText).add(new NotBlankValidator());
		nameText.focusedProperty().addListener(textValidator);

		validStatusTracker.add(typeCbo, nameText);
		saveBtn.disableProperty().bind(validStatusTracker.validProperty().not());
	}

	@Override
	public String getTitle() {
		String type = "Add";
		if (null != input.getVolunteer()) {
			type = "Edit";
		}
		return type + " Volunteer";
	}

	@Override
	public Volunteer getResponse() {
		return isSaved ? savedVolunteer : null;
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
	public void setModalDialogParent(ModalDialog<AddEditVolunteerDialogInput, Volunteer> parent) {
		this.parent = parent;
	}

}
