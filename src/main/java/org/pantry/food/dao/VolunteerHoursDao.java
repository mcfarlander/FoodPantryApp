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

import org.pantry.food.dao.mapper.ArrayRowMapper;
import org.pantry.food.dao.mapper.VolunteerHourRowMapper;
import org.pantry.food.model.VolunteerHoursSummary;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of volunteer hours and
 * the VolunteerHour class.
 * 
 * @author mcfarland_davej
 */
public class VolunteerHoursDao extends AbstractCsvDao<VolunteerHoursSummary> {
	private static final String Col_VolunteerHourId = "volunteerhourid";
	private static final String Col_Num_Adults = "num_adults";
	private static final String Col_Hrs_Adults = "hrs_adults";
	private static final String Col_Num_Students = "num_students";
	private static final String Col_Hrs_Students = "hrs_students";
	private static final String Col_Comment = "comment";
	private static final String Col_EntryDate = "entrydate";

	private final String[] csvHeader = { Col_VolunteerHourId, Col_Num_Adults, Col_Hrs_Adults, Col_Num_Students,
			Col_Hrs_Students, Col_Comment, Col_EntryDate };

	private File csvFile;

	@Override
	protected void setDeactivated(VolunteerHoursSummary entity) {
		super.delete(entity);
	}

	@Override
	protected ArrayRowMapper<VolunteerHoursSummary> getRowMapper() {
		return new VolunteerHourRowMapper();
	}

	@Override
	protected int getId(VolunteerHoursSummary entity) {
		return entity.getVolunteerHourId();
	}

	@Override
	protected File getCsvFile() throws IOException {
		if (null == csvFile) {
			csvFile = new File(new java.io.File(".").getCanonicalPath(),
					DataFiles.getInstance().getCsvFileVolunteerHours());
		}

		return csvFile;
	}

	@Override
	protected String[] getCsvHeader() {
		return csvHeader;
	}

}
