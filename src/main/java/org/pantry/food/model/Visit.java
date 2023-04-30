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

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Models a customer visit
 * 
 * @author Dave Johnson
 */
public class Visit {
	private SimpleStringProperty idProperty = new SimpleStringProperty();
	private SimpleStringProperty householdIdProperty = new SimpleStringProperty();
	private SimpleIntegerProperty weekNumberProperty = new SimpleIntegerProperty();
	private SimpleBooleanProperty newCustomerProperty = new SimpleBooleanProperty();
	private SimpleStringProperty numberAdultsProperty = new SimpleStringProperty();
	private SimpleStringProperty numberKidsProperty = new SimpleStringProperty();
	private SimpleStringProperty numberSeniorsProperty = new SimpleStringProperty();
	private SimpleBooleanProperty workingIncomeProperty = new SimpleBooleanProperty();
	private SimpleBooleanProperty noIncomeProperty = new SimpleBooleanProperty();
	private SimpleBooleanProperty otherIncomeProperty = new SimpleBooleanProperty();
	private SimpleStringProperty dateProperty = new SimpleStringProperty();
	private SimpleBooleanProperty activeProperty = new SimpleBooleanProperty();

	public Visit() {
	}

	public Visit(Visit other) {
		if (null == other) {
			return;
		}

		setId(other.getId());
		setHouseholdId(other.getHouseholdId());
		setWeekNumber(other.getWeekNumber());
		setNewCustomer(other.isNewCustomer());
		setNumberAdults(other.getNumberAdults());
		setNumberKids(other.getNumberKids());
		setNumberSeniors(other.getNumberSeniors());
		setWorkingIncome(other.isWorkingIncome());
		setOtherIncome(other.isOtherIncome());
		setNoIncome(other.isNoIncome());
		setDate(other.getDate());
		setActive(other.isActive());
	}

	public int getId() {
		String value = idProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setId(int id) {
		idProperty.set(String.valueOf(id));
	}

	public int getHouseholdId() {
		String value = householdIdProperty.get();
		if (null == value || "".equals(value)) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setHouseholdId(int householdId) {
		householdIdProperty.set(String.valueOf(householdId));
	}

	public boolean isNewCustomer() {
		return newCustomerProperty.get();
	}

	public void setNewCustomer(boolean newCustomer) {
		newCustomerProperty.set(newCustomer);
	}

	public int getNumberAdults() {
		String value = numberAdultsProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setNumberAdults(int numberAdults) {
		numberAdultsProperty.set(String.valueOf(numberAdults));
	}

	public int getNumberKids() {
		String value = numberKidsProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setNumberKids(int numberKids) {
		numberKidsProperty.set(String.valueOf(numberKids));
	}

	public int getNumberSeniors() {
		String value = numberSeniorsProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setNumberSeniors(int numberSeniors) {
		numberSeniorsProperty.set(String.valueOf(numberSeniors));
	}

	public int getWeekNumber() {
		return weekNumberProperty.get();
	}

	public void setWeekNumber(int weekNumber) {
		weekNumberProperty.set(weekNumber);
	}

	public String getDate() {
		return dateProperty.get();
	}

	public void setDate(String visitDate) {
		dateProperty.set(visitDate);
	}

	public boolean isWorkingIncome() {
		return workingIncomeProperty.get();
	}

	public void setWorkingIncome(boolean workingIncome) {
		workingIncomeProperty.set(workingIncome);
	}

	public boolean isNoIncome() {
		return noIncomeProperty.get();
	}

	public void setNoIncome(boolean noIncome) {
		noIncomeProperty.set(noIncome);
	}

	public boolean isOtherIncome() {
		return otherIncomeProperty.get();
	}

	public void setOtherIncome(boolean otherIncome) {
		otherIncomeProperty.set(otherIncome);
	}

	public boolean isActive() {
		return activeProperty.get();
	}

	public void setActive(boolean active) {
		activeProperty.set(active);
	}

	public SimpleStringProperty idProperty() {
		return idProperty;
	}

	public SimpleStringProperty householdIdProperty() {
		return householdIdProperty;
	}

	public SimpleIntegerProperty weekNumberProperty() {
		return weekNumberProperty;
	}

	public SimpleBooleanProperty newCustomerProperty() {
		return newCustomerProperty;
	}

	public SimpleStringProperty numberAdultsProperty() {
		return numberAdultsProperty;
	}

	public SimpleStringProperty numberKidsProperty() {
		return numberKidsProperty;
	}

	public SimpleStringProperty numberSeniorsProperty() {
		return numberSeniorsProperty;
	}

	public SimpleBooleanProperty workingIncomeProperty() {
		return workingIncomeProperty;
	}

	public SimpleBooleanProperty noIncomeProperty() {
		return noIncomeProperty;
	}

	public SimpleBooleanProperty otherIncomeProperty() {
		return otherIncomeProperty;
	}

	public SimpleStringProperty dateProperty() {
		return dateProperty;
	}

	public SimpleBooleanProperty activeProperty() {
		return activeProperty;
	}

}
