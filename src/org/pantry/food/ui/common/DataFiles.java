/*
  Copyright 2017 Dave Johnson

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
package org.pantry.food.ui.common;

/**
 * Class to contain the CSV data file names and provide that information
 * to other classes uses in the application.
 * 
 * Note: Singleton Pattern.
 * 
 * @author mcfarland_davej
 *
 */
public class DataFiles
{
	
	/** The instance of this class in the singleton pattern. */
    private static DataFiles instance = null;

    /** Constructor shouldn't ever be used. */
    protected DataFiles() {
      // Exists only to defeat instantiation.
    }

    /**
     * Get the instance of DataFiles.
     * @return	FormState	the singleton instance
     */
    public static DataFiles getInstance() {
      if(instance == null) {
         instance = new DataFiles();
      }
      return instance;
    }
    
    // Data files used in the application
    private String csvFileCustomers 			= "customers.csv";
    private String csvFileVisits 				= "visitors.csv";
    private String csvFileFoodRecord 			= "foodrecords.csv";
    private String cvsFileVolunteerHours 		= "volunteerhours.csv";
    private String cvsFileVolunteers 			= "volunteers.csv";			// regular volunteers
    private String cvsFileVolunteerEvents 		= "volunteerevents.csv";	// regular volunteers hours
    
    public String getCsvFileCustomers() { return this.csvFileCustomers; }
    public void setCsvFileCustomers(String sfile) { this.csvFileCustomers = sfile; }
    
    public String getCsvFileVisits() { return this.csvFileVisits; }
    public void setCsvFileVisits(String sfile) { this.csvFileVisits = sfile; }

    public String getCsvFileFoodRecord() { return this.csvFileFoodRecord; }
    public void setCsvFileFoodRecord(String sfile) { this.csvFileFoodRecord = sfile; }
    
    public String getCsvFileVolunteerHours() { return this.cvsFileVolunteerHours; }
    public void setCsvFileVolunteerHours(String sfile) { this.cvsFileVolunteerHours = sfile; }

    public String getCsvFileVolunteers() { return this.cvsFileVolunteers; }
    public void setCsvFileVolunteers(String sfile) { this.cvsFileVolunteers = sfile; }   
    
    public String getCsvFileVolunteerEvents() { return this.cvsFileVolunteerEvents; }
    public void setCsvFileVolunteerEvents(String sfile) { this.cvsFileVolunteerEvents = sfile; }
    
}
