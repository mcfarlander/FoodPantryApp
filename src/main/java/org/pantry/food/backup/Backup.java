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
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.pantry.food.ui.common.DataFiles;

/**
 * Performs a backup of all data files to a backup directory.
 * @author mcfarland_davej
 *
 */
public class Backup
{
	private final static Logger log = Logger.getLogger(Backup.class.getName());
	
	private boolean backupCustomers = true;
	private boolean backupVisits = true;
	private boolean backupDonations = true;
	private boolean backupLegacyVolunteers = true;
	private boolean backupVolunteers = true;
	private boolean backupVolunteerEvents = true;
	
	public void setBackupCustomers(boolean bbackup) { backupCustomers = bbackup; }
	public void setBackupVisits(boolean bbackup) { backupVisits = bbackup; }
	public void setBackupDonations(boolean bbackup) { backupDonations = bbackup; }
	public void setBackupLegacyVolunteers(boolean bbackup) { backupLegacyVolunteers = bbackup; }
	public void setBackupVolunteers(boolean bbackup) { backupVolunteers = bbackup; }
	public void setBackupVolunteerEvents(boolean bbackup) { backupVolunteerEvents = bbackup; }
	
	/**
	 * Creates the backup folder if it doesn't exist.
	 * @return
	 * @throws IOException
	 */
	public String createBackupFolder() throws IOException
	{
		String startDir = new java.io.File(".").getCanonicalPath() + "/backup";
		File backupDir = new File(startDir);
		
		if ( !backupDir.exists() )
		{
			backupDir.mkdirs();
		}
		
		return startDir;
		
	}
	
	/**
	 * Creates a zip archive for the current month (jan starts with 0)
	 * @throws IOException
	 */
	public void createBackupForCurrentMonth() throws IOException
	{
		String startDirPrime = new java.io.File(".").getCanonicalPath();
		String startDir = createBackupFolder();
		Calendar cal = Calendar.getInstance();
		String archiveFile = startDir + "/"
                + "PantryBackup_"
                + Integer.toString(cal.get(Calendar.MONTH))
                + ".zip";
		
		archiveFiles(startDirPrime, archiveFile, false);
		
	}
	
	/**
	 * Archives each of all the files to a directory with the current date-time.
	 * @return
	 * @throws IOException
	 */
	public String archiveFiles() throws IOException
	{
        String startDir = new java.io.File(".").getCanonicalPath();
        Calendar cal = Calendar.getInstance();
        String archiveFile = startDir + "/"
                + "PantryArchive_"
                + Integer.toString(cal.get(Calendar.YEAR))
                + Integer.toString(cal.get(Calendar.MONTH))
                + Integer.toString(cal.get(Calendar.DAY_OF_MONTH))
                + ".zip";

        return archiveFiles(startDir, archiveFile, true);
		
	}
	
	/**
	 * Archives a specific file.
	 * @param startDir
	 * @param archiveFilePath
	 * @param deleteOld
	 * @return
	 * @throws IOException
	 */
	public String archiveFiles(String startDir, String archiveFilePath, boolean deleteOld) throws IOException
	{
        String customersFile = startDir + "/" + DataFiles.getInstance().getCsvFileCustomers();
        String visitsFile = startDir + "/" + DataFiles.getInstance().getCsvFileVisits();
        String foodRecordFile = startDir + "/" + DataFiles.getInstance().getCsvFileFoodRecord();
        String volunteerHoursFile = startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerHours();
        String volunteersFile = startDir + "/" + DataFiles.getInstance().getCsvFileVolunteers();
        String volunteerEventsFile = startDir + "/" + DataFiles.getInstance().getCsvFileVolunteerEvents();

        byte[] buf = new byte[1024];

        log.info("creating archive:" + archiveFilePath);

        FileOutputStream fos = new FileOutputStream(archiveFilePath);
        ZipOutputStream zos = new ZipOutputStream(fos);

        File file1 = new File(customersFile);
        if (backupCustomers && file1.exists())
        {
            log.info("Adding customer file to archive:" + file1.getName());
            FileInputStream in = new FileInputStream(file1);
            ZipEntry ze= new ZipEntry(file1.getName());
            zos.putNextEntry(ze);

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                zos.write(buf, 0, len);
            }
            
            zos.closeEntry();
            in.close();
            if (deleteOld)
            {
            	file1.delete(); // delete the curren file
            }
        }

        File file2 = new File(visitsFile);
        if (backupVisits && file2.exists())
        {
            log.info("Adding visits file to archive:" + file2.getName());
            FileInputStream in = new FileInputStream(file2);
            ZipEntry ze= new ZipEntry(file2.getName());
            zos.putNextEntry(ze);

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                zos.write(buf, 0, len);
            }


            zos.closeEntry();
            in.close();
            
            if (deleteOld)
            {
            	file2.delete(); // delete the current file
            }
        }

        File file3 = new File(foodRecordFile);
        if (backupDonations && file3.exists())
        {
            log.info("Adding food record file to archive:" + file3.getName());
            FileInputStream in = new FileInputStream(file3);
            ZipEntry ze= new ZipEntry(file3.getName());
            zos.putNextEntry(ze);

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                zos.write(buf, 0, len);
            }


            zos.closeEntry();
            in.close();
            
            if (deleteOld)
            {
            	file3.delete(); // delete the current file
            }
        }

        File file4 = new File(volunteerHoursFile);
        if (backupLegacyVolunteers && file4.exists())
        {
            log.info("Adding volunteer hours file to archive:" + file4.getName());
            FileInputStream in = new FileInputStream(file4);
            ZipEntry ze= new ZipEntry(file4.getName());
            zos.putNextEntry(ze);

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                zos.write(buf, 0, len);
            }


            zos.closeEntry();
            in.close();
            
            if (deleteOld)
            {
            	file4.delete(); // delete the current file
            }
        }
        
        File file5 = new File(volunteersFile);
        if (backupVolunteers && file5.exists())
        {
            log.info("Adding volunteers file to archive:" + file5.getName());
            FileInputStream in = new FileInputStream(file5);
            ZipEntry ze= new ZipEntry(file5.getName());
            zos.putNextEntry(ze);

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                zos.write(buf, 0, len);
            }


            zos.closeEntry();
            in.close();
            
            if (deleteOld)
            {
            	file5.delete(); // delete the current file
            }
        }
        
        File file6 = new File(volunteerEventsFile);
        if (backupVolunteerEvents && file6.exists())
        {
            log.info("Adding volunteer events file to archive:" + file6.getName());
            FileInputStream in = new FileInputStream(file6);
            ZipEntry ze= new ZipEntry(file6.getName());
            zos.putNextEntry(ze);

            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                zos.write(buf, 0, len);
            }

            zos.closeEntry();
            in.close();
            
            if (deleteOld)
            {
            	file6.delete(); // delete the current file
            }
        }

        try
        {
        	zos.close();
        }
        catch (java.util.zip.ZipException zi)
        {
        	// do nothing here
        	log.info("Error creating archive:" + zi.getMessage());
        }
        
        return startDir + "/" + archiveFilePath;
		
	}

	
} // end of class
