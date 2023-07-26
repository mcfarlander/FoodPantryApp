package org.pantry.food.model;

/**
 * HouseholdMakeup is a brief description of a household in terms of the number
 * of people in each age category.
 */
public class HouseholdMakeup {
	
	/** The age a person in the household is considered an adult. */
	public static int AGE_ADULT = 18;
	/** The age a person in the household is considered a senior. */
	public static int AGE_SENIOR = 59;
	
	private int numberChildren = 0;
	private int numberAdults = 0;
	private int numberSeniors = 0;
	
	public int getNumberChildren() {
		return this.numberChildren;
	}
	
	public void setNumberChildren(int numberChildren) {
		this.numberChildren = numberChildren;
	}
	
	public void addChild() {
		this.numberChildren = this.numberChildren++;
	}
	
	public int getNumberAdults() {
		return this.numberAdults;
	}
	
	public void setNumberAdults(int numberAdults) {
		this.numberAdults = numberAdults;
	}
	
	public void addAdult() {
		this.numberAdults = this.numberAdults++;
	}
	
	public int getNumberSeniors() {
		return this.numberSeniors;
	}
	
	public void setNumberSeniors(int numberSeniors) {
		this.numberSeniors = numberSeniors;
	}
	
	public void addSenior() {
		this.numberSeniors = this.numberSeniors++;
	}
	

}
