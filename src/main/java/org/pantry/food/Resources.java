package org.pantry.food;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Resources {
	private static final Logger log = LogManager.getLogger(Resources.class);
	private Properties properties = new Properties();

	public Resources() {
		// Attempt to load the properties file
		try {
			properties.load(getClass().getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			log.error("Could not read properties file", e);
		} catch (NullPointerException e) {
			log.error("Could not find properties file", e);
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
}