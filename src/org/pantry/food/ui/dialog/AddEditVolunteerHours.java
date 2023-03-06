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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.pantry.food.model.VolunteerHour;

/**
 *
 * @author davej
 */
public class AddEditVolunteerHours extends javax.swing.JDialog {
	
    private JButton btnCancel;
    private JButton btnOk;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel lblAdultHrs;
    private JLabel lblComment;
    private JLabel lblDate;
    private JLabel lblNumAdults;
    private JLabel lblNumStudents;
    private JLabel lblStudentHrs;
    private JTextField txtAdultHrs;
    private JTextField txtComment;
    private JTextField txtDate;
    private JTextField txtNumAdults;
    private JTextField txtNumStudents;
    private JTextField txtStudentHrs;

    /** */
	private static final long serialVersionUID = -7780150865600610737L;

	/** Creates new form AddEditVolunteerHours */
    public AddEditVolunteerHours(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
    	
    	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add or Edit Volunteer Hours"); 
        setName("Form");
        setSize(464, 421);

        jPanel1 = new JPanel();
        jPanel1.setBounds(18, 6, 430, 306);
        jPanel1.setName("jPanel1");
        
        lblNumAdults = new JLabel();
        lblNumAdults.setBounds(36, 41, 75, 16);
        lblNumAdults.setText("Num Adults"); 
        lblNumAdults.setName("lblNumAdults");
        
        
        txtNumAdults = new JTextField();
        txtNumAdults.setBounds(147, 35, 44, 28);
        txtNumAdults.setHorizontalAlignment(SwingConstants.RIGHT);
        txtNumAdults.setText("0"); 
        txtNumAdults.setName("txtNumAdults"); 
        txtNumAdults.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumAdultsFocusGained(evt);
            }
        });
        
        
        lblNumStudents = new JLabel();
        lblNumStudents.setBounds(36, 87, 89, 16);
        lblNumStudents.setText("Num Students"); 
        lblNumStudents.setName("lblNumStudents");
        
        
        txtNumStudents = new javax.swing.JTextField();
        txtNumStudents.setBounds(147, 81, 44, 28);
        txtNumStudents.setHorizontalAlignment(SwingConstants.RIGHT);
        txtNumStudents.setText("0"); 
        txtNumStudents.setName("txtNumStudents"); 
        txtNumStudents.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumStudentsFocusGained(evt);
            }
        });
        
        
        lblAdultHrs = new JLabel();
        lblAdultHrs.setBounds(255, 41, 60, 16);
        lblAdultHrs.setText("Adult Hrs"); 
        lblAdultHrs.setName("lblAdultHrs");
        
        
        txtAdultHrs = new JTextField();
        txtAdultHrs.setBounds(346, 35, 48, 28);
        txtAdultHrs.setHorizontalAlignment(SwingConstants.RIGHT);
        txtAdultHrs.setText("0.0"); 
        txtAdultHrs.setName("txtAdultHrs"); 
        txtAdultHrs.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtAdultHrsFocusGained(evt);
            }
        });
       
        
        lblStudentHrs = new JLabel();
        lblStudentHrs.setBounds(254, 87, 74, 16);
        lblStudentHrs.setText("Student Hrs"); 
        lblStudentHrs.setName("lblStudentHrs"); 
        
        
        txtStudentHrs = new JTextField();
        txtStudentHrs.setBounds(346, 81, 59, 28);
        txtStudentHrs.setHorizontalAlignment(SwingConstants.RIGHT);
        txtStudentHrs.setText("0.0"); 
        txtStudentHrs.setName("txtStudentHrs"); 
        txtStudentHrs.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtStudentHrsFocusGained(evt);
            }
        });
        
        
        lblComment = new JLabel();
        lblComment.setBounds(36, 142, 61, 16);
        lblComment.setText("Comment"); 
        lblComment.setName("lblComment");
        
        
        txtComment = new JTextField();
        txtComment.setBounds(115, 136, 272, 28);
        txtComment.setName("txtComment");
        
        
        lblDate = new JLabel();
        lblDate.setBounds(36, 191, 29, 16);
        lblDate.setText("Date"); 
        lblDate.setName("lblDate"); 
        
        
        txtDate = new JTextField();
        txtDate.setBounds(115, 185, 272, 28);
        txtDate.setName("txtDate"); 
        txtDate.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtDateFocusGained(evt);
            }
        });
        
        
        jPanel2 = new JPanel();
        jPanel2.setBounds(18, 324, 429, 54);
        
        btnOk = new JButton();
        btnOk.setBounds(195, 6, 111, 42);
        btnOk.setIcon(new ImageIcon(AddEditVolunteerHours.class.getResource("/org/pantry/food/resources/images/accept.png"))); 
        btnOk.setText("OK"); // NOI18N
        btnOk.setName("btnOk"); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        
        
        btnCancel = new JButton();
        btnCancel.setBounds(318, 6, 90, 43);
        btnCancel.setIcon(new ImageIcon(AddEditVolunteerHours.class.getResource("/org/pantry/food/resources/images/accept.png"))); 
        btnCancel.setText("Cancel"); 
        btnCancel.setName("btnCancel"); 
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().setLayout(null);

        getContentPane().add(jPanel1);
        jPanel1.setLayout(null);
        jPanel1.add(lblNumAdults);
        jPanel1.add(txtNumAdults);
        jPanel1.add(lblNumStudents);
        jPanel1.add(txtNumStudents);
        jPanel1.add(lblAdultHrs);
        jPanel1.add(lblStudentHrs);
        jPanel1.add(txtAdultHrs);
        jPanel1.add(txtStudentHrs);
        jPanel1.add(lblComment);
        jPanel1.add(lblDate);
        jPanel1.add(txtDate);
        jPanel1.add(txtComment);

        jPanel2.setName("jPanel2");

        getContentPane().add(jPanel2);
        jPanel2.setLayout(null);
        jPanel2.add(btnOk);
        jPanel2.add(btnCancel);

    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
        if (validateForm()){
            this.OkCancel = true;
            generateRecord();
            this.dispose();
        }
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        this.OkCancel = false;
        this.record = null;
        this.dispose();
    }

    private void txtNumAdultsFocusGained(java.awt.event.FocusEvent evt) {
        txtNumAdults.selectAll();
    }

    private void txtNumStudentsFocusGained(java.awt.event.FocusEvent evt) {
        txtNumStudents.selectAll();
    }

    private void txtAdultHrsFocusGained(java.awt.event.FocusEvent evt) {
        txtAdultHrs.selectAll();
    }

    private void txtStudentHrsFocusGained(java.awt.event.FocusEvent evt) {
        txtStudentHrs.selectAll();
    }

    private void txtDateFocusGained(java.awt.event.FocusEvent evt) {
        txtDate.selectAll();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddEditVolunteerHours dialog = new AddEditVolunteerHours(new javax.swing.JFrame(), true);
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


    private VolunteerHour record = new VolunteerHour();

    public VolunteerHour getNewRecord(){return this.record;}
    public void setNewRecord(VolunteerHour rec){
        this.record = rec;
        setControlsFromPrevious();
    }

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private void setControlsFromPrevious(){

        this.txtNumAdults.setText("" + this.record.getNumberAdults());
        this.txtAdultHrs.setText("" + this.record.getNumberAdultHours());
        this.txtNumStudents.setText("" + this.record.getNumberStudents());
        this.txtStudentHrs.setText("" + this.record.getNumberStudentHours());
        this.txtComment.setText(this.record.getComment());
        this.txtDate.setText(this.record.getEntryDate());

    }

    private void generateRecord(){

        this.record.setNumberAdults(Integer.parseInt(txtNumAdults.getText()));
        this.record.setNumberAdultHours(Float.parseFloat(this.txtAdultHrs.getText()));
        this.record.setNumberStudents(Integer.parseInt(this.txtNumStudents.getText()));
        this.record.setNumberStudentHours(Float.parseFloat(this.txtStudentHrs.getText()));
        this.record.setComment(txtComment.getText());
        this.record.setEntryDate(txtDate.getText());

    }

    private boolean validateForm(){


        try{
            Integer.parseInt(this.txtNumAdults.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid number for adults.");
            return false;
        }

        try{
            Float.parseFloat(this.txtAdultHrs.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid number for adult hours.");
            return false;
        }

        try{
            Integer.parseInt(this.txtNumStudents.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid number for students.");
            return false;
        }

        try{
            Float.parseFloat(this.txtStudentHrs.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid number for student hours.");
            return false;
        }

        if (this.txtDate.getText().length() == 0){
            JOptionPane.showMessageDialog(this,"Please select an entry date.");
            return false;
        }

        try{
            dateFormat.parse(this.txtDate.getText());
        } catch (ParseException ex){
            JOptionPane.showMessageDialog(this,"Please select an entry date.");
            return false;
        }

        return true;
    }


}
