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
package org.pantry.food.reports;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.dao.VolunteerHourDao;
import org.pantry.food.model.VolunteerHour;


/**
 * Create a report on the actual volunteering at the pantry.
 * 
 * @author davej
 */
public class ReportVolunteerHours  extends ReportBase 
{
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    NumberFormat nf = NumberFormat.getInstance();

    private String[] cols = new String [] {
        "Month",
        "Number Adults", "Total Adult Hrs",
        "Number Students", "Total Student Hrs", "Comments"
    };

    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.ReportBase#createReportTable()
     */
    @Override
    public void createReportTable() 
    {
        this.setReportName("Volunteer_Hours");
        this.setReportTitle("Summary Of Volunteer Hours");

        Calendar c1 = Calendar.getInstance(); // today
        this.setReportDescription(dateFormat.format(c1.getTime()));

        nf.setMaximumFractionDigits(1);

        this.createHeader();

        // create the basic table
        this.getBuffer().append("\n <table border='1' cellpadding='4'> \n");

        // create a header row
        createRow(cols[0], cols[1], cols[2],
                cols[3], cols[4], cols[5], true);

        // create rest of the data rows
        loadReport();

        // finish the table
        this.getBuffer().append("</table> \n");

        // finish the html
        this.createFooter();

    }

    private void createRow(String col1, String col2, String col3,
            String col4, String col5, String col6,
            boolean isHeader)
    {

        this.getBuffer().append("<tr> \n");
        
        if (isHeader)
        {
	        this.getBuffer().append("<th>").append(col1).append("</th> \n");
	        this.getBuffer().append("<th>").append(col2).append("</th> \n");
	        this.getBuffer().append("<th>").append(col3).append("</th> \n");
	        this.getBuffer().append("<th>").append(col4).append("</th> \n");
	        this.getBuffer().append("<th>").append(col5).append("</th> \n");
	        this.getBuffer().append("<th>").append(col6).append("</th> \n");
        }
        else
        {
	        this.getBuffer().append("<td>").append(col1).append("</td> \n");
	        this.getBuffer().append("<td>").append(col2).append("</td> \n");
	        this.getBuffer().append("<td>").append(col3).append("</td> \n");
	        this.getBuffer().append("<td>").append(col4).append("</td> \n");
	        this.getBuffer().append("<td>").append(col5).append("</td> \n");
	        this.getBuffer().append("<td>").append(col6).append("</td> \n");
        }
        
        this.getBuffer().append("</tr> \n");


    }// end of createTable


    private void addBlankLine()
    {
            createRow("","","","","","", false);
    }

