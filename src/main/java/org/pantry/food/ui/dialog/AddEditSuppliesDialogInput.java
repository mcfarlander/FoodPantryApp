package org.pantry.food.ui.dialog;

import java.util.List;

import org.pantry.food.model.Donor;
import org.pantry.food.model.Supplies;

/**
 * Arguments that can be provided to the {@link AddEditSuppliesDialogInput}
 */
public class AddEditSuppliesDialogInput {

	private Supplies supplies;
	private List<Donor> donors;

	public Supplies getSupplies() {
		return supplies;
	}

	public void setSupplies(Supplies supplies) {
		this.supplies = supplies;
	}

	public List<Donor> getDonors() {
		return donors;
	}

	public void setDonors(List<Donor> donors) {
		this.donors = donors;
	}
}
