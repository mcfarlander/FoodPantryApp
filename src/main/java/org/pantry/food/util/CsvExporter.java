package org.pantry.food.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableView;

/**
 * Exports a TableView to a CSV file
 *
 * @param <T> entity type of the table view
 */
public class CsvExporter<T> {

	public void export(TableView<T> tableView, File outputFile) throws IOException {
		StringBuilder sb = new StringBuilder();
		List<String> rowElements = new ArrayList<>();

		// Add column headers to first row
		for (int i = 0; i < tableView.getColumns().size(); i++) {
			rowElements.add(tableView.getColumns().get(i).getText());
		}
		sb.append(String.join(",", rowElements) + "\r\n");

		for (int row = 0; row < tableView.getItems().size(); row++) {
			rowElements = new ArrayList<>();
			for (int col = 0; col < tableView.getColumns().size(); col++) {
				Object cellValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();
				rowElements.add(quote(cellValue));
			}
			sb.append(String.join(",", rowElements) + "\r\n");
		}

		// Save the Excel file and close the workbook
		try (FileOutputStream fos = new FileOutputStream(outputFile)) {
			fos.write(sb.toString().getBytes());
		}
	}

	String quote(Object cellValue) {
		if (cellValue instanceof Number) {
			return quote((Number) cellValue);
		} else {
			return quote((String) cellValue);
		}
	}

	String quote(Number value) {
		if (null == value) {
			return "";
		}
		return value.toString() + ",";

	}

	String quote(String value) {
		if (null == value) {
			return "";
		}
		return "\"" + value.replaceAll("\"", "'").replaceAll("'", "''") + "\"";
	}

}
