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
package org.pantry.food.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;

/**
 * Models a volunteer event. An event is a number of hours a volunteer
 * volunteers on a specific date.
 * 
 * @author mcfarland_davej
 *
 */
public class VolunteerEvent {
	private Logger log = LogManager.getLogger(VolunteerEvent.class);
	private SimpleStringProperty idProperty = new SimpleStringProperty();
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	private SimpleStringProperty volunteerNameProperty = new SimpleStringProperty();
	private SimpleStringProperty hoursProperty = new SimpleStringProperty();
	private SimpleStringProperty notesProperty = new SimpleStringProperty();
	private SimpleStringProperty dateProperty = new SimpleStringProperty("");

	private double monthHrs[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public VolunteerEvent() {
	}

	public VolunteerEvent(VolunteerEvent other) {
		if (null == other) {
			return;
		}

		setVolunteerEventId(other.getVolunteerEventId());
		setEventName(other.getEventName());
		setVolunteerName(other.getVolunteerName());
		setVolunteerHours(other.getVolunteerHours());
		setNotes(other.getNotes());
		setEventDate(other.getEventDate());
		monthHrs = other.getMonthHrs();
	}

	public int getVolunteerEventId() {
		String value = idProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setVolunteerEventId(int id) {
		idProperty.set(String.valueOf(id));
	}

	public String getEventName() {
		return nameProperty.get();
	}

	public void setEventName(String name) {
		nameProperty.set(name);
	}

	public String getVolunteerName() {
		return volunteerNameProperty.get();
	}

	public void setVolunteerName(String volunteerName) {
		volunteerNameProperty.set(volunteerName);
	}

	public double getVolunteerHours() {
		String value = hoursProperty.get();
		if (null == value) {
			return 0;
		}
		return Double.valueOf(value);
	}

	public void setVolunteerHours(double volunteerHours) {
		hoursProperty.set(String.valueOf(volunteerHours));
	}

	public String getNotes() {
		return notesProperty.get();
	}

	public void setNotes(String notes) {
		notesProperty.set(notes);
	}

	public String getEventDate() {
		return dateProperty.get();
	}

	public void setEventDate(String eventDate) {
		dateProperty.set(eventDate);
	}

	public SimpleStringProperty idProperty() {
		return idProperty;
	}

	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	public SimpleStringProperty volunteerNameProperty() {
		return volunteerNameProperty;
	}

	public SimpleStringProperty hoursProperty() {
		return hoursProperty;
	}

	public SimpleStringProperty notesProperty() {
		return notesProperty;
	}

	public SimpleStringProperty dateProperty() {
		return dateProperty;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public double[] getMonthHrs() {
		return monthHrs;
	}

	public void addMonthHrs(VolunteerEvent record) {
		double recordHours = record.getVolunteerHours();
		setVolunteerHours(getVolunteerHours() + recordHours); // this will be the total

		// depending on the month, add to appropriate monthHrs array index
		try {
			Date testDate = dateFormat.parse(record.getEventDate());
			Calendar cal = Calendar.getInstance();
			cal.setTime(testDate);

			int index = cal.get(Calendar.MONTH);

			monthHrs[index] += recordHours;
		} catch (ParseException ex) {
			log.error(ex);
		}
	}

}
