package org.pantry.food.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.SuppliesDao;
import org.pantry.food.model.Donor;
import org.pantry.food.model.Supplies;
import org.pantry.food.ui.common.StringToNumberComparator;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditSuppliesDialogInput;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;

public class SuppliesController extends AbstractController<Supplies, AddEditSuppliesDialogInput> {

	private SuppliesDao suppliesDao = ApplicationContext.getSuppliesDao();
	private List<Donor> donors = new ArrayList<>();
	private List<String> numericColumnIds = new ArrayList<>();

	public SuppliesController() {
		numericColumnIds.addAll(Arrays.asList("id", "pickNSave", "community", "nonTefap", "tefap", "secondHarvest",
				"secondHarvestProduce", "pantry", "nonFood", "nonFoodPlaceholder", "milk", "pantryProduce", "produce"));
	}

	/**
	 * Replaces the current list display with <code>supplies</code>
	 * 
	 * @param supplies supplies to display
	 */
	protected void refreshTable(List<Supplies> supplies) {
		donors.clear();

		Donor anon = new Donor("Anonymous");
		if (!donors.contains(anon)) {
			donors.add(anon);
		}

		List<Supplies> newSupplies = new ArrayList<>();
		Set<String> donorNames = new HashSet<>();
		for (Supplies supply : supplies) {
			newSupplies.add(supply);

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

		data.clear();
		data.addAll(newSupplies);
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

	@Override
	protected void configureColumn(TableColumn<?, ?> column) {
		// The ID of each column is the name of the corresponding property in the
		// Supplies object

		if ("donation".equals(column.getId())) {
			// Boolean columns have to be treated differently if we want them to display a
			// checkbox
			column.setCellFactory(col -> new CheckBoxTableCell<>());

			TableColumn<Supplies, Boolean> col = (TableColumn<Supplies, Boolean>) column;
			col.setCellValueFactory(cellValue -> {
				return new SimpleBooleanProperty(cellValue.getValue().isDonation());
			});
		} else if (numericColumnIds.contains(column.getId())) {
			super.configureColumn(column);
			column.setComparator(StringToNumberComparator.getInstance());
		} else {
			super.configureColumn(column);
		}
	}

}
