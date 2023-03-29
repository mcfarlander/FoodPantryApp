package org.pantry.food.actions;

import org.pantry.food.Fxmls;
import org.pantry.food.Images;
import org.pantry.food.SwitchContextEventHandler;
import org.pantry.food.UiMainContext;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Visits menu display configuration
 */
public class VolunteersMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.volunteers";

	public VolunteersMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+4"));
		setGraphic(Images.getImageView("user.png"));
		setText("Volunteers");
	}

	public VolunteersMenuItem(EventHandler<ActionEvent> onActionHandler) {
		this();
		setOnAction(onActionHandler);
	}

	public VolunteersMenuItem(UiMainContext context) {
		this();
		setOnAction(new SwitchContextEventHandler(context, e -> Fxmls.loadCached("Volunteers.fxml")));
	}

}
