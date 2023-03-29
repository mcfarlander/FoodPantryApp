package org.pantry.food;

import java.util.ArrayList;
import java.util.List;

import org.pantry.food.dao.CustomersDao;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.dao.VolunteersDao;

/**
 * Poor-man's singleton dependency injection context
 */
public class ApplicationContext {
	private static ApplicationContext INSTANCE = new ApplicationContext();

	private List<ApplicationCloseListener> applicationCloseListeners = new ArrayList<>();

	private CustomersDao customersDao;
	private VisitsDao visitsDao;
	private FoodsDao foodsDao;
	private VolunteersDao volunteersDao;

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

	public static VisitsDao getVisitsDao() {
		if (null == INSTANCE.visitsDao) {
			INSTANCE.visitsDao = new VisitsDao();
		}
		return INSTANCE.visitsDao;
	}

	public static FoodsDao getFoodsDao() {
		if (null == INSTANCE.foodsDao) {
			INSTANCE.foodsDao = new FoodsDao();
		}
		return INSTANCE.foodsDao;
	}

	public static VolunteersDao getVolunteersDao() {
		if (null == INSTANCE.volunteersDao) {
			INSTANCE.volunteersDao = new VolunteersDao();
		}
		return INSTANCE.volunteersDao;
	}

}
