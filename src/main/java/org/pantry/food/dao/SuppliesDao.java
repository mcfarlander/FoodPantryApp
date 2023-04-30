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
package org.pantry.food.dao;

import java.io.File;
import java.io.IOException;

import org.pantry.food.dao.mapper.ArrayRowMapper;
import org.pantry.food.dao.mapper.FoodRowMapper;
import org.pantry.food.model.Supplies;
import org.pantry.food.ui.common.DataFiles;

/**
 * Reads and writes food and non/food donation records
 * 
 * @author mcfarland_davej
 *
 */
public class SuppliesDao extends AbstractCsvDao<Supplies> {

	private static final String Col_RecordId = "recordid";
	private static final String Col_EntryDate = "entrydate";
	private static final String Col_PickNSave = "picknsave";
	private static final String Col_Community = "community";
	private static final String Col_NonTefap = "nontefap";
	private static final String Col_Tefap = "tefap";
	private static final String Col_SecondHarvest = "secondharvest";
	private static final String Col_Pantry = "pantry";
	private static final String Col_PantryNonFood = "pantry nonfood";
	private static final String Col_Comment = "comment";
	private static final String Col_NonFood = "nonfood";
	private static final String Col_Milk = "milk";
	private static final String Col_PantryProduce = "pantry produce";
	private static final String Col_Produce = "produce";
	private static final String Col_Donation = "donation";
	private static final String Col_Donor = "donor";
	private static final String Col_DonorAddress = "donoraddress";
	private static final String Col_DonorEmail = "donoremail";
	private static final String Col_SecondHarvestProduce = "secondharvest_produce";

	private String startDir = "";

	@Override
	protected ArrayRowMapper<Supplies> getRowMapper() {
		return new FoodRowMapper();
	}

	@Override
	protected int getId(Supplies entity) {
		return entity.getId();
	}

	@Override
	protected File getCsvFile() throws IOException {
		if (startDir.length() == 0) {
			startDir = new java.io.File(".").getCanonicalPath();
		}

		return new File(startDir + "/" + DataFiles.getInstance().getCsvFileFoodRecord());
	}

	@Override
	protected String[] getCsvHeader() {
		return new String[] { Col_RecordId, Col_EntryDate, Col_PickNSave, Col_Community, Col_NonTefap, Col_Tefap,
				Col_SecondHarvest, Col_Pantry, Col_PantryNonFood, Col_Comment, Col_NonFood, Col_Milk, Col_PantryProduce,
				Col_Produce, Col_Donation, Col_Donor, Col_DonorAddress, Col_DonorEmail, Col_SecondHarvestProduce };
	}

}
