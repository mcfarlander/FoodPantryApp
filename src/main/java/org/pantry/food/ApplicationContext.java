package org.pantry.food;

import org.pantry.food.dao.CustomersDao;

/**
 * Poor-man's singleton dependency injection context
 */
public class ApplicationContext {
	private static ApplicationContext INSTANCE = new ApplicationContext();

	private CustomersDao customersDao;

	public static CustomersDao getCustomersDao() {
		if (null == INSTANCE.customersDao) {
			INSTANCE.customersDao = new CustomersDao();
		}
		return INSTANCE.customersDao;
	}
}
