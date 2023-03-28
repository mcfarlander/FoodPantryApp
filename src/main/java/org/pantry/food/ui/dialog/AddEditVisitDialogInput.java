package org.pantry.food.ui.dialog;

import java.util.List;

import org.pantry.food.model.Visit;

/**
 * Arguments that can be provided to the {@link AddEditVisitDialog}
 */
public class AddEditVisitDialogInput {

	private List<String> householdIds;
	private Visit visit;

	public List<String> getHouseholdIds() {
		return householdIds;
	}

	public void setHouseholdIds(List<String> householdIds) {
		this.householdIds = householdIds;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}
}
