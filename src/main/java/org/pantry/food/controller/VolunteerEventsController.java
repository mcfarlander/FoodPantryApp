package org.pantry.food.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteersDao;
import org.pantry.food.model.VolunteerEvent;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AbstractController;
import org.pantry.food.ui.dialog.AddEditVolunteerEventDialogInput;
import org.pantry.food.util.DateUtil;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class VolunteerEventsController extends AbstractController<VolunteerEvent, AddEditVolunteerEventDialogInput> {

	private final static Logger log = LogManager.getLogger(VolunteerEventsController.class);

	private VolunteerEventsDao volunteerEventDao = ApplicationContext.getVolunteerEventsDao();
	private VolunteersDao volunteersDao = ApplicationContext.getVolunteersDao();

	protected void init() {
		for (TableColumn<?, ?> column : dataTable.getColumns()) {
			// The ID of each column is the name of the corresponding property in the
			// Volunteer object
			column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
		}
	}

	/**
	 * Replaces the current events list display with <code>events</code>
	 * 
	 * @param events events to display
	 */
	protected void refreshTable(List<VolunteerEvent> events) {
		try {
			data.clear();

			LocalDate now = LocalDate.now();
			for (VolunteerEvent event : events) {
				LocalDate eventDate;
				try {
					eventDate = DateUtil.toDate(event.getEventDate());
					if (FormState.getInstance().isShowAll() || (now.getMonthValue() == eventDate.getMonthValue()
							&& now.getYear() == eventDate.getYear())) {
						data.add(event);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.error(ex);
			Alert alert = new Alert(AlertType.ERROR,
					"Volunteer Event file found, but it is incorrect or missing\n" + ex.getMessage());
			alert.show();
		}
	}

	@Override
	protected String getAddEditDialogFxmlFile() {
		return "ui/dialog/AddEditVolunteerEventDialog.fxml";
	}

	@Override
	protected AddEditVolunteerEventDialogInput getAddDialogInput() {
		AddEditVolunteerEventDialogInput input = new AddEditVolunteerEventDialogInput();
		input.setVolunteers(volunteersDao.getAll());
		return input;
	}

	@Override
	protected AddEditVolunteerEventDialogInput getEditDialogInput(VolunteerEvent event) {
		AddEditVolunteerEventDialogInput input = getAddDialogInput();
		input.setEvent(event);
		return input;
	}

	@Override
	protected String getEntityTypeName() {
		return "event";
	}

	@Override
	protected CsvDao<VolunteerEvent> getDao() {
		return volunteerEventDao;
	}

}
