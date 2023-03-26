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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.dao.VisitorsDao;
import org.pantry.food.model.Visit;

/**
 * Create a report for the visitors to the pantry for a given month.
 * 
 * @author mcfarland_davej
 */
public class ReportMonthlySummary extends ReportBase 
{
    private int monthSelected = 0;
    public int getMonthSelected(){ return this.monthSelected; }
    public void setMonthSelected(int iMonth){ this.monthSelected = iMonth; }

    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private String[] cols = new String [] 
    {
        "ID", "HouseholdId", "New?", "# Adults", 
        "# Kids", "# Seniors", "Working Income", "Other Income",
        "No Income", "Date"
    };
    
    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.ReportBase#createReportTable()
     */
    @Override
    public void createReportTable() 
    {
        this.setReportName("Monthly_Summary");
        this.setReportTitle("McFarland Community Food Pantry Monthly Summary");
        
        Calendar c1 = Calendar.getInstance(); // today
        this.setReportDescription(dateFormat.format(c1.getTime()));

        this.createHeader();

        // create the basic table
        this.getBuffer().append("<table border='1' cellpadding='4'>");

        // create a header row
        createRow(cols[0], cols[1], cols[2],
                cols[3], cols[4], cols[5],
                cols[6], cols[7], cols[8],
                cols[9], true);

        // create rest of the data rows
        loadReport();

        // finish the table
        this.getBuffer().append("</table>");

        // finish the html
        this.createFooter();

    }

    private void createRow(String col1, String col2, String col3,
            String col4, String col5, String col6,
            String col7, String col8, String col9, String col10, boolean isHeader)
    {
        this.getBuffer().append("<tr>");

        if (isHeader)
        {
	        this.getBuffer().append("<th>").append(col1).append("</th>");
	        this.getBuffer().append("<th>").append(col2).append("</th>");
	        this.getBuffer().append("<th>").append(col3).append("</th>");
	        this.getBuffer().append("<th>").append(col4).append("</th>");
	        this.getBuffer().append("<th>").append(col5).append("</th>");
	        this.getBuffer().append("<th>").append(col6).append("</th>");
	        this.getBuffer().append("<th>").append(col7).append("</th>");
	        this.getBuffer().append("<th>").append(col8).append("</th>");
	        this.getBuffer().append("<th>").append(col9).append("</th>");
	        this.getBuffer().append("<th>").append(col10).append("</th>");
        }
        else
        {
	        this.getBuffer().append("<td>").append(col1).append("</td>");
	        this.getBuffer().append("<td>").append(col2).append("</td>");
	        this.getBuffer().append("<td>").append(col3).append("</td>");
	        this.getBuffer().append("<td>").append(col4).append("</td>");
	        this.getBuffer().append("<td>").append(col5).append("</td>");
	        this.getBuffer().append("<td>").append(col6).append("</td>");
	        this.getBuffer().append("<td>").append(col7).append("</td>");
	        this.getBuffer().append("<td>").append(col8).append("</td>");
	        this.getBuffer().append("<td>").append(col9).append("</td>");
	        this.getBuffer().append("<td>").append(col10).append("</td>");
        }

        this.getBuffer().append("</tr>");

    }// end of createTable

