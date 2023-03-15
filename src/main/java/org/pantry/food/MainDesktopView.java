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

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.pantry.food.reports.ReportDonatedFoodWeight;
import org.pantry.food.reports.ReportMonthlySummary;
import org.pantry.food.reports.ReportVolunteerEvents;
import org.pantry.food.reports.ReportVolunteerHours;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AboutDialog;
import org.pantry.food.ui.dialog.ReportOptionsDialog;
import org.pantry.food.ui.frame.FrameCustomers;
import org.pantry.food.ui.frame.FrameFoodRecords;
import org.pantry.food.ui.frame.FrameReportViewer;
import org.pantry.food.ui.frame.FrameSettings;
import org.pantry.food.ui.frame.FrameVisits;
import org.pantry.food.ui.frame.FrameVolunteerEvents;
import org.pantry.food.ui.frame.FrameVolunteerHours;
import org.pantry.food.ui.frame.FrameVolunteers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;

import org.pantry.food.reports.ReportPantrySummary;


/**
 * The application's main frame.
 */
public class MainDesktopView extends FrameView {
	
    private JDesktopPane jDesktopPane1;
    private JMenu fileMenu;
    private JMenuItem mnuCustomers;
    private JMenuItem mnuVisits;
    private JMenuItem mnuSettings;
    private JMenuItem mnuReports;
    private JDesktopPane mainPanel;
    private JMenuBar menuBar;
    private JMenuItem mnuItemFoodRecords;
    private JMenuItem mnuVolunteerHours;
    private JMenuItem mnuVolunteers;
    private JMenuItem mnuVolunteerEvents;
    private JProgressBar progressBar;
    private JLabel statusAnimationLabel;
    private JLabel statusMessageLabel;
    private JPanel statusPanel;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem mnuAbout;
    private JSeparator statusPanelSeparator;
    
    private JToolBar toolBar;

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private Alert aboutBox;

