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
package org.pantry.food.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.pantry.food.model.Visit;
import org.pantry.food.ui.common.DataFiles;

/**
 *A class to contain all the logic to map between a file of visits and the Visit class.
 *
 * @author mcfarland_davej
 */
public class VisitorsDao implements CsvDao<Visit>
{
	private final static Logger log = Logger.getLogger(VisitorsDao.class.getName());
	
    private String startDir = "";
    
    private ArrayList<Visit> visitList = new ArrayList<Visit>();

    public ArrayList<Visit> getVisitList(){return this.visitList;}
    public int getVisitCount(){return this.visitList.size();}

    public void setStartDir(String sDir){this.startDir = sDir;}

    private static final int VISITID          = 0;
    private static final int HOUSEHOLDID      = 1;
    private static final int NEW              = 2;
    private static final int NUMBER_ADULTS    = 3;
    private static final int NUMBER_KIDS      = 4;
    private static final int NUMBER_SENIORS	  = 5;
    private static final int WORKING_INCOME   = 6;
    private static final int OTHER_INCOME     = 7;
    private static final int NO_INCOME        = 8;
    private static final int DATE             = 9;
    private static final int WEEK_NUMBER      = 10;
    private static final int ACTIVE           = 11;

    private static final String Col_VisitId = "visitid";
    private static final String Col_HouseholdId = "householdid";
    private static final String Col_New = "new";
    private static final String Col_NumberAdults = "numberadults";
    private static final String Col_NumberKids = "numberchildren";
    private static final String Col_NumberSeniors = "numberseniors";
    private static final String Col_WorkingIncome = "workingincome";
    private static final String Col_OtherIncome = "otherincome";
    private static final String Col_NoIncome = "noincome";
    private static final String Col_Date = "date";
    private static final String Col_WeekNumber = "weeknumber";
    private static final String Col_Active = "active";


    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#readCsvFile()
     */
    public List<Visit> readCsvFile() throws FileNotFoundException, IOException
    {
        log.info("VisitorsDao.readCsvFile");
        
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVisits());
        visitList = new ArrayList<Visit>();

        if (file.exists())
        {
            //read in the whole file into a list
            FileReader fr = new FileReader(file);
            CSVReader reader = new CSVReader(fr);

            String [] nextLine;
            boolean firstLine = true;
            while ((nextLine = reader.readNext()) != null) 
            {
               // nextLine[] is an array of values from the line

                if (!firstLine)
                {
                    Visit vis = new Visit();
                    vis.setVisitId(Integer.parseInt(nextLine[VISITID]));
                    vis.setHouseholdId(Integer.parseInt(nextLine[HOUSEHOLDID]));
                    vis.setNewCustomer(Boolean.parseBoolean(nextLine[NEW]));
                    vis.setNumberAdults(Integer.parseInt(nextLine[NUMBER_ADULTS]));
                    vis.setNumberKids(Integer.parseInt(nextLine[NUMBER_KIDS]));
                    vis.setNumberSeniors(Integer.parseInt(nextLine[NUMBER_SENIORS]));
                    vis.setWorkingIncome(Boolean.parseBoolean(nextLine[WORKING_INCOME]));
                    vis.setOtherIncome(Boolean.parseBoolean(nextLine[OTHER_INCOME]));
                    vis.setNoIncome(Boolean.parseBoolean(nextLine[NO_INCOME]));
                    vis.setVisitDate(nextLine[DATE]);
                    vis.setVisitorWeekNumber(Integer.parseInt(nextLine[WEEK_NUMBER]));
                    vis.setActive(Boolean.parseBoolean(nextLine[ACTIVE]));

                    visitList.add(vis);

                } else {
                    firstLine = !firstLine;
                }

            }
            
            reader.close();

        } 
        else
        {
            log.info("Visitors cvs file NOT found");
        }
        
        return visitList;
    }// end of readCsvFile

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#saveCsvFile()
     */
    public void saveCsvFile() throws IOException
    {
    	log.info("VisitorsDao.saveCsvFile");
    	
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVisits());

        if (file.exists())
        {
            file.delete();
        }

        FileWriter fw = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fw);

        // add the column titles
        String[] titles = {Col_VisitId, Col_HouseholdId,
            Col_New, Col_NumberAdults,
            Col_NumberKids, Col_NumberSeniors, Col_WorkingIncome,
            Col_OtherIncome, Col_NoIncome,
            Col_Date, Col_WeekNumber,
            Col_Active
        };

        writer.writeNext(titles);

        for (int i = 0; i < visitList.size(); i++)
        {
            Visit vis = visitList.get(i);
            writer.writeNext(vis.getCvsEntry());

        }
        
        writer.close();
        
    }

    /*
     * Adds a visit object to the list (in memory).
     */
    public void add(Visit vis)
    {
        visitList.add(vis);
    }

    /*
     * 
     */
    public void edit(Visit vis)
    {

        for (int i = 0; i < visitList.size(); i++)
        {
            Visit testVisit = visitList.get(i);
            
            if (testVisit.getVisitId() == vis.getVisitId())
            {
                testVisit.setHouseholdId(vis.getHouseholdId());
                testVisit.setNewCustomer(vis.isNewCustomer());
                testVisit.setNumberAdults(vis.getNumberAdults());
                testVisit.setNumberKids(vis.getNumberKids());
                testVisit.setNumberSeniors(vis.getNumberSeniors());
                testVisit.setWorkingIncome(vis.isWorkingIncome());
                testVisit.setOtherIncome(vis.isOtherIncome());
                testVisit.setNoIncome(vis.isNoIncome());
                testVisit.setVisitDate(vis.getVisitDate());
                testVisit.setVisitorWeekNumber(vis.getVisitorWeekNumber());
                testVisit.setActive(vis.isActive());

                break;
            }
        }
        

    }	// end of editVisit

    public void delete(Visit vis)
    {
        for (int i = 0; i < visitList.size(); i++)
        {
            Visit testVisit = visitList.get(i);
            if (testVisit.getVisitId() == vis.getVisitId())
            {
                visitList.remove(i);
                break;
            }
        }

    }	// end of deleteVisit

}	// end of class
