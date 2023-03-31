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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.Resources;
import org.pantry.food.ui.dialog.IModalDialogController;
import org.pantry.food.ui.dialog.ModalDialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

		archiveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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
			try {
				resources.save();
				// Notify any open data viewers that settings have changed
				ApplicationContext.settingsChanged();
			} catch (IOException e) {
				log.error("Could not save properties file", e);
				new Alert(AlertType.ERROR, "Could not save properties file\r\n" + e.getMessage());
			}
		}
	}

}
