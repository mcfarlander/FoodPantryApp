/*
  Copyright 2011 Dave Johnson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.pantry.food.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A POJO that describes a food record (a donation or other addition).
 *
 * @author Dave Johnson
 */
public class Food {

	private SimpleStringProperty idProperty = new SimpleStringProperty();
	private SimpleStringProperty entryDateProperty = new SimpleStringProperty();
	private SimpleStringProperty pickNSaveProperty = new SimpleStringProperty("0");
	private SimpleStringProperty communityProperty = new SimpleStringProperty("0");
	private SimpleStringProperty nonTefapProperty = new SimpleStringProperty("0");
	private SimpleStringProperty tefapProperty = new SimpleStringProperty("0");
	private SimpleStringProperty secondHarvestProperty = new SimpleStringProperty("0");
	private SimpleStringProperty secondHarvestProduceProperty = new SimpleStringProperty("0");
	private SimpleStringProperty pantryProperty = new SimpleStringProperty("0");
	private SimpleStringProperty pantryNonFoodProperty = new SimpleStringProperty("0");
	private SimpleStringProperty nonFoodProperty = new SimpleStringProperty("0");
	private SimpleStringProperty milkProperty = new SimpleStringProperty("0");
	private SimpleStringProperty pantryProduceProperty = new SimpleStringProperty("0");
	private SimpleStringProperty produceProperty = new SimpleStringProperty("0");
	private SimpleStringProperty commentProperty = new SimpleStringProperty("");
	private SimpleBooleanProperty donationProperty = new SimpleBooleanProperty();
	private SimpleStringProperty donorNameProperty = new SimpleStringProperty();
	private SimpleStringProperty donorAddressProperty = new SimpleStringProperty();
	private SimpleStringProperty donorEmailProperty = new SimpleStringProperty();

	public Food() {
	}

	public Food(Food other) {
		if (null == other) {
			return;
		}

		setFoodId(other.getFoodId());
		setEntryDate(other.getEntryDate());
		setPickNSave(other.getPickNSave());
		setCommunity(other.getCommunity());
		setNonTefap(other.getNonTefap());
		setTefap(other.getTefap());
		setSecondHarvest(other.getSecondHarvest());
		setSecondHarvestProduce(other.getSecondHarvestProduce());
		setPantry(other.getPantry());
		setPantryNonFood(other.getPantryNonFood());
		setNonFood(other.getNonFood());
		setMilk(other.getMilk());
		setPantryProduce(other.getPantryProduce());
		setProduce(other.getProduce());
		setComment(other.getComment());
		setDonation(other.isDonation());
		setDonorName(other.getDonorName());
		setDonorAddress(other.getDonorAddress());
		setDonorEmail(other.getDonorEmail());
	}

	public int getFoodId() {
		String value = idProperty.get();
		if (null == value) {
			return -1;
		}
		return Integer.valueOf(value);
	}

	public void setFoodId(int recordId) {
		idProperty.set(String.valueOf(recordId));
	}

	/**
	 * Gets the entry date.
	 *
	 * @return the entry date
	 */
	public String getEntryDate() {
		return entryDateProperty.get();
	}

	/**
	 * Sets the entry date.
	 *
	 * @param entryDate the new entry date
	 */
	public void setEntryDate(String entryDate) {
		entryDateProperty.set(entryDate);
	}

