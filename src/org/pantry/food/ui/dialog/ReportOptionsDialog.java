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

import java.awt.Frame;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Dave Johnson
 */
public class ReportOptionsDialog extends javax.swing.JDialog {
	
    private JButton btnCancel;
    private JButton btnOk;
    private JComboBox<String> cboMonth;
    private JPanel frmReports;
    private JRadioButton joptFoodRecords;
    private JRadioButton joptMonthSummary;
    private JRadioButton joptVolunteerHours;
    private JRadioButton joptVolunteerEvents;
    private JRadioButton joptPantrySummary;
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form ReportOptionsDialog */
    public ReportOptionsDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        setMonthToCurrent();
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
    	
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reporting Options"); 
        setName("Form"); 
        setSize(475,351);
        
        frmReports = new JPanel();
        frmReports.setBounds(19, 19, 425, 231);
        frmReports.setBorder(javax.swing.BorderFactory.createTitledBorder("Report Selection")); 
        frmReports.setName("frmReports");

        btnOk = new JButton();
        btnOk.setBounds(256, 262, 80, 44);
        btnOk.setIcon(new ImageIcon(ReportOptionsDialog.class.getResource("/org/pantry/food/resources/images/accept.png"))); 
        btnOk.setText("OK"); 
        btnOk.setName("btnOk"); 
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        
        
        btnCancel = new JButton();
        btnCancel.setBounds(348, 262, 80, 44);
        btnCancel.setIcon(new ImageIcon(ReportOptionsDialog.class.getResource("/org/pantry/food/resources/images/cancel.png"))); 
        btnCancel.setText("Close"); 
        btnCancel.setName("Close"); 
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        
        
        
        joptMonthSummary = new JRadioButton();
        joptMonthSummary.setBounds(12, 44, 173, 23);
        joptMonthSummary.setSelected(true);
        joptMonthSummary.setText("Monthly Summary For:"); 
        joptMonthSummary.setName("joptMonthSummary"); 
        joptMonthSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joptMonthSummaryActionPerformed(evt);
            }
        });
        
        
        cboMonth = new JComboBox<>();
        cboMonth.setBounds(236, 44, 96, 27);
        cboMonth.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        cboMonth.setName("cboMonth"); 
        cboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMonthActionPerformed(evt);
            }
        });
        
        
        joptFoodRecords = new JRadioButton();
        joptFoodRecords.setBounds(12, 79, 212, 23);
        joptFoodRecords.setText("Donated Food Weight Report"); 
        joptFoodRecords.setName("joptFoodRecords"); 
        joptFoodRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joptFoodRecordsActionPerformed(evt);
            }
        });
        
        joptVolunteerHours = new JRadioButton();
        joptVolunteerHours.setBounds(12, 114, 134, 23);
        joptVolunteerHours.setText("Volunteer Hours"); 
        joptVolunteerHours.setName("joptVolunteerHours"); 
        joptVolunteerHours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joptVolunteerHoursActionPerformed(evt);
            }
        });
        
        joptVolunteerEvents = new JRadioButton();
        joptVolunteerEvents.setText("Volunteer Events");
        joptVolunteerEvents.setName("joptVolunteerEvents");
        joptVolunteerEvents.setBounds(12, 149, 212, 23);
        joptVolunteerEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joptVolunteerEventsActionPerformed(evt);
            }
        });
        
        joptPantrySummary = new JRadioButton("Pantry Report Summary");
        joptPantrySummary.setName("joptPantrySummary");
        joptPantrySummary.setBounds(12, 187, 228, 23);
        joptPantrySummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joptPantrySummaryEventsActionPerformed(evt);
            }
        });
        
        getContentPane().setLayout(null);
        getContentPane().add(frmReports);
        frmReports.setLayout(null);
        frmReports.add(joptMonthSummary);
        frmReports.add(cboMonth);
        frmReports.add(joptFoodRecords);
        frmReports.add(joptVolunteerHours);
        frmReports.add(joptVolunteerEvents);
        frmReports.add(joptPantrySummary);
      
        
        getContentPane().add(btnOk);
        getContentPane().add(btnCancel);

    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
    	
        this.OkCancel = true;
        this.dispose();
        
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {

        this.OkCancel = false;
        this.dispose();

    }

    private void cboMonthActionPerformed(java.awt.event.ActionEvent evt) {

        this.monthSelected = this.cboMonth.getSelectedIndex();
        //System.out.println("index:" + this.cboMonth.getSelectedIndex());
    }

    private void joptMonthSummaryActionPerformed(java.awt.event.ActionEvent evt) {
        selectReport(0);
    }

    private void joptFoodRecordsActionPerformed(java.awt.event.ActionEvent evt) {
        selectReport(1);
    }

    private void joptVolunteerHoursActionPerformed(java.awt.event.ActionEvent evt) {
        selectReport(2);
    }
    
    private void joptVolunteerEventsActionPerformed(java.awt.event.ActionEvent evt) {
        selectReport(3);
    }
    
    private void joptPantrySummaryEventsActionPerformed(java.awt.event.ActionEvent evt) {
    	selectReport(4);
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReportOptionsDialog dialog = new ReportOptionsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    private boolean OkCancel = false;
    public boolean getOkCancel(){return this.OkCancel;}

    private int monthSelected = 0;
    public int getMonthSelected(){ return this.monthSelected; }

    private int reportSelected = 0;
    public int getReportSelected(){ return this.reportSelected; }

    
    @SuppressWarnings("unused")
	private void determineReportSelected(){

        if (this.joptMonthSummary.isSelected()){
            this.reportSelected = 0;
        }

    }

    private void selectReport(int ireport){

        this.reportSelected = ireport;

        switch (ireport){
            case 0:
                this.joptMonthSummary.setSelected(true);
                this.joptFoodRecords.setSelected(false);
                this.joptVolunteerHours.setSelected(false);
                this.joptPantrySummary.setSelected(false);
                break;
            case 1:
                this.joptMonthSummary.setSelected(false);
                this.joptFoodRecords.setSelected(true);
                this.joptVolunteerHours.setSelected(false);
                this.joptPantrySummary.setSelected(false);
                break;
            case 2:
                this.joptMonthSummary.setSelected(false);
                this.joptFoodRecords.setSelected(false);
                this.joptVolunteerHours.setSelected(true);
                this.joptPantrySummary.setSelected(false);
                break;
            case 3:
            	break;
            case 4:
            	this.joptMonthSummary.setSelected(false);
                this.joptFoodRecords.setSelected(false);
                this.joptVolunteerHours.setSelected(false);
                this.joptPantrySummary.setSelected(true);
            	break;
            default:
                break;

        }

    }

    private void setMonthToCurrent(){

        Calendar cal = Calendar.getInstance();
        this.cboMonth.setSelectedIndex(cal.get(Calendar.MONTH));

    }
}// end of class
