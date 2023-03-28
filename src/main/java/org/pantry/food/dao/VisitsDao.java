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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationCloseListener;
import org.pantry.food.ApplicationContext;
import org.pantry.food.model.Visit;
import org.pantry.food.ui.common.DataFiles;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A class to contain all the logic to map between a file of visits and the
 * Visit class.
 *
 * @author mcfarland_davej
 */
public class VisitsDao implements CsvDao<Visit> {
	private static final Logger log = LogManager.getLogger(VisitsDao.class.getName());

	private static final int VISITID = 0;
	private static final int HOUSEHOLDID = 1;
	private static final int NEW = 2;
	private static final int NUMBER_ADULTS = 3;
	private static final int NUMBER_KIDS = 4;
	private static final int NUMBER_SENIORS = 5;
	private static final int WORKING_INCOME = 6;
	private static final int OTHER_INCOME = 7;
	private static final int NO_INCOME = 8;
	private static final int DATE = 9;
	private static final int WEEK_NUMBER = 10;
	private static final int ACTIVE = 11;

	private static final String Col_VisitId = "visitid";
	private static final String Col_HouseholdId = "householdid";
	private static final String Col_New = "new";
	private static final String Col_NumberAdults = "numberadults";
	private static final String Col_NumberKids = "numberchildren";
	private static final String Col_NumberSeniors = "numberseniors";
	private static final String Col_WorkingIncome = "workingincome";
	private static final String Col_OtherIncome = "otherincome";
	private static final String Col_NoIncome = "noincome";
	private static final String Col_Date = "date";
	private static final String Col_WeekNumber = "weeknumber";
	private static final String Col_Active = "active";

	private final AtomicInteger lastId = new AtomicInteger();

	private String startDir = "";

	private List<Visit> visitList = new ArrayList<Visit>();
	private List<String> householdIds = new ArrayList<>();

	private NumberAsStringComparator numberAsStringComparator = new NumberAsStringComparator();
	private FileAlterationObserver fileWatcher;
	private FileAlterationListenerAdaptor fileListener;
	private FileAlterationMonitor fileMonitor;
	private List<FileChangedListener> fileChangedListeners = new ArrayList<>();

