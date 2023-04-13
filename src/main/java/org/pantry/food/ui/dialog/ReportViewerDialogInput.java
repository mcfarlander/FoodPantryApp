package org.pantry.food.ui.dialog;

import java.io.File;

import org.pantry.food.controller.ReportViewerDialogController;

/**
 * Arguments that can be provided to the {@link ReportViewerDialogController}
 */
public class ReportViewerDialogInput {

	private File pdfFile;

	public File getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(File pdfFile) {
		this.pdfFile = pdfFile;
	}
}
