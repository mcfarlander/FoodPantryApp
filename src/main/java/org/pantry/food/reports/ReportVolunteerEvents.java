/*
  Copyright 2012 Dave Johnson

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
import java.util.ArrayList;
import java.util.List;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerEvent;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Create a report based on the volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerEvents extends AbstractReportStrategy {
	final NumberFormat nf = NumberFormat.getInstance();

	private static final int REGULAR = 0;
	private static final int STUDENT = 1;
	private static final int SPECIAL = 2;
	private static final int OTHER = 3;
	private static final int UNKNOWN = 4;

	private String[] cols = new String[] { "Volunteer", "Type", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec", "Total" };

	// used to find the type of volunteer based on volunteer's name
	private VolunteersDao volunteersDao = ApplicationContext.getVolunteersDao();

	public ReportVolunteerEvents() {
		nf.setMaximumFractionDigits(2);
	}

	@Override
	public String getTitle() {
		return "Volunteer Events Summary";
	}

	@Override
	public ObservableList<TableColumn<ReportRow, String>> getColumns() {
		return toTableColumns(cols);
	}

	@Override
	public List<ReportRow> getRows() {
		List<ReportRow> rows = new ArrayList<ReportRow>();
		VolunteerEventsDao eventsDao = ApplicationContext.getVolunteerEventsDao();

		// Regular volunteers
		List<VolunteerEvent> volunteers = new ArrayList<VolunteerEvent>();
		double[] monthTotals = new double[12];
		double yearTotal = 0;

		for (VolunteerEvent record : eventsDao.getAll()) {
			String name = record.getVolunteerName();

			boolean bFound = false;
			for (VolunteerEvent e : volunteers) {
				if (name.equals(e.getVolunteerName())) {
					bFound = true;
					e.addMonthHrs(record);
				}
			}

			if (!bFound) {
				VolunteerEvent vol = new VolunteerEvent();
				vol.setVolunteerName(record.getVolunteerName());
				vol.addMonthHrs(record);
				volunteers.add(vol);
			}
		}

		// loop through the volunteerevent array list and generate a row
		// do them by type

		for (int j = 0; j < 5; j++) {
			switch (j) {
			case REGULAR:
				rows.add(new ReportRow().addColumn("").addColumn("Regular").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn(""));
				break;
			case STUDENT:
				rows.add(new ReportRow().addColumn("").addColumn("Student").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn(""));
				break;
			case SPECIAL:
				rows.add(new ReportRow().addColumn("").addColumn("Special").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn(""));
				break;
			case OTHER:
				rows.add(new ReportRow().addColumn("").addColumn("Other").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn(""));
				break;
			case UNKNOWN:
				rows.add(new ReportRow().addColumn("").addColumn("Unknown").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn("").addColumn("").addColumn("")
						.addColumn("").addColumn("").addColumn("").addColumn(""));
				break;
			default:
				break;

			}

			double[] groupMonthTotals = new double[12];
			double groupTotal = 0;

			for (VolunteerEvent vol : volunteers) {
				int type = getTypeFromName(vol.getVolunteerName());

				if (type == j) {
					// add to the group totals
					for (int m = 0; m < 12; m++) {
						groupMonthTotals[m] += vol.getMonthHrs()[m];
						groupTotal += vol.getMonthHrs()[m];

						monthTotals[m] += vol.getMonthHrs()[m];
						yearTotal += vol.getMonthHrs()[m];
					}

					// add a row
					ReportRow row = new ReportRow().addColumn(vol.getVolunteerName()).addColumn("");
					for (int x = 0; x < vol.getMonthHrs().length; x++) {
						row.addColumn(String.valueOf(vol.getMonthHrs()[x]));
					}
					row.addColumn(String.valueOf(vol.getVolunteerHours()));
					rows.add(row);
				}

			} // loop thru volunteers

			// Now add the group totals to the table
			ReportRow row = new ReportRow().addColumn("  Group Totals").addColumn("");
			for (int x = 0; x < groupMonthTotals.length; x++) {
				row.addColumn(String.valueOf(groupMonthTotals[x]));
			}
			row.addColumn(String.valueOf(groupTotal)).setSummary(true);
			rows.add(row);
		} // loop thru types

		// Now the month total headers
		rows.add(new ReportRow().addColumn("").addColumn("").addColumn("Jan").addColumn("Feb").addColumn("Mar")
				.addColumn("Apr").addColumn("May").addColumn("Jun").addColumn("Jul").addColumn("Aug").addColumn("Sep")
				.addColumn("Oct").addColumn("Nov").addColumn("Dec").addColumn(""));

		// Finally the month totals
		ReportRow row = new ReportRow().addColumn("  Month Totals").addColumn("");
		for (int x = 0; x < monthTotals.length; x++) {
			row.addColumn(String.valueOf(monthTotals[x]));
		}
		row.addColumn(String.valueOf(yearTotal)).isSummary();
		rows.add(row);

		return rows;
	}

	private int getTypeFromName(String name) {
		int iReturn = UNKNOWN;

		for (Volunteer volun : volunteersDao.getAll()) {
			if (name.equalsIgnoreCase(volun.getName())) {
				String sType = volun.getType();
				if ("Regular".equalsIgnoreCase(sType)) {
					iReturn = REGULAR;
				}

				if ("Student".equalsIgnoreCase(sType)) {
					iReturn = STUDENT;
				}

				if ("Special".equalsIgnoreCase(sType)) {
					iReturn = SPECIAL;
				}

				if ("Other".equalsIgnoreCase(sType)) {
					iReturn = OTHER;
				}

				break; // get out of the loop
			}
		}

		return iReturn;
	}

}
