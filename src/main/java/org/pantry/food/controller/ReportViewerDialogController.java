package org.pantry.food.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.pantry.food.ui.dialog.IModalDialogController;
import org.pantry.food.ui.dialog.ModalDialog;
import org.pantry.food.ui.dialog.ReportViewerDialogInput;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ReportViewerDialogController implements IModalDialogController<ReportViewerDialogInput, Void> {

	private final static Logger log = LogManager.getLogger(ReportViewerDialogController.class);

	@FXML
	private Button printBtn;

	@FXML
	private Label contentContainer;

	private ModalDialog<ReportViewerDialogInput, Void> parent;
	private File pdfFile;

	@FXML
	private void initialize() {
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
//				try {
//					// Printy printy
////					editorPane.print();
//				} catch (PrinterException e) {
//					e.printStackTrace();
//					new Alert(AlertType.NONE, "Could not print! Check printer is on.").show();
//				}
			}
		});
	}

	private BufferedImage createPdfImage(String path) throws IOException {
		PDDocument document = PDDocument.load(new File(path));
		PDFRenderer renderer = new PDFRenderer(document);
		List<BufferedImage> images = new ArrayList<>();
		int totalHeight = 0;
		int width = 0;
		int imageType = BufferedImage.TYPE_INT_RGB;
		// We have to loop once to identify the total height of all pages combined, then
		// again to generate the final image
		for (int pageNumber = 0; pageNumber < document.getNumberOfPages(); pageNumber++) {
			try {
				BufferedImage image = renderer.renderImage(pageNumber);
				images.add(image);
				imageType = image.getType();
				if (width == 0) {
					width = image.getWidth();
				}
				totalHeight += image.getHeight();
			} catch (IOException e) {
				log.error("Could not render PDF for page " + pageNumber + ", skipping", e);
			}
		}

		BufferedImage finalImage = new BufferedImage(width, totalHeight, imageType);
		Graphics2D graphics = finalImage.createGraphics();
		int lastHeight = 0;
		for (BufferedImage image : images) {
			graphics.drawImage(image, 0, lastHeight, null);
			lastHeight += image.getHeight();
		}
		graphics.dispose();
		images.clear();

		return finalImage;
	}

	@Override
	public void setInput(ReportViewerDialogInput input) {
		pdfFile = input.getPdfFile();
		String path = pdfFile.getAbsolutePath();

		BufferedImage pdfImage;
		try {
			pdfImage = createPdfImage(path);
			contentContainer.setGraphic(new ImageView(SwingFXUtils.toFXImage(pdfImage, null)));
		} catch (IOException e) {
			log.error("Could not read PDF at " + path, e);
			// Alert user
			new Alert(AlertType.NONE, "Could not read PDF\r\n" + e).show();
		}
	}

	@Override
	public void setModalDialogParent(ModalDialog<ReportViewerDialogInput, Void> parent) {
		this.parent = parent;
	}

	@Override
	public Image getIcon() {
		return null;
	}

	@Override
	public String getTitle() {
		return "Report Viewer";
	}

	@Override
	public Void getResponse() {
		return null;
	}

	@Override
	public boolean isPositiveResponse() {
		return false;
	}
}
