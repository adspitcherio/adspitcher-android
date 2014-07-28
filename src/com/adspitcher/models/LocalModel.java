package com.adspitcher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adspitcher.constants.Constants;
import com.adspitcher.dataobjects.BrandDataObject;
import com.adspitcher.dataobjects.CategoryDataObject;
import com.adspitcher.dataobjects.LocationDataObject;
import com.adspitcher.exceptions.ApplicationException;

public class LocalModel {
	
	private boolean retrievedMastedFilterData = false;
	private LocationDataObject[] locationsData;
	private BrandDataObject[] brandsData;
	private CategoryDataObject[] categoriesData;
	
	public LocalModel(){
		
	}
	
	public void setLocations(JSONArray citiesData) throws ApplicationException {
		int cityArrayLength = citiesData.length();
		locationsData = new LocationDataObject[cityArrayLength];
		
		LocationDataObject tempObj;
		JSONObject cityObj;
		for (int i = 0; i < cityArrayLength; i++) {
			try {
				cityObj = citiesData.getJSONObject(i);
				tempObj = new LocationDataObject(cityObj.getInt(Constants.TEXT_ID), cityObj.getString(Constants.TEXT_NAME), cityObj.getString(Constants.TEXT_TYPE));
				locationsData[i] = tempObj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setBrands(JSONArray brandData) throws ApplicationException {
		int arrayLength = brandData.length();
		brandsData = new BrandDataObject[arrayLength];
		
		BrandDataObject tempObj;
		JSONObject tempJsonObj;
		for (int i = 0; i < arrayLength; i++) {
			try {
				tempJsonObj = brandData.getJSONObject(i);
				tempObj = new BrandDataObject(tempJsonObj.getInt(Constants.TEXT_ID), tempJsonObj.getString(Constants.TEXT_NAME), tempJsonObj.getString(Constants.TEXT_USERNAME), tempJsonObj.getString(Constants.TEXT_PASSWORD_DIGEST), 
						tempJsonObj.getString(Constants.TEXT_EMAIL), tempJsonObj.getInt(Constants.TEXT_RATING), tempJsonObj.getInt(Constants.TEXT_LOCATION_ID), tempJsonObj.getInt(Constants.TEXT_STORE_ID));
				brandsData[i] = tempObj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setCategories(JSONArray categoryData) throws ApplicationException {
		int arrayLength = categoryData.length();
		categoriesData = new CategoryDataObject[arrayLength];
		
		CategoryDataObject tempObj;
		JSONObject tempJsonObj;
		for (int i = 0; i < arrayLength; i++) {
			try {
				tempJsonObj = categoryData.getJSONObject(i);
				tempObj = new CategoryDataObject(tempJsonObj.getInt(Constants.TEXT_ID), tempJsonObj.getString(Constants.TEXT_NAME));
				categoriesData[i] = tempObj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		retrievedMastedFilterData = true;
	}

	public LocationDataObject[] getLocationsData() {
		return locationsData;
	}

	public BrandDataObject[] getBrandsData() {
		return brandsData;
	}

	public CategoryDataObject[] getCategoriesData() {
		return categoriesData;
	}

	public boolean isRetrievedMastedFilterData() {
		return retrievedMastedFilterData;
	}
}
