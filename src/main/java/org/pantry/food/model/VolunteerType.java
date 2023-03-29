package org.pantry.food.model;

public enum VolunteerType {
	REGULAR, STUDENT, SPECIAL, OTHER, UNKNOWN;

	public static VolunteerType fromString(String str) {
		return VolunteerType.valueOf(str.toUpperCase());
	}
}
