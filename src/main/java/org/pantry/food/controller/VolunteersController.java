package org.pantry.food.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.Volunteer;
import org.pantry.food.ui.common.StringToNumberComparator;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditVolunteerDialogInput;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;

public class VolunteersController extends AbstractController<Volunteer, AddEditVolunteerDialogInput> {

	private final static Logger log = LogManager.getLogger(VolunteersController.class);

	private VolunteersDao volunteerDao = ApplicationContext.getVolunteersDao();

	/**
	 * Replaces the current volunteer list display with <code>volunteers</code>
	 * 
	 * @param volunteers volunteers to display
	 */
	protected void refreshTable(List<Volunteer> volunteers) {
		try {
			data.clear();
			data.addAll(volunteers);
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.ERROR,
					"Volunteer file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		}
	}

	@Override
	protected String getAddEditDialogFxmlFile() {
		return "ui/dialog/AddEditVolunteerDialog.fxml";
	}

	@Override
	protected AddEditVolunteerDialogInput getAddDialogInput() {
		return new AddEditVolunteerDialogInput();
	}

	@Override
	protected AddEditVolunteerDialogInput getEditDialogInput(Volunteer volunteer) {
		AddEditVolunteerDialogInput input = getAddDialogInput();
		input.setVolunteer(volunteer);
		return input;
	}

	@Override
	protected String getEntityTypeName() {
		return "volunteer";
	}

	@Override
	protected CsvDao<Volunteer> getDao() {
		return volunteerDao;
	}

	@Override
	protected void configureColumn(TableColumn<?, ?> column) {
		super.configureColumn(column);
		if ("volunteerId".equals(column.getId())) {
			column.setComparator(StringToNumberComparator.getInstance());
		}
	}

}
