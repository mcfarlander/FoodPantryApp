package org.pantry.food.util;

import java.io.File;
import java.io.IOException;

/*
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
*/
import javafx.scene.control.TableView;

/**
 * Exports a TableView to an Excel spreadsheet. To use, add the
 * org.apache.poi:poi dependency to the project.
 *
 * @param <T> entity type of the table view
 */
public class ExcelExporter<T> {

	public void export(TableView<T> tableView, File outputFile, String sheetName) throws IOException {
		/*
		 * HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); HSSFSheet hssfSheet =
		 * hssfWorkbook.createSheet(null == sheetName ? "Sheet 1" : sheetName); HSSFRow
		 * firstRow = hssfSheet.createRow(0);
		 * 
		 * // Add column headers to first row for (int i = 0; i <
		 * tableView.getColumns().size(); i++) { HSSFCell cell = firstRow.createCell(i);
		 * cell.setCellValue(tableView.getColumns().get(i).getText()); }
		 * 
		 * for (int row = 0; row < tableView.getItems().size(); row++) { HSSFRow hssfRow
		 * = hssfSheet.createRow(row + 1);
		 * 
		 * for (int col = 0; col < tableView.getColumns().size(); col++) { Object
		 * cellValue =
		 * tableView.getColumns().get(col).getCellObservableValue(row).getValue();
		 * HSSFCell cell = hssfRow.createCell(col);
		 * 
		 * if (cellValue != null) { if (cellValue instanceof Number) {
		 * cell.setCellValue(Double.parseDouble(cellValue.toString())); } else {
		 * cell.setCellValue(cellValue.toString()); } } } }
		 * 
		 * // Save the Excel file and close the workbook try (FileOutputStream fos = new
		 * FileOutputStream(outputFile)) { hssfWorkbook.write(fos);
		 * hssfWorkbook.close(); }
		 */
	}

}