	public VisitsDao() {
		ApplicationContext.addApplicationCloseListener(new ApplicationCloseListener() {

			@Override
			public void onClosing() {
				if (null != fileMonitor) {
					try {
						fileMonitor.stop();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public List<Visit> getAll() {
		return this.visitList;
	}

	/**
	 * IDs of every household in the customer list
	 * 
	 * @return
	 */
	public List<String> getHouseholdIds() {
		return householdIds;
	}

	public void setStartDir(String sDir) {
		this.startDir = sDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.dao.CsvDao#readCsvFile()
	 */
	public List<Visit> read() throws FileNotFoundException, IOException {
		log.info("VisitsDao.read");

		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVisits());

		if (file.exists()) {
			// Watch for file changes on visitors.csv and notify interested listeners
			// The users often change the files manually and are then surprised when the
			// program does not know about the changes.
			if (null == fileWatcher) {
				startFileWatcher(file);
			}

			log.info("Visits CSV file found");
			// Read the whole file into a list
			List<Visit> visits = new ArrayList<>();
			Set<String> householdIdSet = new HashSet<>();
			FileReader fr = new FileReader(file);
			CSVReader reader = new CSVReader(fr);

			String[] nextLine;
			boolean firstLine = true;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line

				if (!firstLine) {
					Visit visit = new Visit();
					visit.setVisitId(Integer.parseInt(nextLine[VISITID]));
					visit.setHouseholdId(Integer.parseInt(nextLine[HOUSEHOLDID]));
					visit.setNewCustomer(Boolean.parseBoolean(nextLine[NEW]));
					visit.setNumberAdults(Integer.parseInt(nextLine[NUMBER_ADULTS]));
					visit.setNumberKids(Integer.parseInt(nextLine[NUMBER_KIDS]));
					visit.setNumberSeniors(Integer.parseInt(nextLine[NUMBER_SENIORS]));
					visit.setWorkingIncome(Boolean.parseBoolean(nextLine[WORKING_INCOME]));
					visit.setOtherIncome(Boolean.parseBoolean(nextLine[OTHER_INCOME]));
					visit.setNoIncome(Boolean.parseBoolean(nextLine[NO_INCOME]));
					visit.setVisitDate(nextLine[DATE]);
					visit.setVisitorWeekNumber(Integer.parseInt(nextLine[WEEK_NUMBER]));
					visit.setActive(Boolean.parseBoolean(nextLine[ACTIVE]));

					visits.add(visit);
					householdIdSet.add(String.valueOf(visit.getHouseholdId()));

					if (visit.getVisitId() > lastId.get()) {
						lastId.set(visit.getVisitId());
					}
				} else {
					firstLine = !firstLine;
				}
			}

			reader.close();

			visitList = visits;
			householdIds.clear();
			householdIds.addAll(householdIdSet);
			householdIds.sort(numberAsStringComparator);
		} else {
			log.info("Visits CSV file NOT found");
		}

		return visitList;
	}// end of readCsvFile

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.dao.CsvDao#persist()
	 */
	public void persist() throws IOException {
		log.info("VisitsDao.persist");

		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileVisits());

		if (file.exists()) {
			file.delete();
		}

		FileWriter fw = new FileWriter(file);
		CSVWriter writer = new CSVWriter(fw);

		// add the column titles
		String[] titles = { Col_VisitId, Col_HouseholdId, Col_New, Col_NumberAdults, Col_NumberKids, Col_NumberSeniors,
				Col_WorkingIncome, Col_OtherIncome, Col_NoIncome, Col_Date, Col_WeekNumber, Col_Active };

		writer.writeNext(titles);

		for (int i = 0; i < visitList.size(); i++) {
			Visit vis = visitList.get(i);
			writer.writeNext(vis.getCvsEntry());

		}

		writer.close();

	}

	/*
	 * Adds a visit object to the list (in memory).
	 */
	public void add(Visit vis) {
		visitList.add(vis);
	}

	/*
	 * 
	 */
	public void edit(Visit visit) {
		Integer foundIndex = null;
		for (int i = 0; i < visitList.size(); i++) {
			Visit testVisit = visitList.get(i);

			if (testVisit.getVisitId() == visit.getVisitId()) {
				foundIndex = i;
				break;
			}
		}

		// Replace in-memory customer with the updated visit
		if (null != foundIndex) {
			visitList.set(foundIndex, visit);
		}

	} // end of editVisit

	public void deactivate(Visit vis) {
		for (int i = 0; i < visitList.size(); i++) {
			Visit testVisit = visitList.get(i);
			if (testVisit.getVisitId() == vis.getVisitId()) {
				testVisit.setActive(false);
				break;
			}
		}
	}

	public void delete(Visit vis) {
		for (int i = 0; i < visitList.size(); i++) {
			Visit testVisit = visitList.get(i);
			if (testVisit.getVisitId() == vis.getVisitId()) {
				visitList.remove(i);
				break;
			}
		}
	} // end of deleteVisit

	/**
	 * @return highest existing visit ID plus 1
	 */
	public int getNextId() {
		return lastId.addAndGet(1);
	}

	/**
	 * Registers an observer to be notified when the customers data file is modified
	 * 
	 * @param listener
	 */
	public void addFileChangedListener(FileChangedListener listener) {
		fileChangedListeners.add(listener);
	}

	/**
	 * Unregisters a previously-registered data file change listener
	 * 
	 * @param listener
	 */
	public void removeFileChangedListener(FileChangedListener listener) {
		fileChangedListeners.remove(listener);
	}

	/**
	 * Starts the CSV file watcher
	 * 
	 * @param file file to watch for changes
	 */
	private void startFileWatcher(File file) {
		fileWatcher = new FileAlterationObserver(file.getParentFile(), FileFilterUtils.nameFileFilter(file.getName()));
		fileListener = new FileAlterationListenerAdaptor() {
			public void onFileChange(File file) {
				// First we update our own local in-memory data repository
				try {
					read();
				} catch (IOException e) {
					String message = "Could not read Visits file\r\n" + e.getMessage();
					log.error(message, e);
					new Alert(AlertType.WARNING, message).show();
				}

				// Then notify listeners
				for (FileChangedListener listener : fileChangedListeners) {
					listener.onFileChanged(file.getName());
				}
			}
		};

		fileWatcher.addListener(fileListener);
		fileMonitor = new FileAlterationMonitor(500, fileWatcher);
		try {
			fileWatcher.initialize();
			fileMonitor.start();
		} catch (Exception e) {
			log.error("Could not start file monitor", e);
		}
	}

} // end of class
