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

/**
 * A POJO describing a customer.
 * 
 * @author Dave Johnson
 */
public class Customer 
{
    private int customerId;
    private int householdId;
    private int personId;
    private String gender = "Female";
    private String birthDate;
    private int age;
    private int monthRegistered;
    private boolean newCustomer;
    private String comments;
    private boolean active = false;

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int custId) { this.customerId = custId; }
    
    public int getHouseholdId() { return householdId; }
    public void setHouseholdId(int householdId) { this.householdId = householdId; }

    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getMonthRegistered() { return monthRegistered; }
    public void setMonthRegistered(int monthRegistered) { this.monthRegistered = monthRegistered; }

    public boolean isNewCustomer() { return this.newCustomer; }
    public void setNewCustomer(boolean newCustomer) { this.newCustomer = newCustomer; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public boolean isActive() { return this.active; }
    public void setActive(boolean active) { this.active = active; }

    /**
     * Helper method to set a line in the csv file.
     * @return
     */
    public String[] getCvsEntry()
    {
        String[] entry = {"" + this.customerId,
                          "" + this.householdId,
                          "" + this.personId,
                          "" + this.gender,
                          "" + this.birthDate,
                          "" + this.age,
                          "" + this.monthRegistered,
                          "" + this.newCustomer,
                          "" + this.comments,
                          "" + this.active};

        return entry;
    }

    /**
     * Helper method to return an object to the jtable model.
     * @return
     */
    public Object[] getCustomerObject()
    {
        return new Object[] {this.customerId,
                             this.householdId,
                             this.personId,
                             this.gender,
                             this.birthDate,
                             this.age,
                             this.monthRegistered,
                             this.newCustomer,
                             this.comments,
                             this.active
        };
    }
    

}	// end of class
