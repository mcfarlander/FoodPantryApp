package org.pantry.food.ui.dialog;

import java.io.IOException;

import org.pantry.food.Fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Modal dialog convenience class. Loads and shows a dialog from an FXML file.
 * The FXML file must include a controller attribute. The controller must
 * implement the {@link IModalDialogController} interface. The controller will
 * be provided with input sent by the caller, and will return output sent by the
 * dialog controller.
 *
 * @param <I> dialog input type
 * @param <O> dialog response type (return type of dialog controller's
 *            <code>getResponse()</code> function)
 */
public class ModalDialog<I, O> {

	protected Stage stage;

	public O show(String fxmlFile) throws IOException {
		return show(fxmlFile, null);
	}

	public O show(String fxmlFile, I input) throws IOException {
		FXMLLoader loader = Fxmls.getLoader(fxmlFile);
		Parent parent = loader.<Parent>load();
		parent.getStyleClass().add("dialog");

		IModalDialogController<I, O> controller = loader.<IModalDialogController<I, O>>getController();
		controller.setModalDialogParent(this);
		controller.setInput(input);

		Scene scene = new Scene(parent);
		scene.getStylesheets().add("styles/global.css");

		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);

		Image icon = controller.getIcon();
		if (null != icon) {
			stage.getIcons().add(icon);
		}
		stage.setTitle(controller.getTitle());
		stage.showAndWait();

		return controller.getResponse();
	}

	public void close() {
		stage.close();
	}
}
