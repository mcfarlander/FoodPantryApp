package org.pantry.food.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Resources;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.model.Visit;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditVisitDialogInput;
import org.pantry.food.util.DateUtil;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisitsController extends AbstractController<Visit, AddEditVisitDialogInput> {

	private final static Logger log = LogManager.getLogger(VisitsController.class);

	private VisitsDao visitsDao = ApplicationContext.getVisitsDao();
	private Resources resources = ApplicationContext.getResources();

	protected void init() {
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
	}

	/**
	 * Replaces the current visit list display with <code>visits</code>
	 * 
	 * @param visits visits to display
	 */
	protected void refreshTable(List<Visit> visits) {
		try {
			data.clear();

			boolean showAll = resources.getBoolean("visits.show.all");
			boolean showInactive = resources.getBoolean("visits.show.inactive");
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
					} else if (visit.isActive() && showAll) {
						// Visits outside of the current month that are active are shown if Show All
						// Year Visits is true
						canAdd = true;
					} else if (!visit.isActive() && showAll && showInactive) {
						// Inactive visits outside of the current month are shown if Show All Year
						// Visits and Show Inactive Visits are both true
						canAdd = true;
					}
				} catch (ParseException e) {
					log.error("Could not parse date " + visit.getVisitDate(), e);
					Alert alert = new Alert(AlertType.ERROR,
							"Could not parse date for visit ID " + visit.getVisitId() + ", skipping");
					alert.show();
				}

				if (canAdd) {
					data.add(visit);
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.ERROR,
					"Visit file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		}
	}

	@Override
	protected String getAddEditDialogFxmlFile() {
		return "ui/dialog/AddEditVisitDialog.fxml";
	}

	@Override
	protected AddEditVisitDialogInput getAddDialogInput() {
		AddEditVisitDialogInput input = new AddEditVisitDialogInput();
		input.setHouseholdIds(visitsDao.getHouseholdIds());
		return input;
	}

	@Override
	protected AddEditVisitDialogInput getEditDialogInput(Visit visit) {
		AddEditVisitDialogInput input = getAddDialogInput();
		input.setVisit(visit);
		return input;
	}

	@Override
	protected String getEntityTypeName() {
		return "visit";
	}

	@Override
	protected CsvDao<Visit> getDao() {
		return visitsDao;
	}

}
