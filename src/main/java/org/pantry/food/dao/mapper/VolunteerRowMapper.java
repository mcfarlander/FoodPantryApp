package org.pantry.food.dao.mapper;

import org.pantry.food.model.Volunteer;

public class VolunteerRowMapper implements ArrayRowMapper<Volunteer> {

	private static final int VOLUNTEERID = 0;
	private static final int NAME = 1;
	private static final int PHONE = 2;
	private static final int EMAIL = 3;
	private static final int TYPE = 4;
	private static final int NOTE = 5;

	@Override
	public Volunteer map(String[] row) {
		Volunteer volunteer = new Volunteer();
		volunteer.setVolunteerId(Integer.parseInt(row[VOLUNTEERID]));
		volunteer.setName(row[NAME]);
		volunteer.setPhone(row[PHONE]);
		volunteer.setEmail(row[EMAIL]);
		volunteer.setType(row[TYPE]);
		volunteer.setNote(row[NOTE]);

		return volunteer;
	}

	public String[] toCsvRow(Volunteer volunteer) {
		return new String[] { String.valueOf(volunteer.getVolunteerId()), volunteer.getName(), volunteer.getPhone(),
				volunteer.getEmail(), volunteer.getType(), volunteer.getNote() };
	}

}
