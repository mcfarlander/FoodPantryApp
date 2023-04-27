package org.pantry.food.reports;

import org.pantry.food.ui.dialog.IModalDialogController;
import org.pantry.food.ui.dialog.ModalDialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;

public class GenericReportController implements IModalDialogController<AbstractReportStrategy, Void> {

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
		this.strategy = strategy;
		titleLabel.setText(strategy.getTitle());
		dateLabel.setText(strategy.getDate());

		primaryTable.setRowFactory(table -> new TableRow<ReportRow>() {

			@Override
			protected void updateItem(ReportRow item, boolean empty) {
				super.updateItem(item, empty);
				if (null != item && item.isSummary()) {
					// Highlight summary rows
					setStyle("-fx-font-weight: bold");
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
