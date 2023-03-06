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
import java.util.logging.Logger;

import org.pantry.food.model.VolunteerHour;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of volunteer hours and the VolunteerHour class.
 * @author mcfarland_davej
 */
public class VolunteerHourDao implements CsvDao 
{
	private final static Logger log = Logger.getLogger(VolunteerHourDao.class.getName());
	
    private String startDir = "";

    private ArrayList<VolunteerHour> cvsList = new ArrayList<VolunteerHour>();

    public ArrayList<VolunteerHour> getCvsList(){return this.cvsList;}
    public int getCvsCount(){return this.cvsList.size();}

    public void setStartDir(String sDir){this.startDir = sDir;}

    private static final int VOLUNTEERHOURID      = 0;
    private static final int NUMBER_ADULTS        = 1;
    private static final int HOURS_ADULTS         = 2;
    private static final int NUMBER_STUDENTS      = 3;
    private static final int HOURS_STUDENTS       = 4;
    private static final int COMMENT              = 5;
    private static final int ENTRY_DATE           = 6;

    private static final String Col_VolunteerHourId = "volunteerhourid";
    private static final String Col_Num_Adults      = "num_adults";
    private static final String Col_Hrs_Adults      = "hrs_adults";
    private static final String Col_Num_Students    = "num_students";
    private static final String Col_Hrs_Students    = "hrs_students";
    private static final String Col_Comment         = "comment";
    private static final String Col_EntryDate       = "entrydate";

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#readCsvFile()
     */
    public void readCsvFile() throws FileNotFoundException, IOException
    {
    	log.info("VolunteerHourDao.readCsvFile");
    	
        if (startDir.length() == 0){
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerHours());
        cvsList = new ArrayList<VolunteerHour>();

        if (file.exists()){

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
                    VolunteerHour record = new VolunteerHour();
                    record.setVolunteerHourId(Integer.parseInt(nextLine[VOLUNTEERHOURID]));
                    record.setNumberAdults(Integer.parseInt(nextLine[NUMBER_ADULTS]));
                    record.setNumberAdultHours(Float.parseFloat(nextLine[HOURS_ADULTS]));
                    record.setNumberStudents(Integer.parseInt(nextLine[NUMBER_STUDENTS]));
                    record.setNumberStudentHours(Float.parseFloat(nextLine[HOURS_STUDENTS]));
                    record.setComment(nextLine[COMMENT]);
                    record.setEntryDate(nextLine[ENTRY_DATE]);
                    cvsList.add(record);
                } 
                else 
                {
                    firstLine = !firstLine;
                }

            }
            
            reader.close();
        } 
        else 
        {
            log.info("Volunteer Hours cvs file NOT found");
        }

    } // end of readCsvFile

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#saveCsvFile()
     */
    public void saveCsvFile() throws IOException
    {
    	log.info("VolunteerHourDao.saveCsvFile");
    	
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerHours());

        if (file.exists())
        {
            file.delete();
        }

        FileWriter fw = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fw);

        // add the column titles
        String[] titles = {Col_VolunteerHourId ,
                    Col_Num_Adults,
                    Col_Hrs_Adults,
                    Col_Num_Students,
                    Col_Hrs_Students,
                    Col_Comment,
                    Col_EntryDate
        };

        writer.writeNext(titles);

        for (int i = 0; i < cvsList.size(); i++)
        {
            VolunteerHour record = cvsList.get(i);
            writer.writeNext(record.getCvsEntry());

        }

        writer.close();
    }

    /*
     * Adds a visit object to the list (in memory).
     */
    public void add(VolunteerHour vol)
    {
        cvsList.add(vol);
    }

    /*
     *
     */
    public void edit(VolunteerHour vol)
    {
        for (int i = 0; i < cvsList.size(); i++){

            VolunteerHour testRecord = cvsList.get(i);

            if (testRecord.getVolunteerHourId() == vol.getVolunteerHourId()){

                testRecord.setNumberAdults(vol.getNumberAdults());
                testRecord.setNumberAdultHours(vol.getNumberAdultHours());
                testRecord.setNumberStudents(vol.getNumberStudents());
                testRecord.setNumberStudentHours(vol.getNumberStudentHours());
                testRecord.setComment(vol.getComment());
                testRecord.setEntryDate(vol.getEntryDate());

                break;
            }
        }


    } // end of edit

    public void delete(VolunteerHour vol)
    {
        for (int i = 0; i < cvsList.size(); i++)
        {
            VolunteerHour testRecord = cvsList.get(i);
            if (testRecord.getVolunteerHourId() == vol.getVolunteerHourId())
            {
                cvsList.remove(i);
                break;
            }
        }

    }	// end of delete

}
