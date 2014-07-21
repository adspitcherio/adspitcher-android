package com.adspitcher.constants;

public class Constants {

	// Required URLs
	public static final String BASE_URL = "http://api.adspitcher.com/v1/";
	//public static final String BASE_URL = "http://54.243.74.12/v1/";
	public static final String URL_AUTHENTICATEUSER_REQUEST = "authenticateuser";
	public static final String URL_REGISTERUSER_REQUEST = "registeruser";
	public static final String URL_OFFERS_REQUEST = "offers";
	public static final String URL_USERPROFILE_REQUEST = "userprofile";
	public static final String URL_GET_CITIES_REQUEST = "locations.json";
	// ----------------------------------------------------------------------------------
	
	public static final String TYPE_LATEST_OFFERS = "-1";
	public static final String TYPE_NEARBY_OFFERS = "0";
	public static final String TYPE_FAV_OFFERS = "1";
	public static final String TYPE_FILTERED_OFFERS = "2";
	// ----------------------------------------------------------------------------------

	// Response Handling Constants
	public static final int SUCCESSFUL_RESPONSE = 0;
	public static final int ERROR = 1;
	public static final int EXCEPTION = 2;
	// ----------------------------------------------------------------------------------
	
	/**
     * Shared Preference Name
     */
    public static final String DATABASE_PREF_NAME = "adspitcherLoginPrefName";
    public static final String TEXT_DATABASE_ACCESS_VALUE_DEFAULT = "DatabaseKeyDoesNotExist";
    //--------------------------------------------------------------------

	// Error Messages
	public static String ERROR_NETWORK_PROBLEM;
	public static String ERROR_READING_DATA_FROM_SERVER_PROBLEM;
	// ----------------------------------------------------------------------------------

	public static final String TEXT_MESSAGE = "message";
	public static final String TEXT_ERROR = "error";
	public static final String TEXT_SUCCESS = "success";
	public static final String TEXT_RESPONSE = "response";
	public static final String TEXT_DATA = "data";
	public static final String TEXT_LOCATION_CITYNAME = "cityname";
	// ----------------------------------------------------------------------------------
	
	public static final String TEXT_USERNAME = "username";
	public static final String TEXT_PASSWORD = "password";
	public static final String TEXT_EMAIL = "email";
	public static final String TEXT_ACCESSTOKEN = "accesstoken";
	public static final String TEXT_CREDITS = "credits";
	public static final String TEXT_BADGELEVEL = "badgelevel";
	public static final String TEXT_NAME = "name";
	public static final String TEXT_CURRENTLOCATION = "currentlocation";
	public static final String TEXT_REVIEWS = "reviews";
	public static final String TEXT_PREFERENCES = "preferences";
	public static final String TEXT_LOCATIONS = "locations";
	public static final String TEXT_BUSINESSES = "businesses";
	public static final String TEXT_CATEGORIES = "categories";
}
