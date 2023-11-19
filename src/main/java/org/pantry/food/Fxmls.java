package org.pantry.food;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.dao.AbstractCsvDao;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Fxmls {
	private static final Map<String, Node> cache = new HashMap<>();
	private static final Logger log = LogManager.getLogger(Fxmls.class);

	public static FXMLLoader getLoader(String fxmlFileName) {
		return new FXMLLoader(JfxApplication.class.getResource(fxmlFileName));
	}

	public static <T> T load(String fxmlFileName) {
		FXMLLoader loader = getLoader(fxmlFileName);
		try {
			return (T) loader.load();
		} catch (IOException e) {
			System.out.println("Could not load FXML file " + fxmlFileName);
			e.printStackTrace();
			log.debug(e.getStackTrace());
		}
		return null;
	}

	/**
	 * Attempts to find and return cached node before loading fresh
	 * 
	 * @param <T>          expected return type - usually Node
	 * @param fxmlFileName
	 * @return
	 */
	public static <T> T loadCached(String fxmlFileName) {
		if (cache.containsKey(fxmlFileName)) {
			Node node = cache.get(fxmlFileName);
			if (null != node) {
				return (T) node;
			}
		}

		T node = load(fxmlFileName);
		cache.put(fxmlFileName, (Node) node);
		return node;
	}

}