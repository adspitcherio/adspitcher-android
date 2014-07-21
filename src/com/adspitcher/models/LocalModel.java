package com.adspitcher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adspitcher.exceptions.ApplicationException;

public class LocalModel {
	private String[] citiesName;
	
	public LocalModel(){
		
	}
	
	public void setCitiesName(JSONArray citiesData) throws ApplicationException {
		int cityArrayLength = citiesData.length();
		citiesName = new String[cityArrayLength];
		JSONObject cityObj;
		for (int i = 0; i < cityArrayLength; i++) {
			try {
				cityObj = citiesData.getJSONObject(i);
				citiesName[i] = cityObj.getString("name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String[] getCitiesName() {
		return citiesName;
	}
}
