package org.pantry.food.actions;

import java.io.IOException;

import org.pantry.food.Images;
import org.pantry.food.UiMainContext;
import org.pantry.food.ui.dialog.ModalDialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Settings menu display configuration
 */
public class SettingsMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.settings";

	public SettingsMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+7"));
		setGraphic(Images.getImageView("wrench_orange.png"));
		setText("Settings");
	}

	public SettingsMenuItem(EventHandler<ActionEvent> onActionHandler) {
		this();
		setOnAction(onActionHandler);
	}

	public SettingsMenuItem(UiMainContext context) {
		this();

		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new ModalDialog<Void, Void>().show("Settings.fxml", null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
