package org.pantry.food.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.model.Donor;
import org.pantry.food.model.Food;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditFoodDialogInput;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class FoodsController extends AbstractController<Food, AddEditFoodDialogInput> {

	private final static Logger log = LogManager.getLogger(FoodsController.class);

	private FoodsDao foodsDao = ApplicationContext.getFoodsDao();

	private List<Donor> donors = new ArrayList<>();

	protected void init() {
		for (TableColumn<?, ?> column : dataTable.getColumns()) {
			// The ID of each column is the name of the corresponding property in the
			// Food object

			// Boolean columns have to be treated differently if we want them to display a
			// checkbox
			if ("donation".equals(column.getId())) {
				column.setCellFactory(col -> new CheckBoxTableCell<>());

				TableColumn<Food, Boolean> col = (TableColumn<Food, Boolean>) column;
				col.setCellValueFactory(cellValue -> {
					return new SimpleBooleanProperty(cellValue.getValue().isDonation());
				});
			} else {
				column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
			}
		}
	}

	/**
	 * Replaces the current food list display with <code>foods</code>
	 * 
	 * @param foods foods to display
	 */
	protected void refreshTable(List<Food> foods) {
		data.clear();
		donors.clear();

		Donor anon = new Donor("Anonymous");
		if (!donors.contains(anon)) {
			donors.add(anon);
		}

		Set<String> donorNames = new HashSet<>();
		for (Food food : foods) {
			data.add(food);

			if (food.isDonation()) {
				String donorName = food.getDonorName();

				// Compile a unique list of donors
				if (null != donorName && !"".equals(donorName.trim())) {
					donorName = donorName.trim();

					if (!donorNames.contains(donorName)) {
						donors.add(new Donor(donorName, food.getDonorAddress(), food.getDonorEmail()));
						donorNames.add(donorName);
					}
				}
			}
		}
	}

	@Override
	protected String getAddEditDialogFxmlFile() {
		return "ui/dialog/AddEditFoodDialog.fxml";
	}

	@Override
	protected AddEditFoodDialogInput getAddDialogInput() {
		AddEditFoodDialogInput input = new AddEditFoodDialogInput();
		input.setDonors(donors);
		return input;
	}

	@Override
	protected AddEditFoodDialogInput getEditDialogInput(Food food) {
		AddEditFoodDialogInput input = getAddDialogInput();
		input.setFood(food);
		return input;
	}

	@Override
	protected String getEntityTypeName() {
		return "food";
	}

	@Override
	protected CsvDao<Food> getDao() {
		return foodsDao;
	}

}
