/*
  Copyright 2012 Dave Johnson

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
import org.pantry.food.dao.mapper.VolunteerEventRowMapper;
import org.pantry.food.model.VolunteerEvent;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of volunteer events
 * and the VolunteerEvent class.
 * 
 * @author mcfarland_davej
 *
 */
public class VolunteerEventsDao extends AbstractCsvDao<VolunteerEvent> {
	private static final String Col_VolunteerEventId = "volunteereventid";
	private static final String Col_EventName = "event_name";
	private static final String Col_VolunteerName = "volunteer";
	private static final String Col_VolunteerHours = "hours";
	private static final String Col_Notes = "notes";
	private static final String Col_EventDate = "eventdate";

	private final String[] csvHeader = new String[] { Col_VolunteerEventId, Col_EventName, Col_VolunteerName,
			Col_VolunteerHours, Col_Notes, Col_EventDate };

	private File csvFile;

	@Override
	protected void setDeactivated(VolunteerEvent entity) {
		super.delete(entity);
	}

	@Override
	protected ArrayRowMapper<VolunteerEvent> getRowMapper() {
		return new VolunteerEventRowMapper();
	}

	@Override
	protected int getId(VolunteerEvent entity) {
		return entity.getVolunteerEventId();
	}

	@Override
	protected File getCsvFile() throws IOException {
		if (null == csvFile) {
			csvFile = new File(new java.io.File(".").getCanonicalPath(),
					DataFiles.getInstance().getCsvFileVolunteerEvents());
		}

		return csvFile;
	}

	@Override
	protected String[] getCsvHeader() {
		return csvHeader;
	}

}
