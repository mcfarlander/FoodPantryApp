package org.pantry.food.ui.dialog;

import java.util.List;
import java.util.Map;

import org.pantry.food.model.Customer;

/**
 * Arguments that can be provided to the {@link AddEditCustomerDialog}
 */
public class AddEditCustomerDialogInput {

	private List<String> householdIds;
	private Map<Integer, List<Integer>> householdToPersonMap;
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

	public Map<Integer, List<Integer>> getHouseholdToPersonMap() {
		return householdToPersonMap;
	}

	public void setHouseholdToPersonMap(Map<Integer, List<Integer>> householdToPersonMap) {
		this.householdToPersonMap = householdToPersonMap;
	}
}
