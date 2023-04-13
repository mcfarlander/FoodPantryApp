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

import net.sf.nervalreports.core.ReportGenerationException;

/**
 * Report interface. All reporting needs to utilize this interface.
 * 
 * @author mcfarland_davej
 */
public interface IReportBase {
	/**
	 * Set the report name.
	 * 
	 * @param name String the report name
	 */
	void setReportName(String name);

	/**
	 * Set the report's title.
	 * 
	 * @param title String the title to appear in the report
	 */
	void setReportTitle(String title);

	/**
	 * Set the report description.
	 * 
	 * @param descr String the description to appear in the report
	 */
	void setReportDescription(String descr);

	void createReport() throws ReportGenerationException;

	/**
	 * Save the report as an HTML file.
	 * 
	 * @return String the path the report was saved to
	 * @throws IOException
	 */
	String savePdf() throws IOException;

}
