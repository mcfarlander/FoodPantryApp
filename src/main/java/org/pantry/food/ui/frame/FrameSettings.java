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
package org.pantry.food.ui.frame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.pantry.food.backup.Backup;
import org.pantry.food.dao.CustomersDao;
import org.pantry.food.model.Customer;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.SelectBackupDialog;

/**
 * The Class FrameSettings.
 *
 * @author mcfarland_davej
 */
public class FrameSettings extends javax.swing.JInternalFrame 
{
	
	/** The Constant log. */
	private final static Logger log = Logger.getLogger(FrameSettings.class.getName());
	
    /** The btn add month 1. */
    private JButton btnAddMonth1;
    
    /** The btn archive. */
    private JButton btnArchive;
    
    /** The chk show inactive. */
    private JCheckBox chkShowInactive;
    
    /** The chk show inactive visits. */
    private JCheckBox chkShowInactiveVisits;
    
    /** The chk show year visits. */
    private JCheckBox chkShowYearVisits;
    
    /** The chk show all. */
    private JCheckBox chkShowAll;
    
    /** The j panel 1. */
    private JPanel jPanel1;
    
    /** The j panel 2. */
    private JPanel jPanel2;
    
    /** The j panel 3. */
    private JPanel jPanel3;
    
    /** The j panel 4. */
    private JPanel jPanel4;

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 513215325014000028L;

