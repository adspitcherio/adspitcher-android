package com.adspitcher.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.exceptions.ApplicationException;
import com.adspitcher.models.ConnectionModel;
import com.adspitcher.models.LocalModel;
import com.adspitcher.models.UserModel;

public class NetworkResponseHandler {
	public static final String TAG = "Network Response Handler";

	public static final Handler AUTHENTICATEUSER_HANDLER = authenticateUserHandler();
	public static final Handler REGISTERUSER_HANDLER = registerUserHandler();
	public static final Handler FILTERED_OFFERS_HANDLER = filteredOffersHandler();
	public static final Handler LATEST_OFFERS_HANDLER = latestOffersHandler();
	public static final Handler CITIES_HANDLER = citiesHandler();
	public static final Handler BRANDS_HANDLER = brandsHandler();
	public static final Handler CATEGORIES_HANDLER = categoriesHandler();

	private static Handler authenticateUserHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ConnectionModel model = AppEventsController.getInstance()
						.getModelFacade().getConnModel();
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					Log.d("response==", ((JSONObject) msg.obj).toString());
						UserModel userModel = AppEventsController
								.getInstance().getModelFacade()
								.getUserModel();
						try {
							userModel.setAuthenticationDetails((JSONObject) msg.obj);
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						model.setConnectionStatus(ConnectionModel.SUCCESS);
						userModel.setUserLoggedInStatus(UserModel.USER_NATIVE_LOGGEDIN);
					model.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					model.setConnectionStatus(ConnectionModel.ERROR);
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
				ConnectionModel connmodel = AppEventsController.getInstance()
						.getModelFacade().getConnModel();
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					JSONArray response = (JSONArray)msg.obj; 
					Log.d("response==", response.toString());
					LocalModel localModel = AppEventsController
							.getInstance().getModelFacade()
							.getLocalModel();
					try {
						if( response.length() > 0 ){
							localModel.setLocations(response);
						}else{
							connmodel.setConnectionStatus(ConnectionModel.ERROR);
							connmodel.setConnectionErrorMessage("No Data Found.");
						}
					} catch (ApplicationException appEx) {
					}
					//Code to fetch Brands MasterData
					TextView view = (TextView)((Activity)connmodel.getRegisteredView().get(0)).findViewById(R.id.textview_search);
					AppEventsController.getInstance().handleEvent(NetworkEvents.EVENT_ID_GET_BRANDS, null, view);
					//connmodel.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					connmodel.setConnectionStatus(ConnectionModel.ERROR);
					connmodel.setConnectionErrorMessage(exceptionObj.getMessage());
					connmodel.notifyView();
				}
					break;
				}
			}

		};
	}
	
	private static Handler brandsHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ConnectionModel connmodel = AppEventsController.getInstance()
						.getModelFacade().getConnModel();
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					JSONArray response = (JSONArray)msg.obj; 
					Log.d("response==", response.toString());
					LocalModel localModel = AppEventsController
							.getInstance().getModelFacade()
							.getLocalModel();
					try {
						if( response.length() > 0 ){
							localModel.setBrands(response);
						}else{
							connmodel.setConnectionStatus(ConnectionModel.ERROR);
							connmodel.setConnectionErrorMessage("No Data Found.");
						}
					} catch (ApplicationException appEx) {
						// TODO Auto-generated catch block
						appEx.printStackTrace();
					}
					//Code to fetch Categories MasterData
					TextView view = (TextView)((Activity)connmodel.getRegisteredView().get(0)).findViewById(R.id.textview_search);
					AppEventsController.getInstance().handleEvent(NetworkEvents.EVENT_ID_GET_CATEGORIES, null, view);
					//connmodel.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					connmodel.setConnectionStatus(ConnectionModel.ERROR);
					connmodel.setConnectionErrorMessage(exceptionObj.getMessage());
					connmodel.notifyView();
				}
					break;
				}
			}

		};
	}
	
	private static Handler categoriesHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ConnectionModel connmodel = AppEventsController.getInstance()
						.getModelFacade().getConnModel();
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					JSONArray response = (JSONArray)msg.obj; 
					Log.d("response==", response.toString());
					LocalModel localModel = AppEventsController
							.getInstance().getModelFacade()
							.getLocalModel();
					try {
						if( response.length() > 0 ){
							localModel.setCategories(response);
						}else{
							connmodel.setConnectionStatus(ConnectionModel.ERROR);
							connmodel.setConnectionErrorMessage("No Data Found.");
						}
					} catch (ApplicationException appEx) {
						// TODO Auto-generated catch block
						appEx.printStackTrace();
					}
					connmodel.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					connmodel.setConnectionStatus(ConnectionModel.ERROR);
					connmodel.setConnectionErrorMessage(exceptionObj.getMessage());
					connmodel.notifyView();
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
				ConnectionModel model = AppEventsController.getInstance()
						.getModelFacade().getConnModel();
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					UserModel userModel = AppEventsController
							.getInstance().getModelFacade()
							.getUserModel();
					try {
						userModel.setCompleteProfileDetails((JSONObject) msg.obj);
					} catch (ApplicationException exceptionObj) {
						Log.d(TAG, "exception:" + exceptionObj.getMessage());
						model.setConnectionStatus(ConnectionModel.ERROR);
						model.setConnectionErrorMessage(exceptionObj.getMessage());
						model.notifyView();
					}
					userModel.setUserLoggedInStatus(UserModel.USER_NATIVE_LOGGEDIN);
					model.notifyView();
				}
					break;
				case Constants.EXCEPTION: {
					Exception exceptionObj = (Exception) msg.obj;
					Log.d(TAG, "exception:" + exceptionObj.getMessage());
					model.setConnectionStatus(ConnectionModel.ERROR);
					model.setConnectionErrorMessage(exceptionObj.getMessage());
					model.notifyView();
				}
					break;
				}
			}

		};
	}
}
