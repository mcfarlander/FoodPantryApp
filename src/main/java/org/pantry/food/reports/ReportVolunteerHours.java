/*
  Copyright 2011 Dave Johnson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.pantry.food.reports;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerEvent;
import org.pantry.food.model.VolunteerHour;
import org.pantry.food.util.DateUtil;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Create a report on the actual volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerHours extends AbstractReportStrategy {
	private static final Logger log = LogManager.getLogger(ReportVolunteerHours.class);
	final NumberFormat nf = NumberFormat.getInstance();

	private String[] cols = new String[] { "Month", "Number Adults", "Total Adult Hrs", "Number Students",
			"Total Student Hrs", "Comments" };

	public ReportVolunteerHours() {
		nf.setMaximumFractionDigits(1);
	}

	@Override
	public String getTitle() {
		return "Summary Of Volunteer Hours";
	}

	@Override
	public ObservableList<TableColumn<ReportRow, String>> getColumns() {
		return toTableColumns(cols);
	}

	@Override
	public List<ReportRow> getRows() {
		List<ReportRow> rows = new ArrayList<>();

		VolunteersDao volunteersDao = ApplicationContext.getVolunteersDao();
		VolunteerEventsDao eventsDao = ApplicationContext.getVolunteerEventsDao();

		List<String> studentVolunteers = new ArrayList<>();
		Map<String, VolunteerHour> monthToHours = new HashMap<>();

		String date = "";
		try {
			// First separate volunteer names into students and everyone else ("Adults")
			for (Volunteer volunteer : volunteersDao.getAll()) {
				if ("Student".equalsIgnoreCase(volunteer.getType())) {
					studentVolunteers.add(volunteer.getName().toLowerCase());
				}
			}

			// Now spin through the volunteer events and tally up the adult hours and
			// student hours for each month
			for (VolunteerEvent event : eventsDao.getAll()) {
				if (event.getVolunteerHours() <= 0) {
					// Since this is a summary report, skip any entries that have no hours
					continue;
				}

				// Figure out which month bucket to put these hours into
				date = event.getEventDate();
				LocalDate eventDate = DateUtil.toDate(date);
				String monthName = DateUtil.getMonthName(eventDate.getMonthValue());

				// Figure out if this is student hours or adult hours
				VolunteerHour hours = new VolunteerHour();
				if (studentVolunteers.contains(event.getVolunteerName().toLowerCase())) {
					hours.setNumberStudents(1);
					hours.setNumberStudentHours((float) event.getVolunteerHours());
				} else {
					hours.setNumberAdults(1);
					hours.setNumberAdultHours((float) event.getVolunteerHours());
				}
				hours.setComment(event.getNotes());

				// Update month tally if it already exists, or create a new one if it doesn't
				VolunteerHour monthHours = monthToHours.get(monthName);
				if (null == monthHours) {
					monthHours = new VolunteerHour();
					monthToHours.put(monthName, monthHours);
				}
				monthHours.addToCurrent(hours);
			}

			Locale locale = Locale.getDefault();
			VolunteerHour grandTotalHours = new VolunteerHour();
			// Create a row for every possible month
			for (Month month : Month.values()) {
				// Create a blank row above this row if it's the first month of a quarter
				// Except for January, the first month we display
				if (month.equals(month.firstMonthOfQuarter()) && !month.equals(Month.JANUARY)) {
					createBlankRow();
				}

				// First see if there's an entry for this month
				String monthName = month.getDisplayName(TextStyle.SHORT, locale);
				VolunteerHour hours = monthToHours.get(monthName);
				if (null == hours) {
					// Use all zeros instead
					hours = new VolunteerHour();
				}

				rows.add(createTableRow(monthName, hours));

				// Add this monthly record to the grand total
				grandTotalHours.addToCurrent(hours);
			}

			rows.add(createTableRow("Totals", grandTotalHours).setSummary(true));
		} catch (ParseException ex) {
			log.error("Could not parse date " + date, ex);
		}

		return rows;
	}

	private ReportRow createTableRow(String month, VolunteerHour hours) {
		ReportRow row = new ReportRow();
		row.addColumn(month);
		row.addColumn(nf.format(hours.getNumberAdults()));
		row.addColumn(nf.format(hours.getNumberAdultHours()));
		row.addColumn(nf.format(hours.getNumberStudents()));
		row.addColumn(nf.format(hours.getNumberStudentHours()));
		row.addColumn(hours.getComment());

		return row;
	}

	private ReportRow createBlankRow() {
		ReportRow row = new ReportRow();
		for (int x = 1; x <= 6; x++) {
			row.addColumn("");
		}

		return row;
	}

}