    private void loadReport()
    {
        try 
        {
            VolunteerHourDao recIo = new VolunteerHourDao();
            recIo.read();

            // use the POJO to store the working sums
            VolunteerHour janRecord = new VolunteerHour();
            VolunteerHour febRecord = new VolunteerHour();
            VolunteerHour marRecord = new VolunteerHour();
            VolunteerHour aprRecord = new VolunteerHour();
            VolunteerHour mayRecord = new VolunteerHour();
            VolunteerHour junRecord = new VolunteerHour();
            VolunteerHour julRecord = new VolunteerHour();
            VolunteerHour augRecord = new VolunteerHour();
            VolunteerHour sepRecord = new VolunteerHour();
            VolunteerHour octRecord = new VolunteerHour();
            VolunteerHour novRecord = new VolunteerHour();
            VolunteerHour decRecord = new VolunteerHour();

            Calendar cal = Calendar.getInstance();

             for (int i = 0; i < recIo.getCvsCount(); i++){
                VolunteerHour record = recIo.getCvsList().get(i);

                Date testDate = dateFormat.parse(record.getEntryDate());
                cal.setTime(testDate);

                switch(cal.get(Calendar.MONTH)){

                    case 0:
                        janRecord.addToCurrent(record);
                        break;
                    case 1:
                        febRecord.addToCurrent(record);
                        break;
                    case 2:
                        marRecord.addToCurrent(record);
                        break;
                    case 3:
                        aprRecord.addToCurrent(record);
                        break;
                    case 4:
                        mayRecord.addToCurrent(record);
                        break;
                    case 5:
                        junRecord.addToCurrent(record);
                        break;
                    case 6:
                        julRecord.addToCurrent(record);
                        break;
                    case 7:
                        augRecord.addToCurrent(record);
                        break;
                    case 8:
                        sepRecord.addToCurrent(record);
                        break;
                    case 9:
                        octRecord.addToCurrent(record);
                        break;
                    case 10:
                        novRecord.addToCurrent(record);
                        break;
                    case 11:
                        decRecord.addToCurrent(record);
                        break;
                    default:
                        break;

                }

            }

           createRow("January", 
                   nf.format(janRecord.getNumberAdults()), nf.format(janRecord.getNumberAdultHours()),
                   nf.format(janRecord.getNumberStudents()), nf.format(janRecord.getNumberStudentHours()),
                   janRecord.getComment(), false);

           createRow("Febuary", 
                   nf.format(febRecord.getNumberAdults()), nf.format(febRecord.getNumberAdultHours()),
                   nf.format(febRecord.getNumberStudents()), nf.format(febRecord.getNumberStudentHours()),
                   febRecord.getComment(), false);

           createRow("March", 
                   nf.format(marRecord.getNumberAdults()), nf.format(marRecord.getNumberAdultHours()),
                   nf.format(marRecord.getNumberStudents()), nf.format(marRecord.getNumberStudentHours()),
                   marRecord.getComment(), false);

           addBlankLine();

           createRow("April", 
                   nf.format(aprRecord.getNumberAdults()), nf.format(aprRecord.getNumberAdultHours()),
                   nf.format(aprRecord.getNumberStudents()), nf.format(aprRecord.getNumberStudentHours()),
                   aprRecord.getComment(), false);

           createRow("May", 
                   nf.format(mayRecord.getNumberAdults()), nf.format(mayRecord.getNumberAdultHours()),
                   nf.format(mayRecord.getNumberStudents()), nf.format(mayRecord.getNumberStudentHours()),
                   mayRecord.getComment(), false);

           createRow("June", 
                   nf.format(junRecord.getNumberAdults()), nf.format(junRecord.getNumberAdultHours()),
                   nf.format(junRecord.getNumberStudents()), nf.format(junRecord.getNumberStudentHours()),
                   junRecord.getComment(), false);

           addBlankLine();

           createRow("July", 
                   nf.format(julRecord.getNumberAdults()), nf.format(julRecord.getNumberAdultHours()),
                   nf.format(julRecord.getNumberStudents()), nf.format(julRecord.getNumberStudentHours()),
                   julRecord.getComment(), false);

           createRow("August", 
                   nf.format(augRecord.getNumberAdults()), nf.format(augRecord.getNumberAdultHours()),
                   nf.format(augRecord.getNumberStudents()), nf.format(augRecord.getNumberStudentHours()),
                   augRecord.getComment(), false);

           createRow("September", 
                   nf.format(sepRecord.getNumberAdults()), nf.format(sepRecord.getNumberAdultHours()),
                   nf.format(sepRecord.getNumberStudents()), nf.format(sepRecord.getNumberStudentHours()),
                   sepRecord.getComment(), false);

           addBlankLine();

           createRow("October", 
                   nf.format(octRecord.getNumberAdults()), nf.format(octRecord.getNumberAdultHours()),
                   nf.format(octRecord.getNumberStudents()), nf.format(octRecord.getNumberStudentHours()),
                   octRecord.getComment(), false);

           createRow("November", 
                   nf.format(novRecord.getNumberAdults()), nf.format(novRecord.getNumberAdultHours()),
                   nf.format(novRecord.getNumberStudents()), nf.format(novRecord.getNumberStudentHours()),
                   novRecord.getComment(), false);

           createRow("December", 
                   nf.format(decRecord.getNumberAdults()), nf.format(decRecord.getNumberAdultHours()),
                   nf.format(decRecord.getNumberStudents()), nf.format(decRecord.getNumberStudentHours()),
                   decRecord.getComment(), false);

           addBlankLine();


        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ReportVolunteerHours.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(ReportVolunteerHours.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ReportVolunteerHours.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
