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

public interface CsvDao<T> {
	/**
	 * Maps entities from the underlying CSV file into objects in memory
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	List<T> read() throws FileNotFoundException, IOException;

	/**
	 * @return the in-memory entities. Does NOT read the CSV file. Prefer this over
	 *         read().
	 */
	List<T> getAll();

	/**
	 * Saves all objects contained by the DAO to a CSV file.
	 * 
	 * @throws IOException
	 */
	void persist() throws IOException;

	/**
	 * Marks the entity inactive
	 * 
	 * @param entity entity to deactivate
	 */
	void deactivate(T entity);

	/**
	 * Removes the entity from the data store
	 * 
	 * @param entity entity to delete
	 */
	void delete(T entity);

	/**
	 * 
	 * @return
	 */
	int getNextId();

	/**
	 * Registers an observer to be notified when the underlying data file is
	 * modified
	 * 
	 * @param listener observer to add
	 */
	void addFileChangedListener(FileChangedListener listener);

}
