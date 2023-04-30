package org.pantry.food.ui.dialog;

import java.util.List;

import org.pantry.food.model.Customer;

/**
 * Arguments that can be provided to the {@link AddEditCustomerDialog}
 */
public class AddEditCustomerDialogInput {

	private List<String> householdIds;
	private Customer customer;

	public List<String> getHouseholdIds() {
		return householdIds;
	}

	public void setHouseholdIds(List<String> householdIds) {
		this.householdIds = householdIds;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
