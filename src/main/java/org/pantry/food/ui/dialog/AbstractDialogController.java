package org.pantry.food.ui.dialog;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public abstract class AbstractDialogController<T, DIT> {

	private static final Logger log = LogManager.getLogger();

	@FXML
	private Button addBtn;

	@FXML
	private Button editBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	public void initialize() {
		addBtn.setGraphic(getAddButtonGraphic());
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Open Add/Edit dialog
				try {
					DIT input = getAddDialogInput();
					new ModalDialog<DIT, T>().show(getAddEditDialogFxmlFile(), input);
					// When the new customer is saved to the data file, the controller's file change
					// listener will be invoked which will trigger a refresh of the data table - so
					// no need to handle that here
				} catch (IOException e) {
					log.error("Could not add customer", e);
					new Alert(AlertType.ERROR, "Could not add customer\r\n" + e.getMessage()).show();
				}
			}

		});
	}

	protected abstract ImageView getAddButtonGraphic();

	protected abstract ImageView getEditButtonGraphic();

	protected abstract ImageView getDeleteButtonGraphic();

	protected abstract String getAddEditDialogFxmlFile();

	protected abstract DIT getAddDialogInput();

	protected abstract DIT getEditDialogInput();

}
