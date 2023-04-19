package org.pantry.food.reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class GenericReportController {

	@FXML
	private Label titleLabel;

	@FXML
	private Label dateLabel;

	@FXML
	protected Node root;

	@FXML
	protected TableView<ReportRow> primaryTable;

	protected ObservableList<ReportRow> data = FXCollections.observableArrayList();

	protected AbstractReportStrategy strategy;

	public void setStrategy(AbstractReportStrategy strategy) {
		titleLabel.setText(strategy.getTitle());
		dateLabel.setText(strategy.getDate());

		primaryTable.setRowFactory(table -> new TableRow<ReportRow>() {

			@Override
			protected void updateItem(ReportRow item, boolean empty) {
				super.updateItem(item, empty);
				if (null != item && item.isSummary()) {
					setStyle("-fx-font-weight: bold");
				}
			}

		});
		// All columns are rendered as string columns
		primaryTable.getColumns().addAll(strategy.getColumns());
		primaryTable.setItems(data);
		data.addAll(strategy.getRows());

		root.minWidth(primaryTable.getMaxWidth() + 200);
		root.minHeight(primaryTable.getMaxHeight() + 100);
	}

}
