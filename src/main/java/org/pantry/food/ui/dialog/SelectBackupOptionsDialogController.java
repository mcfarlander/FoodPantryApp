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
import org.pantry.food.backup.Backup;
import org.pantry.food.backup.BackupKey;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class SelectBackupOptionsDialogController implements IModalDialogController<Void, Void> {
	private static final Logger log = LogManager.getLogger(SelectBackupOptionsDialogController.class.getName());

	@FXML
	private VBox checkboxesContainer;
	@FXML
	private Button okBtn;
	@FXML
	private Button cancelBtn;

	private ModalDialog<Void, Void> parent;

	private boolean isOk = false;

	@FXML
	private void initialize() {
		okBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					runBackup();

					isOk = true;
					parent.close();
				} catch (IOException e) {
					String message = "Could not run backup: " + e.getMessage();
					log.error(message, e);
					new Alert(AlertType.WARNING, message).show();
				}
			}

		});

		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				log.info("User canceled archival");
				// Close the dialog
				parent.close();
			}

		});
	}

	@Override
	public void setInput(Void input) {
	}

	@Override
	public String getTitle() {
		return "Select Files to Back Up";
	}

	@Override
	public Void getResponse() {
		return null;
	}

	@Override
	public boolean isPositiveResponse() {
		return isOk;
	}

	@Override
	public Image getIcon() {
//		return Images.getImageView("cart.png").getImage();
		return null;
	}

	@Override
	public void setModalDialogParent(ModalDialog<Void, Void> parent) {
		this.parent = parent;
	}

	protected void runBackup() throws IOException {
		List<BackupKey> backupKeys = new ArrayList<>();

		for (Node node : checkboxesContainer.getChildren()) {
			backupKeys.add(BackupKey.fromString(node.getId()));
		}

		Backup backup = new Backup(backupKeys);
		backup.archiveFiles();
	}

}
