package org.pantry.food.ui.dialog;

import java.util.List;

import org.pantry.food.model.Donor;
import org.pantry.food.model.Food;

/**
 * Arguments that can be provided to the {@link AddEditFoodDialog}
 */
public class AddEditFoodDialogInput {

	private Food food;
	private List<Donor> donors;

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public List<Donor> getDonors() {
		return donors;
	}

	public void setDonors(List<Donor> donors) {
		this.donors = donors;
	}
}