    public MainDesktopView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
        	FXMLLoader loader = new FXMLLoader(AboutDialog.class.getResource("AboutDialog.fxml"));
	        DialogPane container;
			try {
				container = (DialogPane) loader.load();
				aboutBox = new Alert(AlertType.NONE);
		        aboutBox.setDialogPane(container);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        aboutBox.showAndWait();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

    	jDesktopPane1 = new JDesktopPane();
        mainPanel = new JDesktopPane();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        mnuCustomers = new JMenuItem();
        mnuVisits = new JMenuItem();
        mnuItemFoodRecords = new JMenuItem();
        mnuVolunteerHours = new JMenuItem();
        mnuVolunteers = new JMenuItem();
        mnuVolunteerEvents = new JMenuItem();
        mnuReports = new JMenuItem();
        mnuSettings = new JMenuItem();
        exitMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        mnuAbout = new JMenuItem();
        statusPanel = new JPanel();
        statusPanelSeparator = new JSeparator();
        statusMessageLabel = new JLabel();
        statusAnimationLabel = new JLabel();
        progressBar = new JProgressBar();
        
        toolBar = new JToolBar();
        toolBar.setFloatable( false);

        mainPanel.setName("mainPanel");  

        jDesktopPane1.setName("jDesktopPane1"); 
        
        org.jdesktop.application.ResourceMap resourceMap = 
        		org.jdesktop.application.Application.getInstance(org.pantry.food.FoodPantryApp.class).getContext().getResourceMap(MainDesktopView.class);
        

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar");  

        fileMenu.setText("File");  
        fileMenu.setName("fileMenu");  

        mnuCustomers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuCustomers.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/table_key.png")));  
        mnuCustomers.setText("Customers");  
        mnuCustomers.setActionCommand(resourceMap.getString("jMenuCustomers.actionCommand"));  
        mnuCustomers.setName("jMenuCustomers");  
        mnuCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCustomersActionPerformed(evt);
            }
        });
        fileMenu.add(mnuCustomers);

        mnuVisits.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mnuVisits.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/cart.png")));  
        mnuVisits.setText("Visits");  
        mnuVisits.setActionCommand(resourceMap.getString("jMenuVisits.actionCommand"));  
        mnuVisits.setName("jMenuVisits");  
        mnuVisits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVisitsActionPerformed(evt);
            }
        });
        fileMenu.add(mnuVisits);

        mnuItemFoodRecords.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mnuItemFoodRecords.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/tag_blue.png")));  
        mnuItemFoodRecords.setText("Food Records");  
        mnuItemFoodRecords.setName("mnuItemFoodRecords");  
        mnuItemFoodRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemFoodRecordsActionPerformed(evt);
            }
        });
        fileMenu.add(mnuItemFoodRecords);

        mnuVolunteerHours.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        mnuVolunteerHours.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/telephone.png")));  
        mnuVolunteerHours.setText("Volunteer Hours");  
        mnuVolunteerHours.setName("mnuVolunteerHours");  
        mnuVolunteerHours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuVolunteerHoursActionPerformed(evt);
            }
        });
        fileMenu.add(mnuVolunteerHours);
        mnuVolunteerHours.setVisible(false);

        mnuVolunteers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        mnuVolunteers.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/user.png")));  
        mnuVolunteers.setText("Regular Volunteers");  
        mnuVolunteers.setName("mnuVolunteers");  
        mnuVolunteers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuVolunteersActionPerformed(evt);
            }
        });
        fileMenu.add(mnuVolunteers);
        
        mnuVolunteerEvents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        mnuVolunteerEvents.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/user_go.png")));  
        mnuVolunteerEvents.setText("Regular Volunteers Events");  
        mnuVolunteerEvents.setName("mnuVolunteerEvents");  
        mnuVolunteerEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuVolunteerEventsActionPerformed(evt);
            }
        });
        fileMenu.add(mnuVolunteerEvents);

        mnuReports.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnuReports.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/report.png")));  
        mnuReports.setText("Reports");  
        mnuReports.setName("mnuReports");  
        mnuReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        fileMenu.add(mnuReports);

        mnuSettings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSettings.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/wrench_orange.png")));  
        mnuSettings.setText("Settings");  
        mnuSettings.setName("mnuSettings");  
        mnuSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSettingsActionPerformed(evt);
            }
        });
        fileMenu.add(mnuSettings);
 
        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/cancel.png")));  
        exitMenuItem.setName("exitMenuItem"); 
        exitMenuItem.setText("Quit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	getApplication().exit();
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");  
        helpMenu.setName("helpMenu");  

        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAboutBox();
            }
        });;  
        mnuAbout.setIcon(new ImageIcon(MainDesktopView.class.getResource("/org/pantry/food/resources/images/help.png")));  
        mnuAbout.setName("aboutMenuItem");  
        mnuAbout.setText("About");
        helpMenu.add(mnuAbout);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel");  

        statusPanelSeparator.setName("statusPanelSeparator");  

        statusMessageLabel.setName("statusMessageLabel");  

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel");  

        progressBar.setName("progressBar");  

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );
        
        // add toolbar buttons
        JButton buttonCustomers = new JButton("Customers");
        buttonCustomers.setIcon(resourceMap.getIcon("jMenuCustomers.icon"));
        buttonCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	jMenuItemCustomersActionPerformed(null);
            }
        });
        toolBar.add(buttonCustomers);
        
        JButton buttonVisits = new JButton("Visits");
        buttonVisits.setIcon(resourceMap.getIcon("jMenuVisits.icon"));
        buttonVisits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	jMenuItemVisitsActionPerformed(null) ;
            }
        });
        toolBar.add(buttonVisits);
        
        JButton buttonFoodRecords = new JButton("Food");
        buttonFoodRecords.setIcon(resourceMap.getIcon("mnuItemFoodRecords.icon"));
        buttonFoodRecords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mnuItemFoodRecordsActionPerformed(null);
            }
        });
        toolBar.add(buttonFoodRecords);
        

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(toolBar);
    }

    private void jMenuItemCustomersActionPerformed(java.awt.event.ActionEvent evt) {

        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen())
        {

            try 
            {
                FrameCustomers customers = new FrameCustomers();
                this.jDesktopPane1.add(customers);
                customers.setMaximum(true);
                customers.setVisible(true);
                FormState.getInstance().setFormOpen(true, customers);
                
            } 
            catch (PropertyVetoException ex) 
            {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
        else 
        {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }

    }

    private void jMenuItemVisitsActionPerformed(java.awt.event.ActionEvent evt) {

        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen()){
            try 
            {
                FrameVisits visits = new FrameVisits(this.jDesktopPane1);
                this.jDesktopPane1.add(visits);
                visits.setMaximum(true);
                visits.setVisible(true);
                FormState.getInstance().setFormOpen(true, visits);
            } 
            catch (PropertyVetoException ex) 
            {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else 
        {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }

    }

    private void jMenuItemSettingsActionPerformed(java.awt.event.ActionEvent evt) {

        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen())
        {
            try 
            {
                FrameSettings settings = new FrameSettings();
                this.jDesktopPane1.add(settings);
                settings.setMaximum(true);
                settings.setVisible(true);
                FormState.getInstance().setFormOpen(true, settings);
            } 
            catch (PropertyVetoException ex) 
            {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else 
        {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }
    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {

        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen())
        {

            ReportOptionsDialog dial = new ReportOptionsDialog(null, true);
            dial.setLocationRelativeTo(this.getFrame());
            dial.setVisible(true);

            if (dial.getOkCancel())
            {

                FrameReportViewer reportViewer = new FrameReportViewer();

                switch(dial.getReportSelected()){

                    case 0:
                        ReportMonthlySummary reportMonthSummary = new ReportMonthlySummary();
                        reportMonthSummary.setMonthSelected(dial.getMonthSelected());
                        reportMonthSummary.createReportTable();
                        reportViewer.setDisplayFromIReport(reportMonthSummary);

                        break;


                    case 1:
                        ReportDonatedFoodWeight reportDonatedFood = new ReportDonatedFoodWeight();
                        reportDonatedFood.createReportTable();
                        reportViewer.setDisplayFromIReport(reportDonatedFood);

                        break;

                    case 2:
                        ReportVolunteerHours reportVolunteerHours = new ReportVolunteerHours();
                        reportVolunteerHours.createReportTable();
                        reportViewer.setDisplayFromIReport(reportVolunteerHours);
                        
                    case 3:
                    	ReportVolunteerEvents reportVolunteerEvents = new ReportVolunteerEvents();
                        reportVolunteerEvents.createReportTable();
                        reportViewer.setDisplayFromIReport(reportVolunteerEvents);
                    	break;
                    	
                    case 4:
                    	ReportPantrySummary report= new ReportPantrySummary();
                    	report.setMonthSelected(dial.getMonthSelected());
                        report.createReportTable();
                        reportViewer.setDisplayFromIReport(report);

                    default:
                        break;
                }

                try 
                {
                    this.jDesktopPane1.add(reportViewer);
                    reportViewer.setMaximum(true);
                    reportViewer.setVisible(true);

                    FormState.getInstance().setFormOpen(true, reportViewer);
                    
                } 
                catch (PropertyVetoException ex) 
                {
                    Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (dial.getReportSelected() == 0){


                }

            }

        } 
        else 
        {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }


    }

    private void mnuItemFoodRecordsActionPerformed(java.awt.event.ActionEvent evt) {

        closeCurrentForm();
        
        if (!FormState.getInstance().isFormOpen()){
            try {
                FrameFoodRecords records = new FrameFoodRecords();
                this.jDesktopPane1.add(records);
                records.setMaximum(true);
                records.setVisible(true);
                FormState.getInstance().setFormOpen(true, records);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }
        
    }

    private void mnuVolunteersActionPerformed(java.awt.event.ActionEvent evt) {
    	
        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen()){

            try {
                FrameVolunteers form = new FrameVolunteers();
                this.jDesktopPane1.add(form);
                form.setMaximum(true);
                form.setVisible(true);
                FormState.getInstance().setFormOpen(true, form);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }
        
    }
    
    private void mnuVolunteerEventsActionPerformed(java.awt.event.ActionEvent evt) {
    	
        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen()){

            try {
                FrameVolunteerEvents form = new FrameVolunteerEvents();
                this.jDesktopPane1.add(form);
                form.setMaximum(true);
                form.setVisible(true);
                FormState.getInstance().setFormOpen(true, form);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }
    }

    private void mnuVolunteerHoursActionPerformed(java.awt.event.ActionEvent evt) {

        closeCurrentForm();

        if (!FormState.getInstance().isFormOpen()){

            try {
                FrameVolunteerHours form = new FrameVolunteerHours();
                this.jDesktopPane1.add(form);
                form.setMaximum(true);
                form.setVisible(true);
                FormState.getInstance().setFormOpen(true, form);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainDesktopView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Please close open window first.");
        }
    }


    private void closeCurrentForm(){

        if (FormState.getInstance().isFormOpen()){

            if (FormState.getInstance().getCurrentForm() != null){
                FormState.getInstance().getCurrentForm().dispose();
                FormState.getInstance().setFormOpen(false);
            }
            
        }

    }
    

}// end of class