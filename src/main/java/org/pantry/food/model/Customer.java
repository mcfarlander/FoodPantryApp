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
import javafx.beans.property.SimpleStringProperty;

/**
 * A POJO describing a customer
 * 
 * @author Dave Johnson
 */
public class Customer {
	private SimpleStringProperty customerIdProperty = new SimpleStringProperty();
	private SimpleStringProperty householdIdProperty = new SimpleStringProperty();
	private SimpleStringProperty personIdProperty = new SimpleStringProperty();
	private SimpleStringProperty genderProperty = new SimpleStringProperty("Female");
	private SimpleStringProperty birthDateProperty = new SimpleStringProperty();
	private SimpleStringProperty ageProperty = new SimpleStringProperty();
	private SimpleStringProperty monthRegisteredProperty = new SimpleStringProperty();
	private SimpleBooleanProperty newCustomerProperty = new SimpleBooleanProperty();
	private SimpleStringProperty commentsProperty = new SimpleStringProperty();
	private SimpleBooleanProperty activeProperty = new SimpleBooleanProperty();

	public Customer() {
	}

	public Customer(Customer clone) {
		if (null == clone) {
			return;
		}
		setCustomerId(clone.getCustomerId());
		setHouseholdId(clone.getHouseholdId());
		setPersonId(clone.getPersonId());
		setGender(clone.getGender());
		setBirthDate(clone.getBirthDate());
		setAge(clone.getAge());
		setMonthRegistered(clone.getMonthRegistered());
		setNewCustomer(clone.isNewCustomer());
		setComments(clone.getComments());
		setActive(clone.isActive());
	}

	public int getCustomerId() {
		String value = customerIdProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setCustomerId(int custId) {
		this.customerIdProperty.set(String.valueOf(custId));
	}

	public int getHouseholdId() {
		String value = householdIdProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setHouseholdId(int householdId) {
		this.householdIdProperty.set(String.valueOf(householdId));
	}

	public int getPersonId() {
		String value = personIdProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setPersonId(int personId) {
		this.personIdProperty.set(String.valueOf(personId));
	}

	public String getGender() {
		return genderProperty.get();
	}

	public void setGender(String gender) {
		this.genderProperty.set(gender);
	}

	public String getBirthDate() {
		return birthDateProperty.get();
	}

	public void setBirthDate(String birthDate) {
		this.birthDateProperty.set(birthDate);
	}

	public int getAge() {
		String value = ageProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setAge(int age) {
		this.ageProperty.set(String.valueOf(age));
	}

	public int getMonthRegistered() {
		String value = monthRegisteredProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setMonthRegistered(int monthRegistered) {
		this.monthRegisteredProperty.set(String.valueOf(monthRegistered));
	}

	public boolean isNewCustomer() {
		return newCustomerProperty.get();
	}

	public void setNewCustomer(boolean newCustomer) {
		this.newCustomerProperty.set(newCustomer);
	}

	public String getComments() {
		return commentsProperty.get();
	}

	public void setComments(String comments) {
		this.commentsProperty.set(comments);
	}

	public boolean isActive() {
		return activeProperty.get();
	}

	public void setActive(boolean active) {
		this.activeProperty.set(active);
	}

	public SimpleStringProperty customerIdProperty() {
		return customerIdProperty;
	}

	public SimpleStringProperty householdIdProperty() {
		return householdIdProperty;
	}

	public SimpleStringProperty personIdProperty() {
		return personIdProperty;
	}

	public SimpleStringProperty genderProperty() {
		return genderProperty;
	}

	public SimpleStringProperty birthDateProperty() {
		return birthDateProperty;
	}

	public SimpleStringProperty ageProperty() {
		return ageProperty;
	}

	public SimpleStringProperty monthRegisteredProperty() {
		return monthRegisteredProperty;
	}

	public SimpleBooleanProperty newCustomerProperty() {
		return newCustomerProperty;
	}

	public SimpleStringProperty commentsProperty() {
		return commentsProperty;
	}

	public SimpleBooleanProperty activeProperty() {
		return activeProperty;
	}

	/**
	 * Helper method to set a line in the csv file.
	 * 
	 * @return
	 */
	public String[] getCsvEntry() {
		String[] entry = { "" + customerIdProperty.get(), "" + householdIdProperty.get(), "" + personIdProperty.get(),
				"" + genderProperty.get(), "" + birthDateProperty.get(), "" + ageProperty.get(),
				"" + monthRegisteredProperty.get(), "" + newCustomerProperty.get(), "" + commentsProperty.get(),
				"" + activeProperty.get() };

		return entry;
	}

	/**
	 * Helper method to return an object to the jtable model.
	 * 
	 * @return
	 */
	public Object[] getCustomerObject() {
		return new Object[] { customerIdProperty.get(), householdIdProperty.get(), personIdProperty.get(),
				genderProperty.get(), birthDateProperty.get(), ageProperty.get(), monthRegisteredProperty.get(),
				newCustomerProperty.get(), commentsProperty.get(), activeProperty.get() };
	}

} // end of class
