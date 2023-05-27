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
package org.pantry.food.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pantry.food.dao.mapper.ArrayRowMapper;
import org.pantry.food.dao.mapper.CustomerRowMapper;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.common.DataFiles;

/**
 * Contains all the logic to map between a customer file and the Customer class.
 * Holds the source-of-truth repository of customer records. Updates the
 * customer records repository when the data file is changed and triggers an
 * observable event so interested observers can refresh their display of that
 * data.
 * 
 * @author mcfarland_davej
 */
public class CustomersDao extends AbstractCsvDao<Customer> {

	private static final String Col_CustomerId = "customerid";
	private static final String Col_HouseholdId = "householdid";
	private static final String Col_PersonId = "personid";
	private static final String Col_Gender = "gender";
	private static final String Col_BirthDate = "birthdate";
	private static final String Col_Age = "age";
	private static final String Col_MonthRegistered = "monthregistered";
	private static final String Col_NewCustomer = "new";
	private static final String Col_Comments = "comments";
	private static final String Col_Active = "active";

	private String startDir = "";
	private NumberAsStringComparator numberAsStringComparator = new NumberAsStringComparator();
	private List<String> householdIds = new ArrayList<>();
	private Map<Integer, List<Integer>> householdToPersonMap = new HashMap<>();
	private Set<String> tempHouseHoldIds = new HashSet<>();
	private Map<Integer, List<Integer>> tempHouseholdToPersonMap = new HashMap<>();

	/**
	 * IDs of every household in the customer list
	 * 
	 * @return
	 */
	public List<String> getHouseholdIds() {
		return householdIds;
	}

	/**
	 * @return map of each household ID to its associated persons
	 */
	public Map<Integer, List<Integer>> getHouseholdToPersonMap() {
		return householdToPersonMap;
	}

	@Override
	protected ArrayRowMapper<Customer> getRowMapper() {
		return new CustomerRowMapper();
	}

	@Override
	protected int getId(Customer entity) {
		return entity.getCustomerId();
	}

	@Override
	protected void setDeactivated(Customer entity) {
		entity.setActive(false);
	}

	@Override
	protected File getCsvFile() throws IOException {
		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		return new File(startDir + "/" + DataFiles.getInstance().getCsvFileCustomers());
	}

	@Override
	protected String[] getCsvHeader() {
		return new String[] { Col_CustomerId, Col_HouseholdId, Col_PersonId, Col_Gender, Col_BirthDate, Col_Age,
				Col_MonthRegistered, Col_NewCustomer, Col_Comments, Col_Active };
	}

	@Override
	protected void afterLineRead(Customer entity) {
		tempHouseHoldIds.add(String.valueOf(entity.getHouseholdId()));
		List<Integer> personIds = tempHouseholdToPersonMap.get(entity.getHouseholdId());
		if (null == personIds) {
			personIds = new ArrayList<>();
			tempHouseholdToPersonMap.put(entity.getHouseholdId(), personIds);
		}
		personIds.add(entity.getPersonId());
	}

	@Override
	protected void afterRead(List<Customer> entities) {
		// Replace the existing household IDs with the new unique, sorted set
		householdIds.clear();
		householdIds.addAll(tempHouseHoldIds);
		householdIds.sort(numberAsStringComparator);
		tempHouseHoldIds.clear();

		getHouseholdToPersonMap().putAll(tempHouseholdToPersonMap);
		tempHouseholdToPersonMap.clear();
	}

}
