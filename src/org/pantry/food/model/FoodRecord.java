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

/**
 * A POJO that describes a food record (a donation or other addition).
 *
 * @author Dave Johnson
 */
public class FoodRecord 
{
	
	/** The record id. */
	private int recordId;
	
	/** The entry date. */
	private String entryDate;
	
	/** The pick N save. */
	private double pickNSave;
	
	/** The community. */
	private double community;
	
	/** The non tefap. */
	private double nonTefap;
	
	/** The tefap. */
	private double tefap;
	
	/** The sec harvest. */
	private double secHarvest;
	
	/** The pantry. */
	private double pantry;
    
    /** The other. */
    private double other;
    
    /** The non food. */
    private double nonFood;
    
    /** The milk. */
    private double milk;
    
    /** The sec harvest produce. */
    private double secHarvestProduce;
    
    /** The other 2. */
    private double other2;
    
    /** The produce. */
    private double produce;

    /** The comment. */
    private String comment = "";
    
    /** The donation. */
    private boolean donation = false;
    
    /** The donor name. */
    private String donorName = "";
    
    /** The donor address. */
    private String donorAddress = "";
    
    /** The donor email. */
    private String donorEmail = "";
	
	/**
	 * Gets the record id.
	 *
	 * @return the record id
	 */
	public int getRecordId() {return recordId;}
	
	/**
	 * Sets the record id.
	 *
	 * @param recordId the new record id
	 */
	public void setRecordId(int recordId) {this.recordId = recordId;}
	
	/**
	 * Gets the entry date.
	 *
	 * @return the entry date
	 */
	public String getEntryDate() {return entryDate;}
	
	/**
	 * Sets the entry date.
	 *
	 * @param entryDate the new entry date
	 */
	public void setEntryDate(String entryDate) {this.entryDate = entryDate;}
	
	/**
	 * Gets the pick N save.
	 *
	 * @return the pick N save
	 */
	public double getPickNSave() {return pickNSave;}
	
	/**
	 * Sets the pick N save.
	 *
	 * @param pickNSave the new pick N save
	 */
	public void setPickNSave(double pickNSave) {this.pickNSave = pickNSave;}
	
	/**
	 * Gets the community.
	 *
	 * @return the community
	 */
	public double getCommunity() {return community;}
	
	/**
	 * Sets the community.
	 *
	 * @param community the new community
	 */
	public void setCommunity(double community) {this.community = community;}
	
	/**
	 * Gets the non tefap.
	 *
	 * @return the non tefap
	 */
	public double getNonTefap() {return nonTefap;}
	
	/**
	 * Sets the non tefap.
	 *
	 * @param nonTefap the new non tefap
	 */
	public void setNonTefap(double nonTefap) {this.nonTefap = nonTefap;}
	
	/**
	 * Gets the tefap.
	 *
	 * @return the tefap
	 */
	public double getTefap() {return tefap;}
	
	/**
	 * Sets the tefap.
	 *
	 * @param tefap the new tefap
	 */
	public void setTefap(double tefap) {this.tefap = tefap;}
	
	/**
	 * Gets the sec harvest.
	 *
	 * @return the sec harvest
	 */
	public double getSecHarvest() {return secHarvest;}
	
	/**
	 * Sets the sec harvest.
	 *
	 * @param secHarvest the new sec harvest
	 */
	public void setSecHarvest(double secHarvest) {this.secHarvest = secHarvest;}
	
	/**
	 * Gets the pantry.
	 *
	 * @return the pantry
	 */
	public double getPantry() {return pantry;}
	
	/**
	 * Sets the pantry.
	 *
	 * @param pantry the new pantry
	 */
	public void setPantry(double pantry) {this.pantry = pantry;}

	/**
	 * Gets the other.
	 *
	 * @return the other
	 */
	public double getOther() {return other;}
	
	/**
	 * Sets the other.
	 *
	 * @param other the new other
	 */
	public void setOther(double other) {this.other = other;}
	
	/**
	 * Gets the other 2.
	 *
	 * @return the other 2
	 */
	public double getOther2() {return other2;}
	
	/**
	 * Sets the other 2.
	 *
	 * @param other2 the new other 2
	 */
	public void setOther2(double other2) {this.other2 = other2;}
	
	/**
	 * Gets the produce.
	 *
	 * @return the produce
	 */
	public double getProduce() {return produce;}
	
	/**
	 * Sets the produce.
	 *
	 * @param produce the new produce
	 */
	public void setProduce(double produce) {this.produce = produce;}

	/**
	 * Gets the non food.
	 *
	 * @return the non food
	 */
	public double getNonFood() {return nonFood;}
	
