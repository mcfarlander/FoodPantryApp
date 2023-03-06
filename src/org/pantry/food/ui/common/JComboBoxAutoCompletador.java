/*
  Copyright 2018 Dave Johnson

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
package org.pantry.food.ui.common;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

@SuppressWarnings("rawtypes")
public class JComboBoxAutoCompletador extends PlainDocument implements FocusListener, KeyListener, PropertyChangeListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//
	private JComboBox comboBox;
	private ComboBoxModel model;
	private JTextComponent editor;
	private boolean hidePopupOnFocusLoss;
	
	//
	//
	public JComboBoxAutoCompletador() 
	{
		hidePopupOnFocusLoss = true;
	}
	
	public JComboBoxAutoCompletador(JComboBox jcb) 
	{
		this();
		registraComboBox(jcb);
	}
	
	//
	//
	public void registraComboBox(JComboBox jcb) 
	{
		desregistraComboBox();
		this.comboBox = jcb;
		comboBox.setEditable(true);
		model = comboBox.getModel();
		editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
		editor.setDocument(this);
		
		// Highlight whole text when focus gets lost
		editor.addFocusListener(this);
		
		// Highlight whole text when user hits enter
		editor.addKeyListener(this);
		comboBox.addPropertyChangeListener(this);
		
		// Handle initially selected object
		Object selected = comboBox.getSelectedItem();
		
		if(selected != null)
			editor.setText(selected.toString());
		else
			editor.setText("");
	}
	
	public void desregistraComboBox() 
	{
		if(comboBox != null) 
		{
			comboBox.getEditor().getEditorComponent().removeFocusListener(this);
			comboBox.getEditor().getEditorComponent().removeKeyListener(this);
			comboBox.removePropertyChangeListener(this);
			comboBox.setSelectedItem(null);
			comboBox = null;
		}
	}
	
	//
	//
	@SuppressWarnings("unchecked")
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException 
	{
		// construct the resulting string
		String currentText = getText(0, getLength());
		String beforeOffset = currentText.substring(0, offs);
		String afterOffset = currentText.substring(offs, currentText.length());
		String futureText = beforeOffset + str + afterOffset;
		boolean isNewText = false;
		
		// lookup and select a matching item
		Object item = lookupItem(futureText);
		if (item != null)
			comboBox.setSelectedItem(item);
		else 
		{
			// keep old item selected if there is no match
			//item = comboBox.getSelectedItem();
			// imitate no insert (later on offs will be incremented by str.length(): selection won’t move forward)
			//offs = offs - str.length();
			
			isNewText = true;
			comboBox.addItem(makeObj(futureText));
			item = lookupItem(futureText);
		}
	
		// remove all text and insert the completed string
		super.remove(0, getLength());
		super.insertString(0, item.toString(), a);
		
		// if the user selects an item via mouse the the whole string will be inserted.
		// highlight the entire text if this happens.
		if (item.toString().equals(str) && offs == 0 && !isNewText)
			highlightCompletedText(0);
		else 
		{
			highlightCompletedText(offs + str.length());
			// show popup when the user types
			if(comboBox.isShowing() && comboBox.isFocusOwner())
				comboBox.setPopupVisible(true);
		}
	}
	
	private Object makeObj(final String item)  {
		return new Object() {
			@Override
			public String toString() { return item; } };
	}
	
	private void highlightCompletedText(int start) 
	{
		editor.setCaretPosition(getLength());
		editor.moveCaretPosition(start);
	}
	
	private Object lookupItem(String pattern) 
	{
		Object selectedItem = model.getSelectedItem();
		// only search for a different item if the currently selected does not match
		if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern))
			return selectedItem;
		else 
		{
			// iterate over all items
			for (int i = 0, n = model.getSize(); i < n; i++) 
			{
				Object currentItem = model.getElementAt(i);
				// current item starts with the pattern?
				if(startsWithIgnoreCase(currentItem.toString(), pattern))
					return currentItem;
			}
		}
		// no item starts with the pattern => return null
		return null;
	}
	
	private boolean startsWithIgnoreCase(String str1, String str2) 
	{
		return str1.toUpperCase().startsWith(str2.toUpperCase());
	}
	
	//
	//
	@Override
	public void focusGained(FocusEvent e) { }
	
	@Override
	public void focusLost(FocusEvent e) 
	{
		highlightCompletedText(0);
		// Workaround for Bug 5100422 – Hide Popup on focus loss
		if(hidePopupOnFocusLoss)
			comboBox.setPopupVisible(false);
	}
	
	//
	//
	@Override
	public void keyTyped(KeyEvent e) { }
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			highlightCompletedText(0);
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			comboBox.setSelectedIndex(0);
			editor.setText(comboBox.getSelectedItem().toString());
			highlightCompletedText(0);
		}
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
		if(evt.getPropertyName().equals("model"))
			registraComboBox(comboBox);
	}

}