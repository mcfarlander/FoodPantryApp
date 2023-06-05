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
package org.pantry.food.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.Resources;
import org.pantry.food.dao.CustomersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.dialog.IModalDialogController;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.util.DateUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class SettingsController implements IModalDialogController<Void, Void> {
	private static final Logger log = LogManager.getLogger(SettingsController.class.getName());

	@FXML
	private VBox controlsBox;
	@FXML
	private CheckBox showInactiveCustomersChk;
	@FXML
	private CheckBox showInactiveVisitsChk;
	@FXML
	private CheckBox showAllVisitsChk;
	@FXML
	private CheckBox showAllEventsChk;
	@FXML
	private Button updateAgesBtn;
	@FXML
	private Button archiveBtn;

	private Resources resources;

	public SettingsController() {
		resources = ApplicationContext.getResources();
	}

	@FXML
	private void initialize() {
		// When any checkbox changes, save its value to the properties file
		CheckBoxChangedListener listener = new CheckBoxChangedListener();
		for (Node node : controlsBox.getChildren()) {
			if (node instanceof CheckBox) {
				CheckBox checkbox = ((CheckBox) node);
				checkbox.setSelected(Boolean.valueOf(resources.getString(checkbox.getId())));
				checkbox.addEventHandler(ActionEvent.ACTION, listener);
			}
		}

		updateAgesBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"This will update the customer ages based on their birthday. Continue?", ButtonType.YES,
						ButtonType.NO);
				Optional<ButtonType> result = alert.showAndWait();
				if (ButtonType.NO.equals(result.get())) {
					return;
				}

				log.info("Updating customer ages based on their reported birthdates");
				boolean allupdated = updateAllAges();

				String message = allupdated ? "All ages updated." : "Not all ages were updated, check dates.";
				alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.OK);
				alert.showAndWait();

			}

		});

		archiveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"Are you sure you want to archive the files? You will be prompted for files to archive.",
						ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> result = alert.showAndWait();
				if (ButtonType.NO.equals(result.get())) {
					return;
				}

				// try {
				// log.info("Starting archiving");
				// // Launch SelectBackupOptionsDialog here
				//
				// String archiveFile = "";
				// log.info("Archive to file {} complete", archiveFile);
				//
				// new Alert(AlertType.INFORMATION, "Archive created OK. File can be found at:"
				// + archiveFile).show();
				// } catch (IOException ex) {
				// log.error("Could not archive!", ex);
				// new Alert(AlertType.INFORMATION, "Unable to archive files. See log files for
				// details.").show();
				// }
			}

		});

	}

	@Override
	public void setInput(Void input) {
	}

	@Override
	public String getTitle() {
		return "Settings";
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
		return Images.getImageView("wrench_orange.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<Void, Void> parent) {
	}

	class CheckBoxChangedListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			CheckBox checkBox = ((CheckBox) event.getSource());
			resources.set(checkBox.getId(), String.valueOf(checkBox.isSelected()));
			// Notify any open data viewers that settings have changed
			ApplicationContext.settingsChanged();
		}
	}

	/**
	 * Read the customer file and update all ages based on the reported birth date.
	 * 
	 * @return true if all were updated, false otherwise
	 */
	protected boolean updateAllAges() {

		boolean result = true;

		CustomersDao dao = ApplicationContext.getCustomersDao();

		List<Customer> customers = dao.getAll();

		for (Customer customer : customers) {

			try {
				LocalDate birthdate = DateUtil.toDate(customer.getBirthDate());
				int age = (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());

				if (age < 0 || age > 120) {
					log.debug("Birthdate {} results in invalid age {}", customer.getBirthDate(), age);
					// Try to reconcile the date by adding 1900 to the year. If the year is
					// presented in a 2-digit format, Java assumes it's a 2-digit representation of
					// a year sub-100 AD. But the user probably means a year within the 20th
					// century.
					birthdate = birthdate.plusYears(1900);
					age = (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());
					if (age < 0 || age > 120) {
						log.error("Incorrect birth year - date {} results in age {}", birthdate.toString(), age);
						result = false;
					}
				}

				if (result) {
					customer.setAge(age);
				}
			} catch (ParseException e) {
				log.error("Invalid birthdate - date {} could not be parsed", customer.getBirthDate(), e);
				result = false;
			}
		}

		try {
			dao.persist();
		} catch (IOException e) {
			log.error("Unable to update customer ages", e);
			result = false;
		}

		return result;
	}

}
