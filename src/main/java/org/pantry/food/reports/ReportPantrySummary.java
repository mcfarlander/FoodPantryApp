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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.dao.CustomersDao;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.dao.VolunteerDao;
import org.pantry.food.dao.VolunteerEventDao;
import org.pantry.food.model.Customer;
import org.pantry.food.model.Food;
import org.pantry.food.model.Visit;
import org.pantry.food.model.VolunteerEvent;

/**
 * Create a report of the summary of activity at the pantry.
 * 
 * @author mcfarland_davej
 *
 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.ReportBase#createReportTable()
	 */
	@Override
	public void createReportTable() {
		nf.setMaximumFractionDigits(1);

		this.setReportName("Pantry_Summary");
		this.setReportTitle("MCFP Report Summary");

		Calendar c1 = Calendar.getInstance(); // today
		this.setReportDescription(dateFormat.format(c1.getTime()));

		this.createHeader();

		// create the basic table
		this.getBuffer().append("<table border='1' cellpadding='4'>").append("\n");

		// create a header row
		createRow(cols[0], cols[1], cols[2], true);

		// create rest of the data rows
		loadReport();

		// finish the table
		this.getBuffer().append("</table>").append("\n");

		// finish the html
		this.createFooter();
	}

	private void createRow(String col1, String col2, String col3, boolean isHeader) {
		this.getBuffer().append("<tr>").append("\n");

		if (isHeader) {
			this.getBuffer().append("<th>").append(col1).append("</th>").append("\n");
			this.getBuffer().append("<th>").append(col2).append("</th>").append("\n");
			this.getBuffer().append("<th>").append(col3).append("</th>").append("\n");
		} else {
			this.getBuffer().append("<td>").append(col1).append("</td>").append("\n");
			this.getBuffer().append("<td>").append(col2).append("</td>").append("\n");
			this.getBuffer().append("<td>").append(col3).append("</td>").append("\n");
		}

		this.getBuffer().append("</tr>").append("\n");

	}// end of createTable

	private void getCustomerData() throws FileNotFoundException, IOException {
		CustomersDao custs = new CustomersDao();
		custs.read();

		int numberCustomers = 0;
		int numberNewAdults = 0;
		int numberNewSeniors = 0;
		int numberNewKids = 0;

		int numberMonthCustomers = 0;
		int numberMonthAdults = 0;
		int numberMonthSeniors = 0;
		int numberMonthKids = 0;

		ArrayList<Customer> distinctHouseholdsYtd = new ArrayList<Customer>();
		ArrayList<Customer> distinctHouseholdsMonth = new ArrayList<Customer>();

		boolean isFromMonth = false;

		for (Customer cust : custs.getAll()) {
			numberCustomers++;

			isFromMonth = (cust.getMonthRegistered() - 1 == monthSelected);

			if (cust.isNewCustomer()) {
				if (cust.isActive()) {
					if (isFromMonth)
						numberMonthCustomers++;

					if (cust.getAge() > 19) {
						if (cust.getAge() >= 60) {
							numberNewSeniors++;

							if (isFromMonth)
								numberMonthSeniors++;
						} else {
							numberNewAdults++;

							if (isFromMonth)
								numberMonthAdults++;
						}

					} else {
						numberNewKids++;

						if (isFromMonth)
							numberMonthKids++;

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

		createRow("New Customers Month", "" + numberMonthCustomers, "Adults:" + numberMonthAdults + " Seniors:"
				+ numberMonthSeniors + " Children:" + numberMonthKids + " Households:" + distinctHouseholdsMonth.size(),
				false);

		createRow("New Customers YTD", "" + numberCustomers, "Adults:" + numberNewAdults + " Seniors:"
				+ numberNewSeniors + " Children:" + numberNewKids + " Households:" + distinctHouseholdsYtd.size(),
				false);

	}

	private void getVisitsData() throws FileNotFoundException, IOException, ParseException {
		int numberTotalVisits = 0;
		int numberDistinctHouseholds = 0;
		int numberDistinctAdults = 0;
		int numberDistintKids = 0;
		int numberDistintSeniors = 0;

		ArrayList<Visit> distinctHouseholdVisits = new ArrayList<Visit>();
		boolean bFound = false;
		Calendar mydate = new GregorianCalendar();

		VisitsDao visitsDao = new VisitsDao();
		List<Visit> visits = visitsDao.read();

		for (Visit visit : visits) {
			Date thedate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(visit.getVisitDate());
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

		for (int i = 0; i < distinctHouseholdVisits.size(); i++) {
			numberDistinctHouseholds++;
			Visit visit = distinctHouseholdVisits.get(i);
			numberDistinctAdults += visit.getNumberAdults();
			numberDistintKids += visit.getNumberKids();
			numberDistintSeniors += visit.getNumberSeniors();

		}

		createRow("Total Visits", "" + numberTotalVisits, "", false);
		createRow("&nbsp;Distinct Houses", "" + numberDistinctHouseholds, "", false);
		createRow("&nbsp;Distinct Adults", "" + numberDistinctAdults, "", false);
		createRow("&nbsp;Distinct Kids", "" + numberDistintKids, "", false);
		createRow("&nbsp;Distinct Seniors", "" + numberDistintSeniors, "", false);

	}

	private void getVolunteerData() throws FileNotFoundException, IOException, ParseException {
		double totalHours = 0;
		double totalStudentHours = 0;
		double totalRegularHours = 0;
		double totalOtherHours = 0;

		VolunteerDao volunteers = new VolunteerDao();
		volunteers.read();

		VolunteerEventDao events = new VolunteerEventDao();
		events.read();

		Calendar mydate = new GregorianCalendar();

		ArrayList<String> distinctStudents = new ArrayList<String>();
		ArrayList<String> distinctRegulars = new ArrayList<String>();
		ArrayList<String> distinctOthers = new ArrayList<String>();
		boolean bfound = false;

		for (int i = 0; i < events.getCvsCount(); i++) {
			VolunteerEvent event = (VolunteerEvent) events.getAll().get(i);

			Date thedate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(event.getEventDate());
			mydate.setTime(thedate);

			if (mydate.get(Calendar.MONTH) == monthSelected) {
				totalHours += event.getVolunteerHours();
				bfound = false;

				String volunteerType = (volunteers.getTypeFromName(event.getVolunteerName()));

				if (volunteerType.equalsIgnoreCase("Regular")) {
					totalRegularHours += event.getVolunteerHours();

					for (int j = 0; j < distinctRegulars.size(); j++) {
						if (distinctRegulars.get(j).equalsIgnoreCase(event.getVolunteerName())) {
							bfound = true;
							break;
						}
					}

					if (!bfound)
						distinctRegulars.add(event.getVolunteerName());

				} else if (volunteerType.equalsIgnoreCase("Student")) {
					totalStudentHours += event.getVolunteerHours();

					for (int j = 0; j < distinctStudents.size(); j++) {
						if (distinctStudents.get(j).equalsIgnoreCase(event.getVolunteerName())) {
							bfound = true;
							break;
						}
					}

					if (!bfound)
						distinctStudents.add(event.getVolunteerName());

				} else {
					totalOtherHours += event.getVolunteerHours();

					for (int j = 0; j < distinctOthers.size(); j++) {
						if (distinctOthers.get(j).equalsIgnoreCase(event.getVolunteerName())) {
							bfound = true;
							break;
						}
					}

					if (!bfound)
						distinctOthers.add(event.getVolunteerName());

				}
			}
		}

		createRow("Total Hours", "" + nf.format(totalHours),
				"Count:" + (distinctStudents.size() + distinctRegulars.size() + distinctOthers.size()), false);
		createRow("&nbsp;Student Hours", "" + nf.format(totalStudentHours), "Count:" + distinctStudents.size(), false);
		createRow("&nbsp;Regular Hours", "" + nf.format(totalRegularHours), "Count:" + distinctRegulars.size(), false);
		createRow("&nbsp;Other Hours", "" + nf.format(totalOtherHours), "Count:" + distinctOthers.size(), false);

	}

	private void getDonationData() throws FileNotFoundException, IOException, ParseException {
		FoodsDao records = new FoodsDao();
		records.read();

		Food totals = new Food();

		Calendar mydate = new GregorianCalendar();

		for (Food record : records.getAll()) {
			Date thedate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(record.getEntryDate());
			mydate.setTime(thedate);

			if (mydate.get(Calendar.MONTH) == monthSelected) {
				totals.addToCurrent(record);
			}

		}

		createRow("Total Donations", "" + nf.format(totals.getTotal()), "", false);
		createRow("&nbsp;Community", "" + nf.format(totals.getCommunity()), "", false);
		createRow("&nbsp;Pick N Save", "" + nf.format(totals.getPickNSave()), "", false);
		createRow("&nbsp;Tefap", "" + nf.format(totals.getTefap()), "", false);
		createRow("&nbsp;Second Harvest", "" + nf.format(totals.getSecondHarvest()), "", false);
		createRow("&nbsp;Second Harvest Produce", "" + nf.format(totals.getSecondHarvestProduce()), "", false);
		createRow("&nbsp;Milk", "" + nf.format(totals.getMilk()), "", false);
		createRow("&nbsp;Non-Tefap", "" + nf.format(totals.getNonTefap()), "", false);
		createRow("&nbsp;Non-Food", "" + nf.format(totals.getNonFood()), "", false);
		createRow("&nbsp;Pantry Non-Food", "" + nf.format(totals.getOther()), "", false);
		createRow("&nbsp;Pantry Produce", "" + nf.format(totals.getOther2()), "", false);
		createRow("&nbsp;Produce", "" + nf.format(totals.getProduce()), "", false);
		createRow("&nbsp;Pantry Purchases", "" + nf.format(totals.getPantry()), "", false);

	}

	private void loadReport() {
		try {
			getCustomerData();
			getVisitsData();
			getDonationData();
			getVolunteerData();

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ReportPantrySummary.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ReportPantrySummary.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ParseException ex) {
			Logger.getLogger(ReportPantrySummary.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

} // end of class
