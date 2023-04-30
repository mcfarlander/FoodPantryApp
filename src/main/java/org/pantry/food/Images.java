package org.pantry.food;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Images {
	private static final Images INSTANCE = new Images();
	// Map<image key, icon>
	private final Map<String, Image> icons = new HashMap<>();

	private Images() {
	}

	public static ImageView getImageView(String key) {
		return new ImageView(getIcon(key));
	}

	public static Image getIcon(String key) {
		if (!INSTANCE.icons.containsKey(key)) {
			URL url = Images.class.getResource("/icons/" + key);
			if (null != url) {
				Image icon = new Image("file://" + url.getPath(), true);
				INSTANCE.icons.put(key, icon);
			}
		}
		return INSTANCE.icons.get(key);
	}
}
