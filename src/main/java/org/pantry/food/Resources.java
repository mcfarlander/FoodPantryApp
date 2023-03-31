package org.pantry.food;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.util.DateUtil;

public class Resources {
	private static final Logger log = LogManager.getLogger(Resources.class);
	private final File file;
	private Properties properties = new Properties();

	public Resources() {
		// Attempt to load the properties file
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()
				+ "/application.properties";
		file = new File(path);
		try {
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			log.error("Could not load properties from " + path, e);
		} catch (IOException e) {
			log.error("Could not read properties at " + path, e);
		}
	}

	public String getString(String name) {
		return properties.getProperty(name);
	}

	public Boolean getBoolean(String name) {
		return Boolean.valueOf(getString(name));
	}

	public void set(String name, String value) {
		properties.setProperty(name, value);
	}

	public void save() throws FileNotFoundException, IOException {
		log.info("Saving properties file {}", file.getName());
		properties.store(new FileOutputStream(file), "Saved at " + DateUtil.getCurrentDateStringFourDigitYear());
		log.info("Successfully saved properties file {}", file.getName());
	}
}