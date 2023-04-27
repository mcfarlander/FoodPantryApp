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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.model.Visit;
import org.pantry.food.util.DateUtil;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Create a report for the visits to the pantry for a given month.
 * 
 * @author mcfarland_davej
 */
public class ReportMonthlySummary extends AbstractReportStrategy {
	private static final Logger log = LogManager.getLogger(ReportMonthlySummary.class);
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private int monthSelected = 0;

	public int getMonthSelected() {
		return monthSelected;
	}

	public void setMonthSelected(int monthSelected) {
		this.monthSelected = monthSelected;
	}

	private String[] cols = new String[] { "ID", "HouseholdId", "New?", "# Adults", "# Kids", "# Seniors",
			"Working Income", "Other Income", "No Income", "Week of Year" };

	@Override
	public String getTitle() {
		return "McFarland Community Food Pantry Monthly Summary - " + DateUtil.getMonthName(monthSelected + 1);
	}

	@Override
	public ObservableList<TableColumn<ReportRow, String>> getColumns() {
		return toTableColumns(cols);
	}

	@Override
	public List<ReportRow> getRows() {
		List<ReportRow> rows = new ArrayList<>();

		try {
			List<Visit> monthVisits = new ArrayList<Visit>();
			List<String> weekNumbers = new ArrayList<String>();

			int monthSumHouse = 0;
			int monthSumNew = 0;
			int monthSumAdults = 0;
			int monthSumKids = 0;
			int monthSumSeniors = 0;
			int monthSumWorkingIncome = 0;
			int monthSumOtherIncome = 0;
			int monthSumNoIncome = 0;

			Calendar testCal = Calendar.getInstance();
			VisitsDao visitsDao = ApplicationContext.getVisitsDao();
			for (Visit vis : visitsDao.getAll()) {
				if (vis.isActive()) {
					Date testDate = dateFormat.parse(vis.getDate());
					testCal.setTime(testDate);

					if (testCal.get(Calendar.MONTH) == this.monthSelected) {
						monthVisits.add(vis);
						monthSumHouse++;

						if (vis.isNewCustomer()) {
							monthSumNew++;
						}

						monthSumAdults += vis.getNumberAdults();
						monthSumKids += vis.getNumberKids();
						monthSumSeniors += vis.getNumberSeniors();

						if (vis.isWorkingIncome()) {
							monthSumWorkingIncome++;
						}

						if (vis.isOtherIncome()) {
							monthSumOtherIncome++;
						}

						if (vis.isNoIncome()) {
							monthSumNoIncome++;
						}

						boolean bFound = false;
						for (int j = 0; j < weekNumbers.size(); j++) {
							if (Integer.parseInt(weekNumbers.get(j).toString()) == vis.getWeekNumber()) {
								bFound = true;
							}
						}

						if (!bFound) {
							weekNumbers.add(String.valueOf(vis.getWeekNumber()));
						}
					}
				}
			}

			// loop thru the current visits and get summaries
			Collections.sort(weekNumbers); // sort the week numbers asc

			for (int i = 0; i < weekNumbers.size(); i++) {
				int iWeekNumber = Integer.parseInt(weekNumbers.get(i).toString());
				int weekSumHouse = 0;
				int weekSumNew = 0;
				int weekSumAdults = 0;
				int weekSumKids = 0;
				int weekSumSeniors = 0;
				int weekSumWorkingIncome = 0;
				int weekSumOtherIncome = 0;
				int weekSumNoIncome = 0;

				for (Visit visit : monthVisits) {
					if (visit.getWeekNumber() == iWeekNumber) {
						weekSumHouse++;

						if (visit.isNewCustomer()) {
							weekSumNew++;
						}

						weekSumAdults += visit.getNumberAdults();
						weekSumKids += visit.getNumberKids();
						weekSumSeniors += visit.getNumberSeniors();

						if (visit.isWorkingIncome()) {
							weekSumWorkingIncome++;
						}

						if (visit.isOtherIncome()) {
							weekSumOtherIncome++;
						}

						if (visit.isNoIncome()) {
							weekSumNoIncome++;
						}

						if (visit.getWeekNumber() <= 0) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(dateFormat.parse(visit.getDate()));
							cal.setMinimalDaysInFirstWeek(1);
							visit.setWeekNumber(cal.get(Calendar.WEEK_OF_YEAR));
						}

						// show the visit info on the table
						rows.add(createTableRow(visit, weekSumHouse));
					}
				}

				// show the weekly summary info on the table
				VisitSummary weekTotal = new VisitSummary();
				weekTotal.setFirstColumnlabel("Week Sum:");
				weekTotal.setHouseholdSum(weekSumHouse);
				weekTotal.setNewHouseholdSum(weekSumNew);
				weekTotal.setAdultsSum(weekSumAdults);
				weekTotal.setKidsSum(weekSumKids);
				weekTotal.setSeniorsSum(weekSumSeniors);
				weekTotal.setWorkingIncomeSum(weekSumWorkingIncome);
				weekTotal.setOtherIncomeSum(weekSumOtherIncome);
				weekTotal.setNoIncomeSum(weekSumNoIncome);
				weekTotal.setLastColumnlabel("");
				rows.add(createTableSummary(weekTotal, "w="));

				rows.add(createEmptyRow());
			}

			// show the monthly summary line
			VisitSummary monthTotal = new VisitSummary();
			monthTotal.setFirstColumnlabel("Month Totals:");
			monthTotal.setHouseholdSum(monthSumHouse);
			monthTotal.setNewHouseholdSum(monthSumNew);
			monthTotal.setAdultsSum(monthSumAdults);
			monthTotal.setKidsSum(monthSumKids);
			monthTotal.setSeniorsSum(monthSumSeniors);
			monthTotal.setWorkingIncomeSum(monthSumWorkingIncome);
			monthTotal.setOtherIncomeSum(monthSumOtherIncome);
			monthTotal.setNoIncomeSum(monthSumNoIncome);
			monthTotal.setLastColumnlabel("");
			rows.add(createTableSummary(monthTotal, "m="));
		} catch (ParseException ex) {
			log.error("Could not parse visit date", ex);
		}

