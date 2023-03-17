package org.pantry.food.ui.dialog;

import javafx.scene.image.Image;

/**
 * Common dialog controller interface
 *
 * @param <I> type of input expected by the dialog controller
 * @param <O> type of output/response returned by the dialog controller
 */
public interface IModalDialogController<I, O> {
	/**
	 * Provides any input to this dialog. Called after FXML initialize().
	 * 
	 * @param input any input supplied by the class opening the dialog. May be null
	 *              if <code>O</code> is <code>Void</code>.
	 */
	void setInput(I input);

	/**
	 * Provides a reference to the modal dialog manager that can be used to close
	 * the dialog. Called after FXML initialize().
	 * 
	 * @param parent modal dialog manager reference
	 */
	void setModalDialogParent(ModalDialog<I, O> parent);

	/**
	 * @return this dialog's custom titlebar icon, or null. Called after FXML
	 *         initialize().
	 */
	Image getIcon();

	/**
	 * @return this dialog's custom titlebar title, or null. Called after FXML
	 *         initialize().
	 */
	String getTitle();

	/**
	 * @return custom response/result object, or null. Called after dialog closes.
	 */
	O getResponse();

	/**
	 * @return true if user confirmed/committed the dialog's action, false if they
	 *         canceled. Called after dialog closes.
	 */
	boolean isPositiveResponse();
}
