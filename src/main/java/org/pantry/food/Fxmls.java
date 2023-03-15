package org.pantry.food;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class Fxmls {
	
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
		}
		return null;
	}
	
}