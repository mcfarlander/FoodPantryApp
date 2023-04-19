package org.pantry.food.controller;

import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pantry.food.Fxmls;
import org.pantry.food.reports.AbstractReportStrategy;
import org.pantry.food.reports.GenericReportController;
import org.pantry.food.reports.IReportBase;
import org.pantry.food.reports.ReportDonatedFoodWeight;
import org.pantry.food.util.DateUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

//	@FXML
//	private Button pdfBtn;

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

				FXMLLoader loader = Fxmls.getLoader("reports/ReportContainer.fxml");
				try {
					Node rootNode = loader.load();
					GenericReportController controller = (GenericReportController) loader.getController();
					controller.setStrategy(strategy);
					root.getChildren().clear();
					root.getChildren().add(rootNode);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String path = ""; // runSelectedReport();
					// Printy printy
					PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
					DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
					PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
					PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
					PrintService service = ServiceUI
							.printDialog(
									GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
											.getDefaultConfiguration(),
									200, 200, printService, defaultService, flavor, pras);
					if (service != null) {
						DocPrintJob job = service.createPrintJob();
						FileInputStream fis = new FileInputStream(path);
						DocAttributeSet das = new HashDocAttributeSet();
						Doc document = new SimpleDoc(fis, flavor, das);
						job.print(document, pras);
						job.addPrintJobListener(new PrintJobListener() {

							@Override
							public void printJobRequiresAttention(PrintJobEvent pje) {
							}

							@Override
							public void printJobNoMoreEvents(PrintJobEvent pje) {
								delete(path);
							}

							@Override
							public void printJobFailed(PrintJobEvent pje) {
								delete(path);
							}

							@Override
							public void printJobCompleted(PrintJobEvent pje) {
								delete(path);
							}

							@Override
							public void printJobCanceled(PrintJobEvent pje) {
								delete(path);
							}

							@Override
							public void printDataTransferCompleted(PrintJobEvent pje) {
								delete(path);
							}
						});
					}
				} catch (PrintException | FileNotFoundException e) {
					e.printStackTrace();
					new Alert(AlertType.NONE, "Could not print! Check printer is on.").show();
				}
			}
		});

//		pdfBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				String path = null;
//					path = runSelectedReport();
//				outputLabel.setText("Saved to " + path);
//			}
//		});

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
			break;
		case "food.weight":
			reportStrategy = new ReportDonatedFoodWeight();
			break;
//		case "volunteer.hours":
//			reportStrategy = new ReportVolunteerHours();
//			break;
//		case "volunteer.events":
//			reportStrategy = new ReportVolunteerEvents();
//			break;
//		case "pantry.report":
//			ReportPantrySummary specificReport = new ReportPantrySummary();
//			specificReport.setMonthSelected(monthCbo.getSelectionModel().getSelectedIndex());
//			reportStrategy = specificReport;
//			break;
		}
		return reportStrategy;
	}

	private void delete(String path) {
		try {
			Files.deleteIfExists(Paths.get(path));
		} catch (IOException e) {
			log.error("Could not delete file " + path, e);
		}
	}
}
