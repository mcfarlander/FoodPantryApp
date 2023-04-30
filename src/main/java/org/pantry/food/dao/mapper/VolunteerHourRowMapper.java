package org.pantry.food.dao.mapper;

import org.pantry.food.model.VolunteerHoursSummary;

public class VolunteerHourRowMapper implements ArrayRowMapper<VolunteerHoursSummary> {

	private static final int VOLUNTEERHOURID = 0;
	private static final int NUMBER_ADULTS = 1;
	private static final int HOURS_ADULTS = 2;
	private static final int NUMBER_STUDENTS = 3;
	private static final int HOURS_STUDENTS = 4;
	private static final int COMMENT = 5;
	private static final int ENTRY_DATE = 6;

	@Override
	public VolunteerHoursSummary map(String[] row) {
		VolunteerHoursSummary hour = new VolunteerHoursSummary();
		hour.setVolunteerHourId(Integer.parseInt(row[VOLUNTEERHOURID]));
		hour.setNumberAdults(Integer.parseInt(row[NUMBER_ADULTS]));
		hour.setNumberAdultHours(Float.parseFloat(row[HOURS_ADULTS]));
		hour.setNumberStudents(Integer.parseInt(row[NUMBER_STUDENTS]));
		hour.setNumberStudentHours(Float.parseFloat(row[HOURS_STUDENTS]));
		hour.setComment(row[COMMENT]);
		hour.setEntryDate(row[ENTRY_DATE]);

		return hour;
	}

	public String[] toCsvRow(VolunteerHoursSummary hour) {
		return new String[] { String.valueOf(hour.getVolunteerHourId()), String.valueOf(hour.getNumberAdults()),
				String.valueOf(hour.getNumberAdultHours()), String.valueOf(hour.getNumberStudents()),
				String.valueOf(hour.getNumberStudentHours()), String.valueOf(hour.getComment()),
				String.valueOf(hour.getEntryDate()) };
	}

}
