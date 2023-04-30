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
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.dao.SuppliesDao;
import org.pantry.food.model.Donor;
import org.pantry.food.model.Supplies;
import org.pantry.food.ui.validation.DateValidator;
import org.pantry.food.ui.validation.NotBlankValidator;
import org.pantry.food.ui.validation.NotNegativeValidator;
import org.pantry.food.ui.validation.TextInputFocusValidator;
import org.pantry.food.ui.validation.ValidStatusTracker;
import org.pantry.food.util.DateUtil;
import org.pantry.food.util.ValidationStyleUtil;

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
import javafx.scene.layout.GridPane;

public class AddEditSuppliesDialogController implements IModalDialogController<AddEditSuppliesDialogInput, Supplies> {
	private static final Logger log = LogManager.getLogger(AddEditSuppliesDialogController.class.getName());

	@FXML
	private TextField pickNSaveText;
	@FXML
	private TextField communityText;
	@FXML
	private TextField nonTefapText;
	@FXML
	private TextField tefapText;
	@FXML
	private TextField secondHarvestText;
	@FXML
	private TextField secondHarvestProduceText;
	@FXML
	private TextField pantryText;
	@FXML
	private TextField pantryNonFoodText;
	@FXML
	private TextField nonFoodText;
	@FXML
	private TextField milkText;
	@FXML
	private TextField pantryProduceText;
	@FXML
	private TextField produceText;
	@FXML
	private TextField dateText;
	@FXML
	private TextField commentsText;
	@FXML
	private GridPane donationGrd;
	@FXML
	private CheckBox donationChk;
	@FXML
	private ComboBox<Donor> donorCbo;
	@FXML
	private TextField donorAddressText;
	@FXML
	private TextField donorEmailText;

	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<AddEditSuppliesDialogInput, Supplies> parent;
	private AddEditSuppliesDialogInput input;
	private boolean isSaved = false;
	private ValidStatusTracker validStatusTracker;
	private Supplies savedFood = null;

