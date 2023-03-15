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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.text.JTextComponent;

import org.pantry.food.model.VolunteerEvent;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.common.JComboBoxAutoCompletador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author davej
 */
public class AddEditVolunteerEvents extends javax.swing.JDialog {
	
    private JButton btnCancel;
    private JButton btnOk;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel lblEventName;
    private JLabel lblNotes;
    private JLabel lblDate;
    private JLabel lblEventId;
    private JLabel lblVolunteer;
    private JLabel lblVolunteerHours;
    private JTextField txtEventName;
    private JTextField txtNotes;
    private JTextField txtDate;
    private JTextField txtEventId;
    private JComboBox<String> txtVolunteer;
    private JTextField txtVolunteerHours;

    /**
	 * 
	 */
	private static final long serialVersionUID = -7780150865600610737L;

	/** Creates new form AddEditVolunteerHours */
    public AddEditVolunteerEvents(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
    	
    	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add or Edit Volunteer Events"); 
        setName("Form");
        setSize(467, 423);

        jPanel1 = new JPanel();
        jPanel1.setBounds(18, 6, 430, 306);
        jPanel1.setName("jPanel1");
        
        lblEventId = new JLabel();
        lblEventId.setBounds(35, 22, 75, 16);
        lblEventId.setText("Event ID"); 
        lblEventId.setName("lblNumAdults");
        
        
        txtEventId = new JTextField();
        txtEventId.setEditable(false);
        txtEventId.setBounds(337, 16, 44, 28);
        txtEventId.setHorizontalAlignment(SwingConstants.RIGHT);
        txtEventId.setText("0"); 
        txtEventId.setName("txtEventId"); 
        txtEventId.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtEventIdFocusGained(evt);
            }
        });
        
        
        lblVolunteer = new JLabel();
        lblVolunteer.setBounds(35, 132, 89, 16);
        lblVolunteer.setText("Volunteer"); 
        lblVolunteer.setName("lblVolunteer");
        
        
        txtVolunteer = new JComboBox<String>();
        txtVolunteer.setEditable(true);
        txtVolunteer.setBounds(136, 126, 244, 28);
        txtVolunteer.setName("txtVolunteer"); 
        txtVolunteer.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtVolunteerFocusGained(evt);
            }
        });
        // get the combo boxes editor component
        JTextComponent editor = (JTextComponent) txtVolunteer.getEditor().getEditorComponent();
        // change the editor's document
        editor.setDocument(new JComboBoxAutoCompletador(txtVolunteer));
        
        
        lblEventName = new JLabel();
        lblEventName.setBounds(35, 56, 89, 16);
        lblEventName.setText("Event Name"); 
        lblEventName.setName("lblEventName");
        
        
        txtEventName = new JTextField();
        txtEventName.setBounds(137, 50, 244, 28);
        txtEventName.setHorizontalAlignment(SwingConstants.LEFT);
        txtEventName.setName("txtEventName"); 
        txtEventName.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtEventNameFocusGained(evt);
            }
        });
       
        
        lblVolunteerHours = new JLabel();
        lblVolunteerHours.setBounds(37, 170, 120, 16);
        lblVolunteerHours.setText("Volunteer Hours"); 
        lblVolunteerHours.setName("lblVolunteerHours"); 
        
        
        txtVolunteerHours = new JTextField();
        txtVolunteerHours.setBounds(322, 164, 59, 28);
        txtVolunteerHours.setHorizontalAlignment(SwingConstants.RIGHT);
        txtVolunteerHours.setText("0.0"); 
        txtVolunteerHours.setName("txtVolunteerHours"); 
        txtVolunteerHours.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
			public void focusGained(java.awt.event.FocusEvent evt) {
                txtVolunteerHoursFocusGained(evt);
            }
        });
        
        
        lblNotes = new JLabel();
        lblNotes.setBounds(36, 207, 61, 16);
        lblNotes.setText("Notes"); 
        lblNotes.setName("lblNotes");
        
        
        txtNotes = new JTextField();
        txtNotes.setHorizontalAlignment(SwingConstants.LEFT);
        txtNotes.setBounds(137, 201, 244, 28);
        txtNotes.setName("txtNotes");
        
        
        lblDate = new JLabel();
        lblDate.setBounds(38, 246, 29, 16);
        lblDate.setText("Date"); 
        lblDate.setName("lblDate"); 
        
        
        txtDate = new JTextField();
        txtDate.setHorizontalAlignment(SwingConstants.LEFT);
        txtDate.setBounds(137, 240, 244, 28);
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
        btnOk.setIcon(new ImageIcon(AddEditVolunteerEvents.class.getResource("/org/pantry/food/resources/images/accept.png"))); 
        btnOk.setText("OK"); 
        btnOk.setName("btnOk"); 
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        
        
        btnCancel = new JButton();
        btnCancel.setBounds(318, 6, 90, 43);
        btnCancel.setIcon(new ImageIcon(AddEditVolunteerEvents.class.getResource("/org/pantry/food/resources/images/cancel.png"))); 
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
        jPanel1.add(lblEventId);
        jPanel1.add(txtEventId);
        jPanel1.add(lblVolunteer);
        jPanel1.add(txtVolunteer);
        jPanel1.add(lblEventName);
        jPanel1.add(lblVolunteerHours);
        jPanel1.add(txtEventName);
        jPanel1.add(txtVolunteerHours);
        jPanel1.add(lblNotes);
        jPanel1.add(lblDate);
        jPanel1.add(txtDate);
        jPanel1.add(txtNotes);
        
        JButton btnCopy = new JButton("Copy");
        btnCopy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		copy();
        	}
        });
        btnCopy.setBounds(242, 85, 69, 29);
        jPanel1.add(btnCopy);
        
        JButton btnPaste = new JButton("Paste");
        btnPaste.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		paste();
        	}
        });
        btnPaste.setBounds(312, 85, 69, 29);
        jPanel1.add(btnPaste);

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

    private void txtEventIdFocusGained(java.awt.event.FocusEvent evt) {
        txtEventId.selectAll();
    }

    private void txtVolunteerFocusGained(java.awt.event.FocusEvent evt) {
        //txtVolunteer.getSelectedItem()..selectAll();
    }

    private void txtEventNameFocusGained(java.awt.event.FocusEvent evt) {
        txtEventName.selectAll();
    }

    private void txtVolunteerHoursFocusGained(java.awt.event.FocusEvent evt) {
        txtVolunteerHours.selectAll();
    }

    private void txtDateFocusGained(java.awt.event.FocusEvent evt) {
        txtDate.selectAll();
    }


    private boolean OkCancel = false;
    public boolean getOkCancel(){return this.OkCancel;}


    private VolunteerEvent record = new VolunteerEvent();

    public VolunteerEvent getNewRecord(){return this.record;}
    public void setNewRecord(VolunteerEvent rec){
        this.record = rec;
        setControlsFromPrevious();
    }

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
	public void setVolunteerList(ArrayList<String> volunteers){

        String nothingStr = "";
        this.txtVolunteer.addItem(makeObj(nothingStr).toString());

        for (int i = 0; i < volunteers.size(); i++){
            String str = volunteers.get(i);
            this.txtVolunteer.addItem((makeObj(str).toString()));
        }

    }
    
    private Object makeObj(final String item)  {
        return new Object() {
            @Override
            public String toString() { return item; } };
    }
    

    private void setControlsFromPrevious(){

        this.txtEventId.setText("" + this.record.getVolunteerEventId());
        this.txtEventName.setText(this.record.getEventName());
        this.txtVolunteer.setSelectedItem(this.record.getVolunteerName());
        this.txtVolunteerHours.setText("" + this.record.getVolunteerHours());
        this.txtNotes.setText(this.record.getNotes());
        this.txtDate.setText(this.record.getEventDate());

    }

    private void generateRecord(){

        this.record.setEventName(txtEventName.getText());
        this.record.setVolunteerName(this.txtVolunteer.getSelectedItem().toString());
        this.record.setVolunteerHours(Double.parseDouble(this.txtVolunteerHours.getText()));
        this.record.setNotes(txtNotes.getText());

        //Date date = new Date();
        //this.record.setEntryDate(dateFormat.format(date));

        this.record.setEventDate(txtDate.getText());

    }
    
    private void copy()
    {
    	FormState.getInstance().setClipbardEventName(this.txtEventName.getText());
    }
    
    private void paste()
    {
    	txtEventName.setText(FormState.getInstance().getClipbardEventName());
    }

    private boolean validateForm(){


        try{
            Integer.parseInt(this.txtEventId.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid volunteer id.");
            return false;
        }

        try{
            Float.parseFloat(this.txtVolunteerHours.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid number for volunteer hours.");
            return false;
        }

        if (this.txtDate.getText().length() == 0){
            JOptionPane.showMessageDialog(this,"Please select an event date.");
            return false;
        }

        try{
            dateFormat.parse(this.txtDate.getText());
        } catch (ParseException ex){
            JOptionPane.showMessageDialog(this,"Please select an event date.");
            return false;
        }

        return true;
    }
}
