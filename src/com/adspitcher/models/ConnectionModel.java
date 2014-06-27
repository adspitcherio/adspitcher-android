package com.adspitcher.models;

import com.adspitcher.listeners.ConnListener;

public class ConnectionModel extends BaseModel {

	private int connectionStatus;
	private String connectionErrorMessage;
	private ConnListener listener;

	public static final int GOT_ERROR = -1;
	public static final int START_CONN = 0;
	public static final int LOGGED_IN = 1;
	public static final int GOT_LATEST_FEEDS = 2;
	public static final int GOT_FILTERED_OFFERS = 3;
	
	public ConnectionModel() {

	}

	public int getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(int connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public ConnListener getListener() {
		return listener;
	}

	public void setListener(ConnListener listener) {
		this.listener = listener;
	}

	public String getConnectionErrorMessage() {
		return connectionErrorMessage;
	}

	public void setConnectionErrorMessage(String connectionErrorMessage) {
		this.connectionErrorMessage = connectionErrorMessage;
	}

}