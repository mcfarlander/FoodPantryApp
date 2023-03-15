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

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * All reports use common code, such as setting the header, adding
 * CSS and closing all HTML tags. This class abstracts much of the
 * boiler plate code. In this way, only the creation of the data need
 * be implemented in the various report sub-classes.
 * 
 * @author mcfarland_davej
 */
abstract class ReportBase implements IReportBase 
{
    private String m_reportName = "";
    private String m_reportTitle = "";
    private String m_reportDescription = "";
    private StringBuffer m_buffer = new StringBuffer();

    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#setReportName(java.lang.String)
     */
    public void setReportName(String name){ m_reportName = name; }
    
    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#setReportTitle(java.lang.String)
     */
    public void setReportTitle(String title){ m_reportTitle = title; }
    
    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#setReportDescription(java.lang.String)
     */
    public void setReportDescription(String descr) { m_reportDescription = descr;}
    
    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#getBuffer()
     */
    public StringBuffer getBuffer() { return m_buffer; }

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * Create a new instance of ReportBase.
     */
    public ReportBase(){}

    /**
     * Create a new instance of ReportBase with the given parameters.
     * @param name			String the name of the report
     * @param title			String the title to display in the report
     * @param description	String the description to display in the report
     */
    public ReportBase(String name, String title, String description)
    {
        this.m_reportName = name;
        this.m_reportTitle = title;
        this.m_reportDescription = description;
    }

    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#createHeader()
     */
    public void createHeader()
    {
        m_buffer.append("<html>").append("\n");
        m_buffer.append("<head>").append("\n");

        String title = "<title>" + this.m_reportName + "</title>";
        m_buffer.append(title).append("\n");
        
        addCss();

        m_buffer.append("</head>").append("\n");

        m_buffer.append("<body>").append("\n");

        m_buffer.append("<H2>").append(this.m_reportTitle).append("</H2>").append("\n");
        m_buffer.append("<br />").append("\n");
        m_buffer.append(m_reportDescription);
        m_buffer.append("<br /> <br />").append("\n");
        
    }	// end  of createHeader
    
    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#addCss()
     */
    public void addCss()
    {
    	m_buffer.append("\n").append("  <style>").append("\n");
    	m_buffer.append("     body {background-color: #FFFFFF;}").append("\n");
    	m_buffer.append("     h1   {color: blue;}").append("\n");
    	m_buffer.append("     p    {color: red;}").append("\n");
    	m_buffer.append("     th, td {text-align: left;padding: 8px;}").append("\n");
    	m_buffer.append("     tr:nth-child(even){background-color: #DCDCDC;}").append("\n");
    	m_buffer.append("     th {background-color: #4CAF50;color: white;}").append("\n");
    	m_buffer.append("  </style>");
    	
    }	// end of addCss
    
    /**
     * Each report needs to implement this method to create the data table.
     */
    abstract void createReportTable();

    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#createFooter()
     */
    public void createFooter()
    {
        m_buffer.append("\n").append("</body>").append("\n");
        m_buffer.append("</html>").append("\n");

    }	// end of createFooter
    
    /**
     * Helper method to create a path to where the JAR is currently
     * running, the report's name and the correct extension for the file.
     * 
     * @param isHtml		boolean flag if the file path has an .html extension
     * @return				String the derived path
     * @throws IOException
     */
    private String getPath(boolean isHtml) throws IOException 
    {
    	Calendar c1 = Calendar.getInstance(); // today
        String dateCode = dateFormat.format(c1.getTime());

        String path = new java.io.File(".").getCanonicalPath() +
                "/" + this.m_reportName + "_" + dateCode;
        
        if (isHtml)
        {
        	path += ".html";
        }
        else
        {
        	path += ".pdf";
        }
        
        return path;
    	
    }

    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#saveReport()
     */
    public String saveReport() throws IOException
    {
        
    	String path = getPath(true);
        String sReturn = "";
       
        Writer out = new OutputStreamWriter(new FileOutputStream(path));

        try 
        {
            out.write(m_buffer.toString());
            sReturn = path;
        } finally {
            out.close();
        }

        return sReturn;

    } // end of saveReport
    
    /*
     * (non-Javadoc)
     * @see org.pantry.food.reports.IReportBase#saveReportPdf()
     */
    public String saveReportPdf() throws IOException, DocumentException
    {
    	String path = getPath(false);
        String text = m_buffer.toString();
        
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        // step 3
        document.open();
        // step 4
        InputStream inputHtmlAsStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputHtmlAsStream);
        // step 5
        document.close();
        
        return path;
        
    } // end of SaveReportPdf


}// end of class
