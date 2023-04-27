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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pantry.food.dao.mapper.ArrayRowMapper;
import org.pantry.food.dao.mapper.VisitRowMapper;
import org.pantry.food.model.Visit;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of visits and the
 * Visit class.
 *
 * @author mcfarland_davej
 */
public class VisitsDao extends AbstractCsvDao<Visit> {
	private static final String Col_VisitId = "visitid";
	private static final String Col_HouseholdId = "householdid";
	private static final String Col_New = "new";
	private static final String Col_NumberAdults = "numberadults";
	private static final String Col_NumberKids = "numberchildren";
	private static final String Col_NumberSeniors = "numberseniors";
	private static final String Col_WorkingIncome = "workingincome";
	private static final String Col_OtherIncome = "otherincome";
	private static final String Col_NoIncome = "noincome";
	private static final String Col_Date = "date";
	private static final String Col_WeekNumber = "weeknumber";
	private static final String Col_Active = "active";

	private String startDir = "";
	private List<String> householdIds = new ArrayList<>();
	private Set<String> tempHouseHoldIds = new HashSet<>();

	private NumberAsStringComparator numberAsStringComparator = new NumberAsStringComparator();

	/**
	 * IDs of every household in the visit list
	 * 
	 * @return
	 */
	public List<String> getHouseholdIds() {
		return householdIds;
	}

	@Override
	protected File getCsvFile() throws IOException {
		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		return new File(startDir + "/" + DataFiles.getInstance().getCsvFileVisits());
	}

	@Override
	protected String[] getCsvHeader() {
		return new String[] { Col_VisitId, Col_HouseholdId, Col_New, Col_NumberAdults, Col_NumberKids,
				Col_NumberSeniors, Col_WorkingIncome, Col_OtherIncome, Col_NoIncome, Col_Date, Col_WeekNumber,
				Col_Active };
	}

	@Override
	protected ArrayRowMapper<Visit> getRowMapper() {
		return new VisitRowMapper();
	}

	@Override
	protected int getId(Visit entity) {
		return entity.getId();
	}

	@Override
	protected void setDeactivated(Visit entity) {
		entity.setActive(false);
	}

	@Override
	protected void afterLineRead(Visit entity) {
		tempHouseHoldIds.add(String.valueOf(entity.getHouseholdId()));
	}

	@Override
	protected void afterRead(List<Visit> entities) {
		// Replace the existing household IDs with the new unique, sorted set
		householdIds.clear();
		householdIds.addAll(tempHouseHoldIds);
		householdIds.sort(numberAsStringComparator);
		tempHouseHoldIds.clear();
	}

}
