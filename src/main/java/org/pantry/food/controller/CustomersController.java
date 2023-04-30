package org.pantry.food.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Resources;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.CustomersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditCustomerDialogInput;
import org.pantry.food.util.DateUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomersController extends AbstractController<Customer, AddEditCustomerDialogInput> {

	private final static Logger log = LogManager.getLogger(CustomersController.class);

	private CustomersDao customerDao = ApplicationContext.getCustomersDao();
	private Resources resources = ApplicationContext.getResources();

	public void init() {
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

		try {
			refreshTable(customerDao.read());
		} catch (ArrayIndexOutOfBoundsException | FileNotFoundException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.NONE,
					"Customer file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		} catch (IOException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.NONE, "Problem opening file\n" + ex.getMessage());
			alert.show();
		}
	}

	/**
	 * Replaces the current customer list display with <code>customers</code>. This
	 * should be called instead of <code>loadCustomers()</code>.
	 * 
	 * @param customers customers to display
	 */
	protected void refreshTable(List<Customer> customers) {
		try {
			data.clear();

			boolean showInactive = resources.getBoolean("customers.show.inactive");
			for (Customer customer : customers) {
				boolean canAdd = showInactive ? true : customer.isActive();
				if (canAdd) {
					data.add(customer);
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.WARNING,
					"Customer file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		}
	}

	@Override
	protected String getAddEditDialogFxmlFile() {
		return "ui/dialog/AddEditCustomerDialog.fxml";
	}

	@Override
	protected AddEditCustomerDialogInput getAddDialogInput() {
		AddEditCustomerDialogInput input = new AddEditCustomerDialogInput();
		input.setHouseholdIds(customerDao.getHouseholdIds());
		return input;
	}

	@Override
	protected AddEditCustomerDialogInput getEditDialogInput(Customer customer) {
		AddEditCustomerDialogInput input = getAddDialogInput();
		input.setCustomer(customer);
		return input;
	}

	@Override
	protected String getEntityTypeName() {
		return "customer";
	}

	@Override
	protected CsvDao<Customer> getDao() {
		return customerDao;
	}
}
