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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A POJO to describe a volunteer event.
 * 
 * @author mcfarland_davej
 *
 */
public class VolunteerEvent 
{
	private int volunteerEventId;
	private String eventName;
	private String volunteerName;
	private double volunteerHours;
	private String notes;
	private String eventDate = "";
	
	private double monthHrs[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public int getVolunteerEventId() {return volunteerEventId;}
	public void setVolunteerEventId(int volunteerEventId) {this.volunteerEventId = volunteerEventId;}
	
	public String getEventName() {return eventName;}
	public void setEventName(String eventName) {this.eventName = eventName;}
	
	public String getVolunteerName() {return volunteerName;}
	public void setVolunteerName(String volunteerName) {this.volunteerName = volunteerName;}
	
	public double getVolunteerHours() {return volunteerHours;}
	public void setVolunteerHours(double volunteerHours) {this.volunteerHours = volunteerHours;}
	
	public String getNotes() {return notes;}
	public void setNotes(String notes) {this.notes = notes;}
	
	public String getEventDate() {return eventDate;}
	public void setEventDate(String eventDate) {this.eventDate = eventDate;}
	
	public double[] getMonthHrs() {return this.monthHrs;}
	
    /**
     * Helper for setting a line in the csv file.
     * @return
     */
    public String[] getCvsEntry()
    {
        String[] entry = {"" + this.getVolunteerEventId(),
                          this.getEventName(),
                          this.getVolunteerName(),
                          "" + this.getVolunteerHours(),
                          this.getNotes(),
                          this.getEventDate()};

        return entry;
    }

    /**
     * Helper method to return an object to a jtable model.
     * @return
     */
    public Object[] getVolunteerEventObject()
    {
        return new Object[] {this.getVolunteerEventId(),
                          this.getEventName(),
                          this.getVolunteerName(),
                          this.getVolunteerHours(),
                          this.getNotes(),
                          this.getEventDate()
        };

    }
    
    public void addMonthHrs(VolunteerEvent record)
    {
        this.volunteerHours += record.getVolunteerHours(); // this will be the total
        
        // depending on the month, add to appropriate monthHrs array index
		try 
		{
			Date testDate = dateFormat.parse(record.getEventDate());
			Calendar cal = Calendar.getInstance();
	        cal.setTime(testDate);
	     
	        int index = cal.get(Calendar.MONTH);
	        
	        this.monthHrs[index] += record.getVolunteerHours();
	        //System.out.println("index" + index + " adding:" + record.getVolunteerHours() + "  recorded:" + this.monthHrs[index]);
	        
		} 
	    catch (ParseException ex) 
		{
			Logger.getLogger(VolunteerEvent.class.getName()).log(Level.SEVERE, null, ex);
		}
          
    }

} // end of class