		return rows;
	}

	private ReportRow createTableRow(Visit record, int householdVisitNumber) {
		ReportRow row = new ReportRow();
		row.addColumn(String.valueOf(householdVisitNumber));
		row.addColumn(String.valueOf(record.getHouseholdId()));
		row.addColumn(String.valueOf(getYesNo(record.isNewCustomer())));
		row.addColumn(String.valueOf(record.getNumberAdults()));
		row.addColumn(String.valueOf(record.getNumberKids()));
		row.addColumn(String.valueOf(record.getNumberSeniors()));
		row.addColumn(String.valueOf(getYesNo(record.isWorkingIncome())));
		row.addColumn(String.valueOf(getYesNo(record.isOtherIncome())));
		row.addColumn(String.valueOf(getYesNo(record.isNoIncome())));
		row.addColumn("Week " + record.getWeekNumber());

		return row;
	}

	private ReportRow createTableSummary(VisitSummary record, String prefix) {
		ReportRow row = new ReportRow();
		row.addColumn(record.getFirstColumnlabel());
		row.addColumn(prefix + String.valueOf(record.getHouseholdSum()));
		row.addColumn(prefix + String.valueOf(record.getNewHouseholdSum()));
		row.addColumn(prefix + String.valueOf(record.getAdultsSum()));
		row.addColumn(prefix + String.valueOf(record.getKidsSum()));
		row.addColumn(prefix + String.valueOf(record.getSeniorsSum()));
		row.addColumn(prefix + String.valueOf(record.getWorkingIncomeSum()));
		row.addColumn(prefix + String.valueOf(record.getOtherIncomeSum()));
		row.addColumn(prefix + String.valueOf(record.getNoIncomeSum()));
		row.addColumn(record.getLastColumnlabel());
		row.setSummary(true);

		return row;
	}

	private ReportRow createEmptyRow() {
		ReportRow row = new ReportRow();
		for (int x = 1; x <= 10; x++) {
			row.addColumn("");
		}
		return row;
	}

	private String getYesNo(Boolean bool) {
		return bool ? "Yes" : "No";
	}

	class VisitSummary {
		String firstColumnlabel = "";
		String lastColumnlabel = "";
		int householdSum = 0;
		int newHouseholdSum = 0;
		int adultsSum = 0;
		int kidsSum = 0;
		int seniorsSum = 0;
		int workingIncomeSum = 0;
		int otherIncomeSum = 0;
		int noIncomeSum = 0;

		public String getFirstColumnlabel() {
			return firstColumnlabel;
		}

		public void setFirstColumnlabel(String firstColumnlabel) {
			this.firstColumnlabel = firstColumnlabel;
		}

		public String getLastColumnlabel() {
			return lastColumnlabel;
		}

		public void setLastColumnlabel(String lastColumnlabel) {
			this.lastColumnlabel = lastColumnlabel;
		}

		public int getHouseholdSum() {
			return householdSum;
		}

		public void setHouseholdSum(int householdSum) {
			this.householdSum = householdSum;
		}

		public int getNewHouseholdSum() {
			return newHouseholdSum;
		}

		public void setNewHouseholdSum(int newHouseholdSum) {
			this.newHouseholdSum = newHouseholdSum;
		}

		public int getAdultsSum() {
			return adultsSum;
		}

		public void setAdultsSum(int adultsSum) {
			this.adultsSum = adultsSum;
		}

		public int getKidsSum() {
			return kidsSum;
		}

		public void setKidsSum(int kidsSum) {
			this.kidsSum = kidsSum;
		}

		public int getSeniorsSum() {
			return seniorsSum;
		}

		public void setSeniorsSum(int seniorsSum) {
			this.seniorsSum = seniorsSum;
		}

		public int getWorkingIncomeSum() {
			return workingIncomeSum;
		}

		public void setWorkingIncomeSum(int workingIncomeSum) {
			this.workingIncomeSum = workingIncomeSum;
		}

		public int getOtherIncomeSum() {
			return otherIncomeSum;
		}

		public void setOtherIncomeSum(int otherIncomeSum) {
			this.otherIncomeSum = otherIncomeSum;
		}

		public int getNoIncomeSum() {
			return noIncomeSum;
		}

		public void setNoIncomeSum(int noIncomeSum) {
			this.noIncomeSum = noIncomeSum;
		}
	}

}// end of class
