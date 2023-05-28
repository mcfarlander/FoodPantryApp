package org.pantry.food.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.reports.AbstractReportStrategy;
import org.pantry.food.reports.ReportDonatedFoodWeight;
import org.pantry.food.reports.ReportMonthlySummary;
import org.pantry.food.reports.ReportPantrySummary;
import org.pantry.food.reports.ReportVolunteerEvents;
import org.pantry.food.reports.ReportVolunteerHours;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.util.DateUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class ReportsController {

	private final static Logger log = LogManager.getLogger(ReportsController.class);

	@FXML
	private ToggleGroup radios;

	@FXML
	private ComboBox<String> startMonthCbo;

	@FXML
	private ComboBox<String> endMonthCbo;

	@FXML
	private Button printBtn;

	@FXML
	private Button openBtn;

	@FXML
	private Label outputLabel;

	@FXML
	private Pane root;

	@FXML
	private void initialize() {
		openBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Open report viewer
				AbstractReportStrategy strategy = runSelectedReport();

				try {
					new ModalDialog<AbstractReportStrategy, Void>().show("reports/ReportContainer.fxml", strategy);
				} catch (IOException e) {
					log.error("Could not show report dialog", e);
				}
			}
		});

		startMonthCbo.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec");
		endMonthCbo.getItems().addAll(startMonthCbo.getItems());
		int currentMonthIndex = DateUtil.getCurrentMonth() - 1;
		startMonthCbo.getSelectionModel().select(currentMonthIndex);
		endMonthCbo.getSelectionModel().select(currentMonthIndex);
	}

	private AbstractReportStrategy runSelectedReport() {
		RadioButton radio = ((RadioButton) radios.getSelectedToggle());
		log.info("Running report {}", radio.getId());
		return getSelectedReport(radio);
	}

	private AbstractReportStrategy getSelectedReport(RadioButton radio) {
		AbstractReportStrategy reportStrategy = null;
		switch (radio.getId()) {
		case "monthly.summary":
			ReportMonthlySummary monthlySummary = new ReportMonthlySummary();
			monthlySummary.setMonthSelected(startMonthCbo.getSelectionModel().getSelectedIndex());
			reportStrategy = monthlySummary;
			break;
		case "food.weight":
			reportStrategy = new ReportDonatedFoodWeight();
			break;
		case "volunteer.hours":
			reportStrategy = new ReportVolunteerHours();
			break;
		case "volunteer.events":
			reportStrategy = new ReportVolunteerEvents();
			break;
		case "pantry.report":
			ReportPantrySummary pantrySummaryReport = new ReportPantrySummary();
			pantrySummaryReport.setDateRange(startMonthCbo.getSelectionModel().getSelectedIndex(),
					endMonthCbo.getSelectionModel().getSelectedIndex());
			reportStrategy = pantrySummaryReport;
			break;
		}
		return reportStrategy;
	}

}
