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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.nervalreports.core.ReportFontSize;
import net.sf.nervalreports.core.ReportGenerationException;
import net.sf.nervalreports.core.ReportGenerator;
import net.sf.nervalreports.core.paper.formats.A4;
import net.sf.nervalreports.generators.PDFReportGenerator;

/**
 * All reports use common code, such as setting the header, adding CSS and
 * closing all HTML tags. This class abstracts much of the boiler plate code. In
 * this way, only the creation of the data need be implemented in the various
 * report sub-classes.
 * 
 * @author mcfarland_davej
 */
abstract class ReportBase implements IReportBase {
	protected ReportGenerator report = new PDFReportGenerator();
	private String m_reportName = "";
	private String m_reportTitle = "";
	private String m_reportDescription = "";
	private StringBuffer m_buffer = new StringBuffer();
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Create a new instance of ReportBase.
	 */
	public ReportBase() {
	}

	/**
	 * Create a new instance of ReportBase with the given parameters.
	 * 
	 * @param name        String the name of the report
	 * @param title       String the title to display in the report
	 * @param description String the description to display in the report
	 */
	public ReportBase(String name, String title, String description) {
		this.m_reportName = name;
		this.m_reportTitle = title;
		this.m_reportDescription = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.IReportBase#setReportName(java.lang.String)
	 */
	public void setReportName(String name) {
		m_reportName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.IReportBase#setReportTitle(java.lang.String)
	 */
	public void setReportTitle(String title) {
		m_reportTitle = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pantry.food.reports.IReportBase#setReportDescription(java.lang.String)
	 */
	public void setReportDescription(String descr) {
		m_reportDescription = descr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.IReportBase#getBuffer()
	 */
	public StringBuffer getBuffer() {
		return m_buffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.IReportBase#createHeader()
	 */
	protected void createHeader() throws ReportGenerationException {
		report.beginPageHeaderCenter();
		report.setFontSize(ReportFontSize.LARGE);
		report.addText(m_reportName);
		report.setFontSize(ReportFontSize.NORMAL);
		report.addText(m_reportDescription);
		report.endPageHeaderCenter();
	}

	/**
	 * Generates the report
	 */
	public void createReport() throws ReportGenerationException {
		report.setPaper(A4.getSingleton());
		report.beginDocument();
		report.beginDocumentHead();
		createHeader();
		report.endDocumentHead();
		report.addLineBreak();
		report.addLineBreak();

		report.beginDocumentBody();
		buildReport();
		createFooter();
		report.endDocumentBody();
		report.endDocument();
	}

	/**
	 * Each report needs to implement this method to create the data table.
	 * 
	 * @throws ReportGenerationException
	 */
	protected abstract void buildReport() throws ReportGenerationException;

	protected abstract void createFooter() throws ReportGenerationException;

	/**
	 * Helper method to create a path to where the JAR is currently running, the
	 * report's name and the correct extension for the file.
	 * 
	 * @return String the derived path
	 * @throws IOException
	 */
	private String getPath() throws IOException {
		Calendar c1 = Calendar.getInstance(); // today
		String dateCode = dateFormat.format(c1.getTime());

		String path = new java.io.File(".").getCanonicalPath() + "/" + this.m_reportName + "_" + dateCode + ".pdf";
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.reports.IReportBase#savePdf()
	 */
	public String savePdf() throws IOException {
		String path = getPath();

		try {
			report.saveToFile(path);
		} catch (ReportGenerationException e) {
			throw new IOException(e.getMessage(), e);
		}

		return path;

	}

}// end of class
