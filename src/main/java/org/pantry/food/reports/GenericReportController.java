package org.pantry.food.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ui.dialog.IModalDialogController;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.util.CsvExporter;
import org.pantry.food.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;

/**
 * Displays a report consisting of at minimum a data table view. Export to Excel
 * functionality is provided.
 * 
 * The report's details/rows are provided by the supplied
 * {@link AbstractReportStrategy}. Rows that should be emphasized can supply an
 * isSummary() value of true.
 */
public class GenericReportController implements IModalDialogController<AbstractReportStrategy, Void> {

	private static final Logger log = LogManager.getLogger(GenericReportController.class);
	@FXML
	private Label titleLabel;

	@FXML
	private Label dateLabel;

	@FXML
	private Button exportBtn;

	@FXML
	protected Node root;

	@FXML
	protected TableView<ReportRow> primaryTable;

	protected ObservableList<ReportRow> data = FXCollections.observableArrayList();

	protected AbstractReportStrategy strategy;

	@FXML
	private void initialize() {
		exportBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					File outputFile = new File(
							strategy.getTitle().replaceAll(" ", "-") + "_" + DateUtil.getDatestamp() + ".csv");
					log.info("Exporting report '{}' to CSV at path '{}'", getTitle(), outputFile.getAbsolutePath());
					new CsvExporter<ReportRow>().export(primaryTable, outputFile);
					log.info("Export complete");
					Desktop.getDesktop().open(outputFile);
				} catch (IOException e) {
					log.error("Could not export table to Excel", e);
					new Alert(AlertType.WARNING, "Could not export to Excel: " + e.getMessage()).show();
				}
			}
		});
	}

	public void setStrategy(AbstractReportStrategy strategy) {
		this.strategy = strategy;
		titleLabel.setText(strategy.getTitle());
		dateLabel.setText(strategy.getDate());

		primaryTable.setRowFactory(table -> new TableRow<ReportRow>() {

			@Override
			protected void updateItem(ReportRow item, boolean empty) {
				super.updateItem(item, empty);
				if (null != item && item.isSummary()) {
					// Highlight summary rows
					setStyle("-fx-font-weight: bold;-fx-font-size: 105%");
				} else {
					setStyle(null);
				}
			}

		});

		// All columns are rendered as string columns
		primaryTable.getColumns().addAll(strategy.getColumns());
		ObservableList<TableColumn<ReportRow, ?>> columns = primaryTable.getColumns();
		for (TableColumn<ReportRow, ?> column : columns) {
			column.setSortable(false);
		}
		primaryTable.setItems(data);
		data.addAll(strategy.getRows());
	}

	@Override
	public void setInput(AbstractReportStrategy input) {
		setStrategy(input);
	}

	@Override
	public void setModalDialogParent(ModalDialog<AbstractReportStrategy, Void> parent) {
	}

	@Override
	public Image getIcon() {
		return strategy.getIcon();
	}

	@Override
	public String getTitle() {
		return strategy.getTitle();
	}

	@Override
	public Void getResponse() {
		return null;
	}

	@Override
	public boolean isPositiveResponse() {
		return false;
	}

}
