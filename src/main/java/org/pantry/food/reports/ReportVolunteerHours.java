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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VolunteerHourDao;
import org.pantry.food.model.VolunteerHour;

import net.sf.nervalreports.core.ReportGenerationException;

/**
 * Create a report on the actual volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerHours extends ReportBase {
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	NumberFormat nf = NumberFormat.getInstance();

	private String[] cols = new String[] { "Month", "Number Adults", "Total Adult Hrs", "Number Students",
			"Total Student Hrs", "Comments" };

	public ReportVolunteerHours() {
		setReportName("Volunteer_Hours");
		setReportTitle("Summary Of Volunteer Hours");
		setReportDescription(dateFormat.format(Calendar.getInstance().getTime()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.ReportBase#createReportTable()
	 */
	@Override
	public void buildReport() throws ReportGenerationException {
		nf.setMaximumFractionDigits(1);

		// create the basic table
		report.beginTable(cols.length);
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

	@Override
	public void createFooter() {

	}

	private void loadReport() throws ReportGenerationException {
		try {
			VolunteerHourDao hourDao = ApplicationContext.getVolunteerHourDao();

			// use the POJO to store the working sums
			VolunteerHour janRecord = new VolunteerHour();
			VolunteerHour febRecord = new VolunteerHour();
			VolunteerHour marRecord = new VolunteerHour();
			VolunteerHour aprRecord = new VolunteerHour();
			VolunteerHour mayRecord = new VolunteerHour();
			VolunteerHour junRecord = new VolunteerHour();
			VolunteerHour julRecord = new VolunteerHour();
			VolunteerHour augRecord = new VolunteerHour();
			VolunteerHour sepRecord = new VolunteerHour();
			VolunteerHour octRecord = new VolunteerHour();
			VolunteerHour novRecord = new VolunteerHour();
			VolunteerHour decRecord = new VolunteerHour();

			Calendar cal = Calendar.getInstance();

			for (VolunteerHour record : hourDao.getAll()) {
				Date testDate = dateFormat.parse(record.getEntryDate());
				cal.setTime(testDate);

				switch (cal.get(Calendar.MONTH)) {

				case 0:
					janRecord.addToCurrent(record);
					break;
				case 1:
					febRecord.addToCurrent(record);
					break;
				case 2:
					marRecord.addToCurrent(record);
					break;
				case 3:
					aprRecord.addToCurrent(record);
					break;
				case 4:
					mayRecord.addToCurrent(record);
					break;
				case 5:
					junRecord.addToCurrent(record);
					break;
				case 6:
					julRecord.addToCurrent(record);
					break;
				case 7:
					augRecord.addToCurrent(record);
					break;
				case 8:
					sepRecord.addToCurrent(record);
					break;
				case 9:
					octRecord.addToCurrent(record);
					break;
				case 10:
					novRecord.addToCurrent(record);
					break;
				case 11:
					decRecord.addToCurrent(record);
					break;
				default:
					break;
				}
			}

			addTableRow("January", janRecord);
			addTableRow("Febuary", febRecord);
			addTableRow("March", marRecord);

			addBlankRow();

			addTableRow("April", aprRecord);
			addTableRow("May", mayRecord);
			addTableRow("June", junRecord);

			addBlankRow();

			addTableRow("July", julRecord);
			addTableRow("August", augRecord);
			addTableRow("September", sepRecord);

			addBlankRow();

			addTableRow("October", octRecord);
			addTableRow("November", novRecord);
			addTableRow("December", decRecord);

			addBlankRow();
		} catch (ParseException ex) {
			Logger.getLogger(ReportVolunteerHours.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void addTableRow(String month, VolunteerHour hours) throws ReportGenerationException {
		report.beginTableRow();
		report.addTableCell(month);
		report.addTableCell(nf.format(hours.getNumberAdults()));
		report.addTableCell(nf.format(hours.getNumberAdultHours()));
		report.addTableCell(nf.format(hours.getNumberStudents()));
		report.addTableCell(nf.format(hours.getNumberStudentHours()));
		report.addTableCell(hours.getComment());
		report.endTableRow();
	}

	private void addBlankRow() throws ReportGenerationException {
		report.beginTableRow();
		report.addTableRow(Arrays.asList("", "", "", "", "", ""));
		report.endTableRow();
	}

}
