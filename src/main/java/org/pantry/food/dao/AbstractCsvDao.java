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
import org.pantry.food.ApplicationCloseListener;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.mapper.ArrayRowMapper;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class AbstractCsvDao<T> implements CsvDao<T> {
	private static final Logger log = LogManager.getLogger(AbstractCsvDao.class);
	private final AtomicInteger lastId = new AtomicInteger();
	private File csvFile;
	private FileAlterationObserver fileWatcher;
	private FileAlterationListenerAdaptor fileListener;
	private FileAlterationMonitor fileMonitor;
	private List<FileChangedListener> fileChangedListeners = new ArrayList<>();
	private ArrayRowMapper<T> rowMapper;
	private List<T> entities;

	protected AbstractCsvDao() {
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

		rowMapper = getRowMapper();
	}

	@Override
	public List<T> read() throws FileNotFoundException, IOException {
		if (null == csvFile) {
			csvFile = getCsvFile();
			if (csvFile.createNewFile()) {
				log.info("CSV file {} not found, created new empty file", csvFile.getAbsolutePath());
			}
		}

		// Watch for file changes on the data file and notify interested listeners
		// The users often change the files manually and are then surprised when the
		// program does not know about the changes.
		if (null == fileWatcher) {
			startFileWatcher(csvFile);
		}

		log.info("CSV file found: {}", csvFile.getName());
		// Read the whole file into a list
		List<T> entityList = new ArrayList<>();
		CSVReader reader = new CSVReader(new FileReader(csvFile));

		String[] nextLine;
		boolean firstLine = true;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			if (!firstLine) {
				T entity = rowMapper.map(nextLine);
				int id = getId(entity);
				if (id > lastId.get()) {
					lastId.set(id);
				}
				entityList.add(entity);
				// Give subclass a chance to do further per-entity processing (e.g household
				// IDs)
				afterLineRead(entity);
			} else {
				firstLine = !firstLine;
			}
		}

		reader.close();

		entities = entityList;

		// Give subclass a chance to do further post-processing (e.g. household IDs)
		afterRead(entities);

		return entities;
	}

	@Override
	public List<T> getAll() {
		if (null == entities) {
			try {
				entities = read();
			} catch (FileNotFoundException e) {
				// We don't have a good way to handle this scenario
				log.error("Could not find file", e);
			} catch (IOException e) {
				log.error("Could not read file", e);
			}
		}
		return entities;
	}

	/**
	 * Saves the in-memory entities to the underlying CSV file
	 */
	@Override
	public void persist() throws IOException {
		log.info("Persisting");

		if (csvFile.exists()) {
			csvFile.delete();
		}

		FileWriter fw = new FileWriter(csvFile);
		CSVWriter writer = new CSVWriter(fw);

		// add the column titles
		writer.writeNext(getCsvHeader());
		List<T> entities = getAll();
		for (T entity : entities) {
			writer.writeNext(rowMapper.toCsvRow(entity));
		}

		writer.close();
	}

	/**
	 * Adds an entity to the in-memory data store
	 * 
	 * @param entity
	 */
	public void add(T entity) {
		log.info("Adding new entity");
		getAll().add(entity);
	}

	/**
	 * Replaces a in-memory entity with the given entity using IDs to match them
	 * 
	 * @param entity
	 */
	public void edit(T entity) {
		Integer foundIndex = null;
		int entityId = getId(entity);
		log.info("Editing entity {}", entityId);
		List<T> entities = getAll();
		for (int i = 0; i < entities.size(); i++) {
			T tester = entities.get(i);

			if (getId(tester) == entityId) {
				foundIndex = i;
				break;
			}
		}

		// Replace in-memory entity with the updated one
		if (null != foundIndex) {
			entities.set(foundIndex, entity);
		}
	}

	public void deactivate(T entity) {
		int entityId = getId(entity);
		log.info("Deactivating entity {}", entityId);
		List<T> entities = getAll();
		for (T tester : entities) {
			if (getId(tester) == entityId) {
				setDeactivated(tester);
				break;
			}
		}
	}

	public void delete(T entity) {
		int entityId = getId(entity);
		log.info("Deleting entity {}", entityId);
		List<T> entities = getAll();
		for (int i = 0; i < entities.size(); i++) {
			T tester = entities.get(i);
			if (getId(tester) == entityId) {
				entities.remove(i);
				break;
			}
		}
	}

	/**
	 * @return highest existing ID plus 1
	 */
	@Override
	public int getNextId() {
		return lastId.addAndGet(1);
	}

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

	protected abstract ArrayRowMapper<T> getRowMapper();

	protected abstract int getId(T entity);

	/**
	 * Invoked before any entities have been read from the data file. The default
	 * implementation does nothing.
	 */
	protected void beforeRead() {

	}

	/**
	 * Invoked as each entity is read from the data file. The default implementation
	 * does nothing.
	 * 
	 * @param entity entity that was last read from the data file
	 */
	protected void afterLineRead(T entity) {

	}

	/**
	 * Invoked after all entities have been read from the data file. The default
	 * implementation does nothing.
	 * 
	 * @param entities all entities read
	 */
	protected void afterRead(List<T> entities) {

	}

	protected abstract File getCsvFile() throws IOException;

	protected abstract String[] getCsvHeader();

	protected void setDeactivated(T entity) {

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
					String message = "Could not read data file\r\n" + e.getMessage();
					log.error(message + " (" + file.getName() + ")", e);
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
