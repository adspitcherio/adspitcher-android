package com.adspitcher.views;

import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.listeners.ConnListener;
import com.adspitcher.models.ConnectionModel;

public class UpdateListener implements ActivityUpdateListener {

	private ConnectionModel connModel;

	public UpdateListener() {
	}

	@Override
	public void updateActivity() {
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		((ConnListener) connModel.getListener()).onConnection();
	}
}