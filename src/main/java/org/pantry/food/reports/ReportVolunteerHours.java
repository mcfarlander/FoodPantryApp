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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VolunteerHourDao;
import org.pantry.food.model.VolunteerHour;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Create a report on the actual volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerHours extends AbstractReportStrategy {
	private static final Logger log = LogManager.getLogger(ReportVolunteerHours.class);
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
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

		String date = "";
		try {
			for (VolunteerHour record : hourDao.getAll()) {
				date = record.getEntryDate();
				Date testDate = dateFormat.parse(record.getEntryDate());
				cal.setTime(testDate);

				switch (cal.get(Calendar.MONTH)) {

				case Calendar.JANUARY:
					janRecord.addToCurrent(record);
					break;
				case Calendar.FEBRUARY:
					febRecord.addToCurrent(record);
					break;
				case Calendar.MARCH:
					marRecord.addToCurrent(record);
					break;
				case Calendar.APRIL:
					aprRecord.addToCurrent(record);
					break;
				case Calendar.MAY:
					mayRecord.addToCurrent(record);
					break;
				case Calendar.JUNE:
					junRecord.addToCurrent(record);
					break;
				case Calendar.JULY:
					julRecord.addToCurrent(record);
					break;
				case Calendar.AUGUST:
					augRecord.addToCurrent(record);
					break;
				case Calendar.SEPTEMBER:
					sepRecord.addToCurrent(record);
					break;
				case Calendar.OCTOBER:
					octRecord.addToCurrent(record);
					break;
				case Calendar.NOVEMBER:
					novRecord.addToCurrent(record);
					break;
				case Calendar.DECEMBER:
					decRecord.addToCurrent(record);
					break;
				default:
					break;
				}
			}

			rows.add(createTableRow("January", janRecord));
			rows.add(createTableRow("Febuary", febRecord));
			rows.add(createTableRow("March", marRecord));

			rows.add(createBlankRow());

			rows.add(createTableRow("April", aprRecord));
			rows.add(createTableRow("May", mayRecord));
			rows.add(createTableRow("June", junRecord));

			rows.add(createBlankRow());

			rows.add(createTableRow("July", julRecord));
			rows.add(createTableRow("August", augRecord));
			rows.add(createTableRow("September", sepRecord));

			rows.add(createBlankRow());

			rows.add(createTableRow("October", octRecord));
			rows.add(createTableRow("November", novRecord));
			rows.add(createTableRow("December", decRecord));

			rows.add(createBlankRow());
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