	@FXML
	private void initialize() {
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Little hack here - force focus on Donor Name combo if the Is Donation
				// checkbox is checked so the validation is triggered
				if (donationChk.isSelected()) {
					donorCbo.requestFocus();
					saveBtn.requestFocus();
				}
				if (!validStatusTracker.checkAll()) {
					return;
				}

				boolean isNew = savedFood.getId() <= 0;
				log.info("Attempting to save {} food record {}", isNew ? "new" : "existing",
						isNew ? savedFood.getId() : "");

				// Attempt to save the record
				// We only ever use the CustomersDao in this handler, so no reason to make it
				// available to the rest of this class
				SuppliesDao dao = ApplicationContext.getSuppliesDao();
				if (isNew) {
					savedFood.setId(dao.getNextId());
					dao.add(savedFood);
				} else {
					dao.edit(savedFood);
				}

				try {
					dao.persist();
					isSaved = true;
					parent.close();
				} catch (IOException e) {
					String message = "Could not " + (isNew ? "add" : "edit") + " food record\r\n" + e.getMessage();
					log.error(message, e);
					new Alert(AlertType.WARNING, message).show();
				}
			}

		});

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				log.info("User canceled add/edit food record");
				// Close the dialog
				parent.close();
			}

		});

		validStatusTracker = new ValidStatusTracker();
	}

	@Override
	public void setInput(AddEditSuppliesDialogInput input) {
		this.input = input;
		boolean isNew = null == input.getSupplies();
		log.info("Opening dialog to {} a food record", isNew ? "add" : "edit");
		savedFood = new Supplies(input.getSupplies());

		// Bind all the inputs to their Food properties
		pickNSaveText.textProperty().bindBidirectional(savedFood.pickNSaveProperty());
		communityText.textProperty().bindBidirectional(savedFood.communityProperty());
		nonTefapText.textProperty().bindBidirectional(savedFood.nonTefapProperty());
		tefapText.textProperty().bindBidirectional(savedFood.tefapProperty());
		secondHarvestText.textProperty().bindBidirectional(savedFood.secondHarvestProperty());
		secondHarvestProduceText.textProperty().bindBidirectional(savedFood.secondHarvestProduceProperty());
		pantryText.textProperty().bindBidirectional(savedFood.pantryProperty());
		pantryNonFoodText.textProperty().bindBidirectional(savedFood.pantryNonFoodProperty());
		nonFoodText.textProperty().bindBidirectional(savedFood.nonFoodProperty());
		pantryProduceText.textProperty().bindBidirectional(savedFood.pantryProduceProperty());
		produceText.textProperty().bindBidirectional(savedFood.produceProperty());
		milkText.textProperty().bindBidirectional(savedFood.milkProperty());

		dateText.textProperty().bindBidirectional(savedFood.entryDateProperty());
		commentsText.textProperty().bindBidirectional(savedFood.commentProperty());

		donationChk.selectedProperty().bindBidirectional(savedFood.donationProperty());

		donorCbo.getItems().addAll(input.getDonors());

		donorAddressText.textProperty().bindBidirectional(savedFood.donorAddressProperty());
		donorEmailText.textProperty().bindBidirectional(savedFood.donorEmailProperty());

		// If this is a new record, prepopulate the Event Date
		if (isNew) {
			dateText.setText(DateUtil.getCurrentDateStringFourDigitYear());
		}

		// Bind the input validators
		log.debug("Binding input validators");

		TextInputFocusValidator textValidator = new TextInputFocusValidator(pickNSaveText)
				.add(new NotNegativeValidator());
		pickNSaveText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(communityText).add(new NotNegativeValidator());
		communityText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(nonTefapText).add(new NotNegativeValidator());
		nonTefapText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(tefapText).add(new NotNegativeValidator());
		tefapText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(secondHarvestText).add(new NotNegativeValidator());
		secondHarvestText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(secondHarvestProduceText).add(new NotNegativeValidator());
		secondHarvestProduceText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(pantryText).add(new NotNegativeValidator());
		pantryText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(pantryNonFoodText).add(new NotNegativeValidator());
		pantryNonFoodText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(nonFoodText).add(new NotNegativeValidator());
		nonFoodText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(milkText).add(new NotNegativeValidator());
		milkText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(produceText).add(new NotNegativeValidator());
		produceText.focusedProperty().addListener(textValidator);

		textValidator = new TextInputFocusValidator(dateText).add(new NotBlankValidator()).add(new DateValidator());
		dateText.focusedProperty().addListener(textValidator);

		// Only show donor section if Is Donation is checked
		donationChk.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				donationGrd.setVisible(newValue);
			}
		});

		// Only validate donor selection if the Is Donation checkbox is checked
		donorCbo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasFocused, Boolean isFocused) {
				if (donationChk.isSelected() && wasFocused && !isFocused) {
					ValidationStyleUtil.validate(donorCbo, new Function<Void, Boolean>() {
						@Override
						public Boolean apply(Void t) {
							String newDonorName = donorCbo.getEditor().getText();
							// If the editor text is non-null, the user is entering a new donor
							if (null != newDonorName) {
								if ("".equals(newDonorName)) {
									// Empty string is the same as no selection
									return false;
								}
								Donor donor = new Donor(newDonorName);
								if (!donorCbo.getItems().contains(donor)) {
									donorCbo.getItems().add(donor);
								}
								donorCbo.getSelectionModel().select(donor);
								// Apply the new donor name to the food record
								savedFood.setDonorName(donor.getName());
								return true;
							}

							// Otherwise we require a selection
							Donor donor = donorCbo.getSelectionModel().getSelectedItem();
							if (null == donor) {
								return false;
							}
							if (null == donor.getName() || "".equals(donor.getName())) {
								return false;
							}
							return true;
						}
					});
				}
			}
		});

		validStatusTracker.add(pickNSaveText, communityText, nonTefapText, secondHarvestText, secondHarvestProduceText,
				pantryText, pantryNonFoodText, nonFoodText, milkText, produceText, dateText, donorCbo);
		saveBtn.disableProperty().bind(validStatusTracker.validProperty().not());
	}

	@Override
	public String getTitle() {
		String type = "Add";
		if (null != input.getSupplies()) {
			type = "Edit";
		}
		return type + " Donor";
	}

	@Override
	public Supplies getResponse() {
		return isSaved ? savedFood : null;
	}

	@Override
	public boolean isPositiveResponse() {
		return isSaved;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("tag_blue.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<AddEditSuppliesDialogInput, Supplies> parent) {
		this.parent = parent;
	}

}
