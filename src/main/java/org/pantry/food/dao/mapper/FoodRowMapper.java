package org.pantry.food.dao.mapper;

import org.pantry.food.model.Food;

public class FoodRowMapper implements ArrayRowMapper<Food> {

	private static final int RECORDID = 0;
	private static final int ENTRYDATE = 1;
	private static final int PICKNSAVE = 2;
	private static final int COMMUNITY = 3;
	private static final int NONTEFAP = 4;
	private static final int TEFAP = 5;
	private static final int SECONDHARVEST = 6;
	private static final int PANTRY = 7;
	private static final int OTHER = 8;
	private static final int COMMENT = 9;
	private static final int NONFOOD = 10;
	private static final int MILK = 11;
	private static final int OTHER2 = 12;
	private static final int PRODUCE = 13;
	private static final int DONATION = 14;
	private static final int DONOR = 15;
	private static final int DONORADDRESS = 16;
	private static final int DONOREMAIL = 17;
	private static final int SECONDHARVEST_PRODUCE = 18;

	@Override
	public Food map(String[] row) {
		boolean hasDonorUpdate = false;
		boolean hasSecHarvestProduceUpdate = false;
		if (row.length >= 18) {
			hasDonorUpdate = true;
		}

		if (row.length >= 19) {
			hasSecHarvestProduceUpdate = true;
		}

		Food record = new Food();
		record.setFoodId(Integer.parseInt(row[RECORDID]));
		record.setEntryDate(row[ENTRYDATE]);
		record.setPickNSave(Double.parseDouble(row[PICKNSAVE]));
		record.setCommunity(Double.parseDouble(row[COMMUNITY]));
		record.setNonTefap(Double.parseDouble(row[NONTEFAP]));
		record.setTefap(Double.parseDouble(row[TEFAP]));
		record.setSecondHarvest(Double.parseDouble(row[SECONDHARVEST]));
		record.setPantry(Double.parseDouble(row[PANTRY]));
		record.setOther(Double.parseDouble(row[OTHER]));
		record.setComment(row[COMMENT]);
		record.setNonFood(Double.parseDouble(row[NONFOOD]));
		record.setMilk(Double.parseDouble(row[MILK]));
		record.setOther2(Double.parseDouble(row[OTHER2]));
		record.setProduce(Double.parseDouble(row[PRODUCE]));

		// if the file has the donor update, use it
		if (hasDonorUpdate) {
			record.setDonation(Boolean.parseBoolean(row[DONATION]));
			record.setDonorName(row[DONOR]);
			record.setDonorAddress(row[DONORADDRESS]);
			record.setDonorEmail(row[DONOREMAIL]);
		} else {
			// otherwise, add fake donor data
			record.setDonation(false);
			record.setDonorName("");
			record.setDonorAddress("");
			record.setDonorEmail("");
		}

		// if the file has the second harvest produce column
		if (hasSecHarvestProduceUpdate) {
			record.setSecondHarvestProduce(Double.parseDouble(row[SECONDHARVEST_PRODUCE]));
		} else {
			record.setSecondHarvestProduce(0);
		}

		return record;
	}

	public String[] toCsvRow(Food customer) {
		return new String[] { String.valueOf(customer.getFoodId()), customer.getEntryDate(),
				String.valueOf(customer.getPickNSave()), String.valueOf(customer.getCommunity()),
				String.valueOf(customer.getNonTefap()), String.valueOf(customer.getTefap()),
				String.valueOf(customer.getSecondHarvest()), String.valueOf(customer.getPantry()),
				String.valueOf(customer.getOther()), String.valueOf(customer.getComment()),
				String.valueOf(customer.getNonFood()), String.valueOf(customer.getMilk()),
				String.valueOf(customer.getOther2()), String.valueOf(customer.getProduce()),
				String.valueOf(customer.isDonation()), String.valueOf(customer.getDonorName()),
				String.valueOf(customer.getDonorAddress()), String.valueOf(customer.getDonorEmail()),
				String.valueOf(customer.getSecondHarvestProduce()) };
	}

}
