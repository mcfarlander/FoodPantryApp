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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jdesktop.application.Action;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutDialog {
	
	private javax.swing.JButton closeButton;

	private static final long serialVersionUID = 1L;

	@FXML
	private Label titleLabel;
	@FXML
	private Label versionLabel;
	
	public AboutDialog() {
        initComponents();
    }

    @FXML
    private void initialize() {
    	org.jdesktop.application.ResourceMap resourceMap = 
    			org.jdesktop.application.Application.getInstance(org.pantry.food.FoodPantryApp.class).getContext().getResourceMap(AboutDialog.class);
    	titleLabel.setText(resourceMap.getString("Application.title"));
    	versionLabel.setText(resourceMap.getString("Application.version"));
    }
    
    private void initComponents() {
//    	FXMLLoader loader = new FXMLLoader(AboutDialog.class.getResource("/AboutDialog.fxml"));
//    	try {
//			DialogPane container = (DialogPane) loader.load();
//			Alert alert = new Alert(AlertType.NONE);
//			alert.setDialogPane(container);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    			
//    	org.jdesktop.application.ResourceMap resourceMap = 
//    			org.jdesktop.application.Application.getInstance(org.pantry.food.FoodPantryApp.class).getContext().getResourceMap(AboutDialog.class);
//
//        closeButton = new javax.swing.JButton();
//        closeButton.setText("OK");
//        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
//        javax.swing.JLabel versionLabel = new javax.swing.JLabel();
//        javax.swing.JLabel appVersionLabel = new javax.swing.JLabel();
//        javax.swing.JLabel vendorLabel = new javax.swing.JLabel();
//        javax.swing.JLabel appVendorLabel = new javax.swing.JLabel();
//        javax.swing.JLabel supportLabel = new javax.swing.JLabel();
//        javax.swing.JLabel appSupportLabel = new javax.swing.JLabel();
//        javax.swing.JLabel appDescLabel = new javax.swing.JLabel();
//        javax.swing.JLabel imageLabel = new javax.swing.JLabel();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle(resourceMap.getString("title")); 
//        setModal(true);
//        setName("aboutBox"); 
//        setResizable(false);
//
//        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.pantry.food.FoodPantryApp.class).getContext().getActionMap(AboutDialog.class, this);
//        closeButton.setAction(actionMap.get("closeAboutBox")); 
//        closeButton.setName("closeButton"); 
//        closeButton.setText("Close");
//
//        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
//        appTitleLabel.setText(resourceMap.getString("Application.title")); 
//        appTitleLabel.setName("appTitleLabel"); 
//
//        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
//        versionLabel.setText("Version:"); 
//        versionLabel.setName("versionLabel"); 
//
//        appVersionLabel.setText(resourceMap.getString("Application.version")); 
//        appVersionLabel.setName("appVersionLabel"); 
//
//        vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | java.awt.Font.BOLD));
//        vendorLabel.setText("Vendor:"); 
//        vendorLabel.setName("vendorLabel"); 
//
//        appVendorLabel.setText(resourceMap.getString("Application.vendor")); 
//        appVendorLabel.setName("appVendorLabel"); 
//
//        supportLabel.setFont(supportLabel.getFont().deriveFont(supportLabel.getFont().getStyle() | java.awt.Font.BOLD));
//        supportLabel.setText("Support:"); 
//        supportLabel.setName("supportLabel"); 
//        
//        appSupportLabel.setText(resourceMap.getString("Application.support")); 
//        appSupportLabel.setName("appSupportLabel"); 
//
//        appDescLabel.setText(resourceMap.getString("appDescLabel.text")); 
//        appDescLabel.setName("appDescLabel"); 
//
//        imageLabel.setIcon(new ImageIcon(AboutDialog.class.getResource("/org/pantry/food/resources/about.png"))); 
//        imageLabel.setName("imageLabel"); 
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        layout.setHorizontalGroup(
//        	layout.createParallelGroup(Alignment.LEADING)
//        		.addGroup(layout.createSequentialGroup()
//        			.addComponent(imageLabel)
//        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
//        				.addGroup(layout.createSequentialGroup()
//        					.addGap(18)
//        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
//        						.addGroup(layout.createSequentialGroup()
//        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
//        								.addComponent(versionLabel)
//        								.addComponent(vendorLabel)
//        								.addComponent(supportLabel))
//        							.addPreferredGap(ComponentPlacement.RELATED)
//        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
//        								.addComponent(appVersionLabel)
//        								.addComponent(appVendorLabel)
//        								.addComponent(appSupportLabel)))
//        						.addComponent(appTitleLabel)
//        						.addComponent(appDescLabel, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
//        				.addGroup(layout.createSequentialGroup()
//        					.addPreferredGap(ComponentPlacement.RELATED)
//        					.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
//        			.addContainerGap())
//        );
//        layout.setVerticalGroup(
//        	layout.createParallelGroup(Alignment.LEADING)
//        		.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        		.addGroup(layout.createSequentialGroup()
//        			.addContainerGap()
//        			.addComponent(appTitleLabel)
//        			.addPreferredGap(ComponentPlacement.RELATED)
//        			.addComponent(appDescLabel)
//        			.addPreferredGap(ComponentPlacement.RELATED)
//        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
//        				.addComponent(versionLabel)
//        				.addComponent(appVersionLabel))
//        			.addPreferredGap(ComponentPlacement.RELATED)
//        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
//        				.addComponent(vendorLabel)
//        				.addComponent(appVendorLabel))
//        			.addPreferredGap(ComponentPlacement.RELATED)
//        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
//        				.addComponent(supportLabel)
//        				.addComponent(appSupportLabel))
//        			.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
//        			.addComponent(closeButton)
//        			.addContainerGap())
//        );
//        getContentPane().setLayout(layout);
//
//        pack();
    }
    
}
