package org.pantry.food.dao.mapper;

import org.pantry.food.model.Customer;

public class CustomerRowMapper implements ArrayRowMapper<Customer> {

	private static final int CUSTOMERID = 0;
	private static final int HOUSEHOLDID = 1;
	private static final int PERSONID = 2;
	private static final int GENDER = 3;
	private static final int BIRTHDATE = 4;
	private static final int AGE = 5;
	private static final int MONTHREGISTERED = 6;
	private static final int NEWCUSTOMER = 7;
	private static final int COMMENTS = 8;
	private static final int ACTIVE = 9;

	@Override
	public Customer map(String[] row) {
		Customer customer = new Customer();
		customer.setCustomerId(Integer.parseInt(row[CUSTOMERID]));
		customer.setHouseholdId(Integer.parseInt(row[HOUSEHOLDID]));
		customer.setPersonId(Integer.parseInt(row[PERSONID]));
		customer.setGender(row[GENDER]);
		customer.setBirthDate(row[BIRTHDATE]);
		customer.setAge(Integer.parseInt(row[AGE]));
		customer.setMonthRegistered(Integer.parseInt(row[MONTHREGISTERED]));
		customer.setNewCustomer(Boolean.parseBoolean(row[NEWCUSTOMER]));
		customer.setComments(row[COMMENTS]);
		customer.setActive(Boolean.parseBoolean(row[ACTIVE]));

		return customer;
	}

	public String[] toCsvRow(Customer customer) {
		return new String[] { String.valueOf(customer.getCustomerId()), String.valueOf(customer.getHouseholdId()),
				String.valueOf(customer.getPersonId()), customer.getGender(), customer.getBirthDate(),
				String.valueOf(customer.getAge()), String.valueOf(customer.getMonthRegistered()),
				String.valueOf(customer.isNewCustomer()), customer.getComments(), String.valueOf(customer.isActive()) };
	}

}
