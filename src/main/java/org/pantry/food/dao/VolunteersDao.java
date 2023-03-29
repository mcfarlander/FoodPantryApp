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
import org.pantry.food.dao.mapper.VolunteerRowMapper;
import org.pantry.food.model.Volunteer;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of volunteers and the
 * Volunteer class.
 * 
 * @author mcfarland_davej
 */
public class VolunteersDao extends AbstractCsvDao<Volunteer> {

	private static final String Col_VolunteerId = "volunteerid";
	private static final String Col_Name = "name";
	private static final String Col_Phone = "phone";
	private static final String Col_Email = "email";
	private static final String Col_Type = "type";
	private static final String Col_Note = "note";

	private String startDir = "";

	/**
	 * Only used by reports to look up a volunteer's volunteer type using only their
	 * given name
	 * 
	 * @param name
	 * @return
	 */
	public String getTypeFromName(String name) {
		String volunteerType = "";

		for (Volunteer volunteer : getAll()) {
			if (volunteer.getName().equals(name)) {
				volunteerType = volunteer.getType();
				break;
			}
		}

		return volunteerType;
	}

	@Override
	protected ArrayRowMapper<Volunteer> getRowMapper() {
		return new VolunteerRowMapper();
	}

	@Override
	protected int getId(Volunteer entity) {
		return entity.getVolunteerId();
	}

	@Override
	protected void setDeactivated(Volunteer entity) {
		super.delete(entity);
	}

	@Override
	protected File getCsvFile() throws IOException {
		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		return new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteers());
	}

	@Override
	protected String[] getCsvHeader() {
		return new String[] { Col_VolunteerId, Col_Name, Col_Phone, Col_Email, Col_Type, Col_Note };
	}

}
