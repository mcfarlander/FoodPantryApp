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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.model.VolunteerEvent;

/**
 * Create a report based on the volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerEvents extends ReportBase {
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	NumberFormat nf = NumberFormat.getInstance();

	private static final int REGULAR = 0;
	private static final int STUDENT = 1;
	private static final int SPECIAL = 2;
	private static final int OTHER = 3;
	private static final int UNKNOWN = 4;

	private String[] cols = new String[] { "Volunteer", "Type", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec", "Total" };

	private VolunteersDao volunteerIO = new VolunteersDao(); // used to find the type of volunteer based on voluneer's
																// name

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.ReportBase#createReportTable()
	 */
	@Override
	public void createReportTable() {
		try {
			volunteerIO.read();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ReportVolunteerEvents.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ReportVolunteerEvents.class.getName()).log(Level.SEVERE, null, ex);
		}

		this.setReportName("Volunteer_Events");
		this.setReportTitle("Summary Of Volunteer Events");

		Calendar c1 = Calendar.getInstance(); // today
		this.setReportDescription(dateFormat.format(c1.getTime()));

		nf.setMaximumFractionDigits(2);

		this.createHeader();

		// create the basic table
		this.getBuffer().append("<table border='1' cellpadding='4'>");

		// create a header row
		createRow(cols[0], cols[1], cols[2], cols[3], cols[4], cols[5], cols[6], cols[7], cols[8], cols[9], cols[10],
				cols[11], cols[12], cols[13], cols[14], false, false);

		// create rest of the data rows
		loadReport();

		// finish the table
		this.getBuffer().append("</table>");

		// finish the html
		this.createFooter();

	}

	private void createRow(String col1, String col2, String col3, String col4, String col5, String col6, String col7,
			String col8, String col9, String col10, String col11, String col12, String col13, String col14,
			String col15, boolean isBlue, boolean isGrey) {

		if (isBlue) {
			this.getBuffer().append("<tr style='background-color: #6374AB'>");
		} else if (isGrey) {
			this.getBuffer().append("<tr style='background-color: #d8dce9'>");
		} else {
			this.getBuffer().append("<tr> \n");
		}

		this.getBuffer().append("<td>").append(col1).append("</td> \n");
		this.getBuffer().append("<td>").append(col2).append("</td> \n");
		this.getBuffer().append("<td>").append(col3).append("</td> \n");
		this.getBuffer().append("<td>").append(col4).append("</td> \n");
		this.getBuffer().append("<td>").append(col5).append("</td> \n");
		this.getBuffer().append("<td>").append(col6).append("</td> \n");
		this.getBuffer().append("<td>").append(col7).append("</td> \n");
		this.getBuffer().append("<td>").append(col8).append("</td> \n");
		this.getBuffer().append("<td>").append(col9).append("</td> \n");
		this.getBuffer().append("<td>").append(col10).append("</td> \n");
		this.getBuffer().append("<td>").append(col11).append("</td> \n");
		this.getBuffer().append("<td>").append(col12).append("</td> \n");
		this.getBuffer().append("<td>").append(col13).append("</td> \n");
		this.getBuffer().append("<td>").append(col14).append("</td> \n");
		this.getBuffer().append("<td>").append(col15).append("</td> \n");

		this.getBuffer().append("</tr> \n");

	}// end of createTable

//    private void addBlankLine(){
//
//            createRow("","","","", "", "", "", "", "", "", "", "", "", "", "", true, false);
//    }

	private void loadReport() {
		try {

			VolunteerEventsDao recIo = new VolunteerEventsDao();
			recIo.read();

			// create an arraylist of regular volunteers
			ArrayList<VolunteerEvent> vols = new ArrayList<VolunteerEvent>();
			double[] monthTotals = new double[12];
			double yearTotal = 0;

			for (VolunteerEvent record : recIo.getAll()) {
				String name = record.getVolunteerName();

				boolean bFound = false;
				VolunteerEvent vol = record;
				for (int j = 0; j < vols.size(); j++) {
					vol = vols.get(j);

					if (name.equals(vol.getVolunteerName())) {
						bFound = true;
						vol.addMonthHrs(record);
					}

				}

				if (!bFound && (vol != null)) {
					vol = new VolunteerEvent();
					vol.setVolunteerName(record.getVolunteerName());
					vol.addMonthHrs(record);
					vols.add(vol);
				}

			}

			// loop through the volunteerevent array list and generate a row
			// do them by type

			for (int j = 0; j < 5; j++) {
				switch (j) {
				case REGULAR:
					createRow("", "Regular", "", "", "", "", "", "", "", "", "", "", "", "", "", false, false);
					break;
				case STUDENT:
					createRow("", "Student", "", "", "", "", "", "", "", "", "", "", "", "", "", false, false);
					break;
				case SPECIAL:
					createRow("", "Special", "", "", "", "", "", "", "", "", "", "", "", "", "", false, false);
					break;
				case OTHER:
					createRow("", "Other", "", "", "", "", "", "", "", "", "", "", "", "", "", false, false);
					break;
				case UNKNOWN:
					createRow("", "Unknown", "", "", "", "", "", "", "", "", "", "", "", "", "", false, false);
					break;
				default:
					break;

				}

				double[] groupMonthTotals = new double[12];
				double groupTotal = 0;

				for (int i = 0; i < vols.size(); i++) {
					VolunteerEvent vol = vols.get(i);
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
						createRow(vol.getVolunteerName(), "", "" + vol.getMonthHrs()[0], "" + vol.getMonthHrs()[1],
								"" + vol.getMonthHrs()[2], "" + vol.getMonthHrs()[3], "" + vol.getMonthHrs()[4],
								"" + vol.getMonthHrs()[5], "" + vol.getMonthHrs()[6], "" + vol.getMonthHrs()[7],
								"" + vol.getMonthHrs()[8], "" + vol.getMonthHrs()[9], "" + vol.getMonthHrs()[10],
								"" + vol.getMonthHrs()[11], "" + vol.getVolunteerHours(), false, false);
					}

				} // loop thru volunteers

				createRow("  Group Totals", "", "" + groupMonthTotals[0], "" + groupMonthTotals[1],
						"" + groupMonthTotals[2], "" + groupMonthTotals[3], "" + groupMonthTotals[4],
						"" + groupMonthTotals[5], "" + groupMonthTotals[6], "" + groupMonthTotals[7],
						"" + groupMonthTotals[8], "" + groupMonthTotals[9], "" + groupMonthTotals[10],
						"" + groupMonthTotals[11], "" + groupTotal, false, true);

			} // loop thru types

			createRow("", "", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "",
					false, false);

			createRow("  Month Totals", "", "" + monthTotals[0], "" + monthTotals[1], "" + monthTotals[2],
					"" + monthTotals[3], "" + monthTotals[4], "" + monthTotals[5], "" + monthTotals[6],
					"" + monthTotals[7], "" + monthTotals[8], "" + monthTotals[9], "" + monthTotals[10],
					"" + monthTotals[11], "" + yearTotal, false, true);

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ReportVolunteerEvents.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ReportVolunteerEvents.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private int getTypeFromName(String name) {
		int iReturn = UNKNOWN;

		for (Volunteer volun : volunteerIO.getAll()) {
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

} // end of class
