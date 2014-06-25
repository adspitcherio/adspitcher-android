package com.adspitcher.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.models.ConnectionModel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NetworkResponseHandler {
	public static final String TAG = "Network Response Handler";
	
	public static final Handler AUTHENTICATEUSER_HANDLER = authenticateUserHandler();

	private static Handler authenticateUserHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.SUCCESSFUL_RESPONSE: {
					ConnectionModel model = AppEventsController.getInstance()
							.getModelFacade().getConnModel();
					try {
						String response = ((JSONObject) msg.obj).getString(Constants.TEXT_RESPONSE);
						Log.d("response==", response);
						if(response.equalsIgnoreCase(Constants.TEXT_SUCCESS)){
							model.setConnectionStatus(ConnectionModel.LOGGED_IN);
						}
						else if(response.equalsIgnoreCase(Constants.TEXT_ERROR)){
							model.setConnectionStatus(ConnectionModel.GOT_ERROR);
							String message = ((JSONObject) msg.obj).getString(Constants.TEXT_MESSAGE);
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
