package org.pantry.food.actions;

import org.pantry.food.Images;

import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 * Customers menu display configuration 
 */
public class VisitsMenuItem extends MenuItem {

	public VisitsMenuItem() {
		setId("open.visits");
		setGraphic(new ImageView(Images.getIcon("cart.png")));  
		setText("Visits");   
	}
	
}