	/**
	 * Sets the non food.
	 *
	 * @param nonfood the new non food
	 */
	public void setNonFood(double nonfood) {this.nonFood = nonfood;}

    /**
     * Gets the comment.
     *
     * @return the comment
     */
    public String getComment(){return this.comment;}
    
    /**
     * Sets the comment.
     *
     * @param comment the new comment
     */
    public void setComment(String comment){this.comment = comment;}
    
    /**
     * Sets the milk.
     *
     * @param milk the new milk
     */
    public void setMilk(double milk) {this.milk = milk;}
    
    /**
     * Gets the milk.
     *
     * @return the milk
     */
    public double getMilk() {return this.milk;}
    
	/**
	 * Checks if is donation.
	 *
	 * @return true, if is donation
	 */
	public boolean isDonation() {return donation;}
	
	/**
	 * Sets the donation.
	 *
	 * @param donation the new donation
	 */
	public void setDonation(boolean donation) {this.donation = donation;}
	
	/**
	 * Gets the donor name.
	 *
	 * @return the donor name
	 */
	public String getDonorName() {return donorName;}
	
	/**
	 * Sets the donor name.
	 *
	 * @param donorName the new donor name
	 */
	public void setDonorName(String donorName) {this.donorName = donorName;}
	
	/**
	 * Gets the donor address.
	 *
	 * @return the donor address
	 */
	public String getDonorAddress() {return donorAddress;}
	
	/**
	 * Sets the donor address.
	 *
	 * @param donorAddress the new donor address
	 */
	public void setDonorAddress(String donorAddress) {this.donorAddress = donorAddress;}
	
	/**
	 * Gets the donor email.
	 *
	 * @return the donor email
	 */
	public String getDonorEmail() {return donorEmail;}
	
	/**
	 * Sets the donor email.
	 *
	 * @param donorEmail the new donor email
	 */
	public void setDonorEmail(String donorEmail) {this.donorEmail = donorEmail;}
	
	/**
	 * Gets the sec harvest produce.
	 *
	 * @return the sec harvest produce
	 */
	public double getSecHarvestProduce() { return secHarvestProduce; }
	
	/**
	 * Sets the sec harvest produce.
	 *
	 * @param produce the new sec harvest produce
	 */
	public void setSecHarvestProduce(double produce) { this.secHarvestProduce = produce; }
	
	
    /**
     * Helper method to set a line in the csv file.
     *
     * @return the cvs entry
     */
    public String[] getCvsEntry(){

        String[] entry = {"" + this.recordId,
                          "" + this.entryDate,
                          "" + this.pickNSave,
                          "" + this.community,
                          "" + this.nonTefap,
                          "" + this.tefap,
                          "" + this.secHarvest,
                          "" + this.pantry,
                          "" + this.other,
                          this.comment,
                          "" + this.nonFood,
                          "" + this.milk,
                          "" + this.other2,
                          "" + this.produce,
                          "" + this.donation,
                          this.donorName,
                          this.donorAddress,
                          this.donorEmail,
                          "" + this.secHarvestProduce
           };

        return entry;
    }

    /**
     * Helper method to return an object to the jtable model.
     *
     * @return the food record object
     */
    public Object[] getFoodRecordObject()
    {

        return new Object[] {this.recordId,
			                this.entryDate,
			                this.pickNSave,
			                this.community,
			                this.nonTefap,
			                this.tefap,
			                this.secHarvest,
			                this.secHarvestProduce,
			                this.pantry,
                            this.other,
                            this.comment,
                            this.nonFood,
                            this.milk,
                            this.other2,
                            this.produce,
                            this.donation,
                            this.donorName,
                            this.donorAddress,
                            this.donorEmail
        };
    }

    /**
     * Adds the to current.
     *
     * @param record the record
     */
    public void addToCurrent(FoodRecord record)
    {

        this.pickNSave += record.getPickNSave();
        this.community += record.getCommunity();
        this.nonTefap += record.getNonTefap();
        this.tefap += record.getTefap();
        this.secHarvest += record.getSecHarvest();
        this.secHarvestProduce += record.getSecHarvestProduce();
        this.pantry += record.getPantry();
        this.other += record.getOther();
        this.nonFood += record.getNonFood();
        this.milk += record.getMilk();
        this.other2 += record.getOther2();
        this.produce += record.getProduce();
        
        if (record.getComment().length() > 0)
        {
            this.comment += record.getComment() + ", ";
        }

    }

    /**
     * Gets the total.
     *
     * @return the total
     */
    public double getTotal()
    {

        return this.pickNSave + this.community +
                this.nonTefap + this.tefap +
                this.secHarvest + this.secHarvestProduce +
                this.pantry + this.other + this.nonFood +
                this.milk + this.other2 + this.produce;
    }


}
