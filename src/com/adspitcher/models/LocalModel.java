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
	private String[] locations, brands, categories;
	private Double currentlocation_latitude, currentlocation_longitude;
	private String currentCity;
	
	public LocalModel(){
		
	}
	
	public void setLocations(JSONArray citiesData) throws ApplicationException {
		int cityArrayLength = citiesData.length();
		locationsData = new LocationDataObject[cityArrayLength];
		
		LocationDataObject tempObj;
		JSONObject cityObj;
		locations = new String[cityArrayLength];
		for (int i = 0; i < cityArrayLength; i++) {
			try {
				cityObj = citiesData.getJSONObject(i);
				locations[i] = cityObj.getString(Constants.TEXT_NAME);
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
		brands = new String[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			try {
				tempJsonObj = brandData.getJSONObject(i);
				brands[i] = tempJsonObj.getString(Constants.TEXT_NAME);
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
		categories = new String[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			try {
				tempJsonObj = categoryData.getJSONObject(i);
				categories[i] = tempJsonObj.getString(Constants.TEXT_NAME);
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

	public String[] getLocations() {
		return locations;
	}

	public String[] getBrands() {
		return brands;
	}

	public String[] getCategories() {
		return categories;
	}

	public Double getCurrentlocation_latitude() {
		return currentlocation_latitude;
	}

	public void setCurrentlocation_latitude(Double currentlocation_latitude) {
		this.currentlocation_latitude = currentlocation_latitude;
	}

	public Double getCurrentlocation_longitude() {
		return currentlocation_longitude;
	}

	public void setCurrentlocation_longitude(Double currentlocation_longitude) {
		this.currentlocation_longitude = currentlocation_longitude;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	
}
