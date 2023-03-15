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

import org.pantry.food.dao.FoodRecordDao;
import org.pantry.food.model.FoodRecord;

/**
 * Create a report based on the donations of food and other misc items.
 * 
 * @author mcfarland_davej
 */
public class ReportDonatedFoodWeight extends ReportBase {

    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    NumberFormat nf = NumberFormat.getInstance();

    private String[] cols = new String [] 
    {
        "Month", "Total Weight (lbs)", "Pick N Save", "Community",
        "Non-TEFAP", "TEFAP", "2nd Harvest", "2nd Harvest Produce",
        "Pantry Purchase", "Pantry Non-Food", "NonFood", "Milk", "Bread", "Produce", "Comments"
    };

    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.ReportBase#createReportTable()
     */
    @Override
    public void createReportTable() 
    {
        this.setReportName("Donated_Food_Weight");
        this.setReportTitle("Donated Food Weight Record");

        Calendar c1 = Calendar.getInstance(); // today
        this.setReportDescription(dateFormat.format(c1.getTime()));

        nf.setMaximumFractionDigits(1);

        this.createHeader();

        // create the basic table
        this.getBuffer().append("<table border='1' cellpadding='4'>");

        // create a header row
        createRow(cols[0], cols[1], cols[2],
                cols[3], cols[4], cols[5],
                cols[6], cols[7], cols[8],
                cols[9], cols[10], cols[11],
                cols[12], cols[13], cols[14], true);

        // create rest of the data rows
        loadReport();

        // finish the table
        this.getBuffer().append("</table>");

        // finish the html
        this.createFooter();

    }

