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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import org.pantry.food.model.Food;
import org.pantry.food.ui.common.JComboBoxAutoCompletador;

public class AddEditFoodRecord extends JDialog {

	/** */
	private static final long serialVersionUID = -2414702577508097538L;

	private JButton btnCancel;
	private JButton btnOk;
	private JPanel jPanel2;
	private JLabel lblComment;
	private JLabel lblCommunity;
	private JLabel lblDate;
	private JLabel lblNonFood;
	private JLabel lblNonTefap;
	private JLabel lblOther1;
	private JLabel lblPantry;
	private JLabel lblPickNSave;
	private JLabel lblSecHarvest;
	private JLabel lblTefap;
	private JTextField txtComments;
	private JTextField txtCommunity;
	private JTextField txtDate;
	private JTextField txtNonFood;
	private JTextField txtNonTefap;
	private JTextField txtOther;
	private JTextField txtOther2;
	private JTextField txtProduce;
	private JTextField txtPantry;
	private JTextField txtPickNSave;
	private JTextField txtSecHarvest;
	private JTextField txtTefap;
	private JTextField txtMilk;
	private JCheckBox chkDonation;
	private JLabel lblDonor;
	private JComboBox<String> txtDonor;
	private JLabel lblAddress;
	private JTextField txtDonorAddress;
	private JLabel lblEmail;
	private JTextField txtDonorEmail;

	private ArrayList<Food> records = new ArrayList<Food>();

	private boolean formLoaded = false;

