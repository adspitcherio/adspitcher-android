package com.adspitcher.models;

public class ModelFacade {
	public static final String TAG = "Model Facade";
	/**
	 * Remote Model Reference to handle Network data and function calls
	 */
	private RemoteModel remoteModel;
	private ConnectionModel connModel;

	// ---------------------------------------------------------------------------------

	/**
	 * Constructor
	 */
	public ModelFacade() {
		// Initializing Remote Model
		remoteModel = new RemoteModel();
		connModel = new ConnectionModel();
	}

	// ---------------------------------------------------------------------------------

	/**
	 * Returns Remote Model Reference
	 * 
	 * @return RemoteModel
	 */
	public RemoteModel getRemoteModel() {
		return remoteModel;
	}

	public ConnectionModel getConnModel() {
		return connModel;
	}

	// ---------------------------------------------------------------------------------

}