    private void loadReport() 
    {
        try 
        {
            VisitorsDao visIo = new VisitorsDao();
            visIo.read();

            //Calendar cal = Calendar.getInstance();
            Calendar testCal = Calendar.getInstance();

            ArrayList<Visit> monthVisits = new ArrayList<Visit>();
            ArrayList<String> weekNumbers = new ArrayList<String>();

            int monthSumHouse = 0;
            int monthSumNew = 0;
            int monthSumAdults = 0;
            int monthSumKids = 0;
            int monthSumSeniors = 0;
            int monthSumWorkingIncome = 0;
            int monthSumOtherIncome = 0;
            int monthSumNoIncome = 0;

            for (int i = 0; i < visIo.getVisitCount(); i++)
            {
                Visit vis = visIo.getVisitList().get(i);

                if (vis.isActive())
                {
                    Date testDate = dateFormat.parse(vis.getVisitDate());
                    testCal.setTime(testDate);

                    if (testCal.get(Calendar.MONTH) == this.monthSelected)
                    {

                        monthVisits.add(vis);
                        monthSumHouse++; 

                        if (vis.isNewCustomer())
                        {
                            monthSumNew++;
                        }

                        monthSumAdults += vis.getNumberAdults();
                        monthSumKids += vis.getNumberKids();
                        monthSumSeniors += vis.getNumberSeniors();

                        if (vis.isWorkingIncome())
                        {
                            monthSumWorkingIncome++;
                        }

                        if (vis.isOtherIncome())
                        {
                            monthSumOtherIncome++;
                        }

                        if (vis.isNoIncome())
                        {
                            monthSumNoIncome++;
                        }


                        boolean bFound = false;
                        for (int j = 0; j < weekNumbers.size(); j++){
                            if (Integer.parseInt(weekNumbers.get(j).toString()) == vis.getVisitorWeekNumber())
                            {
                                bFound = true;
                            }

                        }

                        if (!bFound)
                        {
                            weekNumbers.add("" + vis.getVisitorWeekNumber());
                        }

                    }

                }

            }

            // loop thru the current visits and get summaries
            Collections.sort(weekNumbers); // sort the week numbers asc

            for (int i = 0; i < weekNumbers.size(); i++)
            {
                int iWeekNumber = Integer.parseInt(weekNumbers.get(i).toString());
                System.out.println("week number:" + iWeekNumber);

                int weekSumHouse = 0;
                int weekSumNew = 0;
                int weekSumAdults = 0;
                int weekSumKids = 0;
                int weekSumSeniors = 0;
                int weekSumWorkingIncome = 0;
                int weekSumOtherIncome = 0;
                int weekSumNoIncome = 0;

                for (int j = 0; j < monthVisits.size(); j++)
                {
                    Visit vis = monthVisits.get(j);

                    if (vis.getVisitorWeekNumber() == iWeekNumber)
                    {
                        weekSumHouse++;

                        if (vis.isNewCustomer())
                        {
                            weekSumNew++;
                        }

                        weekSumAdults += vis.getNumberAdults();
                        weekSumKids += vis.getNumberKids();
                        weekSumSeniors += vis.getNumberSeniors();

                        if (vis.isWorkingIncome())
                        {
                            weekSumWorkingIncome++;
                        }

                        if (vis.isOtherIncome())
                        {
                            weekSumOtherIncome++;
                        }

                        if (vis.isNoIncome())
                        {
                            weekSumNoIncome++;
                        }

                        String sNew = "";
                        if (vis.isNewCustomer())
                        {
                            sNew = "Yes";
                        } 
                        else 
                        {
                            sNew = "No";
                        }

                        String sWorking = "";
                        if (vis.isWorkingIncome())
                        {
                            sWorking = "Yes";
                        } 
                        else 
                        {
                            sWorking = "No";
                        }

                        String sOther = "";
                        if (vis.isOtherIncome())
                        {
                            sOther = "Yes";
                        } 
                        else 
                        {
                            sOther = "No";
                        }

                        String sNoIncome = "";
                        if (vis.isNoIncome())
                        {
                            sNoIncome = "Yes";
                        } 
                        else 
                        {
                            sNoIncome = "No";
                        }

                        // show the visit info on the table
                        createRow("" + weekSumHouse,
                                        "" + vis.getHouseholdId(),
                                        sNew,
                                        "" + vis.getNumberAdults(),
                                        "" + vis.getNumberKids(),
                                        "" + vis.getNumberSeniors(),
                                        sWorking,
                                        sOther,
                                        sNoIncome,
                                        "" + vis.getVisitorWeekNumber(),
                                        false
                        );

                    }

                }

                    // show the weekly summary info on the table

                   createRow("Week Sum:",
                             "w=" + weekSumHouse,
                             "w=" + weekSumNew,
                             "w=" + weekSumAdults,
                             "w=" + weekSumKids,
                             "w=" + weekSumSeniors,
                             "w=" + weekSumWorkingIncome,
                             "w=" + weekSumOtherIncome,
                             "w=" + weekSumNoIncome,
                             "Week " + (i + 1),
                             false
                    );


                   createRow("",
                             "",
                             "",
                             "",
                             "",
                             "",
                             "",
                             "",
                             "",
                             "",
                             false
                     );



            }

            // show the monthly summary line
            createRow("Month Totals:",
                      "T=" + monthSumHouse,
                      "T=" + monthSumNew,
                      "T=" + monthSumAdults,
                      "T=" + monthSumKids,
                      "T=" + monthSumSeniors,
                      "T=" + monthSumWorkingIncome,
                      "T=" + monthSumOtherIncome,
                      "T=" + monthSumNoIncome,
                      "Month Summary",
                      false

            );


        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ReportMonthlySummary.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(ReportMonthlySummary.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ReportMonthlySummary.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}// end of class
