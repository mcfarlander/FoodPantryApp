package org.pantry.food.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pantry.food.Images;
import org.pantry.food.dao.CustomersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AddEditCustomerDialogInput;
import org.pantry.food.ui.dialog.ModalDialog;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CustomersController {

	private final static Logger log = Logger.getLogger(CustomersController.class.getName());

	@FXML
	private Button addCustomerBtn;

	@FXML
	private Button editCustomerBtn;

	@FXML
	private Button deleteCustomerBtn;

	@FXML
	private TableView<Customer> dataTable;

	private ObservableList<Customer> data = FXCollections.observableArrayList();

	private CustomersDao customerDao = new CustomersDao();

	private Integer nextCustomerId = 0;

	@FXML
	public void initialize() {
		addCustomerBtn.setGraphic(Images.getImageView("table_add.png"));
		addCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Open Add/Edit Customer dialog
				try {
					AddEditCustomerDialogInput input = new AddEditCustomerDialogInput();
					input.setHouseholdIds(customerDao.getHouseholdIds());
					new ModalDialog<AddEditCustomerDialogInput, Void>().show("ui/dialog/AddEditCustomerDialog.fxml",
							input);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		editCustomerBtn.setGraphic(Images.getImageView("table_edit.png"));
		editCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Invoke the Edit listener
				try {
					Customer customer = dataTable.getSelectionModel().getSelectedItem();
					AddEditCustomerDialogInput input = new AddEditCustomerDialogInput();
					input.setHouseholdIds(customerDao.getHouseholdIds());
					input.setCustomer(customer);
					new ModalDialog<AddEditCustomerDialogInput, Void>().show("ui/dialog/AddEditCustomerDialog.fxml",
							input);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		deleteCustomerBtn.setGraphic(Images.getImageView("table_delete.png"));
		deleteCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Invoke the Delete listener
			}

		});

		// Double-click is the same as Edit
		dataTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// Invoke the Edit listener
				}
			}

		});

		for (TableColumn<?, ?> column : dataTable.getColumns()) {
			// The ID of each column is the name of the corresponding property in the
			// Customer object

			// Boolean columns have to be treated differently if we want them to display a
			// checkbox
			if ("newCustomer".equals(column.getId()) || "active".equals(column.getId())) {
				column.setCellFactory(col -> new CheckBoxTableCell<>());

				TableColumn<Customer, Boolean> col = (TableColumn<Customer, Boolean>) column;
				(col).setCellValueFactory(cellValue -> {
					boolean value = false;
					if ("newCustomer".equals(column.getId())) {
						value = cellValue.getValue().isNewCustomer();
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

		loadCustomers();
	}

	private void loadCustomers() {
		try {
			List<Customer> customers = customerDao.readCsvFile();
			data.clear();

			for (Customer customer : customers) {
				boolean canAdd = FormState.getInstance().isShowInactiveCustomers() ? true : customer.isActive();
				if (canAdd) {
					data.add(customer);
				}

				if (customer.getCustomerId() >= nextCustomerId) {
					nextCustomerId = customer.getCustomerId() + 1;
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.log(Level.SEVERE, null, ex);
			Alert alert = new Alert(AlertType.WARNING, "Customer file found, but it is incorrect\n" + ex.getMessage());
			alert.show();
		} catch (FileNotFoundException ex) {
			log.log(Level.SEVERE, null, ex);
			Alert alert = new Alert(AlertType.WARNING, "Customer file found, but it is incorrect\n" + ex.getMessage());
			alert.show();
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
			Alert alert = new Alert(AlertType.WARNING, "Problem opening file\n" + ex.getMessage());
			alert.show();
		}
	}
}
