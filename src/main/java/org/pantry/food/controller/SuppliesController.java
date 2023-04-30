package org.pantry.food.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.SuppliesDao;
import org.pantry.food.model.Donor;
import org.pantry.food.model.Supplies;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditSuppliesDialogInput;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class SuppliesController extends AbstractController<Supplies, AddEditSuppliesDialogInput> {

	private SuppliesDao suppliesDao = ApplicationContext.getSuppliesDao();
	private List<Donor> donors = new ArrayList<>();

	protected void init() {
		for (TableColumn<?, ?> column : dataTable.getColumns()) {
			// The ID of each column is the name of the corresponding property in the
			// Supplies object

			// Boolean columns have to be treated differently if we want them to display a
			// checkbox
			if ("donation".equals(column.getId())) {
				column.setCellFactory(col -> new CheckBoxTableCell<>());

				TableColumn<Supplies, Boolean> col = (TableColumn<Supplies, Boolean>) column;
				col.setCellValueFactory(cellValue -> {
					return new SimpleBooleanProperty(cellValue.getValue().isDonation());
				});
			} else {
				column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
			}
		}
	}

	/**
	 * Replaces the current list display with <code>supplies</code>
	 * 
	 * @param supplies supplies to display
	 */
	protected void refreshTable(List<Supplies> supplies) {
		data.clear();
		donors.clear();

		Donor anon = new Donor("Anonymous");
		if (!donors.contains(anon)) {
			donors.add(anon);
		}

		Set<String> donorNames = new HashSet<>();
		for (Supplies supply : supplies) {
			data.add(supply);

			if (supply.isDonation()) {
				String donorName = supply.getDonorName();

				// Compile a unique list of donors
				if (null != donorName && !"".equals(donorName.trim())) {
					donorName = donorName.trim();

					if (!donorNames.contains(donorName)) {
						donors.add(new Donor(donorName, supply.getDonorAddress(), supply.getDonorEmail()));
						donorNames.add(donorName);
					}
				}
			}
		}
	}

	@Override
	protected String getAddEditDialogFxmlFile() {
		return "ui/dialog/AddEditSuppliesDialog.fxml";
	}

	@Override
	protected AddEditSuppliesDialogInput getAddDialogInput() {
		AddEditSuppliesDialogInput input = new AddEditSuppliesDialogInput();
		input.setDonors(donors);
		return input;
	}

	@Override
	protected AddEditSuppliesDialogInput getEditDialogInput(Supplies supplies) {
		AddEditSuppliesDialogInput input = getAddDialogInput();
		input.setSupplies(supplies);
		return input;
	}

	@Override
	protected String getEntityTypeName() {
		return "food";
	}

	@Override
	protected CsvDao<Supplies> getDao() {
		return suppliesDao;
	}

}
