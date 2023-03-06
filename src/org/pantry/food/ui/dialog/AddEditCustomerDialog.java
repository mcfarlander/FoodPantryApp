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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.pantry.food.model.Customer;

/**
 *
 * @author Dave Johnson
 */
public class AddEditCustomerDialog extends javax.swing.JDialog {
	
    private JButton btnCancel;
    private JButton btnOk;
	private JComboBox<String> cboGender;
	private JComboBox<String> cboHouseholdId;
	private JComboBox<String> cboMonthRegistered;
    private JCheckBox chkActive;
    private JCheckBox chkNew;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel lblAge;
    private JLabel lblBirthDate;
    private JLabel lblComments;
    private JLabel lblGender;
    private JLabel lblHouseholdId;
    private JLabel lblMonthRegistered;
    private JLabel lblPersonId;
    private JTextField txtAge;
    private JTextField txtBirthDate;
    private JTextField txtComments;
    private JTextField txtPersonId;

    /** */
	private static final long serialVersionUID = 1L;
	
	/** Creates new form AddEditCustomerDialog */
    public AddEditCustomerDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setResizable(false);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
    	
    	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add or Edit Customer"); 
        setName("Form");
        setSize(497, 389);

        jPanel1 = new JPanel();
        jPanel1.setBounds(6, 6, 470, 265);
        jPanel1.setName("jPanel1");
        
        lblHouseholdId = new JLabel();
        lblHouseholdId.setBounds(6, 36, 84, 16);
        lblHouseholdId.setText("Household Id");
        lblHouseholdId.setName("lblHouseholdId"); 
        
