package org.pantry.food.reports;

import java.util.List;

import org.pantry.food.Images;
import org.pantry.food.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

/**
 * Common template functionality for generic data reports. Subclasses must
 * override getTitle() to supply a report title. getColumns() must be overridden
 * to supply the column header names and column order.
 * 
 * To add a row to the data table, update the protected `data` member.
 */
public abstract class AbstractReportStrategy {

	@FXML
	protected TableView<ReportRow> primaryTable;

	protected ObservableList<ReportRow> data = FXCollections.observableArrayList();

	public ObservableList<ReportRow> getData() {
		return data;
	}

	/**
	 * Used to populate the subheading label. Defaults to current date.
	 * 
	 * @return current date as a string with four-digit year
	 */
	public String getDate() {
		return DateUtil.getCurrentDateStringFourDigitYear();
	}

	/**
	 * Provides an opportunity for subclasses to customize additional tables or
	 * report elements
	 */
	public void init() {

	}

	public Image getIcon() {
		return Images.getIcon("table.png");
	}

	/**
	 * Used to populate the heading label
	 * 
	 * @return
	 */
	public abstract String getTitle();

	/**
	 * Defines the table columns of the primary table
	 * 
	 * @return
	 */
	public abstract ObservableList<TableColumn<ReportRow, String>> getColumns();

	/**
	 * @return all data table rows to be added to the primary table
	 */
	public abstract List<ReportRow> getRows();

	protected ObservableList<TableColumn<ReportRow, String>> toTableColumns(String... titles) {
		ObservableList<TableColumn<ReportRow, String>> cols = FXCollections.observableArrayList();
		int index = 0;
		for (String title : titles) {
			TableColumn<ReportRow, String> col = new TableColumn<>(title);
			col.setCellValueFactory(new PropertyValueFactory(String.valueOf(index)));
			cols.add(col);
			index++;
		}
		return cols;
	}

}
