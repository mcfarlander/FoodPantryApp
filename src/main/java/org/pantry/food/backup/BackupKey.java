package org.pantry.food.backup;

public enum BackupKey {
	CUSTOMERS("customers"), VISITS("visits"), DONATIONS("donations"), LEGACY_VOLUNTEERS("legacyVolunteers"),
	VOLUNTEERS("volunteers"), VOLUNTEER_EVENTS("volunteerEvents");

	String key;

	private BackupKey(String key) {
		this.key = key;
	}

	public static BackupKey fromString(String str) {
		for (BackupKey key : values()) {
			if (key.key.equalsIgnoreCase(str)) {
				return key;
			}
		}

		return null;
	}
}
