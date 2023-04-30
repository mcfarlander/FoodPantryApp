package org.pantry.food.dao;

/**
 * Listeners interested in observing changes to a file can implement this
 * interface
 */
public interface FileChangedListener {
	void onFileChanged(String filename);
}
