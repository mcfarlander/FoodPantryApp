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

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.model.Food;
import org.pantry.food.model.Visit;
import org.pantry.food.model.VolunteerEvent;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * Create a report of the summary of activity at the pantry.
 * 
 * @author mcfarland_davej
 *
 */
public class ReportPantrySummary extends AbstractReportStrategy {

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
		nf.setMaximumFractionDigits(1);
	}

	@Override
	public String getTitle() {
		return "MCFP Pantry Summary";
	}

	@Override
	public ObservableList<TableColumn<ReportRow, String>> getColumns() {
		return toTableColumns(cols);
	}

	@Override
	public List<ReportRow> getRows() {
		List<ReportRow> rows = new ArrayList<>();
		try {
			rows.addAll(addCustomerData());
			rows.addAll(addVisitsData());
			rows.addAll(addDonationData());
			rows.addAll(addVolunteerData());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rows;
	}

	private List<ReportRow> addCustomerData() {
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

		List<ReportRow> rows = new ArrayList<>();

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

		rows.add(new ReportRow().addColumn("New Customers Month").addColumn(String.valueOf(numberMonthCustomers))
				.addColumn("Adults:" + numberMonthAdults + " Seniors:" + numberMonthSeniors + " Children:"
						+ numberMonthKids + " Households:" + distinctHouseholdsMonth.size()));
		rows.add(new ReportRow().addColumn("New Customers YTD").addColumn(String.valueOf(numberCustomers))
				.addColumn("Adults: " + numberNewAdults + " Seniors: " + numberNewSeniors + " Children: "
						+ numberNewKids + " Households: " + distinctHouseholdsYtd.size()));
		return rows;
	}

	private List<ReportRow> addVisitsData() throws ParseException {
		int numberTotalVisits = 0;
		int numberDistinctHouseholds = 0;
		int numberDistinctAdults = 0;
		int numberDistintKids = 0;
		int numberDistinctSeniors = 0;

		List<Visit> distinctHouseholdVisits = new ArrayList<Visit>();
		List<ReportRow> rows = new ArrayList<>();

		boolean bFound = false;
		Calendar mydate = new GregorianCalendar();

		List<Visit> visits = ApplicationContext.getVisitsDao().getAll();

		for (Visit visit : visits) {
			Date thedate = dateFormat.parse(visit.getDate());
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
			numberDistinctSeniors += visit.getNumberSeniors();
		}

		rows.add(new ReportRow().addColumn("Total Visits").addColumn(String.valueOf(numberTotalVisits)).addColumn(""));
		rows.add(new ReportRow().addColumn(" - Distinct Houses").addColumn(String.valueOf(numberDistinctHouseholds))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Distinct Adults").addColumn(String.valueOf(numberDistinctAdults))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Distinct Kids").addColumn(String.valueOf(numberDistintKids))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Distinct Seniors").addColumn(String.valueOf(numberDistinctSeniors))
				.addColumn(""));
		return rows;
	}

	private List<ReportRow> addVolunteerData() throws ParseException {
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
		List<ReportRow> rows = new ArrayList<>();
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

		rows.add(new ReportRow().addColumn("Total Hours").addColumn(nf.format(totalHours))
				.addColumn("Count:" + (distinctStudents.size() + distinctRegulars.size() + distinctOthers.size())));
		rows.add(new ReportRow().addColumn(" - Student Hours").addColumn(nf.format(totalStudentHours))
				.addColumn("Count:" + distinctStudents.size()));
		rows.add(new ReportRow().addColumn(" - Regular Hours").addColumn(nf.format(totalRegularHours))
				.addColumn("Count:" + distinctRegulars.size()));
		rows.add(new ReportRow().addColumn(" - Other Hours").addColumn(nf.format(totalOtherHours))
				.addColumn("Count:" + distinctOthers.size()));
		return rows;
	}

	private List<ReportRow> addDonationData() throws ParseException {
		FoodsDao foodsDao = ApplicationContext.getFoodsDao();
		Food totals = new Food();
		Calendar mydate = new GregorianCalendar();
		List<ReportRow> rows = new ArrayList<>();

		for (Food record : foodsDao.getAll()) {
			Date thedate = dateFormat.parse(record.getEntryDate());
			mydate.setTime(thedate);

			if (mydate.get(Calendar.MONTH) == monthSelected) {
				totals.addToCurrent(record);
			}
		}

		rows.add(new ReportRow().addColumn("Total Donations").addColumn(nf.format(totals.getTotal())).addColumn(""));
		rows.add(new ReportRow().addColumn(" - Community").addColumn(String.valueOf(nf.format(totals.getCommunity())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Pick N Save").addColumn(String.valueOf(nf.format(totals.getPickNSave())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Tefap").addColumn(String.valueOf(nf.format(totals.getTefap())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Second Harvest")
				.addColumn(String.valueOf(nf.format(totals.getSecondHarvest()))).addColumn(""));
		rows.add(new ReportRow().addColumn(" - Second Harvest Produce")
				.addColumn(String.valueOf(nf.format(totals.getSecondHarvestProduce()))).addColumn(""));
		rows.add(new ReportRow().addColumn(" - Milk").addColumn(String.valueOf(nf.format(totals.getMilk())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Non-Tefap").addColumn(String.valueOf(nf.format(totals.getNonTefap())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Non-Food").addColumn(String.valueOf(nf.format(totals.getNonFood())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Pantry Non-Food").addColumn(String.valueOf(nf.format(totals.getOther())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Pantry Produce").addColumn(String.valueOf(nf.format(totals.getOther2())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Produce").addColumn(String.valueOf(nf.format(totals.getProduce())))
				.addColumn(""));
		rows.add(new ReportRow().addColumn(" - Pantry Purchases")
				.addColumn(String.valueOf(nf.format(totals.getPantry()))).addColumn(""));
		return rows;
	}

} // end of class
