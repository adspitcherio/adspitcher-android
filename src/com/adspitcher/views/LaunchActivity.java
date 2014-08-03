package com.adspitcher.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.dataobjects.LocationDataObject;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;
import com.adspitcher.models.LocalModel;
import com.adspitcher.models.UserModel;
import com.adspitcher.utils.GPSTracker;

public class LaunchActivity extends BaseActivity implements
		ActivityUpdateListener{

	private View activityView;
	private ConnectionModel connModel;
	private TextView textview_search,textview_createaccount,textview_login;
	private ActionBar actionBar;
	private UserModel userModel;
	private LocalModel localModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Launch Activity==", "I am inside onCreate");
		super.onCreate(savedInstanceState);

		// don’t set any content view here, since its already set in DrawerActivity
		FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
	    // inflate the custom activity layout
	    LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    activityView = layoutInflater.inflate(R.layout.activity_launch, null,false);
	    // add the custom layout of this activity to frame layout.
	    frameLayout.addView(activityView);
		
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.registerView(this);
		
		userModel = AppEventsController.getInstance().getModelFacade().getUserModel();
		localModel = AppEventsController.getInstance().getModelFacade().getLocalModel();
		
		GPSTracker gps = new GPSTracker(this);
		if(gps.canGetLocation()){
			localModel.setCurrentlocation_latitude(gps.getLatitude());
	    	localModel.setCurrentlocation_longitude(gps.getLongitude());
	    	localModel.setCurrentCity(gps.getCityName());
		}else{
			gps.showSettingsAlert();
		}
		
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// Action on click of Create An Account Button
		textview_createaccount = (TextView) activityView.findViewById(R.id.textview_createaccount);
		textview_createaccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				connModel.unregisterView(LaunchActivity.this);
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LaunchActivity.this,
						SignupActivity.class);
				LaunchActivity.this.startActivity(screenChangeIntent);
			}
		});

		// Action on click of Create An Account Button
		textview_login = (TextView) activityView.findViewById(R.id.textview_login);
		textview_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				connModel.unregisterView(LaunchActivity.this);
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LaunchActivity.this,
						LoginActivity.class);
				LaunchActivity.this.startActivity(screenChangeIntent);
			}
		});

		// Action on click of Search Button
		textview_search = (TextView) activityView.findViewById(R.id.textview_search);
		textview_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LaunchActivity.this,
						HomeActivity.class);
				LaunchActivity.this.startActivity(screenChangeIntent);
			}
		});
		
		if( userModel.getUserLoggedInStatus() != UserModel.USER_NOT_LOGGEDIN ){
			textview_login.setVisibility(View.INVISIBLE);
			textview_createaccount.setVisibility(View.INVISIBLE);
		}
		
		if( !AppEventsController.getInstance().getModelFacade().getLocalModel().isRetrievedMastedFilterData() ){
			AppEventsController.getInstance().handleEvent(NetworkEvents.EVENT_ID_GET_CITIES,
				 null, textview_search);
		}else{
			Spinner citiesSpinner = (Spinner)activityView.findViewById(R.id.spinner_cities);
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			citiesSpinner.setAdapter(spinnerAdapter);
			LocationDataObject[] cities = AppEventsController.getInstance().getModelFacade().getLocalModel().getLocationsData();
			if( cities != null){
				for(int i = 0; i < cities.length; i++){
					spinnerAdapter.add(cities[i].getName());
				}
				spinnerAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		View view = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);

		if (view instanceof EditText) {
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];

			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
	}
	
	@Override
	protected void onResume() {
		Log.d("Launch Activity==", "I am inside onResume");
		connModel.registerView(this);
		if( userModel.getUserLoggedInStatus() != UserModel.USER_NOT_LOGGEDIN ){
			textview_login.setVisibility(View.INVISIBLE);
			textview_createaccount.setVisibility(View.INVISIBLE);
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d("Launch Activity==", "I am inside onPause");
		connModel.unregisterView(this);
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("Launch Activity==", "I am inside onDestroy");
		connModel.unregisterView(this);
		super.onDestroy();
	}

	@Override
	public void updateActivity() {
		switch(connModel.getConnectionStatus()){
		case ConnectionModel.SUCCESS:{
			Spinner citiesSpinner = (Spinner)activityView.findViewById(R.id.spinner_cities);
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			citiesSpinner.setAdapter(spinnerAdapter);
			LocationDataObject[] cities = AppEventsController.getInstance().getModelFacade().getLocalModel().getLocationsData();
			int start = 0;
			//adding current city on top
			if( localModel.getCurrentCity() != null ){
				spinnerAdapter.add(localModel.getCurrentCity());
			}
			
			for( int i = start; i < cities.length; i++){
				spinnerAdapter.add(cities[i].getName());
			}
			spinnerAdapter.notifyDataSetChanged();
		}
		break;
		}
	}
}