	/**
	 * Create the frame.
	 */
	/**
	 * @wbp.parser.constructor
	 */
	public AddEditFoodRecord(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Add Edit Food Record");
		setBounds(100, 100, 567, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		initComponents();

	}

	public AddEditFoodRecord(java.awt.Frame parent, boolean modal, ArrayList<Food> previousRecords) {
		super(parent, modal);
		setTitle("Add Edit Food Record");
		setBounds(100, 100, 567, 640);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		initComponents();

		records = previousRecords;
		populateFromRecords();

		formLoaded = true;

	}

	private void initComponents() {
		getContentPane().setLayout(null);

		lblPickNSave = new JLabel();
		lblPickNSave.setBounds(70, 15, 91, 34);
		lblPickNSave.setText("Pick N Save");
		lblPickNSave.setName("lblPickNSave");
		getContentPane().add(lblPickNSave);

		txtPickNSave = new JTextField();
		txtPickNSave.setBounds(191, 18, 65, 28);
		txtPickNSave.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPickNSave.setText("0.0");
		txtPickNSave.setName("txtPickNSave");
		txtPickNSave.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtPickNSaveFocusGained(evt);
			}
		});
		getContentPane().add(txtPickNSave);

		lblCommunity = new JLabel();
		lblCommunity.setText("Community");
		lblCommunity.setName("lblCommunity");
		lblCommunity.setBounds(289, 24, 73, 16);
		getContentPane().add(lblCommunity);

		txtCommunity = new JTextField();
		txtCommunity.setBounds(400, 18, 60, 28);
		txtCommunity.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCommunity.setText("0.0");
		txtCommunity.setName("txtCommunity");
		txtCommunity.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtCommunityFocusGained(evt);
			}
		});
		getContentPane().add(txtCommunity);

		lblNonTefap = new JLabel();
		lblNonTefap.setBounds(70, 71, 72, 16);
		lblNonTefap.setText("Non-TEFAP");
		lblNonTefap.setName("lblNonTefap");
		getContentPane().add(lblNonTefap);

		txtNonTefap = new JTextField();
		txtNonTefap.setBounds(191, 58, 65, 28);
		txtNonTefap.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNonTefap.setText("0.0");
		txtNonTefap.setName("txtNonTefap");
		txtNonTefap.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtNonTefapFocusGained(evt);
			}
		});
		getContentPane().add(txtNonTefap);

		lblTefap = new JLabel();
		lblTefap.setBounds(289, 71, 38, 16);
		lblTefap.setText("TEFAP");
		lblTefap.setName("lblTefap");
		getContentPane().add(lblTefap);

		txtTefap = new JTextField();
		txtTefap.setBounds(400, 58, 60, 28);
		txtTefap.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTefap.setText("0.0");
		txtTefap.setName("txtTefap");
		txtTefap.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtTefapFocusGained(evt);
			}
		});
		getContentPane().add(txtTefap);

		lblSecHarvest = new JLabel();
		lblSecHarvest.setBounds(70, 107, 76, 16);
		lblSecHarvest.setText("2nd Harvest");
		lblSecHarvest.setName("lblSecHarvest");
		getContentPane().add(lblSecHarvest);

		txtSecHarvest = new JTextField();
		txtSecHarvest.setBounds(191, 101, 65, 28);
		txtSecHarvest.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSecHarvest.setText("0.0");
		txtSecHarvest.setName("txtSecHarvest");
		txtSecHarvest.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtSecHarvestFocusGained(evt);
			}
		});
		getContentPane().add(txtSecHarvest);

		JLabel lblSecHarvestProduce = new JLabel();
		lblSecHarvestProduce.setText("2nd Har Produce");
		lblSecHarvestProduce.setName("lblSecHarvestProduce");
		lblSecHarvestProduce.setBounds(289, 107, 119, 16);
		getContentPane().add(lblSecHarvestProduce);

		txtSecHarvestProduce = new JTextField();
		txtSecHarvestProduce.setText("0.0");
		txtSecHarvestProduce.setName("txtSecHarvestProduce");
		txtSecHarvestProduce.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSecHarvestProduce.setBounds(400, 102, 60, 28);
		txtSecHarvestProduce.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtSecHarvestProduceFocusGained(evt);
			}
		});
		getContentPane().add(txtSecHarvestProduce);

		lblPantry = new JLabel();
		lblPantry.setBounds(70, 135, 65, 35);
		lblPantry.setText("Pantry");
		lblPantry.setName("lblPantry");
		getContentPane().add(lblPantry);

		txtPantry = new JTextField();
		txtPantry.setBounds(191, 137, 70, 22);
		txtPantry.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPantry.setText("0.0");
		txtPantry.setName("txtPantry");
		txtPantry.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtPantryFocusGained(evt);
			}
		});
		getContentPane().add(txtPantry);

		lblOther1 = new JLabel();
		lblOther1.setBounds(70, 176, 108, 16);
		lblOther1.setText("Pantry Non-Food");
		lblOther1.setName("lblOther1");
		getContentPane().add(lblOther1);

		txtOther = new JTextField();
		txtOther.setBounds(191, 170, 65, 28);
		txtOther.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOther.setText("0.0");
		txtOther.setName("txtOther");
		txtOther.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtOtherFocusGained(evt);
			}
		});
		getContentPane().add(txtOther);

		lblNonFood = new JLabel();
		lblNonFood.setBounds(289, 176, 65, 16);
		lblNonFood.setText("Non-Food");
		lblNonFood.setName("lblNonFood");
		getContentPane().add(lblNonFood);

		txtNonFood = new JTextField();
		txtNonFood.setBounds(400, 170, 60, 28);
		txtNonFood.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNonFood.setText("0.0");
		txtNonFood.setName("txtNonFood");
		txtNonFood.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtNonFoodFocusGained(evt);
			}
		});
		getContentPane().add(txtNonFood);

		JLabel lblMilk = new JLabel();
		lblMilk.setText("Milk");
		lblMilk.setName("lblMilk");
		lblMilk.setBounds(70, 212, 65, 16);
		getContentPane().add(lblMilk);

		txtMilk = new JTextField();
		txtMilk.setText("0.0");
		txtMilk.setName("txtMilk");
		txtMilk.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMilk.setBounds(191, 207, 65, 28);
		txtMilk.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtMilkFocusGained(evt);
			}
		});
		getContentPane().add(txtMilk);

		JLabel lblOther2 = new JLabel("Pantry Produce");
		lblOther2.setBounds(70, 253, 108, 16);
		getContentPane().add(lblOther2);

		txtOther2 = new JTextField();
		txtOther2.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOther2.setName("txtOther2");
		txtOther2.setText("0.0");
		txtOther2.setBounds(191, 247, 65, 28);
		txtOther2.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtOther2FocusGained(evt);
			}
		});
		getContentPane().add(txtOther2);

		JLabel lblOther_1 = new JLabel("Produce");
		lblOther_1.setBounds(289, 253, 61, 16);
		getContentPane().add(lblOther_1);

		txtProduce = new JTextField();
		txtProduce.setHorizontalAlignment(SwingConstants.RIGHT);
		txtProduce.setName("txtOther3");
		txtProduce.setBounds(400, 247, 65, 28);
		txtProduce.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtProduceFocusGained(evt);
			}
		});
		getContentPane().add(txtProduce);

		lblDate = new JLabel();
		lblDate.setBounds(110, 308, 51, 16);
		lblDate.setText("Date");
		lblDate.setName("lblDate");
		getContentPane().add(lblDate);

		txtDate = new JTextField();
		txtDate.setBounds(211, 302, 185, 28);
		txtDate.setName("txtDate");
		txtDate.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtDateFocusGained(evt);
			}
		});
		getContentPane().add(txtDate);

		lblComment = new JLabel();
		lblComment.setBounds(107, 349, 68, 16);
		lblComment.setText("Comments");
		lblComment.setName("lblComment");
		getContentPane().add(lblComment);

		txtComments = new JTextField();
		txtComments.setBounds(211, 343, 185, 28);
		txtComments.setName("txtComments");
		txtComments.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtCommentsFocusGained(evt);
			}
		});
		getContentPane().add(txtComments);

		chkDonation = new JCheckBox("Check if donation");
		chkDonation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showDonorInfo();
			}
		});
		chkDonation.setBounds(50, 390, 162, 23);
		getContentPane().add(chkDonation);

		lblDonor = new JLabel("Donor");
		lblDonor.setBounds(107, 425, 61, 16);
		getContentPane().add(lblDonor);

		txtDonor = new JComboBox<String>();
		txtDonor.setEditable(true);
		txtDonor.setBounds(211, 419, 184, 28);
		txtDonor.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				txtDonorFocusGained(evt);
			}
		});
		txtDonor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getContactInfo();
			}
		});

		// get the combo boxes editor component
		JTextComponent editor = (JTextComponent) txtDonor.getEditor().getEditorComponent();
		// change the editor's document
		editor.setDocument(new JComboBoxAutoCompletador(txtDonor));
		getContentPane().add(txtDonor);

		lblAddress = new JLabel("Address");
		lblAddress.setBounds(110, 465, 61, 16);
		getContentPane().add(lblAddress);

		txtDonorAddress = new JTextField();
		txtDonorAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtDonorAddress.selectAll();
			}
		});
		txtDonorAddress.setBounds(211, 459, 185, 28);
		getContentPane().add(txtDonorAddress);
		txtDonorAddress.setColumns(10);

		lblEmail = new JLabel("Email");
		lblEmail.setBounds(110, 505, 61, 16);
		getContentPane().add(lblEmail);

		txtDonorEmail = new JTextField();
		txtDonorEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtDonorEmail.selectAll();
			}
		});
		txtDonorEmail.setBounds(211, 499, 185, 28);
		getContentPane().add(txtDonorEmail);
		txtDonorEmail.setColumns(10);

		jPanel2 = new JPanel();
		jPanel2.setBounds(6, 549, 551, 60);
		jPanel2.setName("jPanel2");
		jPanel2.setLayout(null);

		btnOk = new JButton();
		btnOk.setBounds(330, 6, 105, 49);
		btnOk.setIcon(
				new ImageIcon(AddEditFoodRecord.class.getResource("/org/pantry/food/resources/images/accept.png")));
		btnOk.setText("OK");
		btnOk.setName("btnOk");
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOkActionPerformed(evt);
			}
		});
		jPanel2.add(btnOk);

		btnCancel = new JButton();
		btnCancel.setBounds(445, 6, 100, 49);
		btnCancel.setIcon(
				new ImageIcon(AddEditFoodRecord.class.getResource("/org/pantry/food/resources/images/cancel.png")));
		btnCancel.setText("Cancel");
		btnCancel.setName("btnCancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		jPanel2.add(btnCancel);

		getContentPane().add(jPanel2);

	}

	// @SuppressWarnings("unchecked")
	private void populateFromRecords() {
		String nothingStr = "";
		// this.txtDonor.addItem(makeObj(nothingStr));
		txtDonor.addItem(nothingStr);

		for (int i = 0; i < records.size(); i++) {
			String str = records.get(i).getDonorName();

			if (str != null && str.length() > 0) {
				// this.txtDonor.addItem(makeObj(str));
				txtDonor.addItem(str);
			}
		}

	}

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
		this.OkCancel = false;
		this.record = null;
		this.dispose();
	}

	private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
		if (validateForm()) {
			this.OkCancel = true;
			generateRecord();
			this.dispose();
		}
	}

	private void txtPickNSaveFocusGained(java.awt.event.FocusEvent evt) {
		txtPickNSave.selectAll();
	}

	private void txtCommunityFocusGained(java.awt.event.FocusEvent evt) {
		txtCommunity.selectAll();
	}

	private void txtNonTefapFocusGained(java.awt.event.FocusEvent evt) {
		txtNonTefap.selectAll();
	}

	private void txtTefapFocusGained(java.awt.event.FocusEvent evt) {
		txtTefap.selectAll();
	}

	private void txtSecHarvestFocusGained(java.awt.event.FocusEvent evt) {
		txtSecHarvest.selectAll();
	}

	private void txtSecHarvestProduceFocusGained(FocusEvent evt) {
		txtSecHarvestProduce.selectAll();
	}

	private void txtPantryFocusGained(java.awt.event.FocusEvent evt) {
		txtPantry.selectAll();
	}

	private void txtCommentsFocusGained(java.awt.event.FocusEvent evt) {
		txtComments.selectAll();
	}

	private void txtNonFoodFocusGained(java.awt.event.FocusEvent evt) {
		txtNonFood.selectAll();
	}

	private void txtOtherFocusGained(java.awt.event.FocusEvent evt) {
		txtOther.selectAll();
	}

	private void txtOther2FocusGained(java.awt.event.FocusEvent evt) {
		txtOther2.selectAll();
	}

	private void txtProduceFocusGained(java.awt.event.FocusEvent evt) {
		txtProduce.selectAll();
	}

	private void txtMilkFocusGained(java.awt.event.FocusEvent evt) {
		txtMilk.selectAll();
	}

	private void txtDateFocusGained(java.awt.event.FocusEvent evt) {
		txtDate.selectAll();
	}

	private void txtDonorFocusGained(FocusEvent evt) {

	}

	private void getContactInfo() {
		// based on the selection of the drop down, show the address and
		// txtDonor (combo box)
		// txtDonorAddress (text box)
		// txtDonorEmail (text box)

		if (formLoaded) {

			int index = txtDonor.getSelectedIndex();
			if (index > 0) {
				Food record = records.get(index - 1);
				if (record != null) {
					txtDonorAddress.setText(record.getDonorAddress());
					txtDonorEmail.setText(record.getDonorEmail());
				}
			}
		}

	}

	private boolean OkCancel = false;

	public boolean getOkCancel() {
		return this.OkCancel;
	}

	private Food record = new Food();

	public Food getNewRecord() {
		return this.record;
	}

	public void setNewRecord(Food rec) {
		this.record = rec;
		setControlsFromPrevious();
	}

	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private JTextField txtSecHarvestProduce;

	private void setControlsFromPrevious() {

		this.txtPickNSave.setText("" + this.record.getPickNSave());
		this.txtCommunity.setText("" + this.record.getCommunity());
		this.txtNonTefap.setText("" + this.record.getNonTefap());
		this.txtTefap.setText("" + this.record.getTefap());
		this.txtSecHarvest.setText("" + this.record.getSecondHarvest());
		this.txtSecHarvestProduce.setText("" + this.record.getSecondHarvestProduce());
		this.txtPantry.setText("" + this.record.getPantry());
		this.txtOther.setText("" + this.record.getPantryNonFood());
		this.txtComments.setText(this.record.getComment());
		this.txtNonFood.setText("" + this.record.getNonFood());
		this.txtMilk.setText("" + this.record.getMilk());
		this.txtDate.setText(this.record.getEntryDate());
		this.txtOther2.setText("" + this.record.getPantryProduce());
		this.txtProduce.setText("" + this.record.getProduce());
		this.chkDonation.setSelected(this.record.isDonation());
		this.txtDonor.setSelectedItem(this.record.getDonorName());
		this.txtDonorAddress.setText(this.record.getDonorAddress());
		this.txtDonorEmail.setText(this.record.getDonorEmail());

		showDonorInfo();

	}

	private void generateRecord() {

		this.record.setPickNSave(Double.parseDouble(txtPickNSave.getText()));
		this.record.setCommunity(Double.parseDouble(this.txtCommunity.getText()));
		this.record.setNonTefap(Double.parseDouble(this.txtNonTefap.getText()));
		this.record.setTefap(Double.parseDouble(this.txtTefap.getText()));
		this.record.setSecondHarvest(Double.parseDouble(this.txtSecHarvest.getText()));
		this.record.setSecondHarvestProduce(Double.parseDouble(this.txtSecHarvestProduce.getText()));
		this.record.setPantry(Double.parseDouble(this.txtPantry.getText()));
		this.record.setPantryNonFood(Double.parseDouble(this.txtOther.getText()));
		this.record.setComment(this.txtComments.getText());
		this.record.setNonFood(Double.parseDouble(this.txtNonFood.getText()));
		this.record.setMilk(Double.parseDouble(txtMilk.getText()));
		this.record.setPantryProduce(Double.parseDouble(this.txtOther2.getText()));
		this.record.setProduce(Double.parseDouble(this.txtProduce.getText()));

		this.record.setEntryDate(this.txtDate.getText());

		this.record.setDonation(this.chkDonation.isSelected());

		if (this.chkDonation.isSelected()) {
			// this was a donation, capture the contact information
			this.record.setDonorName(this.txtDonor.getSelectedItem().toString());
			this.record.setDonorAddress(this.txtDonorAddress.getText());
			this.record.setDonorEmail(this.txtDonorEmail.getText());
		} else {
			// wasn't a donation, remove any donor contact information
			this.record.setDonorName("");
			this.record.setDonorAddress("");
			this.record.setDonorEmail("");
		}

	}

	private void showDonorInfo() {
		lblDonor.setVisible(this.chkDonation.isSelected());
		txtDonor.setVisible(this.chkDonation.isSelected());
		lblAddress.setVisible(this.chkDonation.isSelected());
		txtDonorAddress.setVisible(this.chkDonation.isSelected());
		lblEmail.setVisible(this.chkDonation.isSelected());
		txtDonorEmail.setVisible(this.chkDonation.isSelected());

	}

	private boolean validateForm() {

		double test = 0.0;

		try {
			test = Double.parseDouble(this.txtPickNSave.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Pick N Save.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Pick N Save.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtCommunity.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for the community.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for the community.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtNonTefap.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Non-Tefap.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Non-Tefap.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtTefap.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for TEFAP.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for TEFAP.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtSecHarvest.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Second Harvest.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Second Harvest.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtSecHarvestProduce.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Second Harvest Produce.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for Second Harvest Produce.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtPantry.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for pantry purchases.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for pantry purchases.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtOther.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for other purchases.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for other purchases.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtOther.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for other kinds of purchases.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for other kinds of purchases.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtProduce.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for produce purchases.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for produce purchases.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtNonFood.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for non-food items.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for non-food items.");
			return false;
		}

		try {
			test = Double.parseDouble(this.txtMilk.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for milk items.");
			return false;
		}

		if (test < 0.0) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for milk items.");
			return false;
		}

		if (this.txtDate.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please select an entry date.");
			return false;
		}

		try {
			@SuppressWarnings("unused")
			Date test1 = dateFormat.parse(this.txtDate.getText());
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(this, "Please select an entry date.");
			return false;
		}

		if (this.chkDonation.isSelected()) {
			if (this.txtDonor.getSelectedItem().toString().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter the donor's name.");
				return false;
			}

		} else {
			txtDonor.setSelectedItem("");
			txtDonorAddress.setText("");
			txtDonorEmail.setText("");
		}

		return true;
	}
}
