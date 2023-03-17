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

import org.pantry.food.Images;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class AboutDialog implements IModalDialogController<Void, Void> {

	@FXML
	private Label imageLabel;
	@FXML
	private Label titleLabel;
	@FXML
	private Label versionLabel;
	
    @FXML
    private void initialize() {
    	org.jdesktop.application.ResourceMap resourceMap = 
    			org.jdesktop.application.Application.getInstance(org.pantry.food.FoodPantryApp.class).getContext().getResourceMap(AboutDialog.class);
    	titleLabel.setText(resourceMap.getString("Application.title"));
    	versionLabel.setText(resourceMap.getString("Application.version"));
    	imageLabel.setGraphic(Images.getImageView("about.png"));
    }
    
	@Override
	public void setInput(Void input) {}

	@Override
	public String getTitle() {
		return "About";
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
	public void setModalDialogParent(ModalDialog<Void, Void> parent) {}
    
}