    private void createRow(String col1, String col2, String col3,
            String col4, String col5, String col6,
            String col7, String col8, String col9,
            String col10, String col11, String col12,
            String col13, String col14, String col15, boolean isHeader)
    {

        this.getBuffer().append("<tr>\n");
        
        if (isHeader)
        {
	        this.getBuffer().append("<th>").append(col1).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col2).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col3).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col4).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col5).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col6).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col7).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col8).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col9).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col10).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col11).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col12).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col13).append("</th>").append("\n");
	        this.getBuffer().append("<th>").append(col14).append("</th>").append("\n");  
	        this.getBuffer().append("<th>").append(col15).append("</th>").append("\n");
        }
        else
        {
	        this.getBuffer().append("<td>").append(col1).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col2).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col3).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col4).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col5).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col6).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col7).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col8).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col9).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col10).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col11).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col12).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col13).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col14).append("</td>").append("\n");
	        this.getBuffer().append("<td>").append(col15).append("</td>").append("\n");
        }

        this.getBuffer().append("</tr>").append("\n");

    }

    private void loadReport()
    {
        try 
        {
            FoodRecordDao recIo = new FoodRecordDao();
            recIo.readCsvFile();

            // use the POJO to store the working sums
            FoodRecord janRecord = new FoodRecord();
            FoodRecord febRecord = new FoodRecord();
            FoodRecord marRecord = new FoodRecord();
            FoodRecord aprRecord = new FoodRecord();
            FoodRecord mayRecord = new FoodRecord();
            FoodRecord junRecord = new FoodRecord();
            FoodRecord julRecord = new FoodRecord();
            FoodRecord augRecord = new FoodRecord();
            FoodRecord sepRecord = new FoodRecord();
            FoodRecord octRecord = new FoodRecord();
            FoodRecord novRecord = new FoodRecord();
            FoodRecord decRecord = new FoodRecord();

            FoodRecord q1Record = new FoodRecord();
            FoodRecord q2Record = new FoodRecord();
            FoodRecord q3Record = new FoodRecord();
            FoodRecord q4Record = new FoodRecord();
            FoodRecord yrRecord = new FoodRecord();

            Calendar cal = Calendar.getInstance();

             for (int i = 0; i < recIo.getRecordCount(); i++)
             {
                FoodRecord record = recIo.getRecordList().get(i);

                Date testDate = dateFormat.parse(record.getEntryDate());
                cal.setTime(testDate);

                yrRecord.addToCurrent(record);
                
                switch(cal.get(Calendar.MONTH))
                {
                    case 0:
                        janRecord.addToCurrent(record);
                        q1Record.addToCurrent(record);
                        break;
                    case 1:
                        febRecord.addToCurrent(record);
                        q1Record.addToCurrent(record);
                        break;
                    case 2:
                        marRecord.addToCurrent(record);
                        q1Record.addToCurrent(record);
                        break;
                    case 3:
                        aprRecord.addToCurrent(record);
                        q2Record.addToCurrent(record);
                        break;
                    case 4:
                        mayRecord.addToCurrent(record);
                        q2Record.addToCurrent(record);
                        break;
                    case 5:
                        junRecord.addToCurrent(record);
                        q2Record.addToCurrent(record);
                        break;
                    case 6:
                        julRecord.addToCurrent(record);
                        q3Record.addToCurrent(record);
                        break;
                    case 7:
                        augRecord.addToCurrent(record);
                        q3Record.addToCurrent(record);
                        break;
                    case 8:
                        sepRecord.addToCurrent(record);
                        q3Record.addToCurrent(record);
                        break;
                    case 9:
                        octRecord.addToCurrent(record);
                        q4Record.addToCurrent(record);
                        break;
                    case 10:
                        novRecord.addToCurrent(record);
                        q4Record.addToCurrent(record);
                        break;
                    case 11:
                        decRecord.addToCurrent(record);
                        q4Record.addToCurrent(record);
                        break;
                    default:
                        break;
                    
                }

            }
            
           createRow("January", nf.format(janRecord.getTotal()),
                   nf.format(janRecord.getPickNSave()), nf.format(janRecord.getCommunity()),
                   nf.format(janRecord.getNonTefap()), nf.format(janRecord.getTefap()),
                   nf.format(janRecord.getSecHarvest()),  nf.format(janRecord.getSecHarvestProduce()), 
                   nf.format(janRecord.getPantry()),
                   nf.format(janRecord.getOther()), nf.format(janRecord.getNonFood()), 
                   nf.format(janRecord.getMilk()), 
                   nf.format(janRecord.getOther2()),nf.format(janRecord.getProduce()),
                   janRecord.getComment(), false);

           createRow("Febuary", nf.format(febRecord.getTotal()),
                   nf.format(febRecord.getPickNSave()), nf.format(febRecord.getCommunity()),
                   nf.format(febRecord.getNonTefap()), nf.format(febRecord.getTefap()),
                   nf.format(febRecord.getSecHarvest()), nf.format(febRecord.getSecHarvestProduce()),
                   nf.format(febRecord.getPantry()),
                   nf.format(febRecord.getOther()),nf.format(febRecord.getNonFood()), 
                   nf.format(febRecord.getMilk()), 
                   nf.format(febRecord.getOther2()), nf.format(febRecord.getProduce()),
                   febRecord.getComment(), false);

           createRow("March", nf.format(marRecord.getTotal()),
                   nf.format(marRecord.getPickNSave()), nf.format(marRecord.getCommunity()),
                   nf.format(marRecord.getNonTefap()), nf.format(marRecord.getTefap()),
                   nf.format(marRecord.getSecHarvest()), nf.format(marRecord.getSecHarvestProduce()),
                   nf.format(marRecord.getPantry()),
                   nf.format(marRecord.getOther()), nf.format(marRecord.getNonFood()), 
                   nf.format(marRecord.getMilk()), 
                   nf.format(marRecord.getOther2()), nf.format(marRecord.getProduce()),
                   marRecord.getComment(), false);

           createRow("QUARTER 1", nf.format(q1Record.getTotal()),
                   nf.format(q1Record.getPickNSave()), nf.format(q1Record.getCommunity()),
                   nf.format(q1Record.getNonTefap()), nf.format(q1Record.getTefap()),
                   nf.format(q1Record.getSecHarvest()), nf.format(q1Record.getSecHarvestProduce()),
                   nf.format(q1Record.getPantry()),
                   nf.format(q1Record.getOther()), nf.format(q1Record.getNonFood()), 
                   nf.format(q1Record.getMilk()), 
                   nf.format(q1Record.getOther2()), nf.format(q1Record.getProduce()),
                   "", false);


           createRow("April", nf.format(aprRecord.getTotal()),
                   nf.format(aprRecord.getPickNSave()), nf.format(aprRecord.getCommunity()),
                   nf.format(aprRecord.getNonTefap()), nf.format(aprRecord.getTefap()),
                   nf.format(aprRecord.getSecHarvest()), nf.format(aprRecord.getSecHarvestProduce()),
                   nf.format(aprRecord.getPantry()),
                   nf.format(aprRecord.getOther()), nf.format(aprRecord.getNonFood()), 
                   nf.format(aprRecord.getMilk()), 
                   nf.format(aprRecord.getOther2()), nf.format(aprRecord.getProduce()),
                   aprRecord.getComment(), false);

           createRow("May", nf.format(mayRecord.getTotal()),
                   nf.format(mayRecord.getPickNSave()), nf.format(mayRecord.getCommunity()),
                   nf.format(mayRecord.getNonTefap()), nf.format(mayRecord.getTefap()),
                   nf.format(mayRecord.getSecHarvest()), nf.format(mayRecord.getSecHarvestProduce()),
                   nf.format(mayRecord.getPantry()),
                   nf.format(mayRecord.getOther()), nf.format(mayRecord.getNonFood()), 
                   nf.format(mayRecord.getMilk()), 
                   nf.format(mayRecord.getOther2()), nf.format(mayRecord.getProduce()),
                   mayRecord.getComment(), false);

           createRow("June", nf.format(junRecord.getTotal()),
                   nf.format(junRecord.getPickNSave()), nf.format(junRecord.getCommunity()),
                   nf.format(junRecord.getNonTefap()), nf.format(junRecord.getTefap()),
                   nf.format(junRecord.getSecHarvest()), nf.format(junRecord.getSecHarvestProduce()),
                   nf.format(junRecord.getPantry()),
                   nf.format(junRecord.getOther()), nf.format(junRecord.getNonFood()), 
                   nf.format(junRecord.getMilk()), 
                   nf.format(junRecord.getOther2()), nf.format(junRecord.getProduce()),
                   junRecord.getComment(), false);

           createRow("QUARTER 2", nf.format(q2Record.getTotal()),
                   nf.format(q2Record.getPickNSave()), nf.format(q2Record.getCommunity()),
                   nf.format(q2Record.getNonTefap()), nf.format(q2Record.getTefap()),
                   nf.format(q2Record.getSecHarvest()), nf.format(q2Record.getSecHarvestProduce()),
                   nf.format(q2Record.getPantry()),
                   nf.format(q2Record.getOther()), nf.format(q2Record.getNonFood()), 
                   nf.format(q2Record.getMilk()), 
                   nf.format(q2Record.getOther2()), nf.format(q2Record.getProduce()),
                   "", false);


           createRow("July", nf.format(julRecord.getTotal()),
                   nf.format(julRecord.getPickNSave()), nf.format(julRecord.getCommunity()),
                   nf.format(julRecord.getNonTefap()), nf.format(julRecord.getTefap()),
                   nf.format(julRecord.getSecHarvest()), nf.format(julRecord.getSecHarvestProduce()),
                   nf.format(julRecord.getPantry()),
                   nf.format(julRecord.getOther()), nf.format(julRecord.getNonFood()), 
                   nf.format(julRecord.getMilk()), 
                   nf.format(julRecord.getOther2()), nf.format(julRecord.getProduce()),
                   julRecord.getComment(), false);

           createRow("August", nf.format(augRecord.getTotal()),
                   nf.format(augRecord.getPickNSave()), nf.format(augRecord.getCommunity()),
                   nf.format(augRecord.getNonTefap()), nf.format(augRecord.getTefap()),
                   nf.format(augRecord.getSecHarvest()), nf.format(augRecord.getSecHarvestProduce()),
                   nf.format(augRecord.getPantry()),
                   nf.format(augRecord.getOther()), nf.format(augRecord.getNonFood()), 
                   nf.format(augRecord.getMilk()), 
                   nf.format(augRecord.getOther2()), nf.format(augRecord.getProduce()),
                   augRecord.getComment(), false);

           createRow("September", nf.format(sepRecord.getTotal()),
                   nf.format(sepRecord.getPickNSave()), nf.format(sepRecord.getCommunity()),
                   nf.format(sepRecord.getNonTefap()), nf.format(sepRecord.getTefap()),
                   nf.format(sepRecord.getSecHarvest()), nf.format(sepRecord.getSecHarvestProduce()),
                   nf.format(sepRecord.getPantry()),
                   nf.format(sepRecord.getOther()), nf.format(sepRecord.getNonFood()), 
                   nf.format(sepRecord.getMilk()), 
                   nf.format(sepRecord.getOther2()), nf.format(sepRecord.getProduce()),
                   sepRecord.getComment(), false);

           createRow("QUARTER 3", nf.format(q3Record.getTotal()),
                   nf.format(q3Record.getPickNSave()), nf.format(q3Record.getCommunity()),
                   nf.format(q3Record.getNonTefap()), nf.format(q3Record.getTefap()),
                   nf.format(q3Record.getSecHarvest()), nf.format(q3Record.getSecHarvestProduce()),
                   nf.format(q3Record.getPantry()),
                   nf.format(q3Record.getOther()), nf.format(q3Record.getNonFood()), 
                   nf.format(q3Record.getMilk()), 
                   nf.format(q3Record.getOther2()), nf.format(q3Record.getProduce()), 
                   "", false);
           

           createRow("October", nf.format(octRecord.getTotal()),
                   nf.format(octRecord.getPickNSave()), nf.format(octRecord.getCommunity()),
                   nf.format(octRecord.getNonTefap()), nf.format(octRecord.getTefap()),
                   nf.format(octRecord.getSecHarvest()), nf.format(octRecord.getSecHarvestProduce()),
                   nf.format(octRecord.getPantry()),
                   nf.format(octRecord.getOther()), nf.format(octRecord.getNonFood()), 
                   nf.format(octRecord.getMilk()), 
                   nf.format(octRecord.getOther2()), nf.format(octRecord.getProduce()),
                   octRecord.getComment(), false);

           createRow("November", nf.format(novRecord.getTotal()),
                   nf.format(novRecord.getPickNSave()), nf.format(novRecord.getCommunity()),
                   nf.format(novRecord.getNonTefap()), nf.format(novRecord.getTefap()),
                   nf.format(novRecord.getSecHarvest()), nf.format(novRecord.getSecHarvestProduce()),
                   nf.format(novRecord.getPantry()),
                   nf.format(novRecord.getOther()), nf.format(novRecord.getNonFood()), 
                   nf.format(novRecord.getMilk()), 
                   nf.format(novRecord.getOther2()), nf.format(novRecord.getProduce()),
                   novRecord.getComment(), false);

           createRow("December", nf.format(decRecord.getTotal()),
                   nf.format(decRecord.getPickNSave()), nf.format(decRecord.getCommunity()),
                   nf.format(decRecord.getNonTefap()), nf.format(decRecord.getTefap()),
                   nf.format(decRecord.getSecHarvest()), nf.format(decRecord.getSecHarvestProduce()),
                   nf.format(decRecord.getPantry()),
                   nf.format(decRecord.getOther()), nf.format(decRecord.getNonFood()), 
                   nf.format(decRecord.getMilk()), 
                   nf.format(decRecord.getOther2()), nf.format(decRecord.getProduce()),
                   decRecord.getComment(), false);

           createRow("QUARTER 4", nf.format(q4Record.getTotal()),
                   nf.format(q4Record.getPickNSave()), nf.format(q4Record.getCommunity()),
                   nf.format(q4Record.getNonTefap()), nf.format(q4Record.getTefap()),
                   nf.format(q4Record.getSecHarvest()), nf.format(q4Record.getSecHarvestProduce()),
                   nf.format(q4Record.getPantry()),
                   nf.format(q4Record.getOther()), nf.format(q4Record.getNonFood()), 
                   nf.format(q4Record.getMilk()), 
                   nf.format(q4Record.getOther2()), nf.format(q4Record.getProduce()),
                   "", false);



           createRow("YEAR", nf.format(yrRecord.getTotal()),
                   nf.format(yrRecord.getPickNSave()), nf.format(yrRecord.getCommunity()),
                   nf.format(yrRecord.getNonTefap()), nf.format(yrRecord.getTefap()),
                   nf.format(yrRecord.getSecHarvest()), nf.format(yrRecord.getSecHarvestProduce()),
                   nf.format(yrRecord.getPantry()),
                   nf.format(yrRecord.getOther()), nf.format(yrRecord.getNonFood()), 
                   nf.format(yrRecord.getMilk()), 
                   nf.format(yrRecord.getOther2()), nf.format(yrRecord.getProduce()),
                   "", false);


        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ReportDonatedFoodWeight.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(ReportDonatedFoodWeight.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ReportDonatedFoodWeight.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
