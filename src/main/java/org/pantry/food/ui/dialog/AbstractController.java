package org.pantry.food.ui.dialog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.SettingsChangedListener;
import org.pantry.food.dao.CsvDao;
import org.pantry.food.dao.FileChangedListener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class AbstractController<T, DIT> {

	private static final Logger log = LogManager.getLogger(AbstractController.class);

	@FXML
	protected Button addBtn;

	@FXML
	protected Button editBtn;

	@FXML
	protected Button deleteBtn;

	@FXML
	protected Label recordCountLabel;

	@FXML
	protected TableView<T> dataTable;

	protected ObservableList<T> data = FXCollections.observableArrayList();

	protected CsvDao<T> dao;

	@FXML
	public void initialize() {
		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Open Add/Edit dialog
				showAddDialog();
			}

		});

		editBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				T entity = dataTable.getSelectionModel().getSelectedItem();
				showEditDialog(entity);
			}

		});

		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Double-check with the user before deactivating
				T entity = dataTable.getSelectionModel().getSelectedItem();
				if (null == entity) {
					Alert alert = addAlertIcon(new Alert(AlertType.NONE,
							"Please select a " + getEntityTypeName() + " to deactivate", ButtonType.OK));
					alert.show();
				} else {
					Alert alert = addAlertIcon(new Alert(AlertType.NONE,
							"Are you sure you want to deactivate this " + getEntityTypeName() + "?", ButtonType.YES,
							ButtonType.NO));
					alert.showAndWait();
					if (ButtonType.YES == alert.getResult()) {
						dao.deactivate(entity);
						try {
							dao.persist();
						} catch (IOException e) {
							log.error("Could not save file", e);
							addAlertIcon(new Alert(AlertType.ERROR, "Problem saving file\r\n" + e.getMessage())).show();
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
					T entity = dataTable.getSelectionModel().getSelectedItem();
					showEditDialog(entity);
				}
			}

		});

		dao = getDao();
		dao.addFileChangedListener(new FileChangedListener() {

			// Invoked when the underlying data file changes
			@Override
			public void onFileChanged(String filename) {
				refreshTable(dao.getAll());
			}
		});

		ApplicationContext.addSettingsChangedListener(new SettingsChangedListener() {
			@Override
			public void onChanged() {
				refreshTable(dao.getAll());
			}
		});

		for (TableColumn<?, ?> column : dataTable.getColumns()) {
			configureColumn(column);
		}

		// Give the subclass a chance to do any custom startup stuff
		init();

		dataTable.setItems(data);

		data.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				// Update the record count label
				recordCountLabel.setText("Records: " + data.size());
			}
		});

		try {
			refreshTable(dao.read());
		} catch (ArrayIndexOutOfBoundsException | FileNotFoundException ex) {
			log.error(ex);
			Alert alert = addAlertIcon(new Alert(AlertType.ERROR, WordUtils.capitalizeFully(getEntityTypeName())
					+ " file found, but it is incorrect or missing\n" + ex.getMessage()));
			alert.show();
		} catch (IOException ex) {
			log.error(ex);
			Alert alert = addAlertIcon(new Alert(AlertType.ERROR, "Problem opening file\n" + ex.getMessage()));
			alert.show();
		}
	}

	protected abstract CsvDao<T> getDao();

	protected abstract String getAddEditDialogFxmlFile();

	protected abstract DIT getAddDialogInput();

	protected abstract DIT getEditDialogInput(T entity);

	protected abstract String getEntityTypeName();

	protected abstract void refreshTable(List<T> entities);

	protected void init() {
	}

	protected void showAddDialog() {
		try {
			DIT input = getAddDialogInput();
			new ModalDialog<DIT, T>().show(getAddEditDialogFxmlFile(), input);
		} catch (IOException e) {
			log.error("Could not add entity", e);
		}
	}

	protected void showEditDialog(T entity) {
		if (null == entity) {
			addAlertIcon(
					new Alert(AlertType.NONE, "Please select a " + getEntityTypeName() + " to edit", ButtonType.OK))
					.show();
		} else {
			try {
				DIT input = getEditDialogInput(entity);
				new ModalDialog<DIT, T>().show(getAddEditDialogFxmlFile(), input);
			} catch (IOException e) {
				log.error("Could not edit " + entity.getClass().getName(), e);
			}
		}
	}

	/**
	 * Override to customize a table column's configuration such as sort comparator,
	 * cell value factory, etc. By default the column will be configured with a
	 * {@link PropertyValueFactory} based on the column's ID.
	 * 
	 * @param column column to be configured
	 */
	protected void configureColumn(TableColumn<?, ?> column) {
		column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
	}

	protected Image getIcon() {
		return Images.getImageView("generic.png").getImage();
	}

	private Alert addAlertIcon(Alert alert) {
		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(getIcon());
		return alert;
	}

}
