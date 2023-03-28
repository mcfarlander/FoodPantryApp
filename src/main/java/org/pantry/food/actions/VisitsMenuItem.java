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
public class VisitsMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.visits";

	public VisitsMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+2"));
		setGraphic(Images.getImageView("cart.png"));
		setText("Visitors");
	}

	public VisitsMenuItem(EventHandler<ActionEvent> onActionHandler) {
		this();
		setOnAction(onActionHandler);
	}

	public VisitsMenuItem(UiMainContext context) {
		this();
		setOnAction(new SwitchContextEventHandler(context, e -> Fxmls.loadCached("Visits.fxml")));
	}

}
