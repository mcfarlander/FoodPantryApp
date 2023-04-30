package org.pantry.food.ui.dialog;

import java.util.List;

import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerEvent;

/**
 * Arguments that can be provided to the {@link AddEditVolunteerEventDialog}
 */
public class AddEditVolunteerEventDialogInput {

	private VolunteerEvent event;
	private List<Volunteer> volunteers;

	public VolunteerEvent getEvent() {
		return event;
	}

	public void setEvent(VolunteerEvent event) {
		this.event = event;
	}

	public List<Volunteer> getVolunteers() {
		return volunteers;
	}

	public void setVolunteers(List<Volunteer> volunteers) {
		this.volunteers = volunteers;
	}

}
