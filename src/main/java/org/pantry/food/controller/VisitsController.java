package org.pantry.food.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.FileChangedListener;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.model.Visit;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AddEditVisitDialogInput;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.util.DateUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class VisitsController {

	private final static Logger log = LogManager.getLogger(VisitsController.class);

	@FXML
	private Button addBtn;

	@FXML
	private Button editBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	private TableView<Visit> dataTable;

	private ObservableList<Visit> data = FXCollections.observableArrayList();

	private VisitsDao visitsDao = ApplicationContext.getVisitsDao();

	private Integer nextVisitId = 0;

	@FXML
	public void initialize() {
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Open Add/Edit Visit dialog
				try {
					AddEditVisitDialogInput input = new AddEditVisitDialogInput();
					input.setHouseholdIds(visitsDao.getHouseholdIds());
					new ModalDialog<AddEditVisitDialogInput, Visit>().show("ui/dialog/AddEditVisitDialog.fxml", input);
					// When the new visit is saved to the data file, the controller's file change
					// listener will be invoked which will trigger a refresh of the data table - so
					// no need to handle that here
				} catch (IOException e) {
					log.error("Could not add visit", e);
					new Alert(AlertType.ERROR, "Could not add visit\r\n" + e.getMessage()).show();
				}
			}

		});

		editBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Visit visit = dataTable.getSelectionModel().getSelectedItem();
				if (null == visit) {
					new Alert(AlertType.WARNING, "Please select a visit to edit", ButtonType.OK).show();
				} else {
					showEditDialog(visit);
				}
			}

		});

		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Double-check with the user before deactivating
				Visit visit = dataTable.getSelectionModel().getSelectedItem();
				if (null == visit) {
					new Alert(AlertType.WARNING, "Please select a visit to deactivate", ButtonType.OK).show();
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to deactivate this visit?",
							ButtonType.YES, ButtonType.NO);
					alert.showAndWait();
					if (ButtonType.YES == alert.getResult()) {
						visit.setActive(false);
						visitsDao.edit(visit);
						try {
							visitsDao.persist();
						} catch (IOException e) {
							log.error("Could not save file", e);
							new Alert(AlertType.ERROR, "Problem saving file\r\n" + e.getMessage()).show();
						}
					}
				}
			}
		});

		// Double-click is the same as Edit
		dataTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// Invoke the Edit listener
					Visit visit = dataTable.getSelectionModel().getSelectedItem();
					showEditDialog(visit);
				}
			}

		});

		for (TableColumn<?, ?> column : dataTable.getColumns()) {
			// The ID of each column is the name of the corresponding property in the
			// Visit object

			// Boolean columns have to be treated differently if we want them to display a
			// checkbox
			if ("newCustomer".equals(column.getId()) || "active".equals(column.getId())
					|| "workingIncome".equals(column.getId()) || "otherIncome".equals(column.getId())
					|| "noIncome".equals(column.getId())) {
				column.setCellFactory(col -> new CheckBoxTableCell<>());

				TableColumn<Visit, Boolean> col = (TableColumn<Visit, Boolean>) column;
				col.setCellValueFactory(cellValue -> {
					boolean value = false;
					if ("newCustomer".equals(column.getId())) {
						value = cellValue.getValue().isNewCustomer();
					} else if ("workingIncome".equals(column.getId())) {
						value = cellValue.getValue().isWorkingIncome();
					} else if ("otherIncome".equals(column.getId())) {
						value = cellValue.getValue().isOtherIncome();
					} else if ("noIncome".equals(column.getId())) {
						value = cellValue.getValue().isNoIncome();
					} else {
						value = cellValue.getValue().isActive();
					}
					return new SimpleBooleanProperty(value);
				});
			} else {
				column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
			}
		}

		dataTable.setItems(data);

		visitsDao.addFileChangedListener(new FileChangedListener() {

			// Invoked when the underlying data file changes
			@Override
			public void onFileChanged(String filename) {
				refreshTable(visitsDao.getVisitList());
			}
		});

		try {
			refreshTable(visitsDao.read());
		} catch (ArrayIndexOutOfBoundsException | FileNotFoundException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING,
					"Visits file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		} catch (IOException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING, "Problem opening file\n" + ex.getMessage());
			alert.show();
		}
	}

	/**
	 * Replaces the current visit list display with <code>visits</code>
	 * 
	 * @param visits visits to display
	 */
	private void refreshTable(List<Visit> visits) {
		try {
			data.clear();

			LocalDate now = LocalDate.now();

			for (Visit visit : visits) {
				boolean canAdd = false;
				LocalDate visitDate;
				try {
					visitDate = DateUtil.toDate(visit.getVisitDate());

					if (visit.isActive() && now.getYear() == visitDate.getYear()
							&& now.getMonthValue() == visitDate.getMonthValue()) {
						// Active visits in the current month/year are always shown
						canAdd = true;
					} else if (visit.isActive() && FormState.getInstance().isShowAllYearVisits()) {
						// Visits outside of the current month that are active are shown if Show All
						// Year Visits is true
						canAdd = true;
					} else if (!visit.isActive() && FormState.getInstance().isShowAllYearVisits()
							&& FormState.getInstance().isShowInactiveVisits()) {
						// Inactive visits outside of the current month are shown if Show All Year
						// Visits and Show Inactive Visits are both true
						canAdd = true;
					}
				} catch (ParseException e) {
					log.error("Could not parse date " + visit.getVisitDate(), e);
					Alert alert = new Alert(AlertType.WARNING,
							"Could not parse date for visit ID " + visit.getVisitId() + ", skipping");
					alert.show();
				}

				if (canAdd) {
					data.add(visit);
				}

				if (visit.getVisitId() >= nextVisitId) {
					nextVisitId = visit.getVisitId() + 1;
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING,
					"Visit file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		}
	}

	/**
	 * Displays a dialog for modifying a given visit
	 * 
	 * @param visit visit record to be changed
	 */
	private void showEditDialog(Visit visit) {
		try {
			AddEditVisitDialogInput input = new AddEditVisitDialogInput();
			input.setHouseholdIds(visitsDao.getHouseholdIds());
			input.setVisit(visit);
			new ModalDialog<AddEditVisitDialogInput, Visit>().show("ui/dialog/AddEditVisitDialog.fxml", input);
		} catch (IOException e) {
			log.error("Could not edit visit", e);
		}
	}
}
