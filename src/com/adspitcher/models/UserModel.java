package com.adspitcher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.adspitcher.constants.Constants;
import com.adspitcher.exceptions.ApplicationException;

public class UserModel {

	private String accessToken;
	private int credits;
	private int badgeLevel;
	private String name, email, currentlocation;
	private String[] preferredLocations, preferredBusinesses,
			preferredCategories;
	private String[] reviews;
	private boolean userLoggedIn;

	public UserModel() {

	}

	public String getAccessToken() {
		return accessToken;
	}

	public int getCredits() {
		return credits;
	}

	public int getBadgeLevel() {
		return badgeLevel;
	}

	public String getName() {
		return name;
	}

	public String getCurrentlocation() {
		return currentlocation;
	}

	public String[] getPreferredLocations() {
		return preferredLocations;
	}

	public String[] getPreferredBusinesses() {
		return preferredBusinesses;
	}

	public String[] getPreferredCategories() {
		return preferredCategories;
	}

	public String[] getReviews() {
		return reviews;
	}

	public String getEmail() {
		return email;
	}

	public void setUserDetails(JSONObject userData) throws ApplicationException {
		JSONObject userDetails;
		try {
			userDetails = userData.getJSONObject(Constants.TEXT_DATA);
			Log.d("usermodel", userDetails.toString());
			accessToken = userDetails.getString(Constants.TEXT_ACCESSTOKEN)
					.trim();
			credits = Integer.parseInt(userDetails.getString(
					Constants.TEXT_CREDITS).trim());
			badgeLevel = Integer.parseInt(userDetails.getString(
					Constants.TEXT_BADGELEVEL).trim());
			email = userDetails.getString(Constants.TEXT_EMAIL).trim();
		} catch (JSONException e) {
			throw new ApplicationException(
					Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM);
		}
	}

	public void setCompleteProfileDetails(JSONObject userData)
			throws ApplicationException {
		JSONObject userDetails;
		try {
			userDetails = userData.getJSONObject(Constants.TEXT_DATA);
			Log.d("usermodel", userDetails.toString());
			accessToken = userDetails.getString(Constants.TEXT_ACCESSTOKEN)
					.trim();
			name = userDetails.getString(Constants.TEXT_NAME)
					.trim();
			credits = Integer.parseInt(userDetails.getString(
					Constants.TEXT_CREDITS).trim());
			badgeLevel = Integer.parseInt(userDetails.getString(
					Constants.TEXT_BADGELEVEL).trim());
			email = userDetails.getString(Constants.TEXT_EMAIL).trim();
			currentlocation = userDetails.getString(Constants.TEXT_CURRENTLOCATION).trim();
			JSONArray reviewsArray = userDetails.getJSONArray(Constants.TEXT_REVIEWS);
			reviews = new String[reviewsArray.length()];
			for (int i = 0; i < reviewsArray.length(); i++) {
				reviews[i] = reviewsArray.getString(i);
			}
			JSONObject preferencesObject = (JSONObject)userDetails.get(Constants.TEXT_PREFERENCES);
			JSONArray locationsArray = preferencesObject.getJSONArray(Constants.TEXT_LOCATIONS);
			preferredLocations = new String[locationsArray.length()];
			for (int i = 0; i < locationsArray.length(); i++) {
				preferredLocations[i] = locationsArray.getString(i);
			}
			JSONArray businessesArray = preferencesObject.getJSONArray(Constants.TEXT_BUSINESSES);
			preferredBusinesses = new String[businessesArray.length()];
			for (int i = 0; i < businessesArray.length(); i++) {
				preferredBusinesses[i] = businessesArray.getString(i);
			}
			JSONArray categoriesArray = preferencesObject.getJSONArray(Constants.TEXT_CATEGORIES);
			preferredCategories = new String[categoriesArray.length()];
			for (int i = 0; i < categoriesArray.length(); i++) {
				preferredCategories[i] = categoriesArray.getString(i);
			}
		} catch (JSONException e) {
			throw new ApplicationException(
					Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM);
		}
	}

	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}

	public void setUserLoggedIn(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}
}
