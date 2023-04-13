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
public class ReportsMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.reports";

	public ReportsMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+6"));
		setGraphic(Images.getImageView("user_go.png"));
		setText("Reports");
	}

	public ReportsMenuItem(EventHandler<ActionEvent> onActionHandler) {
		this();
		setOnAction(onActionHandler);
	}

	public ReportsMenuItem(UiMainContext context) {
		this();
		setOnAction(new SwitchContextEventHandler(context, e -> Fxmls.loadCached("Reports.fxml")));
	}

}
