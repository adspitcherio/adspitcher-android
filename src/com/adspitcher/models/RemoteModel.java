package com.adspitcher.models;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.adspitcher.constants.Constants;
import com.adspitcher.net.ConnectivityHandler;
import com.adspitcher.net.HttpParams;
import com.adspitcher.net.NetworkAsyncTask;


public class RemoteModel {
	public static final String TAG = "Remote Model";
	
	public void authenticateUser(Bundle params, Handler listener, View view)
			throws Exception {
		ConnectivityHandler connHandler = new ConnectivityHandler(
				view.getContext());
		if (connHandler.isOnline()) {
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL
					+ Constants.URL_AUTHENTICATEUSER_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);

			JSONObject jsonObj = new JSONObject();
			Set<String> keySet = params.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			String keyVal = null;
			while (keyIterator.hasNext()) {
				keyVal = keyIterator.next();
				jsonObj.put(keyVal, params.getString(keyVal));
			}
			httpParams.setRequestData(jsonObj.toString());
			Log.v(TAG, "Request Data=====>" + jsonObj.toString());

			NetworkAsyncTask asyncTask = new NetworkAsyncTask(
					view.getContext(), "Connecting...", listener);
			asyncTask.execute(httpParams);
		} else {
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION,
					Constants.ERROR_NETWORK_PROBLEM));
		}
	}
	
	
	public void registerUser(Bundle params,Handler listener,View view) throws Exception
	{
		ConnectivityHandler connHandler = new ConnectivityHandler(view.getContext());
		if(connHandler.isOnline())
		{
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL + Constants.URL_REGISTERUSER_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);
			
			JSONObject jsonObj = new JSONObject();
			Set<String> keySet = params.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			String keyVal = null;
			while(keyIterator.hasNext())
			{
				keyVal = keyIterator.next();
				jsonObj.put(keyVal, params.get(keyVal));
			}
			httpParams.setRequestData(jsonObj.toString());
			Log.v(TAG, "Request Data for registerUser=====>" + jsonObj.toString());
			
			NetworkAsyncTask asyncTask = new NetworkAsyncTask(view.getContext(), "Connecting", listener);
			asyncTask.execute(httpParams);			
		}
		else
		{
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION, Constants.ERROR_NETWORK_PROBLEM));
		}		
	}
	
	public void getLatestOffers(Bundle params,Handler listener,View view) throws Exception
	{
		ConnectivityHandler connHandler = new ConnectivityHandler(view.getContext());
		if(connHandler.isOnline())
		{
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL + Constants.URL_OFFERS_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);
			
			JSONObject jsonObj = new JSONObject();
			Set<String> keySet = params.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			String keyVal = null;
			while(keyIterator.hasNext())
			{
				keyVal = keyIterator.next();
				jsonObj.put(keyVal, params.get(keyVal));
			}
			httpParams.setRequestData(jsonObj.toString());
			Log.v(TAG, "Request Data for registerUser=====>" + jsonObj.toString());
			
			NetworkAsyncTask asyncTask = new NetworkAsyncTask(view.getContext(), "Connecting", listener);
			asyncTask.execute(httpParams);			
		}
		else
		{
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION, Constants.ERROR_NETWORK_PROBLEM));
		}		
	}
	
	public void getFilteredOffers(Bundle params,Handler listener,View view) throws Exception
	{
		ConnectivityHandler connHandler = new ConnectivityHandler(view.getContext());
		if(connHandler.isOnline())
		{
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL + Constants.URL_OFFERS_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);
			
			JSONObject jsonObj = new JSONObject();
			Set<String> keySet = params.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			String keyVal = null;
			while(keyIterator.hasNext())
			{
				keyVal = keyIterator.next();
				jsonObj.put(keyVal, params.get(keyVal));
			}
			httpParams.setRequestData(jsonObj.toString());
			Log.v(TAG, "Request Data for registerUser=====>" + jsonObj.toString());
			
			NetworkAsyncTask asyncTask = new NetworkAsyncTask(view.getContext(), "Connecting", listener);
			asyncTask.execute(httpParams);			
		}
		else
		{
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION, Constants.ERROR_NETWORK_PROBLEM));
		}		
	}
	/*public void syncDB(Bundle params, Handler listener, View view)
			throws Exception {
		ConnectivityHandler connHandler = new ConnectivityHandler(
				view.getContext());
		if (connHandler.isOnline()) {
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL
					+ Constants.SYNC_DB_REQUEST_URL);
			httpParams.setRequestMethod(HttpParams.HTTP_GET);

			NetworkAsyncTask asyncTask = new NetworkAsyncTask(
					view.getContext(), "Connecting...", listener);
			asyncTask.execute(httpParams);
		} else {
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION,
					Constants.ERROR_NETWORK_PROBLEM));
		}
	}*/
	// --------------------------------------------------------------------------------------------------------
	
	/*public void placeOrder(Bundle params, Handler listener, View view)
			throws Exception {
		ConnectivityHandler connHandler = new ConnectivityHandler(
				view.getContext());
		if (connHandler.isOnline()) {
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL
					+ Constants.ORDERS_POST_REQUEST_URL);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);

			JSONObject jsonObj = new JSONObject();
			Set<String> keySet = params.keySet();
			Iterator<String> keyIterator = keySet.iterator();
			String keyVal = null;
			while (keyIterator.hasNext()) {
				keyVal = keyIterator.next();
				jsonObj.put(keyVal, params.getString(keyVal));
			}
			httpParams.setRequestData(jsonObj.toString());
			Log.v(TAG, "Request Data=====>" + jsonObj.toString());

			NetworkAsyncTask asyncTask = new NetworkAsyncTask(
					view.getContext(), "Connecting...", listener);
			asyncTask.execute(httpParams);
		} else {
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION,
					Constants.ERROR_NETWORK_PROBLEM));
		}
	}*/
	// --------------------------------------------------------------------------------------------------------
}
