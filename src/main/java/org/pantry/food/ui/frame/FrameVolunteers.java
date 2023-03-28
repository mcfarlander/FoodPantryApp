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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.pantry.food.dao.VisitsDao;
import org.pantry.food.dao.VolunteerDao;
import org.pantry.food.model.Visit;
import org.pantry.food.model.Volunteer;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AddEditVolunteerDialog;

import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The Class FrameVolunteers.
 *
 * @author mcfarland_davej
 */
@SuppressWarnings("unused")
public class FrameVolunteers extends javax.swing.JInternalFrame 
{
	
	/** The Constant log. */
	private final static Logger log = Logger.getLogger(FrameVolunteers.class.getName());
	
    /** The j button 1. */
    private JButton jButton1;
    
    /** The j button 2. */
    private JButton jButton2;
    
    /** The j button 3. */
    private JButton jButton3;
    
    /** The j scroll pane 1. */
    private JScrollPane jScrollPane1;
    
    /** The j table 1. */
    private JTable jTable1;
    
    /** The j tool bar 1. */
    private JToolBar jToolBar1;

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2823894451351907109L;


	/**
	 *  Creates new form Frame.
	 */
    public FrameVolunteers() {
        initComponents();
        load();
    }

    /** This method is called from within the constructor to
     * initialize the form..
     */
    @SuppressWarnings({ "unchecked", "serial" })
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        
        jTable1 = new JTable();
        jTable1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent evt) {
        		if (evt.getClickCount() == 2) 
        		{
        			jButtonEditActionPerformed(null); 
        	    }
        	}
        });

        setClosable(true);
        setTitle("Regular Volunteers"); 
        setFrameIcon(new ImageIcon(FrameVolunteers.class.getResource("/org/pantry/food/resources/images/user.png"))); 
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

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); 

        jButton1.setIcon(new ImageIcon(FrameVolunteers.class.getResource("/org/pantry/food/resources/images/user_add.png"))); 
        jButton1.setText("Add Volunteer"); 
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("btnAdd"); 
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new ImageIcon(FrameVolunteers.class.getResource("/org/pantry/food/resources/images/user_edit.png"))); 
        jButton2.setText("Edit Volunteer"); 
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("btnEdit"); 
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new ImageIcon(FrameVolunteers.class.getResource("/org/pantry/food/resources/images/user_delete.png"))); 
        jButton3.setText("Delete Volunteer"); 
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("btnDelete"); 
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setName("jScrollPane1"); 

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Volunteer ID", "Name", "Phone", "Email", "Type", "Note"
            }
        ) {
            @SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            @SuppressWarnings("rawtypes")
			@Override
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
        jTable1.setName("jTable1"); 
        jTable1.setShowGrid(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setHeaderValue("Voluneer ID"); 
        jTable1.getColumnModel().getColumn(1).setHeaderValue("Name"); 
        jTable1.getColumnModel().getColumn(2).setHeaderValue("Phone"); 
        jTable1.getColumnModel().getColumn(3).setHeaderValue("Email"); 
        jTable1.getColumnModel().getColumn(4).setHeaderValue("Type"); 
        jTable1.getColumnModel().getColumn(5).setHeaderValue("Note");
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }

    /**
     * J button add action performed.
     *
     * @param evt the evt
     */
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {

    	
        AddEditVolunteerDialog dial = new AddEditVolunteerDialog(null, true);
        Volunteer obj = new Volunteer();
        obj.setVolunteerId(this.nextId);
        dial.setNewVolunteer(obj);
        dial.setLocationRelativeTo(this);
        
        dial.setVisible(true);

        if (dial.getOkCancel()){

            objIo.add(dial.getNewVolunteer());

            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
            model.addRow(dial.getNewVolunteer().getVolunteerObject());

            save();
            nextId++;
        }
        
        
    }

    /**
     * J button edit action performed.
     *
     * @param evt the evt
     */
    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {

        Volunteer newObj = getSelected();

        if (newObj != null){
            AddEditVolunteerDialog dial = new AddEditVolunteerDialog(null, true);
            dial.setNewVolunteer(newObj);
            dial.setLocationRelativeTo(this);
            dial.setVisible(true);

            if (dial.getOkCancel()){
                objIo.edit(dial.getNewVolunteer());
                save();
                load();
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Please select a volunteer to edit.");
        }

    }

    /**
     * J button delete action performed.
     *
     * @param evt the evt
     */
    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {

        Volunteer del = getSelected();

        if (del != null){

            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to deactivate the selected volunteer?",
                "Confirm", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION){
                objIo.delete(del);
                save();
                load();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to delete.");
        }
    }

    /**
     * Form internal frame closed.
     *
     * @param evt the evt
     */
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {

        FormState.getInstance().setFormOpen(false);
        
    }

    /** The obj io. */
    private VolunteerDao objIo = new VolunteerDao();
    
    /** The next id. */
    private int nextId = 0;

    /**
     * Load.
     */
    private void load(){
        try {
            objIo.read();

            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
            model.setRowCount(0);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            Calendar visCal = Calendar.getInstance();

            for (int i = 0; i < objIo.getCvsCount(); i++){
                Volunteer obj = objIo.getCvsList().get(i);

                model.addRow(obj.getVolunteerObject());

                if (obj.getVolunteerId() >= nextId){
                    nextId = obj.getVolunteerId() + 1;
                }
            }

        } catch (ArrayIndexOutOfBoundsException ex){
        	log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem with the file: found, but it is incorrect.\n" + ex.getMessage());
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem opening file/n" + ex.getMessage());
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem opening file/n" + ex.getMessage());
        }
        
        
    }
    
    /**
     * Save.
     */
    private void save(){
        try {
            objIo.persist();
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem saving file/n" + ex.getMessage());
        } 
        
    }

    /**
     * Gets the selected.
     *
     * @return the selected
     */
    private Volunteer getSelected(){

        Volunteer obj = null;

        int irow = this.jTable1.getSelectedRow();

        if (irow > -1){
            int itestId = Integer.parseInt(this.jTable1.getModel().getValueAt(irow, 0).toString());

            for (int i = 0; i < this.objIo.getCvsCount(); i++){
                Volunteer test = this.objIo.getCvsList().get(i);
                if (test.getVolunteerId() == itestId){
                    obj = test;
                    break;
                }
            }

        }

        return obj;
    }

}
