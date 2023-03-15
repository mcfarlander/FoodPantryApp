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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.pantry.food.dao.VolunteerHourDao;
import org.pantry.food.model.VolunteerHour;
import org.pantry.food.ui.common.DateUtil;
import org.pantry.food.ui.dialog.AddEditVolunteerHours;

/**
 * The Class FrameVolunteerHours.
 *
 * @author davej
 */
public class FrameVolunteerHours extends javax.swing.JInternalFrame 
{
	
	/** The Constant log. */
	private final static Logger log = Logger.getLogger(FrameVolunteerHours.class.getName());
	
    /** The btn add. */
    private JButton btnAdd;
    
    /** The btn delete. */
    private JButton btnDelete;
    
    /** The btn edit. */
    private JButton btnEdit;
    
    /** The j scroll pane 1. */
    private JScrollPane jScrollPane1;
    
    /** The j table 1. */
    private JTable jTable1;
    
    /** The j tool bar 1. */
    private JToolBar jToolBar1;

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3993940824072993340L;

	/**
	 *  Creates new form FrameVolunteerHours.
	 */
    public FrameVolunteerHours() {
        initComponents();
        loadRecords();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings({ "unchecked", "serial" })
    private void initComponents() {
    	
        jToolBar1 = new JToolBar();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();
        jScrollPane1 = new JScrollPane();
        
        jTable1 = new JTable();
        jTable1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent evt) {
        		if (evt.getClickCount() == 2) 
        		{
        			btnEditActionPerformed(null); 
        	    }
        	}
        });

        setClosable(true);
        setTitle("Volunteer Hours"); 
        setFrameIcon(new ImageIcon(FrameVolunteerHours.class.getResource("/org/pantry/food/resources/images/telephone.png"))); 
        setName("Form"); 

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); 

        btnAdd.setIcon(new ImageIcon(FrameVolunteerHours.class.getResource("/org/pantry/food/resources/images/telephone_add.png"))); 
        btnAdd.setText("Add Hours"); 
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setName("btnAdd"); 
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnEdit.setIcon(new ImageIcon(FrameVolunteerHours.class.getResource("/org/pantry/food/resources/images/telephone_edit.png"))); 
        btnEdit.setText("Edit Hours"); 
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setName("btnEdit"); 
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnDelete.setIcon(new ImageIcon(FrameVolunteerHours.class.getResource("/org/pantry/food/resources/images/telephone_delete.png"))); 
        btnDelete.setText("Delete Hours"); 
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setName("btnDelete");
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setName("jScrollPane1"); 

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "VH ID", "Num Adults", "Adult Hrs", "Num Students", "Student Hrs", "Comment", "Entry Date"
            }
        ) {
            @SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jTable1.setName("jTable1"); 
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }

    /**
     * Btn add action performed.
     *
     * @param evt the evt
     */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) 
    {
        AddEditVolunteerHours dial = new AddEditVolunteerHours(null, true);
        VolunteerHour record = new VolunteerHour();
        record.setVolunteerHourId(this.nextRecordId);
        record.setEntryDate(DateUtil.getCurrentDateString());
        dial.setNewRecord(record);
        dial.setLocationRelativeTo(this);

        dial.setVisible(true);

        if (dial.getOkCancel()){

            recordIo.add(dial.getNewRecord());

            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
            model.addRow(dial.getNewRecord().getVolunteerHourObject());

            saveRecords();
            nextRecordId++;
        }

    }

    /**
     * Btn edit action performed.
     *
     * @param evt the evt
     */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) 
    {
        VolunteerHour record = getSelectedRecord();

        if (record != null){
            AddEditVolunteerHours dial = new AddEditVolunteerHours(null, true);
            dial.setNewRecord(record);
            dial.setLocationRelativeTo(this);
            dial.setVisible(true);

            if (dial.getOkCancel()){
                recordIo.edit(dial.getNewRecord());
                saveRecords();
                loadRecords();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select a food record to edit.");
        }


    }

    /**
     * Btn delete action performed.
     *
     * @param evt the evt
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) 
    {
        VolunteerHour record = getSelectedRecord();

        if (record != null){

            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the selected volunteer hours?",
                "Confirm", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION){
                //custIo.deleteCustomer(newCust);
                recordIo.delete(record);

                saveRecords();
                loadRecords();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select volunteer hours to delete.");
        }

    }


    /** The record io. */
    private VolunteerHourDao recordIo = new VolunteerHourDao();
    
    /** The next record id. */
    private int nextRecordId = 0;

    /**
     * Load records.
     */
    private void loadRecords()
    {

        try
        {
            recordIo.readCsvFile();
            DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
            model.setRowCount(0);

            for (int i = 0; i < recordIo.getCvsCount(); i++){
               VolunteerHour record = recordIo.getCvsList().get(i);

               model.addRow(record.getVolunteerHourObject());

                if (record.getVolunteerHourId() >= nextRecordId){
                    nextRecordId = record.getVolunteerHourId() + 1;
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
     * Save records.
     */
    private void saveRecords()
    {
        try 
        {
            recordIo.saveCsvFile();
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"Problem saving file/n" + ex.getMessage());
        }

    }

    /**
     * Gets the selected record.
     *
     * @return the selected record
     */
    private VolunteerHour getSelectedRecord()
    {

        VolunteerHour rec = null;

        int irow = this.jTable1.getSelectedRow();

        if (irow > -1){
            int iId = Integer.parseInt(this.jTable1.getModel().getValueAt(irow, 0).toString());

            for (int i = 0; i < this.recordIo.getCvsCount(); i++){
                VolunteerHour record = this.recordIo.getCvsList().get(i);
                if (record.getVolunteerHourId() == iId){
                    rec = record;
                    break;
                }
            }

        }

        return rec;
    }

}
