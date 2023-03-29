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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.pantry.food.dao.FoodsDao;
import org.pantry.food.model.Food;
import org.pantry.food.ui.common.FormState;
import org.pantry.food.ui.dialog.AddEditFoodRecord;
import org.pantry.food.util.DateUtil;

/**
 * The Class FrameFoodRecords.
 *
 * @author mcfarland_davej
 */

public class FrameFoodRecords extends javax.swing.JInternalFrame {

	/** The Constant log. */
	private final static Logger log = Logger.getLogger(FrameFoodRecords.class.getName());

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

	/** The record io. */
	private FoodsDao recordIo = new FoodsDao();

	/** The next record id. */
	private int nextRecordId = 0;

	/** The donor records. */
	private ArrayList<Food> donorRecords = new ArrayList<Food>();

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new form FrameFoodRecords.
	 */
	public FrameFoodRecords() {
		initComponents();
		loadRecords();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		btnAdd = new javax.swing.JButton();
		btnEdit = new javax.swing.JButton();
		btnDelete = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();

		jTable1 = new JTable();
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					btnEditActionPerformed(null);
				}
			}
		});

		setClosable(true);
		setTitle("Food Records");
		setFrameIcon(
				new ImageIcon(FrameFoodRecords.class.getResource("/org/pantry/food/resources/images/tag_blue.png")));
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

		btnAdd.setIcon(new ImageIcon(
				FrameFoodRecords.class.getResource("/org/pantry/food/resources/images/tag_blue_add.png")));
		btnAdd.setText("Add Record");
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

		btnEdit.setIcon(new ImageIcon(
				FrameFoodRecords.class.getResource("/org/pantry/food/resources/images/tag_blue_edit.png")));
		btnEdit.setText("Edit Record");
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

		btnDelete.setIcon(new ImageIcon(
				FrameFoodRecords.class.getResource("/org/pantry/food/resources/images/tag_blue_delete.png")));
		btnDelete.setText("Delete Record");
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

		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Record", "Entry Date", "Pick N Save", "Community", "Non-TEFAP CAC", "TEFAP", "2nd Harvest",
				"2nd Harvest Produce", "Pantry Purchase", "Pantry Non-Food", "Comment", "Non-Food", "Milk",
				"Pantry Produce", "Produce", "Donation?", "Donor", "Address", "Email"

		}) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.Integer.class, java.lang.String.class, java.lang.Double.class,
					java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
					java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class,
					java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
					java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false };

			@SuppressWarnings("rawtypes")
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.setName("jTable1");
		jTable1.setShowGrid(true);

		jScrollPane1.setViewportView(jTable1);

		getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

		pack();
	}

	/**
	 * Btn add action performed.
	 *
	 * @param evt the evt
	 */
	private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {

		AddEditFoodRecord dial = new AddEditFoodRecord(null, true, donorRecords);
		Food record = new Food();
		record.setFoodId(this.nextRecordId);
		record.setEntryDate(DateUtil.getCurrentDateString());
		dial.setNewRecord(record);
		dial.setLocationRelativeTo(this);

		dial.setVisible(true);

		if (dial.getOkCancel()) {

			recordIo.add(dial.getNewRecord());

			DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
			model.addRow(dial.getNewRecord().getFoodRecordObject());

			saveRecords();
			nextRecordId++;
			loadRecords();
		}

	}

	/**
	 * Btn edit action performed.
	 *
	 * @param evt the evt
	 */
	private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {

		Food record = getSelectedRecord();

		if (record != null) {
			AddEditFoodRecord dial = new AddEditFoodRecord(null, true, donorRecords);
			dial.setNewRecord(record);
			dial.setLocationRelativeTo(this);
			dial.setVisible(true);

			if (dial.getOkCancel()) {
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
	private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {

		Food record = getSelectedRecord();

		if (record != null) {

			int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected record?",
					"Confirm", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				recordIo.deactivate(record);

				saveRecords();
				loadRecords();
			}

		} else {
			JOptionPane.showMessageDialog(this, "Please select a food record to delete.");
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

	/**
	 * Load records.
	 */
	private void loadRecords() {

		try {
			// clear out donors and add anonymous to the top of the list
			donorRecords.clear();

			Food anon = new Food();
			anon.setDonorName("Anonymous");
			donorRecords.add(anon);

			recordIo.read();
			DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
			model.setRowCount(0);

			for (Food record : recordIo.getAll()) {
				model.addRow(record.getFoodRecordObject());

				if (record.getFoodId() >= nextRecordId) {
					nextRecordId = record.getFoodId() + 1;
				}

				if (record.isDonation() && record.getDonorName() != null && record.getDonorName().length() > 0) {
					boolean found = false;
					for (int j = 0; j < donorRecords.size(); j++) {
						Food donorRecord = donorRecords.get(j);

						if (donorRecord.getDonorName().equals(record.getDonorName())) {
							found = true;
							break;
						}
					}

					if (!found)
						donorRecords.add(record);
				}
			}

		} catch (FileNotFoundException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem opening file/n" + ex.getMessage());
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem opening file/n" + ex.getMessage());
		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem opening file/n" + ex.getMessage());
		}

	}

	/**
	 * Save records.
	 */
	private void saveRecords() {
		try {
			recordIo.persist();
		} catch (IOException ex) {
			log.log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Problem saving file/n" + ex.getMessage());
		}
	}

	/**
	 * Gets the selected record.
	 *
	 * @return the selected record
	 */
	private Food getSelectedRecord() {

		Food rec = null;

		int irow = this.jTable1.getSelectedRow();

		if (irow > -1) {
			int iId = Integer.parseInt(this.jTable1.getModel().getValueAt(irow, 0).toString());

			for (Food record : recordIo.getAll()) {
				if (record.getFoodId() == iId) {
					rec = record;
					break;
				}
			}

		}

		return rec;
	}

}
