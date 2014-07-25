package com.adspitcher.models;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.adspitcher.constants.Constants;
import com.adspitcher.net.ConnectivityHandler;
import com.adspitcher.net.HttpParams;
import com.adspitcher.net.NetworkAsyncTask;
import com.adspitcher.utils.CommonUtils;


public class RemoteModel {
	public static final String TAG = "Remote Model";
	
	public void registerUser(Bundle params,Handler listener,View view) throws Exception
	{
		ConnectivityHandler connHandler = new ConnectivityHandler(view.getContext());
		if(connHandler.isOnline())
		{
			/*HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL + Constants.URL_REGISTERUSER_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);
			
			String requestData = CommonUtils.createPostdata(params);
			httpParams.setRequestData(requestData);
			Log.v(TAG, "Request Data=====>" + requestData);
			
			NetworkAsyncTask asyncTask = new NetworkAsyncTask(view.getContext(), "Connecting", listener);
			asyncTask.execute(httpParams);*/
			listener.sendMessage(listener.obtainMessage(
					Constants.SUCCESSFUL_RESPONSE, ""));
		}
		else
		{
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION, Constants.ERROR_NETWORK_PROBLEM));
		}		
	}
	// --------------------------------------------------------------------------------------------------------
	
	public void authenticateUser(Bundle params, Handler listener, View view)
			throws Exception {
		ConnectivityHandler connHandler = new ConnectivityHandler(
				view.getContext());
		if (connHandler.isOnline()) {
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL
					+ Constants.URL_GET_ACCESSTOKEN_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);

			String requestData = CommonUtils.createPostdata(params);
			httpParams.setRequestData(requestData);
			Log.v(TAG, "Request Data=====>" + requestData);

			NetworkAsyncTask asyncTask = new NetworkAsyncTask(
					view.getContext(), "Connecting...", listener);
			asyncTask.execute(httpParams);
		} else {
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION,
					Constants.ERROR_NETWORK_PROBLEM));
		}
	}
	// --------------------------------------------------------------------------------------------------------
	
	public void getAccessToken(Bundle params, Handler listener, View view)
			throws Exception {
		ConnectivityHandler connHandler = new ConnectivityHandler(
				view.getContext());
		if (connHandler.isOnline()) {
			HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL
					+ Constants.URL_GET_ACCESSTOKEN_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_POST);
			String requestData = CommonUtils.createPostdata(params);
			httpParams.setRequestData(requestData);
			Log.v(TAG, "Request Data=====>" + requestData);

			NetworkAsyncTask asyncTask = new NetworkAsyncTask(
					view.getContext(), "Connecting...", listener);
			asyncTask.execute(httpParams);
		} else {
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION,
					Constants.ERROR_NETWORK_PROBLEM));
		}
	}
	// --------------------------------------------------------------------------------------------------------
	
	public void getCities(Bundle params, Handler listener, View view)
			throws Exception {
		ConnectivityHandler connHandler = new ConnectivityHandler(
				view.getContext());
		if (connHandler.isOnline()) {
			JSONObject obj = new JSONObject();
			JSONArray resArr = new JSONArray();
			
			obj.put("id", 1);
			obj.put("name", "Nagpur");
			obj.put("type", "City");
			obj.put("parent_id", 12);
			obj.put("created_at", "2014-07-15T06:05:29.456Z");
			obj.put("updated_at", "2014-07-15T06:05:29.456Z");
			resArr.put(obj);
			
			obj = null;
			obj = new JSONObject();
			obj.put("id", 1);
			obj.put("name", "Gurgaon");
			obj.put("type", "City");
			obj.put("parent_id", 12);
			obj.put("created_at", "2014-07-15T06:05:29.456Z");
			obj.put("updated_at", "2014-07-15T06:05:29.456Z");
			resArr.put(obj);
			
			listener.sendMessage(listener.obtainMessage(
					Constants.SUCCESSFUL_RESPONSE, resArr));
			/*HttpParams httpParams = new HttpParams();
			httpParams.setRequestURL(Constants.BASE_URL
					+ Constants.URL_GET_CITIES_REQUEST);
			httpParams.setRequestMethod(HttpParams.HTTP_GET);

			NetworkAsyncTask asyncTask = new NetworkAsyncTask(
					view.getContext(), "Connecting...", listener);
			asyncTask.execute(httpParams);*/
		} else {
			listener.sendMessage(listener.obtainMessage(Constants.EXCEPTION,
					Constants.ERROR_NETWORK_PROBLEM));
		}
	}
	// --------------------------------------------------------------------------------------------------------
	
/*	public void getLatestOffers(Bundle params,Handler listener,View view) throws Exception
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
	}*/
	// --------------------------------------------------------------------------------------------------------
}