        cboHouseholdId = new JComboBox();
        cboHouseholdId.setBounds(108, 30, 126, 27);
        cboHouseholdId.setEditable(true);
        cboHouseholdId.setName("cboHouseholdId"); 
        cboHouseholdId.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                cboHouseholdIdFocusGained(evt);
            }
        });
      
        
        lblPersonId = new JLabel();
        lblPersonId.setBounds(246, 36, 58, 16);
        lblPersonId.setText("Person Id"); 
        lblPersonId.setName("lblPersonId"); 
        
        
        txtPersonId = new JTextField();
        txtPersonId.setBounds(322, 30, 78, 28);
        txtPersonId.setHorizontalAlignment(SwingConstants.RIGHT);
        txtPersonId.setName("txtPersonId"); 
        txtPersonId.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtPersonIdFocusGained(evt);
            }
        });
        
        
        lblGender = new JLabel();
        lblGender.setBounds(123, 80, 44, 16);
        lblGender.setText("Gender"); 
        lblGender.setName("lblGender"); 
        
        cboGender = new JComboBox<String>();
        cboGender.setBounds(198, 76, 100, 27);
        cboGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Female", "Male" }));
        cboGender.setName("cboGender"); 
        
        lblBirthDate = new JLabel();
        lblBirthDate.setBounds(43, 121, 62, 16);
        lblBirthDate.setText("Birth Date"); 
        lblBirthDate.setName("lblBirthDate"); 
        
        txtBirthDate = new JTextField();
        txtBirthDate.setBounds(123, 115, 133, 28);
        txtBirthDate.setHorizontalAlignment(SwingConstants.RIGHT);
        txtBirthDate.setToolTipText("Format MM/dd/YYYY");
        txtBirthDate.setName("txtBirthDate"); 
        txtBirthDate.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtBirthDateFocusGained(evt);
            }
            @Override
			public void focusLost(java.awt.event.FocusEvent evt) {
                txtBirthDateFocusLost(evt);
            }
        });
        
        lblAge = new JLabel();
        lblAge.setBounds(274, 121, 24, 16);
        lblAge.setText("Age"); 
        lblAge.setName("lblAge"); 
        
        txtAge = new JTextField();
        txtAge.setBounds(310, 115, 58, 28);
        txtAge.setHorizontalAlignment(SwingConstants.RIGHT);
        txtAge.setName("txtAge"); 
        txtAge.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtAgeFocusGained(evt);
            }
        });
        
        
        lblMonthRegistered = new JLabel();
        lblMonthRegistered.setBounds(43, 173, 110, 16);
        lblMonthRegistered.setText("Month Registered"); 
        lblMonthRegistered.setName("lblMonthRegistered"); 
        
        cboMonthRegistered = new JComboBox<String>();
        cboMonthRegistered.setBounds(165, 169, 81, 27);
        cboMonthRegistered.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        cboMonthRegistered.setName("cboMonthRegistered"); 
        
        
        chkNew = new JCheckBox();
        chkNew.setBounds(310, 169, 64, 23);
        chkNew.setText("New?"); 
        chkNew.setName("chkNew"); 
        chkNew.setSelected(true);
        
        lblComments = new JLabel();
        lblComments.setBounds(43, 208, 68, 16);
        lblComments.setText("Comments"); 
        lblComments.setName("lblComments"); 
        
        txtComments = new JTextField();
        txtComments.setBounds(129, 202, 283, 28);
        txtComments.setName("txtComments"); 
        txtComments.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtCommentsFocusGained(evt);
            }
        });
        
        chkActive = new JCheckBox();
        chkActive.setBounds(175, 236, 86, 23);
        chkActive.setText("Is Active");
        chkActive.setName("chkActive"); 
        chkActive.setSelected(true);
        
        
        jPanel2 = new JPanel();
        jPanel2.setBounds(6, 283, 470, 56);
        jPanel2.setName("jPanel2"); 
        
        
        btnOk = new JButton();
        btnOk.setBounds(231, 6, 106, 36);
        btnOk.setIcon(new ImageIcon(AddEditCustomerDialog.class.getResource("/org/pantry/food/resources/images/accept.png"))); 
        btnOk.setText("Ok"); 
        btnOk.setName("btnOk"); 
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        
        btnCancel = new JButton();
        btnCancel.setBounds(349, 6, 102, 37);
        btnCancel.setIcon(new ImageIcon(AddEditCustomerDialog.class.getResource("/org/pantry/food/resources/images/accept.png"))); 
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
        jPanel1.add(lblHouseholdId);
        jPanel1.add(cboHouseholdId);
        jPanel1.add(lblPersonId);
        jPanel1.add(txtPersonId);
        jPanel1.add(lblComments);
        jPanel1.add(txtComments);
        jPanel1.add(lblMonthRegistered);
        jPanel1.add(cboMonthRegistered);
        jPanel1.add(lblBirthDate);
        jPanel1.add(lblGender);
        jPanel1.add(cboGender);
        jPanel1.add(txtBirthDate);
        jPanel1.add(lblAge);
        jPanel1.add(chkNew);
        jPanel1.add(txtAge);
        jPanel1.add(chkActive);

        getContentPane().add(jPanel2);
        jPanel2.setLayout(null);
        jPanel2.add(btnOk);
        jPanel2.add(btnCancel);

    }

    private void txtBirthDateFocusLost(java.awt.event.FocusEvent evt) {

        calculateAge();
    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {

        if (validateForm()){
            this.OkCancel = true;
            generateCustomer();
            this.dispose();
        }

    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {

        this.OkCancel = false;
        this.customer = null;
        this.dispose();

    }

    private void cboHouseholdIdFocusGained(java.awt.event.FocusEvent evt) {

    }

    private void txtPersonIdFocusGained(java.awt.event.FocusEvent evt) {
        this.txtPersonId.selectAll();
    }

    private void txtBirthDateFocusGained(java.awt.event.FocusEvent evt) {
        this.txtBirthDate.selectAll();
    }

    private void txtAgeFocusGained(java.awt.event.FocusEvent evt) {
        this.txtAge.selectAll();
    }

    private void txtCommentsFocusGained(java.awt.event.FocusEvent evt) {
        this.txtComments.selectAll();
    }

    private boolean OkCancel = false;
    public boolean getOkCancel(){return this.OkCancel;}

    private Customer customer = new Customer();

    public Customer getNewCustomer(){return this.customer;}
    public void setNewCustomer(Customer cust){
        this.customer = cust;
        setControlsFromPrevious();
    }

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public void setCustomerList(ArrayList<String> customers){

        String nothingStr = "";
        this.cboHouseholdId.addItem(makeObj(nothingStr).toString());

        for (int i = 0; i < customers.size(); i++){
            String str = customers.get(i);
            this.cboHouseholdId.addItem(makeObj(str).toString());
        }

    }

    private Object makeObj(final String item)  {
        return new Object() {
            @Override
            public String toString() { return item; } };
    }

    private void setControlsFromPrevious(){

        this.cboHouseholdId.setSelectedItem(this.customer.getHouseholdId());
        this.txtPersonId.setText("" + this.customer.getPersonId());
        this.cboGender.setSelectedItem(this.customer.getGender());
        this.txtBirthDate.setText(this.customer.getBirthDate());
        this.txtAge.setText("" + this.customer.getAge());
        this.cboMonthRegistered.setSelectedIndex(this.customer.getMonthRegistered() - 1);
        this.chkNew.setSelected(this.customer.isNewCustomer());
        this.txtComments.setText(this.customer.getComments());
        this.chkActive.setSelected(this.customer.isActive());

    }

    private void generateCustomer(){

        //this.customer.setCustomerId is set by calling form
        this.customer.setHouseholdId(Integer.parseInt(this.cboHouseholdId.getSelectedItem().toString()));
        this.customer.setPersonId(Integer.parseInt(this.txtPersonId.getText()));
        this.customer.setGender(this.cboGender.getSelectedItem().toString());
        this.customer.setBirthDate(this.txtBirthDate.getText());
        this.customer.setAge(Integer.parseInt(txtAge.getText()));
        this.customer.setMonthRegistered(this.cboMonthRegistered.getSelectedIndex() + 1);
        this.customer.setNewCustomer(this.chkNew.isSelected());
        this.customer.setComments(txtComments.getText());
        this.customer.setActive(chkActive.isSelected());

    }

    private boolean validateForm(){

        if (this.cboHouseholdId.getSelectedItem().toString().length() == 0){
            JOptionPane.showMessageDialog(this,"Please select a house hold.");
            return false;
        }

        try{
            @SuppressWarnings("unused")
			int household = Integer.parseInt(this.cboHouseholdId.getSelectedItem().toString());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please select a house hold.");
            return false;
        }

        if (this.txtPersonId.getText().length() == 0){
            JOptionPane.showMessageDialog(this,"Please select a person id.");
            return false;
        }

        try{
            @SuppressWarnings("unused")
			int household = Integer.parseInt(this.txtPersonId.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please select a person id.");
            return false;
        }

        if (this.txtBirthDate.getText().length() == 0){
            JOptionPane.showMessageDialog(this,"Please select a birth date.");
            return false;
        }

        try{
            @SuppressWarnings("unused")
			Date test = dateFormat.parse(this.txtBirthDate.getText());
        } catch (ParseException ex){
            JOptionPane.showMessageDialog(this,"Please select a birth date.");
            return false;
        }

        if (this.txtAge.getText().length() == 0){
            JOptionPane.showMessageDialog(this,"Please enter the person's age.");
            return false;
        }

        int test = 0;

        try{
            test = Integer.parseInt(this.txtAge.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter the person's age.");
            return false;
        }

        if (test < 0 || test > 120)
        {
            JOptionPane.showMessageDialog(this, "Please enter the correct birthdate.");
            return false;
        }


        return true;
    }

    private void calculateAge(){

        try {

            Date birthDate = dateFormat.parse(this.txtBirthDate.getText());

            // Create a calendar object with the date of birth
            Calendar dateOfBirth = new GregorianCalendar();
            dateOfBirth.setTime(birthDate);
            // Create a calendar object with today's date
            Calendar today = Calendar.getInstance();
            // Get age based on year
            int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
            // Add the tentative age to the date of birth to get this year's birthday
            dateOfBirth.add(Calendar.YEAR, age);
            // If this year's birthday has not happened yet, subtract one from age
            if (today.before(dateOfBirth)) { age--; }

            this.txtAge.setText("" + age);

        } catch (Exception ex){
            JOptionPane.showMessageDialog(this,"Cannot calcuate age from this date.");

        }


    }


}// end of class