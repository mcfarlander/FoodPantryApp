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
import org.pantry.food.dao.SuppliesDao;
import org.pantry.food.model.Supplies;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Creates a report based on the donations of food and other misc items.
 * 
 * @author mcfarland_davej
 */
public class ReportDonatedFoodWeight extends AbstractReportStrategy {

	private static final Logger log = LogManager.getLogger(ReportDonatedFoodWeight.class);
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private NumberFormat nf = NumberFormat.getInstance();

	private String[] cols = new String[] { "Month", "Total Weight (lbs)", "Pick N Save", "Community", "Non-TEFAP",
			"TEFAP", "2nd Harvest", "2nd Harvest Produce", "Pantry Purchase", "Pantry Non-Food", "NonFood", "Milk",
			"Bread", "Produce", "Comments" };

	public ReportDonatedFoodWeight() {
		nf.setMaximumFractionDigits(1);
	}

	@Override
	public String getTitle() {
		return "Donated Food Weight Record";
	}

	@Override
	public ObservableList<TableColumn<ReportRow, String>> getColumns() {
		return toTableColumns(cols);
	}

	@Override
	public List<ReportRow> getRows() {
		List<ReportRow> rows = new ArrayList<ReportRow>();
		String entryDate = "";
		try {
			SuppliesDao foodsDao = ApplicationContext.getSuppliesDao();

			// use the POJO to store the working sums
			Supplies janRecord = new Supplies();
			Supplies febRecord = new Supplies();
			Supplies marRecord = new Supplies();
			Supplies aprRecord = new Supplies();
			Supplies mayRecord = new Supplies();
			Supplies junRecord = new Supplies();
			Supplies julRecord = new Supplies();
			Supplies augRecord = new Supplies();
			Supplies sepRecord = new Supplies();
			Supplies octRecord = new Supplies();
			Supplies novRecord = new Supplies();
			Supplies decRecord = new Supplies();

			Supplies q1Record = new Supplies();
			Supplies q2Record = new Supplies();
			Supplies q3Record = new Supplies();
			Supplies q4Record = new Supplies();
			Supplies yrRecord = new Supplies();

			Calendar cal = Calendar.getInstance();

			for (Supplies record : foodsDao.getAll()) {
				entryDate = record.getEntryDate();
				Date testDate = dateFormat.parse(entryDate);
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

			rows.add(createTableRow("January", janRecord));
			rows.add(createTableRow("Febuary", febRecord));
			rows.add(createTableRow("March", marRecord));
			rows.add(createTableRow("QUARTER 1", q1Record).setSummary(true));

			rows.add(createTableRow("April", aprRecord));
			rows.add(createTableRow("May", mayRecord));
			rows.add(createTableRow("June", junRecord));
			rows.add(createTableRow("QUARTER 2", q2Record).setSummary(true));

			rows.add(createTableRow("July", julRecord));
			rows.add(createTableRow("August", augRecord));
			rows.add(createTableRow("Sept", sepRecord));
			rows.add(createTableRow("QUARTER 3", q3Record).setSummary(true));

			rows.add(createTableRow("October", octRecord));
			rows.add(createTableRow("November", novRecord));
			rows.add(createTableRow("December", decRecord));
			rows.add(createTableRow("QUARTER 4", q4Record).setSummary(true));

			rows.add(createTableRow("YEAR", yrRecord).setSummary(true));
		} catch (ParseException ex) {
			log.error("Could not parse date " + entryDate, ex);
		}
		return rows;
	}

	private ReportRow createTableRow(String firstCol, Supplies record) {
		ReportRow row = new ReportRow().addColumn(firstCol).addColumn(nf.format(record.getTotal()))
				.addColumn(nf.format(record.getPickNSave())).addColumn(nf.format(record.getCommunity()))
				.addColumn(nf.format(record.getNonTefap())).addColumn(nf.format(record.getTefap()))
				.addColumn(nf.format(record.getSecondHarvest())).addColumn(nf.format(record.getSecondHarvestProduce()))
				.addColumn(nf.format(record.getPantry())).addColumn(nf.format(record.getPantryNonFood()))
				.addColumn(nf.format(record.getNonFood())).addColumn(nf.format(record.getMilk()))
				.addColumn(nf.format(record.getPantryProduce())).addColumn(nf.format(record.getProduce()))
				.addColumn(record.getComment());
		return row;
	}

}
