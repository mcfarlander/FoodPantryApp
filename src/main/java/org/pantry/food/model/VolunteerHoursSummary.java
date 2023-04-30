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
package org.pantry.food.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Models a total of volunteer hours across multiple volunteers for a given date
 * 
 * @author mcfarland_davej
 */
public class VolunteerHoursSummary {
	private int volunteerHourId;
	private int numberAdults = 0;
	private float numberAdultHours = 0;
	private int numberStudents = 0;
	private float numberStudentHours = 0;
	private String comment = "";
	private String entryDate = "";

	/**
	 * @return the volunteerHourId
	 */
	public int getVolunteerHourId() {
		return volunteerHourId;
	}

	/**
	 * @param volunteerHourId the volunteerHourId to set
	 */
	public void setVolunteerHourId(int volunteerHourId) {
		this.volunteerHourId = volunteerHourId;
	}

	/**
	 * @return the numberAdults
	 */
	public int getNumberAdults() {
		return numberAdults;
	}

	/**
	 * @param numberAdults the numberAdults to set
	 */
	public void setNumberAdults(int numberAdults) {
		this.numberAdults = numberAdults;
	}

	/**
	 * @return the numberAdultHours
	 */
	public float getNumberAdultHours() {
		return numberAdultHours;
	}

	/**
	 * @param numberAdultHours the numberAdultHours to set
	 */
	public void setNumberAdultHours(float numberAdultHours) {
		this.numberAdultHours = numberAdultHours;
	}

	/**
	 * @return the numberStudents
	 */
	public int getNumberStudents() {
		return numberStudents;
	}

	/**
	 * @param numberStudents the numberStudents to set
	 */
	public void setNumberStudents(int numberStudents) {
		this.numberStudents = numberStudents;
	}

	/**
	 * @return the numberStudentHours
	 */
	public float getNumberStudentHours() {
		return numberStudentHours;
	}

	/**
	 * @param numberStudentHours the numberStudentHours to set
	 */
	public void setNumberStudentHours(float numberStudentHours) {
		this.numberStudentHours = numberStudentHours;
	}

	/**
	 * @return the entryDate
	 */
	public String getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public void addToCurrent(VolunteerHoursSummary record) {
		numberAdults += record.getNumberAdults();
		numberAdultHours += record.getNumberAdultHours();
		numberStudents += record.getNumberStudents();
		numberStudentHours += record.getNumberStudentHours();

		if (!StringUtils.isBlank(record.getComment())) {
			String comment = getComment();
			if (!StringUtils.isBlank(comment)) {
				comment += ", ";
			}
			comment += record.getComment();
			setComment(comment);
		}
	}

}
