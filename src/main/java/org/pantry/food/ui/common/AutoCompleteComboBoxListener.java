package org.pantry.food.ui.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

	private ComboBox<T> comboBox;
	private ObservableList<T> items;
	private boolean moveCaretToPos = false;
	private int caretPos;

	public AutoCompleteComboBoxListener(final ComboBox<T> comboBox) {
		this.comboBox = comboBox;
		items = comboBox.getItems();

		this.comboBox.setEditable(true);
		this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				comboBox.hide();
			}
		});
		this.comboBox.setOnKeyReleased(this);
	}

	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
			if (event.getCode() == KeyCode.DOWN && !comboBox.isShowing()) {
				comboBox.show();
			}
			caretPos = -1;
			moveCaret(comboBox.getEditor().getText().length());
			return;
		} else if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
			moveCaretToPos = true;
			caretPos = comboBox.getEditor().getCaretPosition();
		} else if (event.getCode() == KeyCode.ESCAPE) {
			comboBox.hide();
			return;
		} else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.isControlDown()
				|| event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
				|| event.getCode() == KeyCode.TAB) {
			return;
		}

		String originalSearchTerm = comboBox.getEditor().getText();
		String searchTerm = originalSearchTerm.toLowerCase();
		ObservableList<T> matchingItems = FXCollections.observableArrayList();
		for (T item : items) {
			if (item.toString().toLowerCase().startsWith(searchTerm)) {
				matchingItems.add(item);
			}
		}

		comboBox.setItems(matchingItems);
		comboBox.getEditor().setText(originalSearchTerm);
		if (!moveCaretToPos) {
			caretPos = -1;
		}
		moveCaret(originalSearchTerm.length());
		if (!matchingItems.isEmpty()) {
			ComboBoxListViewSkin skin = (ComboBoxListViewSkin) comboBox.getSkin();
			skin.getPopupContent().autosize();
			comboBox.show();
		}
	}

	private void moveCaret(int textLength) {
		if (caretPos == -1) {
			comboBox.getEditor().positionCaret(textLength);
		} else {
			comboBox.getEditor().positionCaret(caretPos);
		}
		moveCaretToPos = false;
	}

}
