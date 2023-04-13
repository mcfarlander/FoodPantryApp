package org.pantry.food;

import java.util.ArrayList;
import java.util.List;

import org.pantry.food.dao.CustomersDao;
import org.pantry.food.dao.FoodsDao;
import org.pantry.food.dao.VisitsDao;
import org.pantry.food.dao.VolunteerEventsDao;
import org.pantry.food.dao.VolunteerHourDao;
import org.pantry.food.dao.VolunteersDao;

/**
 * Poor-man's singleton dependency injection context
 */
public class ApplicationContext {
	private static ApplicationContext INSTANCE = new ApplicationContext();

	private List<ApplicationCloseListener> applicationCloseListeners = new ArrayList<>();
	private List<SettingsChangedListener> settingsChangedListeners = new ArrayList<>();

	private Resources resources;
	private CustomersDao customersDao;
	private VisitsDao visitsDao;
	private FoodsDao foodsDao;
	private VolunteersDao volunteersDao;
	private VolunteerEventsDao volunteerEventsDao;
	private VolunteerHourDao volunteerHourDao;

	private String copiedEventName = "";

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

	public static void addSettingsChangedListener(SettingsChangedListener listener) {
		INSTANCE.settingsChangedListeners.add(listener);
	}

	// Notifies registered listeners that settings have changed
	public static void settingsChanged() {
		for (SettingsChangedListener listener : INSTANCE.settingsChangedListeners) {
			if (null != listener) {
				listener.onChanged();
			}
		}
	}

	public static Resources getResources() {
		if (null == INSTANCE.resources) {
			INSTANCE.resources = new Resources();
		}
		return INSTANCE.resources;
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

	public static VolunteerEventsDao getVolunteerEventsDao() {
		if (null == INSTANCE.volunteerEventsDao) {
			INSTANCE.volunteerEventsDao = new VolunteerEventsDao();
		}
		return INSTANCE.volunteerEventsDao;
	}

	public static VolunteerHourDao getVolunteerHourDao() {
		if (null == INSTANCE.volunteerHourDao) {
			INSTANCE.volunteerHourDao = new VolunteerHourDao();
		}
		return INSTANCE.volunteerHourDao;
	}

	/**
	 * "Copies" an event name for later "pasting" across multiple Add/Edit Volunteer
	 * Event dialogs
	 * 
	 * @param name
	 */
	public static void setCopiedEventName(String name) {
		INSTANCE.copiedEventName = name;
	}

	/**
	 * @return the previously-copied event name, or empty string if none has been
	 *         copied
	 */
	public static String getCopiedEventName() {
		return INSTANCE.copiedEventName;
	}

}
