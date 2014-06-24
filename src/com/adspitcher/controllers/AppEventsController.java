package com.adspitcher.controllers;

import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.models.ModelFacade;
import com.adspitcher.net.NetworkResponseHandler;
import com.adspitcher.views.UpdateListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AppEventsController {
	public static final String TAG = "Application Controller";
	/**
	 * Singleton Class Reference
	 */
	public static AppEventsController appController;
	/**
	 * ModelFacade Reference
	 */
	private ModelFacade modelFacade;
	private UpdateListener activityUpdateListener;

	// ---------------------------------------------------------------------------------

	/**
	 * Constructor
	 */
	private AppEventsController() {
		modelFacade = new ModelFacade();
		activityUpdateListener = new UpdateListener();
	}

	// ---------------------------------------------------------------------------------

	/**
	 * Get Single instance of this class
	 * 
	 * @return ApplicationController single instance
	 */
	public static AppEventsController getInstance() {
		if (appController == null) {
			// creating new instance of application controller
			appController = new AppEventsController();
		}
		return appController;
	}

	// ---------------------------------------------------------------------------------

	/**
	 * Model Facade Reference
	 * 
	 * @return ModelFacade Reference
	 */
	public ModelFacade getModelFacade() {
		return modelFacade;
	}

	// ---------------------------------------------------------------------------------

	/**
	 * Handle Event Based on Event ID and Object
	 * 
	 * @param eventId
	 *            Event raised
	 * @param eventObjects
	 *            It stores the data for the given Event
	 */
	public void handleEvent(int eventId, Bundle eventData, View view) {
		fireEvents(eventId, eventData, view);
	}

	// ---------------------------------------------------------------------------------

	public UpdateListener getActivityUpdateListener() {
		return activityUpdateListener;
	}

	/**
	 * Method to actually handle events
	 */
	private void fireEvents(int eventId, Bundle eventData, View view) {
		switch (eventId) {
		case NetworkEvents.EVENT_ID_AUTHENTICATE_USER: {
			Log.d(TAG, "Creating Bundle");
			try {
				modelFacade.getRemoteModel().authenticateUser(eventData,
						NetworkResponseHandler.AUTHENTICATEUSER_HANDLER, view);
			} catch (Exception ex) {
				Log.d("Application Exception:", ex.getMessage());
			}
		}
			break;
		}
	}

	// ---------------------------------------------------------------------------------
}