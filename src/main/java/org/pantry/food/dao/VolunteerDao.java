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

import org.pantry.food.model.Volunteer;
import org.pantry.food.ui.common.DataFiles;

/**
 * A class to contain all the logic to map between a file of volunteers and the Volunteer class.
 * @author mcfarland_davej
 */
public class VolunteerDao implements CsvDao<Volunteer>
{
	private final static Logger log = Logger.getLogger(VolunteerDao.class.getName());
	
    private String startDir = "";

    private ArrayList<Volunteer> csvList = new ArrayList<Volunteer>();

    public ArrayList<Volunteer> getCvsList(){return this.csvList;}
    public int getCvsCount(){return this.csvList.size();}

    public void setStartDir(String sDir){this.startDir = sDir;}

    private static final int VOLUNTEERID    = 0;
    private static final int NAME           = 1;
    private static final int PHONE     		= 2;
    private static final int EMAIL          = 3;
    private static final int TYPE           = 4;
    private static final int NOTE           = 5;

    private static final String Col_VolunteerId     = "volunteerid";
    private static final String Col_Name            = "name";
    private static final String Col_Phone	        = "phone";
    private static final String Col_Email           = "email";
    private static final String Col_Type            = "type";
    private static final String Col_Note            = "note";

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#readCsvFile()
     */
    public List<Volunteer> readCsvFile() throws FileNotFoundException, IOException
    {
    	log.info("VolunteerDao.readCsvFile");
    	
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteers());
        csvList = new ArrayList<Volunteer>();

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
                    Volunteer record = new Volunteer();
                    record.setVolunteerId(Integer.parseInt(nextLine[VOLUNTEERID]));
                    record.setName(nextLine[NAME]);
                    record.setPhone(nextLine[PHONE]);
                    record.setEmail(nextLine[EMAIL]);
                    record.setType(nextLine[TYPE]);
                    record.setNote(nextLine[NOTE]);

                    csvList.add(record);
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
            log.info("Volunteer cvs file NOT found");
        }

        return csvList;
    }// end of readCsvFile

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#saveCsvFile()
     */
    public void saveCsvFile() throws IOException
    {
    	log.info("VolunteerDao.saveCsvFile");
    	
        if (startDir.length() == 0)
        {
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteers());

        if (file.exists())
        {
            file.delete();
        }

        FileWriter fw = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fw);

        // add the column titles
        String[] titles = {Col_VolunteerId, Col_Name,
            Col_Phone, Col_Email,
            Col_Type, Col_Note
            };

        writer.writeNext(titles);

        for (int i = 0; i < csvList.size(); i++)
        {
            Volunteer record = csvList.get(i);
            writer.writeNext(record.getCvsEntry());
        }

        writer.close();
    }

    /*
     * Adds an object to the list (in memory).
     */
    public void add(Volunteer vol)
    {
        csvList.add(vol);
    }

    /*
     *
     */
    public void edit(Volunteer vol)
    {
        for (int i = 0; i < csvList.size(); i++)
        {
            Volunteer testRecord = csvList.get(i);

            if (testRecord.getVolunteerId() == vol.getVolunteerId())
            {
                testRecord.setName(vol.getName());
                testRecord.setPhone(vol.getPhone());
                testRecord.setEmail(vol.getEmail());
                testRecord.setType(vol.getType());
                testRecord.setNote(vol.getNote());

                break;
            }
        }

    }	// end of edit

    public void delete(Volunteer vol)
    {
        for (int i = 0; i < csvList.size(); i++){

            Volunteer testRecord = csvList.get(i);
            if (testRecord.getVolunteerId() == vol.getVolunteerId())
            {
                csvList.remove(i);
                break;
            }
        }

    }	// end of delete
    
    public String getTypeFromName(String name)
    {
    	String sreturn = "";
    	
    	for (int i = 0; i < csvList.size(); i++){

            Volunteer testRecord = csvList.get(i);
            if (testRecord.getName().equals(name))
            {
            	sreturn = testRecord.getType();
                break;
            }
        }
    	
    	return sreturn;
    }

}
