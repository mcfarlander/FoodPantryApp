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
package org.pantry.food;

import java.io.IOException;

import org.pantry.food.actions.AboutMenuItem;
import org.pantry.food.actions.CustomersMenuItem;
import org.pantry.food.actions.FoodsMenuItem;
import org.pantry.food.actions.MenuActions;
import org.pantry.food.actions.VisitsMenuItem;
import org.pantry.food.actions.VolunteerEventsMenuItem;
import org.pantry.food.actions.VolunteersMenuItem;
import org.pantry.food.ui.dialog.ModalDialog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The main class of the application.
 */
public class JfxApplication extends Application {

	public static void main(String[] args) {
		Application.launch(JfxApplication.class, args);
	}

	@FXML
	private MenuBar mnuBar;

	@FXML
	private HBox buttonBar;

	@FXML
	private Button customersBtn;

	@FXML
	private Button visitsBtn;

	@FXML
	private Button foodBtn;

	@FXML
	private ScrollPane scrollPane;

	private UiMainContext context;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(JfxApplication.class.getResource("Main.fxml"));
		BorderPane container = (BorderPane) loader.load();

		Scene scene = new Scene(container);
		scene.getStylesheets().add("styles/global.css");

		stage.getIcons().add(Images.getImageView("generic.png").getImage());

		String title = Resources.getString("Application.title");
		stage.setTitle(title);

		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		ApplicationContext.applicationClosing();
	}

	@FXML
	public void initialize() {
		context = new UiMainContext(this);
		createMenuItems(context);

		customersBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MenuActions.get(CustomersMenuItem.ACTION_ID).getOnAction().handle(new ActionEvent());
			}

		});

		visitsBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MenuActions.get(VisitsMenuItem.ACTION_ID).getOnAction().handle(new ActionEvent());
			}

		});

		foodBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MenuActions.get(FoodsMenuItem.ACTION_ID).getOnAction().handle(new ActionEvent());
			}

		});

		// Set up menu item icons
		for (Menu menu : mnuBar.getMenus()) {
			if ("views".equals(menu.getId())) {
				addViewMenuItems(menu);
			} else if ("help".equals(menu.getId())) {
				addHelpMenuItems(menu);
			}
		}

		// Show the Customers view by default
		MenuActions.get(CustomersMenuItem.ACTION_ID).getOnAction().handle(new ActionEvent());
	}

	protected void createMenuItems(UiMainContext context) {
		MenuActions.add(new CustomersMenuItem(context));
		MenuActions.add(new VisitsMenuItem(context));
		MenuActions.add(new FoodsMenuItem(context));
		MenuActions.add(new VolunteersMenuItem(context));
		MenuActions.add(new VolunteerEventsMenuItem(context));

		MenuItem item = new AboutMenuItem();
		item.setOnAction(event -> {
			try {
				new ModalDialog<Void, Void>().show("ui/dialog/AboutDialog.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		MenuActions.add(item);
	}

	protected void addViewMenuItems(Menu menu) {
		menu.getItems().add(MenuActions.get(CustomersMenuItem.ACTION_ID));
		menu.getItems().add(MenuActions.get(VisitsMenuItem.ACTION_ID));
		menu.getItems().add(MenuActions.get(FoodsMenuItem.ACTION_ID));
		menu.getItems().add(new SeparatorMenuItem());
		menu.getItems().add(MenuActions.get(VolunteersMenuItem.ACTION_ID));
		menu.getItems().add(MenuActions.get(VolunteerEventsMenuItem.ACTION_ID));
	}

	protected void addHelpMenuItems(Menu menu) {
		menu.getItems().add(MenuActions.get(AboutMenuItem.ACTION_ID));
	}

	void switchContext(Node newContext) {
		scrollPane.setContent(newContext);
	}

}
