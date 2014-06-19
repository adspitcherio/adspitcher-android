package com.adspitcher.constants;

public class Constants {

	// Required URLs
	public static final String BASE_URL = "http://localhost:8080/menuserver/menuservice_launch.php/";
	public static final String LOGIN_REQUEST_URL = "authenticate";
	// ----------------------------------------------------------------------------------

	// Response Handling Constants
	public static final int SUCCESSFUL_RESPONSE = 0;
	public static final int ERROR = 1;
	public static final int EXCEPTION = 2;
	// ----------------------------------------------------------------------------------

	// Error Messages
	public static String ERROR_NETWORK_PROBLEM;
	public static String ERROR_READING_DATA_FROM_SERVER_PROBLEM;
	// ----------------------------------------------------------------------------------

	public static final String TEXT_MESSAGE = "message";
	public static final String TEXT_ERROR = "error";
	public static final String TEXT_SUCCESS = "success";
	public static final String TEXT_RESPONSE = "response";
	// ----------------------------------------------------------------------------------
}
