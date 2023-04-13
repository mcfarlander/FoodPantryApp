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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerEvent;

import net.sf.nervalreports.core.ReportGenerationException;

/**
 * Create a report based on the volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerEvents extends ReportBase {
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	final NumberFormat nf = NumberFormat.getInstance();

	private static final int REGULAR = 0;
	private static final int STUDENT = 1;
	private static final int SPECIAL = 2;
	private static final int OTHER = 3;
	private static final int UNKNOWN = 4;

	private String[] cols = new String[] { "Volunteer", "Type", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec", "Total" };

	private VolunteersDao volunteersDao = ApplicationContext.getVolunteersDao(); // used to find the type of volunteer
																					// based on voluneer's
																					// name

	public ReportVolunteerEvents() {
		setReportName("Volunteer_Events");
		setReportTitle("Summary Of Volunteer Events");
		setReportDescription(dateFormat.format(Calendar.getInstance().getTime()));

		nf.setMaximumFractionDigits(2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.ReportBase#createReportTable()
	 */
	@Override
	public void buildReport() throws ReportGenerationException {
		// create the basic table
		report.beginTable(cols.length);
		// create a header row
		report.beginTableHeaderRow();
		for (int x = 0; x < cols.length; x++) {
			report.addTableHeaderCell(cols[x]);
		}
		report.endTableHeaderRow();

		// create rest of the data rows
		loadReport();

		// finish the table
		report.endTable();
	}

	private void addTableRow(String col1, String col2, String col3, String col4, String col5, String col6, String col7,
			String col8, String col9, String col10, String col11, String col12, String col13, String col14,
			String col15) throws ReportGenerationException {
		report.beginTableRow();
		report.addTableCell(col1);
		report.addTableCell(col2);
		report.addTableCell(col3);
		report.addTableCell(col4);
		report.addTableCell(col5);
		report.addTableCell(col6);
		report.addTableCell(col7);
		report.addTableCell(col8);
		report.addTableCell(col9);
		report.addTableCell(col10);
		report.addTableCell(col11);
		report.addTableCell(col12);
		report.addTableCell(col13);
		report.addTableCell(col14);
		report.addTableCell(col15);
		report.endTableRow();
	}

	private void loadReport() throws ReportGenerationException {
		VolunteerEventsDao eventsDao = ApplicationContext.getVolunteerEventsDao();

		// Regular volunteers
		List<VolunteerEvent> volunteers = new ArrayList<VolunteerEvent>();
		double[] monthTotals = new double[12];
		double yearTotal = 0;

		for (VolunteerEvent record : eventsDao.getAll()) {
			String name = record.getVolunteerName();

			boolean bFound = false;
			VolunteerEvent vol = record;
			for (VolunteerEvent e : volunteers) {
				if (name.equals(e.getVolunteerName())) {
					bFound = true;
					vol.addMonthHrs(record);
				}
			}

			if (!bFound && (vol != null)) {
				vol = new VolunteerEvent();
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
				addTableRow("", "Regular", "", "", "", "", "", "", "", "", "", "", "", "", "");
				break;
			case STUDENT:
				addTableRow("", "Student", "", "", "", "", "", "", "", "", "", "", "", "", "");
				break;
			case SPECIAL:
				addTableRow("", "Special", "", "", "", "", "", "", "", "", "", "", "", "", "");
				break;
			case OTHER:
				addTableRow("", "Other", "", "", "", "", "", "", "", "", "", "", "", "", "");
				break;
			case UNKNOWN:
				addTableRow("", "Unknown", "", "", "", "", "", "", "", "", "", "", "", "", "");
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
					addTableRow(vol.getVolunteerName(), "", "" + vol.getMonthHrs()[0], "" + vol.getMonthHrs()[1],
							"" + vol.getMonthHrs()[2], "" + vol.getMonthHrs()[3], "" + vol.getMonthHrs()[4],
							"" + vol.getMonthHrs()[5], "" + vol.getMonthHrs()[6], "" + vol.getMonthHrs()[7],
							"" + vol.getMonthHrs()[8], "" + vol.getMonthHrs()[9], "" + vol.getMonthHrs()[10],
							"" + vol.getMonthHrs()[11], "" + vol.getVolunteerHours());
				}

			} // loop thru volunteers

			addTableRow("  Group Totals", "", "" + groupMonthTotals[0], "" + groupMonthTotals[1],
					"" + groupMonthTotals[2], "" + groupMonthTotals[3], "" + groupMonthTotals[4],
					"" + groupMonthTotals[5], "" + groupMonthTotals[6], "" + groupMonthTotals[7],
					"" + groupMonthTotals[8], "" + groupMonthTotals[9], "" + groupMonthTotals[10],
					"" + groupMonthTotals[11], "" + groupTotal);

		} // loop thru types

		addTableRow("", "", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "");

		addTableRow("  Month Totals", "", "" + monthTotals[0], "" + monthTotals[1], "" + monthTotals[2],
				"" + monthTotals[3], "" + monthTotals[4], "" + monthTotals[5], "" + monthTotals[6], "" + monthTotals[7],
				"" + monthTotals[8], "" + monthTotals[9], "" + monthTotals[10], "" + monthTotals[11], "" + yearTotal);
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

	@Override
	public void createFooter() {

	}

} // end of class
