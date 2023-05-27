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
 * Class to contain the CSV data file names and provide that information to
 * other classes uses in the application.
 * 
 * Note: Singleton Pattern.
 * 
 * @author mcfarland_davej
 *
 */
public class DataFiles {

	/** The instance of this class in the singleton pattern. */
	private static DataFiles instance = null;

	/** Constructor shouldn't ever be used. */
	protected DataFiles() {
		// Exists only to defeat instantiation.
	}

	/**
	 * Get the instance of DataFiles.
	 * 
	 * @return FormState the singleton instance
	 */
	public static DataFiles getInstance() {
		if (instance == null) {
			instance = new DataFiles();
		}
		return instance;
	}

	// Data files used in the application
	private String csvFileCustomers = "customers.csv";
	private String csvFileVisits = "visitors.csv";
	private String csvFileFoodRecord = "foodrecords.csv";
	@Deprecated(forRemoval = true)
	/**
	 * Volunteer Hours is now calculated from volunteer events. Do not use this
	 * file.
	 */
	private String csvFileVolunteerHours = "volunteerhours.csv";
	private String csvFileVolunteers = "volunteers.csv"; // regular volunteers
	private String csvFileVolunteerEvents = "volunteerevents.csv"; // regular volunteers hours

	public String getCsvFileCustomers() {
		return csvFileCustomers;
	}

	public void setCsvFileCustomers(String sfile) {
		csvFileCustomers = sfile;
	}

	public String getCsvFileVisits() {
		return csvFileVisits;
	}

	public void setCsvFileVisits(String sfile) {
		csvFileVisits = sfile;
	}

	public String getCsvFileFoodRecord() {
		return csvFileFoodRecord;
	}

	public void setCsvFileFoodRecord(String sfile) {
		csvFileFoodRecord = sfile;
	}

	public String getCsvFileVolunteerHours() {
		return csvFileVolunteerHours;
	}

	public void setCsvFileVolunteerHours(String sfile) {
		csvFileVolunteerHours = sfile;
	}

	public String getCsvFileVolunteers() {
		return csvFileVolunteers;
	}

	public void setCsvFileVolunteers(String sfile) {
		csvFileVolunteers = sfile;
	}

	public String getCsvFileVolunteerEvents() {
		return csvFileVolunteerEvents;
	}

	public void setCsvFileVolunteerEvents(String sfile) {
		csvFileVolunteerEvents = sfile;
	}

}
