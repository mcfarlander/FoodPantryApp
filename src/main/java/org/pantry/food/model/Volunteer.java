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

import javafx.beans.property.SimpleStringProperty;

/**
 * A POJO to describe a volunteer
 * 
 * @author mcfarland_davej
 */
public class Volunteer {

	private SimpleStringProperty volunteerIdProperty = new SimpleStringProperty();
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	private SimpleStringProperty phoneProperty = new SimpleStringProperty();
	private SimpleStringProperty emailProperty = new SimpleStringProperty();
	private SimpleStringProperty typeProperty = new SimpleStringProperty();
	private SimpleStringProperty noteProperty = new SimpleStringProperty();

	public Volunteer() {

	}

	public Volunteer(Volunteer other) {
		if (null == other) {
			return;
		}

		setVolunteerId(other.getVolunteerId());
		setName(other.getName());
		setPhone(other.getPhone());
		setEmail(other.getEmail());
		setType(other.getType());
		setNote(other.getNote());
	}

	public int getVolunteerId() {
		String value = volunteerIdProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setVolunteerId(int volunteerId) {
		volunteerIdProperty.set(String.valueOf(volunteerId));
	}

	public String getName() {
		return nameProperty.get();
	}

	public void setName(String name) {
		nameProperty.set(name);
	}

	public String getPhone() {
		return phoneProperty.get();
	}

	public void setPhone(String phone) {
		phoneProperty.set(phone);
	}

	public String getEmail() {
		return emailProperty.get();
	}

	public void setEmail(String email) {
		emailProperty.set(email);
	}

	public String getType() {
		return typeProperty.get();
	}

	public void setType(String type) {
		typeProperty.set(type);
	}

	public String getNote() {
		return noteProperty.get();
	}

	public void setNote(String note) {
		noteProperty.set(note);
	}

	public SimpleStringProperty volunteerIdProperty() {
		return volunteerIdProperty;
	}

	public SimpleStringProperty nameProperty() {
		return nameProperty;
	}

	public SimpleStringProperty phoneProperty() {
		return phoneProperty;
	}

	public SimpleStringProperty emailProperty() {
		return emailProperty;
	}

	public SimpleStringProperty typeProperty() {
		return typeProperty;
	}

	public SimpleStringProperty noteProperty() {
		return noteProperty;
	}

	/**
	 * Helper method to return an object to a jtable model.
	 * 
	 * @return
	 */
	public Object[] getVolunteerObject() {
		return new Object[] { this.getVolunteerId(), this.getName(), this.getPhone(), this.getEmail(), this.getType(),
				this.getNote() };

	}

}
