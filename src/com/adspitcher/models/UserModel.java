package com.adspitcher.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.adspitcher.constants.Constants;
import com.adspitcher.exceptions.ApplicationException;

public class UserModel {

	private String accessToken, token_type, expires_in;
	private int user_id;
	private int user_phonenumber;
	private String user_name, user_username, user_email, user_dob, password_digest;
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public void setUser_dob(String user_dob) {
		this.user_dob = user_dob;
	}

	public void setUser_currentCity(String user_currentCity) {
		this.user_currentCity = user_currentCity;
	}

	private int user_credits;
	private Double user_currentlocation_longitude, user_currentlocation_latitude;
	private String user_currentCity;
	private String user_passworddigest;
	
	public static int USER_NOT_LOGGEDIN = 0;
	public static int USER_FB_LOGGEDIN = 1;
	public static int USER_GPLUS_LOGGEDIN = 2;
	public static int USER_NATIVE_LOGGEDIN = 3;
	private int userLoggedInStatus = USER_NOT_LOGGEDIN;

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
		userLoggedInStatus = USER_NATIVE_LOGGEDIN;
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
	}

	public String getUser_currentCity() {
		return user_currentCity;
	}

	public int getUser_phonenumber() {
		return user_phonenumber;
	}

	public String getUser_dob() {
		return user_dob;
	}

	public int getUserLoggedInStatus() {
		return userLoggedInStatus;
	}

	public void setUserLoggedInStatus(int userLoggedInStatus) {
		this.userLoggedInStatus = userLoggedInStatus;
	}
	
	
}
