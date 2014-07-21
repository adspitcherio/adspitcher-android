package com.adspitcher.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.exceptions.ApplicationException;
import com.adspitcher.models.ConnectionModel;
import com.adspitcher.models.UserModel;

public class NetworkResponseHandler {
	public static final String TAG = "Network Response Handler";

	public static final Handler AUTHENTICATEUSER_HANDLER = authenticateUserHandler();
	public static final Handler REGISTERUSER_HANDLER = registerUserHandler();
	public static final Handler FILTERED_OFFERS_HANDLER = filteredOffersHandler();
	public static final Handler LATEST_OFFERS_HANDLER = latestOffersHandler();
	public static final Handler CITIES_HANDLER = citiesHandler();

	private static Handler authenticateUserHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					try {
						String response = ((JSONObject) msg.obj)
								.getString(Constants.TEXT_RESPONSE);
						Log.d("response==", response);
						if (response.equalsIgnoreCase(Constants.TEXT_SUCCESS)) {
							UserModel userModel = AppEventsController
									.getInstance().getModelFacade()
									.getUserModel();
							try {
								userModel.setCompleteProfileDetails((JSONObject) msg.obj);
							} catch (ApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							model.setConnectionStatus(ConnectionModel.LOGGED_IN);
						} else if (response
								.equalsIgnoreCase(Constants.TEXT_ERROR)) {
							model.setConnectionStatus(ConnectionModel.GOT_ERROR);
							String message = ((JSONObject) msg.obj)
									.getString(Constants.TEXT_MESSAGE);
							model.setConnectionErrorMessage(message);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					model.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					model.setConnectionStatus(ConnectionModel.GOT_ERROR);
					model.setConnectionErrorMessage(exceptionObj.getMessage());
					model.notifyView();
				}
					break;
				}
			}

		};
	}

	private static Handler citiesHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					Log.d("response==", (String)msg.obj);
					/*try {
						String response = ((JSONObject) msg.obj)
								.getString(Constants.TEXT_RESPONSE);
						Log.d("response==", response);
						if (response.equalsIgnoreCase(Constants.TEXT_SUCCESS)) {
							LocalModel localModel = AppEventsController
									.getInstance().getModelFacade()
									.getLocalModel();
							try {
								localModel.setCitiesName((JSONObject) msg.obj);
							} catch (ApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							model.setConnectionStatus(ConnectionModel.GOT_CITIES_NAMES);
						} else if (response
								.equalsIgnoreCase(Constants.TEXT_ERROR)) {
							model.setConnectionStatus(ConnectionModel.GOT_ERROR);
							String message = ((JSONObject) msg.obj)
									.getString(Constants.TEXT_MESSAGE);
							model.setConnectionErrorMessage(message);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					model.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					model.setConnectionStatus(ConnectionModel.GOT_ERROR);
					model.setConnectionErrorMessage(exceptionObj.getMessage());
					model.notifyView();
				}
					break;
				}
			}

		};
	}

	private static Handler latestOffersHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Handler filteredOffersHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Handler registerUserHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					try {
						String response = ((JSONObject) msg.obj)
								.getString(Constants.TEXT_RESPONSE);
						Log.d("response==", response);
						if (response.equalsIgnoreCase(Constants.TEXT_SUCCESS)) {
							UserModel userModel = AppEventsController
									.getInstance().getModelFacade()
									.getUserModel();
							try {
								userModel.setUserDetails((JSONObject) msg.obj);
							} catch (ApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							model.setConnectionStatus(ConnectionModel.LOGGED_IN);
						} else if (response
								.equalsIgnoreCase(Constants.TEXT_ERROR)) {
							model.setConnectionStatus(ConnectionModel.GOT_ERROR);
							String message = ((JSONObject) msg.obj)
									.getString(Constants.TEXT_MESSAGE);
							model.setConnectionErrorMessage(message);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					model.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					model.setConnectionStatus(ConnectionModel.GOT_ERROR);
					model.setConnectionErrorMessage(exceptionObj.getMessage());
					model.notifyView();
				}
					break;
				}
			}

		};
	}
}
