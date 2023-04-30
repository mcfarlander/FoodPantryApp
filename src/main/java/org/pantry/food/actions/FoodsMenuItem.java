package org.pantry.food.actions;

import org.pantry.food.Fxmls;
import org.pantry.food.Images;
import org.pantry.food.SwitchContextEventHandler;
import org.pantry.food.UiMainContext;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Foods menu display configuration
 */
public class FoodsMenuItem extends MenuItem {
	public static final String ACTION_ID = "open.foods";

	public FoodsMenuItem() {
		setId(ACTION_ID);
		setAccelerator(KeyCombination.keyCombination("shortcut+3"));
		setGraphic(Images.getImageView("tag_blue.png"));
		setText("Foods");
	}

	public FoodsMenuItem(UiMainContext context) {
		this();
		setOnAction(new SwitchContextEventHandler(context, e -> Fxmls.loadCached("Foods.fxml")));
	}

}
