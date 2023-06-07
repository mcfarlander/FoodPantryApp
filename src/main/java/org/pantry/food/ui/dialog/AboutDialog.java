/*
  Copyright 2011 Dave Johnson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.pantry.food.ui.dialog;

import org.pantry.food.ApplicationContext;
import org.pantry.food.Images;
import org.pantry.food.Resources;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class AboutDialog implements IModalDialogController<Void, Void> {

	@FXML
	private Label imageLabel;
	@FXML
	private Label versionLabel;
	@FXML
	private Label inMemoryOfLabel;

	@FXML
	private void initialize() {
		Resources resources = ApplicationContext.getResources();
		versionLabel.setText(resources.getString("application.version"));
		imageLabel.setGraphic(Images.getImageView("about.png"));
		inMemoryOfLabel.setText(resources.getString("in.memory.of"));
	}

	@Override
	public void setInput(Void input) {
	}

	@Override
	public String getTitle() {
		return "About " + ApplicationContext.getResources().getString("application.title");
	}

	@Override
	public Void getResponse() {
		return null;
	}

	@Override
	public boolean isPositiveResponse() {
		return true;
	}

	@Override
	public Image getIcon() {
		return Images.getImageView("help.png").getImage();
	}

	@Override
	public void setModalDialogParent(ModalDialog<Void, Void> parent) {
	}

}
