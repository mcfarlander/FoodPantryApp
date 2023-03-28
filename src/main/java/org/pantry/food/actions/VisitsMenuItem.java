package org.pantry.food.actions;

import org.pantry.food.Images;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Visits menu display configuration
 */
public class VisitsMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.visits";

	public VisitsMenuItem() {
		setId("open.visits");
		setAccelerator(KeyCombination.keyCombination("shortcut+2"));
		setGraphic(Images.getImageView("cart.png"));
		setText("Visits");
	}

}
