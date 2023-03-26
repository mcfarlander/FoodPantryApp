package org.pantry.food.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.dao.CustomersDao;
import org.pantry.food.dao.FileChangedListener;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AddEditCustomerDialogInput;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.util.DateUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class CustomersController {

	private final static Logger log = LogManager.getLogger(CustomersController.class);

	@FXML
	private Button addCustomerBtn;

	@FXML
	private Button editCustomerBtn;

	@FXML
	private Button deleteCustomerBtn;

	@FXML
	private TableView<Customer> dataTable;

	private ObservableList<Customer> data = FXCollections.observableArrayList();

	private CustomersDao customerDao = ApplicationContext.getCustomersDao();

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
					new ModalDialog<AddEditCustomerDialogInput, Customer>().show("ui/dialog/AddEditCustomerDialog.fxml",
							input);
					// When the new customer is saved to the data file, the controller's file change
					// listener will be invoked which will trigger a refresh of the data table - so
					// no need to handle that here
				} catch (IOException e) {
					log.error("Could not add customer", e);
					new Alert(AlertType.ERROR, "Could not add customer\r\n" + e.getMessage()).show();
				}
			}

		});

		editCustomerBtn.setGraphic(Images.getImageView("table_edit.png"));
		editCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Customer customer = dataTable.getSelectionModel().getSelectedItem();
				editCustomer(customer);
			}

		});

		deleteCustomerBtn.setGraphic(Images.getImageView("table_delete.png"));
		deleteCustomerBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Double-check with the user before deactivating
				Customer customer = dataTable.getSelectionModel().getSelectedItem();
				if (null == customer) {
					new Alert(AlertType.WARNING, "Please select a customer to deactivate", ButtonType.OK).show();
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION,
							"Are you sure you want to deactivate this customer?", ButtonType.YES, ButtonType.NO);
					alert.showAndWait();
					if (ButtonType.YES == alert.getResult()) {
						customer.setActive(false);
						customerDao.edit(customer);
						try {
							customerDao.persist();
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
					Customer customer = dataTable.getSelectionModel().getSelectedItem();
					editCustomer(customer);
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
				col.setCellValueFactory(cellValue -> {
					boolean value = false;
					if ("newCustomer".equals(column.getId())) {
						value = cellValue.getValue().isNewCustomer();
					} else {
						value = cellValue.getValue().isActive();
					}
					return new SimpleBooleanProperty(value);
				});
			} else if ("monthRegistered".equals(column.getId())) {
				TableColumn<Customer, String> col = (TableColumn<Customer, String>) column;
				col.setCellValueFactory(cellValue -> {
					int monthId = cellValue.getValue().getMonthRegistered();
					String monthName = DateUtil.getMonthName(monthId);
					return new SimpleStringProperty(monthName);
				});
			} else {
				column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
			}
		}

		dataTable.setItems(data);

		customerDao.addFileChangedListener(new FileChangedListener() {

			// Invoked when the underlying data file changes
			@Override
			public void onFileChanged(String filename) {
				refreshCustomers(customerDao.getCustomerList());
			}
		});

		try {
			refreshCustomers(customerDao.read());
		} catch (ArrayIndexOutOfBoundsException | FileNotFoundException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING,
					"Customer file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		} catch (IOException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING, "Problem opening file\n" + ex.getMessage());
			alert.show();
		}
	}

	/**
	 * Replaces the current customer list display with <code>customers</code>. This
	 * should be called instead of <code>loadCustomers()</code>.
	 * 
	 * @param customers customers to display
	 */
	private void refreshCustomers(List<Customer> customers) {
		try {
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
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING,
					"Customer file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		}
	}

	/**
	 * Displays a dialog for modifying a given customer
	 * 
	 * @param customer customer record to be changed
	 */
	private void editCustomer(Customer customer) {
		try {
			AddEditCustomerDialogInput input = new AddEditCustomerDialogInput();
			input.setHouseholdIds(customerDao.getHouseholdIds());
			input.setCustomer(customer);
			new ModalDialog<AddEditCustomerDialogInput, Customer>().show("ui/dialog/AddEditCustomerDialog.fxml", input);
		} catch (IOException e) {
			log.error("Could not edit customer", e);
		}
	}
}
