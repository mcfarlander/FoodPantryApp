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
 * Volunteer Events menu display configuration
 */
public class VolunteerEventsMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.volunteerevents";

	public VolunteerEventsMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+5"));
		setGraphic(Images.getImageView("user_go.png"));
		setText("Regular Volunteer Events");
	}

	public VolunteerEventsMenuItem(EventHandler<ActionEvent> onActionHandler) {
		this();
		setOnAction(onActionHandler);
	}

	public VolunteerEventsMenuItem(UiMainContext context) {
		this();
		setOnAction(new SwitchContextEventHandler(context, e -> Fxmls.loadCached("VolunteerEvents.fxml")));
	}

}
