package org.pantry.food.dao.mapper;

import org.pantry.food.model.VolunteerEvent;

public class VolunteerEventRowMapper implements ArrayRowMapper<VolunteerEvent> {

	private static final int VOLUNTEEREVENTID = 0;
	private static final int EVENT_NAME = 1;
	private static final int VOLUNTEER_NAME = 2;
	private static final int VOLUNTEER_HOURS = 3;
	private static final int NOTES = 4;
	private static final int EVENT_DATE = 5;

	@Override
	public VolunteerEvent map(String[] row) {
		VolunteerEvent event = new VolunteerEvent();
		event.setVolunteerEventId(Integer.parseInt(row[VOLUNTEEREVENTID]));
		event.setEventName(row[EVENT_NAME]);
		event.setVolunteerName(row[VOLUNTEER_NAME]);
		event.setVolunteerHours(Double.parseDouble(row[VOLUNTEER_HOURS]));
		event.setNotes(row[NOTES]);
		event.setEventDate(row[EVENT_DATE]);

		return event;
	}

	public String[] toCsvRow(VolunteerEvent v) {
		return new String[] { String.valueOf(v.getVolunteerEventId()), v.getEventName(), v.getVolunteerName(),
				String.valueOf(v.getVolunteerHours()), v.getNotes(), v.getEventDate() };
	}

}
