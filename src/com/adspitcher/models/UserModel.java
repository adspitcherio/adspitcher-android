package com.adspitcher.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.adspitcher.constants.Constants;
import com.adspitcher.exceptions.ApplicationException;

public class UserModel {

	private static int USER_NO_PRIVILAGES = 0;
	private static int USER_ONLY_SOCIAL_PRIVILAGES = 1;
	private static int USER_FULL_PRIVILAGES = 2;
	
	private String accessToken, token_type, expires_in;
	private boolean userLoggedIn;
	private int userPrivilages = USER_NO_PRIVILAGES;
	private int user_id;
	private String user_name, user_username, user_email, password_digest;
	private int user_credits;
	private Double user_currentlocation_longitude, user_currentlocation_latitude;
	private String user_currentCity;
	private String user_passworddigest;

	public UserModel() {

	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getUser_username() {
		return user_username;
	}

	public String getUser_email() {
		return user_email;
	}

	public String getPassword_digest() {
		return password_digest;
	}

	public int getUser_credits() {
		return user_credits;
	}

	public Double getUser_currentlocation_longitude() {
		return user_currentlocation_longitude;
	}

	public Double getUser_currentlocation_latitude() {
		return user_currentlocation_latitude;
	}

	public String getUser_passworddigest() {
		return user_passworddigest;
	}

	public void setAuthenticationDetails(JSONObject loginData) throws ApplicationException {
		try {
			accessToken = loginData.getString(Constants.TEXT_ACCESSTOKEN)
					.trim();
			token_type = loginData.getString(Constants.TEXT_TOKEN_TYPE)
					.trim();
			expires_in = loginData.getString(Constants.TEXT_EXPIRES_IN)
					.trim();
		} catch (JSONException e) {
			throw new ApplicationException(
					Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM);
		}
	}

	public void setCompleteProfileDetails(JSONObject userData)
			throws ApplicationException {
		try {
			user_id = Integer.parseInt(userData.getString(Constants.TEXT_ID)
					.trim());
			user_name = userData.getString(Constants.TEXT_NAME)
					.trim();
			user_username = userData.getString(Constants.TEXT_USERNAME)
					.trim();
			user_credits = Integer.parseInt(userData.getString(
					Constants.TEXT_CREDITS).trim());
			user_email = userData.getString(Constants.TEXT_EMAIL).trim();
			user_passworddigest = userData.getString(Constants.TEXT_PASSWORD_DIGEST).trim();
			user_currentlocation_latitude = userData.getDouble(Constants.TEXT_CURRENTLOCATION_LATITUDE);
			user_currentlocation_longitude = userData.getDouble(Constants.TEXT_CURRENTLOCATION_LONGITUDE);
		} catch (JSONException e) {
			throw new ApplicationException(
					Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM);
		}
		userPrivilages = USER_FULL_PRIVILAGES;
	}
	
	public void setSocialProfileDetails(JSONObject userData)
			throws ApplicationException {
		try {
			user_id = Integer.parseInt(userData.getString(Constants.TEXT_ID)
					.trim());
			user_name = userData.getString(Constants.TEXT_NAME)
					.trim();
			user_email = userData.getString(Constants.TEXT_EMAIL).trim();
		} catch (JSONException e) {
			throw new ApplicationException(
					Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM);
		}
		userPrivilages = USER_ONLY_SOCIAL_PRIVILAGES;
	}

	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}

	public void setUserLoggedIn(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

	public String getUser_currentCity() {
		return user_currentCity;
	}

	public int getUserPrivilages() {
		return userPrivilages;
	}
	
}