	/**
	 *  Creates new form FrameSettings.
	 */
    public FrameSettings() {
        initComponents();
        initForm();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
    	
    	setClosable(true);
        setTitle("Application Settings"); 
        setFrameIcon(new ImageIcon(FrameSettings.class.getResource("/org/pantry/food/resources/images/wrench_orange.png"))); 
        setName("Form");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        
        getContentPane().setLayout(null);

        jPanel1 = new JPanel();
        jPanel1.setBounds(18, 6, 530, 72);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Options"));
        jPanel1.setName("jPanel1");
        jPanel1.setLayout(null);
        
        chkShowInactive = new JCheckBox();
        chkShowInactive.setBounds(6, 22, 476, 59);
        chkShowInactive.setText("Check to show inactive customers in list"); 
        chkShowInactive.setBorder(null);
        chkShowInactive.setName("chkShowInactive"); 
        chkShowInactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowInactiveActionPerformed(evt);
            }
        });
        
        jPanel1.add(chkShowInactive);
        
        jPanel2 = new JPanel();
        jPanel2.setBounds(18, 75, 530, 87);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Visits Options"));
        jPanel2.setName("jPanel2");
        jPanel2.setLayout(null);
        
        chkShowInactiveVisits = new JCheckBox();
        chkShowInactiveVisits.setBounds(6, 22, 476, 27);
        chkShowInactiveVisits.setText("Check to show inactive visits on list"); 
        chkShowInactiveVisits.setName("chkShowInactiveVisits"); 
        chkShowInactiveVisits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowInactiveVisitsActionPerformed(evt);
            }
        });
        
        chkShowYearVisits = new JCheckBox();
        chkShowYearVisits.setBounds(6, 54, 476, 27);
        chkShowYearVisits.setText("Check to show all visits (yearly). Uncheck to show only monthly visits."); 
        chkShowYearVisits.setName("chkShowYearVisits"); 
        chkShowYearVisits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowYearVisitsActionPerformed(evt);
            }
        });
        
        jPanel2.add(chkShowInactiveVisits);
        jPanel2.add(chkShowYearVisits);
        
        jPanel4 = new JPanel();
        jPanel4.setBounds(18, 166, 530, 80);
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("General View Options"));
        jPanel4.setName("jPanel4");
        jPanel4.setLayout(null);
        
        chkShowAll = new JCheckBox();
        chkShowAll.setBounds(6, 22, 476, 59);
        chkShowAll.setText("Check to show all volunteer events"); 
        chkShowAll.setBorder(null);
        chkShowAll.setName("chkShowAll"); 
        chkShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowAllActionPerformed(evt);
            }
        });
        
        jPanel4.add(chkShowAll);
        
        jPanel3 = new JPanel();
        jPanel3.setBounds(18, 258, 530, 120);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("File Maintenance"));
        jPanel3.setName("jPanel3");
        jPanel3.setLayout(null);
        
        btnArchive = new JButton();
        btnArchive.setBounds(21, 21, 476, 28);
        btnArchive.setIcon(new ImageIcon(FrameReportMonthlySummary.class.getResource("/org/pantry/food/resources/images/table_save.png"))); 
        btnArchive.setText("Archive Current Data Files"); 
        btnArchive.setName("btnArchive"); 
        btnArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchiveActionPerformed(evt);
            }
        });
        
        btnAddMonth1 = new JButton();
        btnAddMonth1.setBounds(21, 53, 476, 28);
        btnAddMonth1.setIcon(new ImageIcon(FrameReportMonthlySummary.class.getResource("/org/pantry/food/resources/images/table_add.png"))); 
        btnAddMonth1.setText("Add 1 To All Months (Use Once!)"); 
        btnAddMonth1.setEnabled(false);
        btnAddMonth1.setName("btnAddMonth1"); 
        btnAddMonth1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMonth1ActionPerformed(evt);
            }
        });
        
        jPanel3.add(btnArchive);
        jPanel3.add(btnAddMonth1);

        
        getContentPane().add(jPanel1);
        getContentPane().add(jPanel2);
        getContentPane().add(jPanel4);
        getContentPane().add(jPanel3);

        pack();
    }

    /**
     * Form internal frame closed.
     *
     * @param evt the evt
     */
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {

        FormState.getInstance().setFormOpen(false);
        
    }

    /**
     * Chk show inactive action performed.
     *
     * @param evt the evt
     */
    private void chkShowInactiveActionPerformed(java.awt.event.ActionEvent evt) {

        FormState.getInstance().setShowInactiveCustomers(this.chkShowInactive.isSelected());

    }

    /**
     * Chk show inactive visits action performed.
     *
     * @param evt the evt
     */
    private void chkShowInactiveVisitsActionPerformed(java.awt.event.ActionEvent evt) {

        FormState.getInstance().setShowInactiveVisits(this.chkShowInactiveVisits.isSelected());

    }

    /**
     * Chk show year visits action performed.
     *
     * @param evt the evt
     */
    private void chkShowYearVisitsActionPerformed(java.awt.event.ActionEvent evt) {

        FormState.getInstance().setShowAllYearVisits(this.chkShowYearVisits.isSelected());

    }
    
    /**
     * Chk show all action performed.
     *
     * @param evt the evt
     */
    private void chkShowAllActionPerformed(java.awt.event.ActionEvent evt) {

        FormState.getInstance().setShowAll(this.chkShowAll.isSelected());

    }

    /**
     * Btn archive action performed.
     *
     * @param evt the evt
     */
    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {

        archiveFiles();

    }

    /**
     * Btn add month 1 action performed.
     *
     * @param evt the evt
     */
    private void btnAddMonth1ActionPerformed(java.awt.event.ActionEvent evt) {
        
        addOneToCustomerMonth();
        
    }


    /**
     * Inits the form.
     */
    private void initForm(){

        this.chkShowInactive.setSelected(FormState.getInstance().isShowInactiveCustomers());
        this.chkShowInactiveVisits.setSelected(FormState.getInstance().isShowInactiveVisits());
        this.chkShowYearVisits.setSelected(FormState.getInstance().isShowAllYearVisits());

    }

    /**
     * Archive files.
     */
    private void archiveFiles()
    {

        int result = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to archive the files? You will be prompted for files to archive.",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        SelectBackupDialog dialog = new SelectBackupDialog();
        dialog.setModal(true);
        dialog.setVisible(true);
        
        if (dialog.getOkCancel() == false)
        {
        	return;
        }
        
        Backup archive = new Backup();
        archive.setBackupCustomers(dialog.isBackupCustomers());
        archive.setBackupVisits(dialog.isBackupVisits());
        archive.setBackupDonations(dialog.isBackupDonations());
        archive.setBackupLegacyVolunteers(dialog.isBackupLegacyVolunteers());
        archive.setBackupVolunteers(dialog.isBackupVolunteers());
        archive.setBackupVolunteerEvents(dialog.isBackupVolunteerEvents());

        try 
        {
        	System.out.println("Starting archiving.");
        	String archiveFile = archive.archiveFiles();

            JOptionPane.showMessageDialog(this,
                    "Archive created OK. File can be found at:" + archiveFile);


        } 
        catch (IOException ex) 
        {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "Unable to archive files. See log files for details.");
        }

    }

    /**
     * Adds the one to customer month.
     */
    private void addOneToCustomerMonth(){

        try {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to add 1 to each customer's registered month?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
            
            log.info("starting to add 1 to each customer's registered month.");
            CustomersDao custIO = new CustomersDao();
            custIO.read();

            int iMonth = 0;

            for ( Customer cust : custIO.getCustomerList() ) {
                iMonth = cust.getMonthRegistered() + 1;
                cust.setMonthRegistered(iMonth);
            }

            custIO.persist();



        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }

    }
    
}
