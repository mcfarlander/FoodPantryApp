package org.pantry.food.dao.mapper;

import org.pantry.food.model.Visit;

public class VisitRowMapper implements ArrayRowMapper<Visit> {

	private static final int VISITID = 0;
	private static final int HOUSEHOLDID = 1;
	private static final int NEW = 2;
	private static final int NUMBER_ADULTS = 3;
	private static final int NUMBER_KIDS = 4;
	private static final int NUMBER_SENIORS = 5;
	private static final int WORKING_INCOME = 6;
	private static final int OTHER_INCOME = 7;
	private static final int NO_INCOME = 8;
	private static final int DATE = 9;
	private static final int WEEK_NUMBER = 10;
	private static final int ACTIVE = 11;

	@Override
	public Visit map(String[] row) {
		Visit visit = new Visit();
		visit.setId(Integer.parseInt(row[VISITID]));
		visit.setHouseholdId(Integer.parseInt(row[HOUSEHOLDID]));
		visit.setNewCustomer(Boolean.parseBoolean(row[NEW]));
		visit.setNumberAdults(Integer.parseInt(row[NUMBER_ADULTS]));
		visit.setNumberKids(Integer.parseInt(row[NUMBER_KIDS]));
		visit.setNumberSeniors(Integer.parseInt(row[NUMBER_SENIORS]));
		visit.setWorkingIncome(Boolean.parseBoolean(row[WORKING_INCOME]));
		visit.setOtherIncome(Boolean.parseBoolean(row[OTHER_INCOME]));
		visit.setNoIncome(Boolean.parseBoolean(row[NO_INCOME]));
		visit.setDate(row[DATE]);
		visit.setWeekNumber(Integer.parseInt(row[WEEK_NUMBER]));
		visit.setActive(Boolean.parseBoolean(row[ACTIVE]));

		return visit;
	}

	public String[] toCsvRow(Visit visit) {
		return new String[] { "" + visit.getId(), "" + visit.getHouseholdId(), "" + visit.isNewCustomer(),
				"" + visit.getNumberAdults(), "" + visit.getNumberKids(), "" + visit.getNumberSeniors(),
				"" + visit.isWorkingIncome(), "" + visit.isOtherIncome(), "" + visit.isNoIncome(),
				"" + visit.getDate(), "" + visit.getWeekNumber(), "" + visit.isActive() };
	}

}
