/*
  Copyright 2013 Dave Johnson

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
package org.pantry.food.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ui.common.DataFiles;

/**
 * Performs a backup of all data files to a backup directory.
 * 
 * @author mcfarland_davej
 *
 */
public class Backup {
	private final static Logger log = LogManager.getLogger(Backup.class.getName());

	private List<BackupKey> backupKeys;

	public Backup(List<BackupKey> backupKeys) {
		this.backupKeys = backupKeys;
	}

	/**
	 * Creates the backup folder if it doesn't exist.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String createBackupFolder() throws IOException {
		String startDir = new java.io.File(".").getCanonicalPath() + "/backup";
		File backupDir = new File(startDir);

		if (!backupDir.exists()) {
			backupDir.mkdirs();
		}

		return startDir;
	}

	/**
	 * Creates a zip archive for the current month (jan starts with 0)
	 * 
	 * @throws IOException
	 */
	public void createBackupForCurrentMonth() throws IOException {
		log.info("Starting month backup");
		String startDirPrime = new java.io.File(".").getCanonicalPath();
		String startDir = createBackupFolder();
		Calendar cal = Calendar.getInstance();
		String archiveFile = startDir + "/" + "PantryBackup_" + Integer.toString(cal.get(Calendar.MONTH)) + ".zip";

		archiveFiles(startDirPrime, archiveFile, false);
		log.info("Month backup completed successfully");
	}

	/**
	 * Archives each of all the files to a zip with the current date-time and then
	 * deletes the files. Used for an end-of-year process where every customer
	 * receives a new ID for the following year.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String archiveFiles() throws IOException {
		log.info("Starting year-end backup and purge");
		String startDir = new File(".").getCanonicalPath();
		Calendar cal = Calendar.getInstance();
		String archiveFile = startDir + "/" + "PantryArchive_" + String.valueOf(cal.get(Calendar.YEAR))
				+ String.valueOf(cal.get(Calendar.MONTH)) + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + ".zip";

		String archivePath = archiveFiles(startDir, archiveFile, true);
		log.info("Year-end backup and purge completed successfully. File is {}", archivePath);
		return archivePath;
	}

	/**
	 * Archives a specific file
	 * 
	 * @param startDir        directory in which the zip file should be created
	 * @param archiveFilePath path to the new zip file
	 * @param deleteOld       delete CSV files after archiving
	 * @return
	 * @throws IOException
	 */
	private String archiveFiles(String startDir, String archiveFilePath, boolean deleteOld) throws IOException {
		log.info("creating archive {}", archiveFilePath);

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archiveFilePath));

		for (BackupKey key : backupKeys) {
			if (BackupKey.CUSTOMERS.equals(key)) {
				archiveFile(new File(startDir + "/" + DataFiles.getInstance().getCsvFileCustomers()), zos, deleteOld);
			} else if (BackupKey.DONATIONS.equals(key)) {
				archiveFile(new File(startDir + "/" + DataFiles.getInstance().getCsvFileFoodRecord()), zos, deleteOld);
			} else if (BackupKey.LEGACY_VOLUNTEERS.equals(key)) {
				archiveFile(new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerHours()), zos,
						deleteOld);
			} else if (BackupKey.VISITS.equals(key)) {
				archiveFile(new File(startDir + "/" + DataFiles.getInstance().getCsvFileVisits()), zos, deleteOld);
			} else if (BackupKey.VOLUNTEER_EVENTS.equals(key)) {
				archiveFile(new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerEvents()), zos,
						deleteOld);
			} else if (BackupKey.VOLUNTEERS.equals(key)) {
				archiveFile(new File(startDir + "/" + DataFiles.getInstance().getCsvFileVolunteers()), zos, deleteOld);
			}
		}

		try {
			zos.close();
		} catch (java.util.zip.ZipException zi) {
			// do nothing here
			log.info("Error creating archive", zi);
		}

		return startDir + "/" + archiveFilePath;
	}

	/**
	 * Archives a file
	 * 
	 * @param file                file to archive
	 * @param archiveOutputStream output stream of existing zip file
	 * @param deleteOld           delete files after archiving them
	 * @throws IOException
	 */
	private void archiveFile(File file, ZipOutputStream archiveOutputStream, boolean deleteOld) throws IOException {
		if (file.exists()) {
			log.info("Adding {} file to archive", file.getName());
			FileInputStream in = new FileInputStream(file);
			ZipEntry ze = new ZipEntry(file.getName());
			archiveOutputStream.putNextEntry(ze);

			// Transfer bytes from the file to the ZIP file
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				archiveOutputStream.write(buf, 0, len);
			}

			archiveOutputStream.closeEntry();
			in.close();
			if (deleteOld) {
				file.delete();
			}
		}
	}

} // end of class
