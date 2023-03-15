package org.pantry.food.actions;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.MenuItem;

public class MenuActions {
	private static final MenuActions INSTANCE = new MenuActions();
	private final Map<String, MenuItem> actions = new HashMap<>();

	/**
	 * Adds or replaces a menu action with the ID returnd by item.getId()
	 * @param item menu action to add or update
	 */
	public static void add(MenuItem item) {
		INSTANCE.actions.put(item.getId(), item);
	}
	
	public static MenuItem get(String id) {
		return INSTANCE.actions.get(id);
	}
}
