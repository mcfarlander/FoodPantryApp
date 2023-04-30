package org.pantry.food;

import javafx.scene.Node;

/**
 * Holds the main content pane context. Classes with a reference to an instance
 * of UiMainContext can change the contents of the main content pane.
 */
public class UiMainContext {

	private JfxApplication application;

	public UiMainContext(JfxApplication application) {
		this.application = application;
	}

	public void switchContext(Node newContext) {
		application.switchContext(newContext);
	}
}
