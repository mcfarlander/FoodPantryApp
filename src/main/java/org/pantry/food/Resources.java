package org.pantry.food;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.util.DateUtil;

public class Resources {
	private static final Logger log = LogManager.getLogger(Resources.class);
	private Path path;
	private Properties properties = new Properties();

	public Resources() {
		// Attempt to load the properties file
		path = null;

		try {
			String file = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			// getPath() returns the path to the jar file. We need to strip off the
			// filename.
			file = file.substring(0, file.lastIndexOf("/"));
			file += "/application.properties";

			if (System.getProperty("os.name").contains("Windows") && file.charAt(0) == '/') {
				// The leading slash returned by toURI().getPath() on Windows forks up
				// Files.copy()
				file = file.replaceFirst("/", "");
			}
			path = Path.of(file);

			log.info("Looking for application.properties at {}", path);
			// Copy application.properties to execution directory if it hasn't already been
			// copied
			if (!Files.exists(path)) {
				try {
					URL url = null;
					ClassLoader loader = getClass().getClassLoader();
					if (loader == null) {
						url = ClassLoader.getSystemResource("application.properties");
					} else {
						url = loader.getResource("application.properties");
					}
//					Files.copy(Path.of(url.toURI()), new FileOutputStream(path.toFile()));
					Files.copy(getClass().getClassLoader().getResourceAsStream("application.properties"), path);
				} catch (IOException e) {
					log.error("Could not copy application.properties to " + path.toString()
							+ ". Saving settings will be disabled.", e);
				}
			}
			properties.load(new FileInputStream(path.toString()));
		} catch (FileNotFoundException e) {
			log.error("Could not load properties from " + path, e);
		} catch (IOException e) {
			log.error("Could not read properties at " + path, e);
		} catch (NullPointerException e) {
			log.error("Could not find properties file", e);
		} catch (URISyntaxException e) {
			log.error(e);
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
		log.info("Saving properties file {}", path.getFileName());
		properties.store(new FileOutputStream(path.toString()),
				"Saved at " + DateUtil.getCurrentDateStringFourDigitYear());
		log.info("Successfully saved properties file {}", path.getFileName());
	}
}