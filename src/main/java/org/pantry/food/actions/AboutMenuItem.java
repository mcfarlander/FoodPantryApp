package org.pantry.food.actions;

import org.pantry.food.Images;

import javafx.scene.control.MenuItem;

/**
 * About menu item display configuration
 */
public class AboutMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.about";

	public AboutMenuItem() {
		setId(ACTION_ID);
		setGraphic(Images.getImageView("help.png"));
		setText("About");
	}

}
