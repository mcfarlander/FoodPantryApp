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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.model.Visit;

import net.sf.nervalreports.core.ReportGenerationException;

/**
 * Create a report for the visitors to the pantry for a given month.
 * 
 * @author mcfarland_davej
 */
public class ReportMonthlySummary extends ReportBase {
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private int monthSelected = 0;

	public int getMonthSelected() {
		return this.monthSelected;
	}

	public void setMonthSelected(int iMonth) {
		this.monthSelected = iMonth;
	}

	private String[] cols = new String[] { "ID", "HouseholdId", "New?", "# Adults", "# Kids", "# Seniors",
			"Working Income", "Other Income", "No Income", "Date" };

	public ReportMonthlySummary() {
		setReportName("Monthly_Summary");
		setReportTitle("McFarland Community Food Pantry Monthly Summary");
		setReportDescription(dateFormat.format(Calendar.getInstance().getTime()));
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
		// finish the table
		report.endTable();
	}

	@Override
	public void createFooter() {
	}

	private void loadReport() throws ReportGenerationException {
		try {
			// Calendar cal = Calendar.getInstance();
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
					Date testDate = dateFormat.parse(vis.getVisitDate());
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
							if (Integer.parseInt(weekNumbers.get(j).toString()) == vis.getVisitorWeekNumber()) {
								bFound = true;
							}

						}

						if (!bFound) {
							weekNumbers.add("" + vis.getVisitorWeekNumber());
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
					if (visit.getVisitorWeekNumber() == iWeekNumber) {
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

						// show the visit info on the table
						addTableRow(visit);
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
				weekTotal.setLastColumnlabel("Week " + (i + 1));
				addTableSummary(weekTotal, "w=");

				addEmptyRow();
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
			monthTotal.setLastColumnlabel("Monthly Summary");
			addTableSummary(monthTotal, "m=");

			report.endTable();
		} catch (ParseException ex) {
			Logger.getLogger(ReportMonthlySummary.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void addTableRow(Visit record) throws ReportGenerationException {
		report.beginTableRow();
		report.addTableCell(String.valueOf(record.getVisitorWeekNumber()));
		report.addTableCell(String.valueOf(record.getHouseholdId()));
		report.addTableCell(String.valueOf(getYesNo(record.isNewCustomer())));
		report.addTableCell(String.valueOf(record.getNumberAdults()));
		report.addTableCell(String.valueOf(record.getNumberKids()));
		report.addTableCell(String.valueOf(record.getNumberSeniors()));
		report.addTableCell(String.valueOf(getYesNo(record.isWorkingIncome())));
		report.addTableCell(String.valueOf(getYesNo(record.isOtherIncome())));
		report.addTableCell(String.valueOf(getYesNo(record.isNoIncome())));
		report.addTableCell(String.valueOf(record.getVisitorWeekNumber()));
		report.endTableRow();
	}

	private void addTableSummary(VisitSummary record, String prefix) throws ReportGenerationException {
		report.beginTableRow();
		report.addTableCell(record.getFirstColumnlabel());
		report.addTableCell(prefix + String.valueOf(record.getHouseholdSum()));
		report.addTableCell(prefix + String.valueOf(record.getNewHouseholdSum()));
		report.addTableCell(prefix + String.valueOf(record.getAdultsSum()));
		report.addTableCell(prefix + String.valueOf(record.getKidsSum()));
		report.addTableCell(prefix + String.valueOf(record.getSeniorsSum()));
		report.addTableCell(prefix + String.valueOf(record.getWorkingIncomeSum()));
		report.addTableCell(prefix + String.valueOf(record.getOtherIncomeSum()));
		report.addTableCell(prefix + String.valueOf(record.getNoIncomeSum()));
		report.addTableCell(record.getLastColumnlabel());
		report.endTableRow();
	}

	private void addEmptyRow() throws ReportGenerationException {
		report.beginTableRow();
		report.endTableRow();
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
