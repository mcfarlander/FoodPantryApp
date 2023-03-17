package org.pantry.food.actions;

import org.pantry.food.Images;

import javafx.scene.control.MenuItem;

/**
 * Visits menu display configuration 
 */
public class VisitsMenuItem extends MenuItem {

	public VisitsMenuItem() {
		setId("open.visits");
		setGraphic(Images.getImageView("cart.png"));
		setText("Visits");   
	}
	
}
