package org.pantry.food.ui.dialog;

import org.pantry.food.model.Volunteer;

/**
 * Arguments that can be provided to the {@link AddEditVolunteerDialog}
 */
public class AddEditVolunteerDialogInput {

	private Volunteer volunteer;

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

}
