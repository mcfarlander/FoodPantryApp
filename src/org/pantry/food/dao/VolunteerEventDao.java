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

import org.pantry.food.model.VolunteerEvent;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of volunteer events and the VolunteerEvent class.
 * @author mcfarland_davej
 *
 */
public class VolunteerEventDao implements CsvDao 
{
	private final static Logger log = Logger.getLogger(VolunteerEventDao.class.getName());
	
    private String startDir = "";

    private ArrayList<VolunteerEvent> cvsList = new ArrayList<VolunteerEvent>();

    public ArrayList<VolunteerEvent> getCvsList(){return this.cvsList;}
    public int getCvsCount(){return this.cvsList.size();}

    public void setStartDir(String sDir){this.startDir = sDir;}

    private static final int VOLUNTEEREVENTID      	= 0;
    private static final int EVENT_NAME        		= 1;
    private static final int VOLUNTEER_NAME        	= 2;
    private static final int VOLUNTEER_HOURS      	= 3;
    private static final int NOTES             		= 4;
    private static final int EVENT_DATE           	= 5;

    private static final String Col_VolunteerEventId 	= "volunteereventid";
    private static final String Col_EventName      		= "event_name";
    private static final String Col_VolunteerName      	= "volunteer";
    private static final String Col_VolunteerHours    	= "hours";
    private static final String Col_Notes         		= "notes";
    private static final String Col_EventDate       	= "eventdate";

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#readCsvFile()
     */
    public void readCsvFile() throws FileNotFoundException, IOException
    {
    	log.info("VolunteerEventDao.readCsvFile");
    	
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerEvents());
        cvsList = new ArrayList<VolunteerEvent>();

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
                    VolunteerEvent record = new VolunteerEvent();
                    record.setVolunteerEventId(Integer.parseInt(nextLine[VOLUNTEEREVENTID]));
                    record.setEventName(nextLine[EVENT_NAME]);
                    record.setVolunteerName(nextLine[VOLUNTEER_NAME]);
                    record.setVolunteerHours(Double.parseDouble(nextLine[VOLUNTEER_HOURS]));
                    record.setNotes(nextLine[NOTES]);
                    record.setEventDate(nextLine[EVENT_DATE]);
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
            log.info("Volunteer Events cvs file NOT found");
        }


    }	// end of readCsvFile

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#saveCsvFile()
     */
    public void saveCsvFile() throws IOException
    {
    	log.info("VolunteerEventDao.saveCsvFile");
    	
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerEvents());

        if (file.exists())
        {
            file.delete();
        }

        log.info("creating and saving to csv file");
        FileWriter fw = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fw);

        // add the column titles
        String[] titles = {Col_VolunteerEventId ,
                    Col_EventName,
                    Col_VolunteerName,
                    Col_VolunteerHours,
                    Col_Notes,
                    Col_EventDate
        };

        writer.writeNext(titles);

        for (int i = 0; i < cvsList.size(); i++){
            VolunteerEvent record = cvsList.get(i);
            writer.writeNext(record.getCvsEntry());

        }

        writer.close();

    }

    /*
     * Adds a visit object to the list (in memory).
     */
    public void add(VolunteerEvent vol)
    {
        cvsList.add(vol);
    }

    /*
     *
     */
    public void edit(VolunteerEvent vol)
    {
        for (int i = 0; i < cvsList.size(); i++)
        {
            VolunteerEvent testRecord = cvsList.get(i);

            if (testRecord.getVolunteerEventId() == vol.getVolunteerEventId())
            {
                testRecord.setEventName(vol.getEventName());
                testRecord.setVolunteerName(vol.getVolunteerName());
                testRecord.setVolunteerHours(vol.getVolunteerHours());
                testRecord.setNotes(vol.getNotes());
                testRecord.setEventDate(vol.getEventDate());

                break;
            }
        }


    }// end of edit

    public void delete(VolunteerEvent vol)
    {
        for (int i = 0; i < cvsList.size(); i++)
        {
            VolunteerEvent testRecord = cvsList.get(i);
            if (testRecord.getVolunteerEventId() == vol.getVolunteerEventId())
            {
                cvsList.remove(i);
                break;
            }
        }

    } // end of delete

}
