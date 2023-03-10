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
 * A POJO to describe a visit.
 * 
 * @author Dave Johnson
 */
public class Visit 
{
    private int visitId = 0;
    private int householdId = 0;
    private int visitorWeekNumber = 0;
    private boolean newCustomer = false;
    private int numberAdults = 0;
    private int numberKids = 0;
    private int numberSeniors = 0;
    private boolean workingIncome = false;
    private boolean otherIncome = false;
    private boolean noIncome = true;
    private String visitDate = "";
    private boolean active = false;

    public int getVisitId() { return this.visitId; }
    public void setVisitId(int id) { this.visitId = id; }

    public int getHouseholdId() { return this.householdId; }
    public void setHouseholdId(int householdId) { this.householdId = householdId; }

    public boolean isNewCustomer() { return this.newCustomer; }
    public void setNewCustomer(boolean newCustomer) { this.newCustomer = newCustomer; }

    public boolean isNoIncome() { return this.noIncome; }
    public void setNoIncome(boolean noIncome) { this.noIncome = noIncome; }

    public int getNumberAdults() { return this.numberAdults; }
    public void setNumberAdults(int numberAdults) { this.numberAdults = numberAdults; }

    public int getNumberKids() { return this.numberKids; }
    public void setNumberKids(int numberKids) { this.numberKids = numberKids; }
    
    public int getNumberSeniors() { return this.numberSeniors; }
    public void setNumberSeniors(int numberSeniors) { this.numberSeniors = numberSeniors; }

    public boolean isOtherIncome() { return otherIncome; }
    public void setOtherIncome(boolean otherIncome) { this.otherIncome = otherIncome; }

    public int getVisitorWeekNumber() { return visitorWeekNumber; }
    public void setVisitorWeekNumber(int visitorWeekNumber) { this.visitorWeekNumber = visitorWeekNumber; }

    public boolean isWorkingIncome() { return workingIncome; }
    public void setWorkingIncome(boolean workingIncome) { this.workingIncome = workingIncome; }

    public String getVisitDate() { return visitDate; }
    public void setVisitDate(String visitDate) { this.visitDate = visitDate; }

    public boolean isActive() { return this.active; }
    public void setActive(boolean active) { this.active = active; }

    /**
     * Helper for setting a line in the csv file.
     * @return
     */
    public String[] getCvsEntry()
    {
        String[] entry = {"" + this.getVisitId(),
                          "" + this.getHouseholdId(),
                          "" + this.isNewCustomer(),
                          "" + this.getNumberAdults(),
                          "" + this.getNumberKids(),
                          "" + this.getNumberSeniors(),
                          "" + this.isWorkingIncome(),
                          "" + this.isOtherIncome(),
                          "" + this.isNoIncome(),
                          "" + this.getVisitDate(),
                          "" + this.getVisitorWeekNumber(),
                          "" + this.active};

        return entry;
    }

    /**
     * Helper method to return an object to a jtable model.
     * @return
     */
    public Object[] getVisitObject()
    {
        return new Object[] {this.getVisitId(),
                             this.getHouseholdId(),
                             this.isNewCustomer(),
                             this.getNumberAdults(),
                             this.getNumberKids(),
                             this.getNumberSeniors(),
                             this.isWorkingIncome(),
                             this.isOtherIncome(),
                             this.isNoIncome(),
                             this.getVisitDate(),
                             this.getVisitorWeekNumber(),
                             this.isActive()
        };

    }

}
