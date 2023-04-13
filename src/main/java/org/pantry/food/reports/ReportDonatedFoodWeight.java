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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.model.Food;

import net.sf.nervalreports.core.ReportGenerationException;

/**
 * Creates a report based on the donations of food and other misc items.
 * 
 * @author mcfarland_davej
 */
public class ReportDonatedFoodWeight extends ReportBase {

	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	NumberFormat nf = NumberFormat.getInstance();

	private String[] cols = new String[] { "Month", "Total Weight (lbs)", "Pick N Save", "Community", "Non-TEFAP",
			"TEFAP", "2nd Harvest", "2nd Harvest Produce", "Pantry Purchase", "Pantry Non-Food", "NonFood", "Milk",
			"Bread", "Produce", "Comments" };

	public ReportDonatedFoodWeight() {
		setReportName("Donated_Food_Weight");
		setReportTitle("Donated Food Weight Record");
		setReportDescription(dateFormat.format(Calendar.getInstance().getTime()));

		nf.setMaximumFractionDigits(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.ReportBase#buildReport()
	 */
	@Override
	public void buildReport() throws ReportGenerationException {
		// create a header row
		report.beginTable(cols.length);
		report.beginTableHeaderRow();
		for (int x = 0; x < cols.length; x++) {
			report.addTableHeaderCell(cols[x]);
		}
		report.endTableHeaderRow();

		// create rest of the data rows
		loadReport();

		report.endTable();
	}

	private void loadReport() throws ReportGenerationException {
		try {
			FoodsDao foodsDao = ApplicationContext.getFoodsDao();

			// use the POJO to store the working sums
			Food janRecord = new Food();
			Food febRecord = new Food();
			Food marRecord = new Food();
			Food aprRecord = new Food();
			Food mayRecord = new Food();
			Food junRecord = new Food();
			Food julRecord = new Food();
			Food augRecord = new Food();
			Food sepRecord = new Food();
			Food octRecord = new Food();
			Food novRecord = new Food();
			Food decRecord = new Food();

			Food q1Record = new Food();
			Food q2Record = new Food();
			Food q3Record = new Food();
			Food q4Record = new Food();
			Food yrRecord = new Food();

			Calendar cal = Calendar.getInstance();

			for (Food record : foodsDao.getAll()) {
				Date testDate = dateFormat.parse(record.getEntryDate());
				cal.setTime(testDate);

				yrRecord.addToCurrent(record);

				switch (cal.get(Calendar.MONTH)) {
				case 0:
					janRecord.addToCurrent(record);
					q1Record.addToCurrent(record);
					break;
				case 1:
					febRecord.addToCurrent(record);
					q1Record.addToCurrent(record);
					break;
				case 2:
					marRecord.addToCurrent(record);
					q1Record.addToCurrent(record);
					break;
				case 3:
					aprRecord.addToCurrent(record);
					q2Record.addToCurrent(record);
					break;
				case 4:
					mayRecord.addToCurrent(record);
					q2Record.addToCurrent(record);
					break;
				case 5:
					junRecord.addToCurrent(record);
					q2Record.addToCurrent(record);
					break;
				case 6:
					julRecord.addToCurrent(record);
					q3Record.addToCurrent(record);
					break;
				case 7:
					augRecord.addToCurrent(record);
					q3Record.addToCurrent(record);
					break;
				case 8:
					sepRecord.addToCurrent(record);
					q3Record.addToCurrent(record);
					break;
				case 9:
					octRecord.addToCurrent(record);
					q4Record.addToCurrent(record);
					break;
				case 10:
					novRecord.addToCurrent(record);
					q4Record.addToCurrent(record);
					break;
				case 11:
					decRecord.addToCurrent(record);
					q4Record.addToCurrent(record);
					break;
				default:
					break;

				}

			}

			addTableRow("January", janRecord);
			addTableRow("Febuary", febRecord);
			addTableRow("March", marRecord);
			addTableRow("QUARTER 1", q1Record);

			addTableRow("April", aprRecord);
			addTableRow("May", mayRecord);
			addTableRow("June", junRecord);
			addTableRow("QUARTER 2", q2Record);

			addTableRow("July", julRecord);
			addTableRow("August", augRecord);
			addTableRow("Sept", sepRecord);
			addTableRow("QUARTER 3", q3Record);

			addTableRow("October", octRecord);
			addTableRow("November", novRecord);
			addTableRow("December", decRecord);
			addTableRow("QUARTER 4", q4Record);

			addTableRow("YEAR", yrRecord);
		} catch (ParseException ex) {
			Logger.getLogger(ReportDonatedFoodWeight.class.getName()).log(Level.SEVERE, null, ex);
		}

		report.endTable();
	}

	private void addTableRow(String firstCol, Food record) throws ReportGenerationException {
		report.beginTableRow();
		report.addTableCell(firstCol);
		report.addTableCell(nf.format(record.getTotal()));
		report.addTableCell(nf.format(record.getPickNSave()));
		report.addTableCell(nf.format(record.getCommunity()));
		report.addTableCell(nf.format(record.getNonTefap()));
		report.addTableCell(nf.format(record.getTefap()));
		report.addTableCell(nf.format(record.getSecondHarvest()));
		report.addTableCell(nf.format(record.getSecondHarvestProduce()));
		report.addTableCell(nf.format(record.getPantry()));
		report.addTableCell(nf.format(record.getOther()));
		report.addTableCell(nf.format(record.getNonFood()));
		report.addTableCell(nf.format(record.getMilk()));
		report.addTableCell(nf.format(record.getOther2()));
		report.addTableCell(nf.format(record.getProduce()));
		report.addTableCell(nf.format(record.getComment()));
		report.endTableRow();
	}

	@Override
	public void createFooter() {
	}
}
