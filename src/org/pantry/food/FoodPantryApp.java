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
import java.util.EventObject;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.pantry.food.backup.Backup;

/**
 * The main class of the application.
 */
public class FoodPantryApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
    	
    	addExitListener(new ExitListener() {
			@Override
			public boolean canExit(EventObject arg0)
			{
				return true;
			}
			@Override
			public void willExit(EventObject arg0)
			{
				Backup backup = new Backup();
				try
				{
					backup.createBackupForCurrentMonth();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
			}
        });
    	
        show(new MainDesktopView(this));

    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of FoodPantryApp
     */
    public static FoodPantryApp getApplication() {
        return Application.getInstance(FoodPantryApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(FoodPantryApp.class, args);
    }
}
