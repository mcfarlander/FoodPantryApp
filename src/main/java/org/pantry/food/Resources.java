package org.pantry.food;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class Resources {
	
	private static Map<String, ResourceMap> resourceMaps = new HashMap<>();
	
	public static String getString(String name) {
		return get(JfxApplication.class).getString(name);
	}
	public static String getString(Class<?> clazz, String name) {
		return get(clazz).getString(name);
	}
	public static ImageIcon getImageIcon(Class<?> clazz, String name) {
		return get(clazz).getImageIcon(name);
	}

	private static ResourceMap get(Class<?> clazz) {
		ResourceMap map = resourceMaps.get(clazz.getName());
		if (null == map) {
			map = Application.getInstance(org.pantry.food.FoodPantryApp.class).getContext().getResourceMap(clazz);
		}
		return map;
	}
}