	/**
	 * Gets the pick N save.
	 *
	 * @return the pick N save
	 */
	public double getPickNSave() {
		String value = pickNSaveProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the pick N save.
	 *
	 * @param pickNSave the new pick N save
	 */
	public void setPickNSave(double pickNSave) {
		pickNSaveProperty.set(String.valueOf(pickNSave));
	}

	/**
	 * Gets the community.
	 *
	 * @return the community
	 */
	public double getCommunity() {
		String value = communityProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the community.
	 *
	 * @param community the new community
	 */
	public void setCommunity(double community) {
		communityProperty.set(String.valueOf(community));
	}

	/**
	 * Gets the non tefap.
	 *
	 * @return the non tefap
	 */
	public double getNonTefap() {
		String value = nonTefapProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the non tefap.
	 *
	 * @param nonTefap the new non tefap
	 */
	public void setNonTefap(double nonTefap) {
		nonTefapProperty.set(String.valueOf(nonTefap));
	}

	/**
	 * Gets the tefap.
	 *
	 * @return the tefap
	 */
	public double getTefap() {
		String value = tefapProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the tefap.
	 *
	 * @param tefap the new tefap
	 */
	public void setTefap(double tefap) {
		tefapProperty.set(String.valueOf(tefap));
	}

	/**
	 * Gets the sec harvest.
	 *
	 * @return the sec harvest
	 */
	public double getSecondHarvest() {
		String value = secondHarvestProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the sec harvest.
	 *
	 * @param secHarvest the new sec harvest
	 */
	public void setSecondHarvest(double secHarvest) {
		secondHarvestProperty.set(String.valueOf(secHarvest));
	}

	/**
	 * Gets the pantry.
	 *
	 * @return the pantry
	 */
	public double getPantry() {
		String value = pantryProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the pantry.
	 *
	 * @param pantry the new pantry
	 */
	public void setPantry(double pantry) {
		pantryProperty.set(String.valueOf(pantry));
	}

	public double getPantryProduce() {
		String value = pantryProduceProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	public void setPantryProduce(double pantryProduce) {
		pantryProduceProperty.set(String.valueOf(pantryProduce));
	}

	/**
	 * Gets the produce.
	 *
	 * @return the produce
	 */
	public double getProduce() {
		String value = produceProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the produce.
	 *
	 * @param produce the new produce
	 */
	public void setProduce(double produce) {
		produceProperty.set(String.valueOf(produce));
	}

	public double getPantryNonFood() {
		String value = pantryNonFoodProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	public void setPantryNonFood(double nonfood) {
		pantryNonFoodProperty.set(String.valueOf(nonfood));
	}

	/**
	 * Gets the non food.
	 *
	 * @return the non food
	 */
	public double getNonFood() {
		String value = nonFoodProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the non food.
	 *
	 * @param nonfood the new non food
	 */
	public void setNonFood(double nonfood) {
		nonFoodProperty.set(String.valueOf(nonfood));
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return commentProperty.get();
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		commentProperty.set(comment);
	}

	/**
	 * Gets the milk.
	 *
	 * @return the milk
	 */
	public double getMilk() {
		String value = milkProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the milk.
	 *
	 * @param milk the new milk
	 */
	public void setMilk(double milk) {
		milkProperty.set(String.valueOf(milk));
	}

	/**
	 * Checks if is donation.
	 *
	 * @return true, if is donation
	 */
	public boolean isDonation() {
		return donationProperty.get();
	}

	/**
	 * Sets the donation.
	 *
	 * @param donation the new donation
	 */
	public void setDonation(boolean donation) {
		donationProperty.set(donation);
	}

	/**
	 * Gets the donor name.
	 *
	 * @return the donor name
	 */
	public String getDonorName() {
		return donorNameProperty.get();
	}

	/**
	 * Sets the donor name.
	 *
	 * @param donorName the new donor name
	 */
	public void setDonorName(String donorName) {
		donorNameProperty.set(donorName);
	}

	/**
	 * Gets the donor address.
	 *
	 * @return the donor address
	 */
	public String getDonorAddress() {
		return donorAddressProperty.get();
	}

	/**
	 * Sets the donor address.
	 *
	 * @param donorAddress the new donor address
	 */
	public void setDonorAddress(String donorAddress) {
		donorAddressProperty.set(donorAddress);
	}

	/**
	 * Gets the donor email.
	 *
	 * @return the donor email
	 */
	public String getDonorEmail() {
		return donorEmailProperty.get();
	}

	/**
	 * Sets the donor email.
	 *
	 * @param donorEmail the new donor email
	 */
	public void setDonorEmail(String donorEmail) {
		donorEmailProperty.set(donorEmail);
	}

	/**
	 * Gets the sec harvest produce.
	 *
	 * @return the sec harvest produce
	 */
	public double getSecondHarvestProduce() {
		String value = secondHarvestProduceProperty.get();
		if (null == value) {
			return -1;
		}
		return Double.valueOf(value);
	}

	/**
	 * Sets the sec harvest produce.
	 *
	 * @param produce the new sec harvest produce
	 */
	public void setSecondHarvestProduce(double produce) {
		secondHarvestProduceProperty.set(String.valueOf(produce));
	}

	public SimpleStringProperty idProperty() {
		return idProperty;
	}

	public SimpleStringProperty entryDateProperty() {
		return entryDateProperty;
	}

	public SimpleStringProperty pickNSaveProperty() {
		return pickNSaveProperty;
	}

	public SimpleStringProperty communityProperty() {
		return communityProperty;
	}

	public SimpleStringProperty nonTefapProperty() {
		return nonTefapProperty;
	}

	public SimpleStringProperty tefapProperty() {
		return tefapProperty;
	}

	public SimpleStringProperty secondHarvestProperty() {
		return secondHarvestProperty;
	}

	public SimpleStringProperty secondHarvestProduceProperty() {
		return secondHarvestProduceProperty;
	}

	public SimpleStringProperty pantryProperty() {
		return pantryProperty;
	}

	public SimpleStringProperty nonFoodProperty() {
		return nonFoodProperty;
	}

	public SimpleStringProperty pantryNonFoodProperty() {
		return pantryNonFoodProperty;
	}

	public SimpleStringProperty pantryProduceProperty() {
		return pantryProduceProperty;
	}

	public SimpleStringProperty produceProperty() {
		return produceProperty;
	}

	public SimpleStringProperty milkProperty() {
		return milkProperty;
	}

	public SimpleStringProperty commentProperty() {
		return commentProperty;
	}

	public SimpleBooleanProperty donationProperty() {
		return donationProperty;
	}

	public SimpleStringProperty donorNameProperty() {
		return donorNameProperty;
	}

	public SimpleStringProperty donorAddressProperty() {
		return donorAddressProperty;
	}

	public SimpleStringProperty donorEmailProperty() {
		return donorEmailProperty;
	}

	/**
	 * Helper method to return an object to the jtable model.
	 *
	 * @return the food record object
	 */
	public Object[] getFoodRecordObject() {

		return new Object[] { getFoodId(), getEntryDate(), getPickNSave(), getCommunity(), getNonTefap(), getTefap(),
				getSecondHarvest(), getSecondHarvestProduce(), getPantry(), getPantryNonFood(), getComment(),
				getNonFood(), getMilk(), -getPantryProduce(), getProduce(), isDonation(), getDonorName(),
				getDonorAddress(), getDonorEmail() };
	}

	/**
	 * Adds the to current.
	 *
	 * @param record the record
	 */
	public void addToCurrent(Food record) {
		setPickNSave(getPickNSave() + record.getPickNSave());
		setCommunity(getCommunity() + record.getCommunity());
		setNonTefap(getNonTefap() + record.getNonTefap());
		setTefap(getTefap() + record.getTefap());
		setSecondHarvest(getSecondHarvest() + record.getSecondHarvest());
		setSecondHarvestProduce(getSecondHarvestProduce() + record.getSecondHarvestProduce());
		setPantry(getPantry() + record.getPantry());
		setNonFood(getNonFood() + record.getNonFood());
		setMilk(getMilk() + record.getMilk());
		setPantryProduce(getPantryProduce() + record.getPantryProduce());
		setProduce(getProduce() + record.getProduce());

		if (record.getComment().length() > 0) {
			setComment(getComment() + record.getComment() + ", ");
		}
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return getPickNSave() + getCommunity() + getNonTefap() + getTefap() + getSecondHarvest()
				+ getSecondHarvestProduce() + getPantry() + getPantryNonFood() + getNonFood() + getMilk()
				+ getPantryProduce() + getProduce();
	}

}
