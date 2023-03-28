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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.model.Food;
import org.pantry.food.ui.common.DataFiles;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A class to contain all the logic to map between a file of food records and
 * the FoodRecord class.
 * 
 * @author mcfarland_davej
 *
 */
public class FoodsDao implements CsvDao<Food> {
	private final static Logger log = LogManager.getLogger(FoodsDao.class.getName());

	private final AtomicInteger lastId = new AtomicInteger();
	private String startDir = "";
	private List<Food> foodList = new ArrayList<Food>();

	private FileAlterationObserver fileWatcher;
	private FileAlterationListenerAdaptor fileListener;
	private FileAlterationMonitor fileMonitor;
	private List<FileChangedListener> fileChangedListeners = new ArrayList<>();

	public List<Food> getAll() {
		return foodList;
	}

	public int getRecordCount() {
		return foodList.size();
	}

	public void setStartDir(String sDir) {
		this.startDir = sDir;
	}

	private static final int RECORDID = 0;
	private static final int ENTRYDATE = 1;
	private static final int PICKNSAVE = 2;
	private static final int COMMUNITY = 3;
	private static final int NONTEFAP = 4;
	private static final int TEFAP = 5;
	private static final int SECONDHARVEST = 6;
	private static final int PANTRY = 7;
	private static final int OTHER = 8;
	private static final int COMMENT = 9;
	private static final int NONFOOD = 10;
	private static final int MILK = 11;
	private static final int OTHER2 = 12;
	private static final int PRODUCE = 13;
	private static final int DONATION = 14;
	private static final int DONOR = 15;
	private static final int DONORADDRESS = 16;
	private static final int DONOREMAIL = 17;
	private static final int SECONDHARVEST_PRODUCE = 18;

	private static final String Col_RecordId = "recordid";
	private static final String Col_EntryDate = "entrydate";
	private static final String Col_PickNSave = "picknsave";
	private static final String Col_Community = "community";
	private static final String Col_NonTefap = "nontefap";
	private static final String Col_Tefap = "tefap";
	private static final String Col_SecondHarvest = "secondharvest";
	private static final String Col_Pantry = "pantry";
	private static final String Col_Other = "other";
	private static final String Col_Comment = "comment";
	private static final String Col_NonFood = "nonfood";
	private static final String Col_Milk = "milk";
	private static final String Col_Other2 = "other2";
	private static final String Col_Produce = "produce";
	private static final String Col_Donation = "donation";
	private static final String Col_Donor = "donor";
	private static final String Col_DonorAddress = "donoraddress";
	private static final String Col_DonorEmail = "donoremail";
	private static final String Col_SecondHarvestProduce = "secondharvest_produce";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.dao.CsvDao#readCsvFile()
	 */
	public List<Food> read() throws FileNotFoundException, IOException {
		log.info("FoodDao.readCsvFile");

		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileFoodRecord());
		foodList = new ArrayList<Food>();

		if (file.exists()) {
			// Watch for file changes on foodrecords.csv and notify interested listeners
			// The users often change the files manually and are then surprised when the
			// program does not know about the changes.
			if (null == fileWatcher) {
				startFileWatcher(file);
			}

			log.info("Foods CSV file found");
			// read in the whole file into a list
			FileReader fr = new FileReader(file);
			CSVReader reader = new CSVReader(fr);

			String[] nextLine;
			boolean firstLine = true;
			boolean hasDonorUpdate = false;
			boolean hasSecHarvestProduceUpdate = false;

			while ((nextLine = reader.readNext()) != null) {
				if (!firstLine) {
					Food record = new Food();
					record.setFoodId(Integer.parseInt(nextLine[RECORDID]));
					record.setEntryDate(nextLine[ENTRYDATE]);
					record.setPickNSave(Double.parseDouble(nextLine[PICKNSAVE]));
					record.setCommunity(Double.parseDouble(nextLine[COMMUNITY]));
					record.setNonTefap(Double.parseDouble(nextLine[NONTEFAP]));
					record.setTefap(Double.parseDouble(nextLine[TEFAP]));
					record.setSecondHarvest(Double.parseDouble(nextLine[SECONDHARVEST]));
					record.setPantry(Double.parseDouble(nextLine[PANTRY]));
					record.setOther(Double.parseDouble(nextLine[OTHER]));
					record.setComment(nextLine[COMMENT]);
					record.setNonFood(Double.parseDouble(nextLine[NONFOOD]));
					record.setMilk(Double.parseDouble(nextLine[MILK]));
					record.setOther2(Double.parseDouble(nextLine[OTHER2]));
					record.setProduce(Double.parseDouble(nextLine[PRODUCE]));

					// if the file has the donor update, use it
					if (hasDonorUpdate) {
						record.setDonation(Boolean.parseBoolean(nextLine[DONATION]));
						record.setDonorName(nextLine[DONOR]);
						record.setDonorAddress(nextLine[DONORADDRESS]);
						record.setDonorEmail(nextLine[DONOREMAIL]);
					} else {
						// otherwise, add fake donor data
						record.setDonation(false);
						record.setDonorName("");
						record.setDonorAddress("");
						record.setDonorEmail("");
					}

					// if the file has the second harvest produce column
					if (hasSecHarvestProduceUpdate) {
						record.setSecondHarvestProduce(Double.parseDouble(nextLine[SECONDHARVEST_PRODUCE]));
					} else {
						record.setSecondHarvestProduce(0);
					}

					foodList.add(record);
				} else {
					firstLine = !firstLine;

					int columns = nextLine.length;

					if (columns >= 18)
						hasDonorUpdate = true;

					if (columns >= 19)
						hasSecHarvestProduceUpdate = true;
				}
			}

			reader.close();
		} else {
			log.info("FoodRecord cvs file NOT found");
		}

