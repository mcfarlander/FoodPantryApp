/*
  Copyright 2013 Dave Johnson

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.model.Food;
import org.pantry.food.model.Visit;
import org.pantry.food.model.VolunteerEvent;

import net.sf.nervalreports.core.ReportGenerationException;

/**
 * Create a report of the summary of activity at the pantry.
 * 
 * @author mcfarland_davej
 *
 */
/// HAS NOT BEEN CONVERTED TO USE Nerval YET!!
public class ReportPantrySummary extends ReportBase {

	private int monthSelected = 0;

	public int getMonthSelected() {
		return this.monthSelected;
	}

	public void setMonthSelected(int iMonth) {
		this.monthSelected = iMonth;
	}

	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private NumberFormat nf = NumberFormat.getInstance();

	private String[] cols = new String[] { "Category", "Value", "Notes" };

	public ReportPantrySummary() {
		setReportName("Pantry_Summary");
		setReportTitle("MCFP Report Summary");
		setReportDescription(dateFormat.format(Calendar.getInstance().getTime()));

		nf.setMaximumFractionDigits(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.ReportBase#createReportTable()
	 */
	@Override
	public void buildReport() throws ReportGenerationException {
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

	private void addCustomerData() throws FileNotFoundException, IOException, ReportGenerationException {
		int numberCustomers = 0;
		int numberNewAdults = 0;
		int numberNewSeniors = 0;
		int numberNewKids = 0;

		int numberMonthCustomers = 0;
		int numberMonthAdults = 0;
		int numberMonthSeniors = 0;
		int numberMonthKids = 0;

		List<Customer> distinctHouseholdsYtd = new ArrayList<Customer>();
		List<Customer> distinctHouseholdsMonth = new ArrayList<Customer>();

		boolean isFromMonth = false;
		List<Customer> customers = ApplicationContext.getCustomersDao().getAll();
		numberCustomers = customers.size();
		for (Customer cust : customers) {
			isFromMonth = (cust.getMonthRegistered() - 1 == monthSelected);

			if (cust.isNewCustomer()) {
				if (cust.isActive()) {
					if (isFromMonth) {
						numberMonthCustomers++;
					}

					if (cust.getAge() > 19) {
						if (cust.getAge() >= 60) {
							numberNewSeniors++;

							if (isFromMonth) {
								numberMonthSeniors++;
							}
						} else {
							numberNewAdults++;

							if (isFromMonth) {
								numberMonthAdults++;
							}
						}

					} else {
						numberNewKids++;

						if (isFromMonth) {
							numberMonthKids++;
						}

					}

					// active, new customer, add to distinct households if not already in
					boolean bFoundYtd = false;
					for (int j = 0; j < distinctHouseholdsYtd.size(); j++) {
						Customer cached = (Customer) distinctHouseholdsYtd.get(j);
						if (cached.getHouseholdId() == cust.getHouseholdId()) {
							bFoundYtd = true;
							break;
						}

					}

					if (!bFoundYtd) {
						distinctHouseholdsYtd.add(cust);
					}

					if (isFromMonth) {
						boolean bFoundMonth = false;
						for (int j = 0; j < distinctHouseholdsMonth.size(); j++) {
							Customer cached = (Customer) distinctHouseholdsMonth.get(j);
							if (cached.getHouseholdId() == cust.getHouseholdId()) {
								bFoundMonth = true;
								break;
							}

						}

						if (!bFoundMonth) {
							distinctHouseholdsMonth.add(cust);
						}

					}

				}

			}

		}

		addTableRow("New Customers Month", String.valueOf(numberMonthCustomers),
				"Adults:" + numberMonthAdults + " Seniors:" + numberMonthSeniors + " Children:" + numberMonthKids
						+ " Households:" + distinctHouseholdsMonth.size());

		addTableRow("New Customers YTD", String.valueOf(numberCustomers), "Adults:" + numberNewAdults + " Seniors:"
				+ numberNewSeniors + " Children:" + numberNewKids + " Households:" + distinctHouseholdsYtd.size());
	}

	private void addVisitsData() throws FileNotFoundException, IOException, ParseException, ReportGenerationException {
		int numberTotalVisits = 0;
		int numberDistinctHouseholds = 0;
		int numberDistinctAdults = 0;
		int numberDistintKids = 0;
		int numberDistintSeniors = 0;

		List<Visit> distinctHouseholdVisits = new ArrayList<Visit>();
		boolean bFound = false;
		Calendar mydate = new GregorianCalendar();

		List<Visit> visits = ApplicationContext.getVisitsDao().getAll();

		for (Visit visit : visits) {
			Date thedate = dateFormat.parse(visit.getVisitDate());
			mydate.setTime(thedate);

			if ((mydate.get(Calendar.MONTH) == monthSelected) && visit.isActive()) {
				numberTotalVisits++;

				bFound = false;
				for (int j = 0; j < distinctHouseholdVisits.size(); j++) {
					Visit stashed = (Visit) distinctHouseholdVisits.get(j);
					if (stashed.getHouseholdId() == visit.getHouseholdId()) {
						bFound = true;
					}
				}

				if (!bFound) {
					distinctHouseholdVisits.add(visit);
				}
			}
		}

		numberDistinctHouseholds += distinctHouseholdVisits.size();
		for (Visit visit : distinctHouseholdVisits) {
			numberDistinctAdults += visit.getNumberAdults();
			numberDistintKids += visit.getNumberKids();
			numberDistintSeniors += visit.getNumberSeniors();
		}

		addTableRow("Total Visits", String.valueOf(numberTotalVisits), "");
		addTableRow("&nbsp;Distinct Houses", String.valueOf(numberDistinctHouseholds), "");
		addTableRow("&nbsp;Distinct Adults", String.valueOf(numberDistinctAdults), "");
		addTableRow("&nbsp;Distinct Kids", String.valueOf(numberDistintKids), "");
		addTableRow("&nbsp;Distinct Seniors", String.valueOf(numberDistintSeniors), "");
	}

	private void addVolunteerData()
			throws FileNotFoundException, IOException, ParseException, ReportGenerationException {
		double totalHours = 0;
		double totalStudentHours = 0;
		double totalRegularHours = 0;
		double totalOtherHours = 0;

		VolunteersDao volunteersDao = ApplicationContext.getVolunteersDao();
		VolunteerEventsDao eventsDao = ApplicationContext.getVolunteerEventsDao();

		Calendar mydate = new GregorianCalendar();

		List<String> distinctStudents = new ArrayList<String>();
		List<String> distinctRegulars = new ArrayList<String>();
		List<String> distinctOthers = new ArrayList<String>();
		boolean bfound = false;

		for (VolunteerEvent event : eventsDao.getAll()) {
			Date thedate = dateFormat.parse(event.getEventDate());
			mydate.setTime(thedate);

			if (mydate.get(Calendar.MONTH) == monthSelected) {
				totalHours += event.getVolunteerHours();
				bfound = false;

				String volunteerType = (volunteersDao.getTypeFromName(event.getVolunteerName()));

				if (volunteerType.equalsIgnoreCase("Regular")) {
					totalRegularHours += event.getVolunteerHours();

					for (String regular : distinctRegulars) {
						if (regular.equalsIgnoreCase(event.getVolunteerName())) {
							bfound = true;
							break;
						}
					}

					if (!bfound) {
						distinctRegulars.add(event.getVolunteerName());
					}

				} else if (volunteerType.equalsIgnoreCase("Student")) {
					totalStudentHours += event.getVolunteerHours();

					for (String student : distinctStudents) {
						if (student.equalsIgnoreCase(event.getVolunteerName())) {
							bfound = true;
							break;
						}
					}

					if (!bfound) {
						distinctStudents.add(event.getVolunteerName());
					}
				} else {
					totalOtherHours += event.getVolunteerHours();

					for (String other : distinctOthers) {
						if (other.equalsIgnoreCase(event.getVolunteerName())) {
							bfound = true;
							break;
						}
					}

					if (!bfound) {
						distinctOthers.add(event.getVolunteerName());
					}
				}
			}
		}

		addTableRow("Total Hours", nf.format(totalHours),
				"Count:" + (distinctStudents.size() + distinctRegulars.size() + distinctOthers.size()));
		addTableRow("&nbsp;Student Hours", nf.format(totalStudentHours), "Count:" + distinctStudents.size());
		addTableRow("&nbsp;Regular Hours", nf.format(totalRegularHours), "Count:" + distinctRegulars.size());
		addTableRow("&nbsp;Other Hours", nf.format(totalOtherHours), "Count:" + distinctOthers.size());
	}

	private void addDonationData()
			throws FileNotFoundException, IOException, ParseException, ReportGenerationException {
		FoodsDao foodsDao = ApplicationContext.getFoodsDao();
		Food totals = new Food();
		Calendar mydate = new GregorianCalendar();

		for (Food record : foodsDao.getAll()) {
			Date thedate = dateFormat.parse(record.getEntryDate());
			mydate.setTime(thedate);

			if (mydate.get(Calendar.MONTH) == monthSelected) {
				totals.addToCurrent(record);
			}
		}

		addTableRow("Total Donations", nf.format(totals.getTotal()), "");
		addTableRow("&nbsp;Community", "" + nf.format(totals.getCommunity()), "");
		addTableRow("&nbsp;Pick N Save", "" + nf.format(totals.getPickNSave()), "");
		addTableRow("&nbsp;Tefap", "" + nf.format(totals.getTefap()), "");
		addTableRow("&nbsp;Second Harvest", "" + nf.format(totals.getSecondHarvest()), "");
		addTableRow("&nbsp;Second Harvest Produce", "" + nf.format(totals.getSecondHarvestProduce()), "");
		addTableRow("&nbsp;Milk", "" + nf.format(totals.getMilk()), "");
		addTableRow("&nbsp;Non-Tefap", "" + nf.format(totals.getNonTefap()), "");
		addTableRow("&nbsp;Non-Food", "" + nf.format(totals.getNonFood()), "");
		addTableRow("&nbsp;Pantry Non-Food", "" + nf.format(totals.getOther()), "");
		addTableRow("&nbsp;Pantry Produce", "" + nf.format(totals.getOther2()), "");
		addTableRow("&nbsp;Produce", "" + nf.format(totals.getProduce()), "");
		addTableRow("&nbsp;Pantry Purchases", "" + nf.format(totals.getPantry()), "");
	}

	private void loadReport() throws ReportGenerationException {
		try {
			addCustomerData();
			addVisitsData();
			addDonationData();
			addVolunteerData();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ReportPantrySummary.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ReportPantrySummary.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ParseException ex) {
			Logger.getLogger(ReportPantrySummary.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void addTableRow(String firstCol, String secondCol, String thirdCol) throws ReportGenerationException {
		report.beginTableRow();
		report.addTableCell(firstCol);
		report.addTableCell(secondCol);
		report.addTableCell(thirdCol);
		report.endTableRow();
	}

} // end of class
