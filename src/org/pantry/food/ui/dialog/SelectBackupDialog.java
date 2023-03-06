/*
  Copyright 2013 Dave Johnson

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

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class SelectBackupDialog extends JDialog
{

	private static final long serialVersionUID = 8848283482367742772L;
	
	private final JPanel contentPanel = new JPanel();
	
	private boolean OkCancel = false;
    public boolean getOkCancel() { return this.OkCancel; }
	
	private boolean backupCustomers = true;
	private boolean backupVisits = true;
	private boolean backupDonations = true;
	private boolean backupLegacyVolunteers = true;
	private boolean backupVolunteers = true;
	private boolean backupVolunteerEvents = true;
	
	public boolean isBackupCustomers() { return backupCustomers; }
	public boolean isBackupVisits() { return backupVisits; }
	public boolean isBackupDonations() { return backupDonations; }
	public boolean isBackupLegacyVolunteers() { return backupLegacyVolunteers; }
	public boolean isBackupVolunteers() { return backupVolunteers; }
	public boolean isBackupVolunteerEvents() { return backupVolunteerEvents; }
	
	JCheckBox chckbxCustomers;
	JCheckBox chckbxCustomerVisits;
	JCheckBox chckbxDonations;
	JCheckBox chckbxVolunteeringlegacy;
	JCheckBox chckbxRegularVolunteers;
	JCheckBox chckbxVolunteerEvents;

	/**
	 * Create the dialog.
	 */
	public SelectBackupDialog()
	{
		setTitle("Archiving Selection");
		setBounds(100, 100, 394, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblSelectAllThe = new JLabel("Select all the files for backup (archiving)");
		lblSelectAllThe.setBounds(22, 20, 262, 16);
		contentPanel.add(lblSelectAllThe);
		
		chckbxCustomers = new JCheckBox("Customers");
		chckbxCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupCustomers = chckbxCustomers.isSelected();
			}
		});
		chckbxCustomers.setSelected(true);
		chckbxCustomers.setBounds(53, 48, 128, 23);
		contentPanel.add(chckbxCustomers);
		
		chckbxCustomerVisits = new JCheckBox("Customer Visits");
		chckbxCustomerVisits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupVisits = chckbxCustomerVisits.isSelected();
			}
		});
		chckbxCustomerVisits.setSelected(true);
		chckbxCustomerVisits.setBounds(53, 72, 153, 23);
		contentPanel.add(chckbxCustomerVisits);
		
		chckbxDonations = new JCheckBox("Donations");
		chckbxDonations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupDonations = chckbxDonations.isSelected();
			}
		});
		chckbxDonations.setSelected(true);
		chckbxDonations.setBounds(53, 97, 128, 23);
		contentPanel.add(chckbxDonations);
		
		chckbxVolunteeringlegacy = new JCheckBox("Volunteering (legacy)");
		chckbxVolunteeringlegacy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupLegacyVolunteers = chckbxVolunteeringlegacy.isSelected();
			}
		});
		chckbxVolunteeringlegacy.setSelected(true);
		chckbxVolunteeringlegacy.setBounds(53, 120, 184, 23);
		contentPanel.add(chckbxVolunteeringlegacy);
		
		chckbxRegularVolunteers = new JCheckBox("Regular Volunteers");
		chckbxRegularVolunteers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupVolunteers = chckbxRegularVolunteers.isSelected();
			}
		});
		chckbxRegularVolunteers.setSelected(true);
		chckbxRegularVolunteers.setBounds(53, 146, 165, 23);
		contentPanel.add(chckbxRegularVolunteers);
		
		chckbxVolunteerEvents = new JCheckBox("Volunteer Events");
		chckbxVolunteerEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupVolunteerEvents = chckbxVolunteerEvents.isSelected();
			}
		});
		chckbxVolunteerEvents.setSelected(true);
		chckbxVolunteerEvents.setBounds(53, 176, 153, 23);
		contentPanel.add(chckbxVolunteerEvents);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						OkCancel = true;
			            dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						OkCancel = false;
			            dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}// end of constructor
	
}// end of class
