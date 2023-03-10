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
 * A POJO to describe a volunteer
 * 
 * @author mcfarland_davej
 */
public class Volunteer
{
	private int volunteerId;
    private String name;
    private String phone;
    private String email;
    private String type; 	// adult, student, special, other, or unknown //TODO declare these constants
    private String note;

    public int getVolunteerId() {return volunteerId;}
	public void setVolunteerId(int volunteerId) {this.volunteerId = volunteerId;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getPhone() {return phone;}
	public void setPhone(String phone) {this.phone = phone;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public String getNote() {return note;}
	public void setNote(String note) {this.note = note;}
	
    /**
     * Helper for setting a line in the csv file.
     * @return
     */
    public String[] getCvsEntry()
    {
        String[] entry = {"" + this.getVolunteerId(),
                          this.getName(),
                          this.getPhone(),
                          this.getEmail(),
                          this.getType(),
                          this.getNote()};

        return entry;
    }

    /**
     * Helper method to return an object to a jtable model.
     * @return
     */
    public Object[] getVolunteerObject()
    {
        return new Object[] {this.getVolunteerId(),
                          this.getName(),
                          this.getPhone(),
                          this.getEmail(),
                          this.getType(),
                          this.getNote()
        };

    }

}