		return foodList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pantry.food.dao.CsvDao#saveCsvFile()
	 */
	public void persist() throws IOException {
		log.info("FoodsDao.saveCsvFile");
		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		log.info("CSV Path:" + startDir + "/" + DataFiles.getInstance().getCsvFileFoodRecord());
		File file = new File(startDir + "/" + DataFiles.getInstance().getCsvFileFoodRecord());

		if (file.exists()) {
			file.delete();
		}

		log.info("creating and saving to csv file");
		FileWriter fw = new FileWriter(file);
		CSVWriter writer = new CSVWriter(fw);

		// add the column titles
		String[] titles = { Col_RecordId, Col_EntryDate, Col_PickNSave, Col_Community, Col_NonTefap, Col_Tefap,
				Col_SecondHarvest, Col_Pantry, Col_Other, Col_Comment, Col_NonFood, Col_Milk, Col_Other2, Col_Produce,
				Col_Donation, Col_Donor, Col_DonorAddress, Col_DonorEmail, Col_SecondHarvestProduce };

		writer.writeNext(titles);

		for (int i = 0; i < foodList.size(); i++) {
			Food record = foodList.get(i);
			writer.writeNext(record.getCvsEntry());

		}

		writer.close();
	}

	/**
	 * Adds a food record object to the list in memory.
	 * 
	 * @param record
	 */
	public void add(Food record) {
		foodList.add(record);
	}

	/**
	 * Modifies a foodrecords object in the list (in memory).
	 * 
	 * @param record
	 */
	public void edit(Food record) {
		for (int i = 0; i < foodList.size(); i++) {
			Food testRecord = foodList.get(i);
			if (testRecord.getFoodId() == record.getFoodId()) {
				testRecord.setFoodId(record.getFoodId());
				testRecord.setEntryDate(record.getEntryDate());
				testRecord.setPickNSave(record.getPickNSave());
				testRecord.setCommunity(record.getCommunity());
				testRecord.setNonTefap(record.getNonTefap());
				testRecord.setTefap(record.getTefap());
				testRecord.setSecondHarvest(record.getSecondHarvest());
				testRecord.setPantry(record.getPantry());
				testRecord.setOther(record.getOther());
				testRecord.setComment(record.getComment());
				testRecord.setNonFood(record.getNonFood());
				testRecord.setMilk(record.getMilk());
				testRecord.setOther2(record.getOther2());
				testRecord.setProduce(record.getProduce());
				testRecord.setDonation(record.isDonation());
				testRecord.setDonorName(record.getDonorName());
				testRecord.setDonorAddress(record.getDonorAddress());
				testRecord.setDonorEmail(record.getDonorEmail());
				testRecord.setSecondHarvestProduce(record.getSecondHarvestProduce());

				break;
			}
		}
	}

	/**
	 * Deletes a food object from the list in memory
	 * 
	 * @param record
	 */
	public void deactivate(Food record) {
		for (int i = 0; i < foodList.size(); i++) {
			Food testRecord = foodList.get(i);
			if (testRecord.getFoodId() == record.getFoodId()) {
				foodList.remove(i);
				break;
			}
		}
	}

	@Override
	public int getNextId() {
		return lastId.addAndGet(1);
	}

	/**
	 * Registers an observer to be notified when the customers data file is modified
	 * 
	 * @param listener
	 */
	@Override
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
					String message = "Could not read Foods file\r\n" + e.getMessage();
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

}
