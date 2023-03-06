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

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.pantry.food.model.Customer;
import org.pantry.food.ui.common.DataFiles;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


/**
 * A class to contain all the logic to map between a file of customer and the Customer class.
 * @author mcfarland_davej
 */
public class CustomersDao implements CsvDao 
{
	private final static Logger log = Logger.getLogger(CustomersDao.class.getName());
	
    private String startDir = "";
    
    private ArrayList<Customer> customerList = new ArrayList<Customer>();

    public ArrayList<Customer> getCustomerList(){return this.customerList;}
    public int getCustomerCount(){return this.customerList.size();}

    public void setStartDir(String sDir){this.startDir = sDir;}

    private static final int CUSTOMERID         = 0;
    private static final int HOUSEHOLDID        = 1;
    private static final int PERSONID           = 2;
    private static final int GENDER             = 3;
    private static final int BIRTHDATE          = 4;
    private static final int AGE                = 5;
    private static final int MONTHREGISTERED    = 6;
    private static final int NEWCUSTOMER        = 7;
    private static final int COMMENTS           = 8;
    private static final int ACTIVE             = 9;

    private static final String Col_CustomerId = "customerid";
    private static final String Col_HouseholdId = "householdid";
    private static final String Col_PersonId = "personid";
    private static final String Col_Gender = "gender";
    private static final String Col_BirthDate = "birthdate";
    private static final String Col_Age = "age";
    private static final String Col_MonthRegistered = "monthregistered";
    private static final String Col_NewCustomer = "new";
    private static final String Col_Comments = "comments";
    private static final String Col_Active = "active";

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#readCsvFile()
     */
    public void readCsvFile() throws FileNotFoundException, IOException
    {

        log.info("CustomersDao.readCsvFile");

        if (startDir.length() == 0){
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileCustomers());
        customerList = new ArrayList<Customer>();

        if (file.exists())
        {
            System.out.println("Customers cvs file found");
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
                    Customer cust = new Customer();
                    cust.setCustomerId(Integer.parseInt(nextLine[CUSTOMERID]));
                    cust.setHouseholdId(Integer.parseInt(nextLine[HOUSEHOLDID]));
                    cust.setPersonId(Integer.parseInt(nextLine[PERSONID]));
                    cust.setGender(nextLine[GENDER]);
                    cust.setBirthDate(nextLine[BIRTHDATE]);
                    cust.setAge(Integer.parseInt(nextLine[AGE]));
                    cust.setMonthRegistered(Integer.parseInt(nextLine[MONTHREGISTERED]));
                    cust.setNewCustomer(Boolean.parseBoolean(nextLine[NEWCUSTOMER]));
                    cust.setComments(nextLine[COMMENTS]);
                    cust.setActive(Boolean.parseBoolean(nextLine[ACTIVE]));

                    customerList.add(cust);

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
            log.info("Customers cvs file NOT found");
        }


    } // end of readCsvFile

    /*
     * (non-Javadoc)
     * @see org.pantry.food.dao.CsvDao#saveCsvFile()
     */
    public void saveCsvFile() throws IOException
    {

        log.info("CustomersDao.saveCsvFile");
        
        if (startDir.length() == 0){
            startDir = new java.io.File(".").getCanonicalPath();
        }

        File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileCustomers());

        if (file.exists()){
            file.delete();
        }

        FileWriter fw = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fw);

        // add the column titles
        String[] titles = {Col_CustomerId, Col_HouseholdId,
            Col_PersonId, Col_Gender,
            Col_BirthDate, Col_Age,
            Col_MonthRegistered, Col_NewCustomer,
            Col_Comments, Col_Active
        };

        writer.writeNext(titles);

        for (int i = 0; i < customerList.size(); i++)
        {
            Customer cust = customerList.get(i);
            writer.writeNext(cust.getCvsEntry());

        }

        writer.close();
    }

    /*
     * Adds a customer object to the customer list in memory.
     */
    public void add(Customer cust)
    {
        customerList.add(cust);
    }

    /**
     * Modifies a customer object in the list (in memory).
     * @param cust
     */
    public void edit(Customer cust)
    {

        for (int i = 0; i < customerList.size(); i++)
        {
            Customer testCust = customerList.get(i);
            if (testCust.getCustomerId() == cust.getCustomerId())
            {
                testCust.setHouseholdId(cust.getHouseholdId());
                testCust.setPersonId(cust.getPersonId());
                testCust.setGender(cust.getGender());
                testCust.setBirthDate(cust.getBirthDate());
                testCust.setAge(cust.getAge());
                testCust.setMonthRegistered(cust.getMonthRegistered());
                testCust.setNewCustomer(cust.isNewCustomer());
                testCust.setComments(cust.getComments());
                testCust.setActive(cust.isActive());

                break;
            }
        }

    }// end of editCustomer

    /*
     * Deletes a customer object from the list in memory.
     */
    public void delete(Customer cust)
    {
        for (int i = 0; i < customerList.size(); i++)
        {
            Customer testCust = customerList.get(i);
            if (testCust.getCustomerId() == cust.getCustomerId())
            {
                customerList.remove(i);
                break;
            }
        }

    }// end of deleteCustomer

}// end of class
