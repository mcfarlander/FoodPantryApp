package org.pantry.food.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.reports.AbstractReportStrategy;
import org.pantry.food.reports.IReportBase;
import org.pantry.food.reports.ReportDonatedFoodWeight;
import org.pantry.food.reports.ReportMonthlySummary;
import org.pantry.food.reports.ReportPantrySummary;
import org.pantry.food.reports.ReportVolunteerEvents;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.util.DateUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private ComboBox<String> monthCbo;

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

		monthCbo.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec");
		monthCbo.getSelectionModel().select(DateUtil.getCurrentMonth() - 1);
	}

	private AbstractReportStrategy runSelectedReport() {
		RadioButton radio = ((RadioButton) radios.getSelectedToggle());
		log.info("Running report {}", radio.getId());
		return getSelectedReport(radio);
	}

	private String savePdf(IReportBase report) {
		log.info("Saving report");
		try {
			String path = report.savePdf();
			String message = "Saved to " + path;
			outputLabel.setText(message);
			log.info(message);
			return path;
		} catch (IOException e) {
			log.error("Could not save report as PDF", e);
			new Alert(AlertType.NONE, "Could not save:\r\n" + e).show();
		}
		return null;
	}

	private AbstractReportStrategy getSelectedReport(RadioButton radio) {
		AbstractReportStrategy reportStrategy = null;
		switch (radio.getId()) {
		case "monthly.summary":
			ReportMonthlySummary monthlySummary = new ReportMonthlySummary();
			monthlySummary.setMonthSelected(monthCbo.getSelectionModel().getSelectedIndex());
			reportStrategy = monthlySummary;
			break;
		case "food.weight":
			reportStrategy = new ReportDonatedFoodWeight();
			break;
		case "volunteer.hours":
			// Volunteer Hours report has been replaced with Volunteer Events
//			reportStrategy = new ReportVolunteerHours();
			break;
		case "volunteer.events":
			reportStrategy = new ReportVolunteerEvents();
			break;
		case "pantry.report":
			ReportPantrySummary pantrySummaryReport = new ReportPantrySummary();
			pantrySummaryReport.setMonthSelected(monthCbo.getSelectionModel().getSelectedIndex());
			reportStrategy = pantrySummaryReport;
			break;
		}
		return reportStrategy;
	}

}
