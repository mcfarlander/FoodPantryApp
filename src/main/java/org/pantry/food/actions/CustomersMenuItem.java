package org.pantry.food.actions;

import org.pantry.food.Fxmls;
import org.pantry.food.Images;
import org.pantry.food.SwitchContextEventHandler;
import org.pantry.food.UiMainContext;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Customers menu display configuration
 */
public class CustomersMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.customers";

	public CustomersMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+1"));
		setGraphic(Images.getImageView("table_key.png"));
		setText("Customers");
	}

	public CustomersMenuItem(UiMainContext context) {
		this();
		setOnAction(new SwitchContextEventHandler(context, e -> Fxmls.loadCached("Customers.fxml")));
	}

}
