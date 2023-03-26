/*
  Copyright 2018 Dave Johnson

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CsvDao<T>
{
	/**
	 * Read the CSV file and store an array of objects related to that type of file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	List<T> read() throws FileNotFoundException, IOException;
	
	/**
	 * Save all objects contained by the DAO to a CSV file.
	 * @throws IOException
	 */
	void persist() throws IOException;
	
	

}
