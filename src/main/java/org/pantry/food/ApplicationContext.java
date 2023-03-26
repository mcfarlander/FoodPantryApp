package org.pantry.food;

import java.util.ArrayList;
import java.util.List;

import org.pantry.food.dao.CustomersDao;

/**
 * Poor-man's singleton dependency injection context
 */
public class ApplicationContext {
	private static ApplicationContext INSTANCE = new ApplicationContext();

	private List<ApplicationCloseListener> applicationCloseListeners = new ArrayList<>();

	private CustomersDao customersDao;

	public static void addApplicationCloseListener(ApplicationCloseListener listener) {
		INSTANCE.applicationCloseListeners.add(listener);
	}

	// Notifies registered listeners that application is closing
	public static void applicationClosing() {
		for (ApplicationCloseListener listener : INSTANCE.applicationCloseListeners) {
			if (null != listener) {
				listener.onClosing();
			}
		}
	}

	public static CustomersDao getCustomersDao() {
		if (null == INSTANCE.customersDao) {
			INSTANCE.customersDao = new CustomersDao();
		}
		return INSTANCE.customersDao;
	}

}
