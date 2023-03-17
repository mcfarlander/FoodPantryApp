package org.pantry.food.actions;

import org.pantry.food.Images;

import javafx.scene.control.MenuItem;

/**
 * About menu item display configuration 
 */
public class AboutMenuItem extends MenuItem {

	public AboutMenuItem() {
		setId("open.about");
		setGraphic(Images.getImageView("help.png"));
		setText("About");   
	}
	
}
