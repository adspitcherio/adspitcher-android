package com.adspitcher.views;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.Toast;

import com.adspitcher.R;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.dataobjects.LocationDataObject;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;
import com.adspitcher.models.LocalModel;
import com.adspitcher.models.UserModel;
import com.google.android.gms.location.LocationClient;

public class LaunchActivity extends BaseActivity implements
		ActivityUpdateListener, LocationListener{

	private String cityName;
	private LocationManager locationManager;
	private String provider;
	// Stores the current instantiation of the location client in this object
	private LocationClient mLocationClient;
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

		// Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	      Log.d("Launch Activity", "Location not available");
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
		
		if( userModel.isUserLoggedIn() ){
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
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause() {
		Log.d("Launch Activity==", "I am inside onPause");
		connModel.unregisterView(this);
		super.onPause();
		locationManager.removeUpdates(this);
	}
	
	@Override
	protected void onDestroy() {
		Log.d("Launch Activity==", "I am inside onDestroy");
		connModel.unregisterView(this);
		super.onDestroy();
	}
	
	@Override
	  public void onLocationChanged(Location location) {
    	localModel.setCurrentlocation_latitude(location.getLatitude());
    	localModel.setCurrentlocation_longitude(location.getLongitude());
    	
    	try {
    			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    	      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); //<10>
    	   // If the reverse geocode returned an address
              if (addresses != null && addresses.size() > 0) {

                  // Get the first address
                  Address address = addresses.get(0);
                  localModel.setCurrentCity(address.getLocality());
              }
    	      
    	    } catch (IOException e) {
    	      Log.e("LocateMe", "Could not get Geocoder data", e);
    	    }
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
				start++;
			}
			
			for( int i = start; i < cities.length; i++){
				spinnerAdapter.add(cities[i].getName());
			}
			spinnerAdapter.notifyDataSetChanged();
			/*if (servicesConnected()) {

            // Get the current location
            Location currentLocation = mLocationClient.getLastLocation();

            // Start the background task
            (new LaunchActivity.GetAddressTask(this)).execute(currentLocation);
        	}*/
		}
		break;
		}
	}
	
	@Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }
	
	@Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }

	/*
	 * Handle results returned to this Activity by other Activities started with
	 * startActivityForResult(). In particular, the method onConnectionFailed()
	 * in LocationUpdateRemover and LocationUpdateRequester may call
	 * startResolutionForResult() to start an Activity that handles Google Play
	 * services problems. The result of this call returns here, to
	 * onActivityResult.
	 */
	/*@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// Choose what to do based on the request code
		switch (requestCode) {

		// If the request code matches the code sent in onConnectionFailed
		case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST:

			switch (resultCode) {
			// If Google Play services resolved the problem
			case Activity.RESULT_OK:

				// Log the result
				Log.d(LocationUtils.APPTAG, getString(R.string.resolved));
				break;

			// If any other result was returned by Google Play services
			default:
				// Log the result
				Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));

				break;
			}

			// If any other request code was received
		default:
			// Report that this Activity received an unknown requestCode
			Log.d(LocationUtils.APPTAG,
					getString(R.string.unknown_activity_request_code,
							requestCode));
			break;
		}
	}*/

	/**
	 * Verify that Google Play services is available before making a request.
	 * 
	 * @return true if Google Play services is available, otherwise false
	 */
	/*private boolean servicesConnected() {

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG,
					getString(R.string.play_services_available));

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			// Display an error dialog
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			if (dialog != null) {
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(),
						LocationUtils.APPTAG);
			}
			return false;
		}
	}*/

	/*
	 * Called when the Activity is no longer visible at all. Stop updates and
	 * disconnect.
	 */
	/*@Override
	public void onStop() {

		// After disconnect() is called, the client is considered "dead".
		mLocationClient.disconnect();
		super.onStop();
	}*/

	/*
	 * Called when the Activity is restarted, even before it becomes visible.
	 */
	/*@Override
	public void onStart() {
		super.onStart();
		mLocationClient.connect();
	}*/

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	/*@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		  Google Play services can resolve some errors it detects. If the error
		  has a resolution, try sending an Intent to start a Google Play
		 services activity that can resolve error.
		 
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

			} catch (IntentSender.SendIntentException e) {

				// Log the error
				e.printStackTrace();
			}
		} else {
			// If no resolution is available, display a dialog to the user with
			// the error.
			showErrorDialog(connectionResult.getErrorCode());
		}
	}*/

	/**
	 * Show a dialog returned by Google Play services for the connection error
	 * code
	 * 
	 * @param errorCode
	 *            An error code returned from onConnectionFailed
	 */
	/*private void showErrorDialog(int errorCode) {

		// Get the error dialog from Google Play services
		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
				this, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

		// If Google Play services can provide an error dialog
		if (errorDialog != null) {

			// Create a new DialogFragment in which to show the error dialog
			ErrorDialogFragment errorFragment = new ErrorDialogFragment();

			// Set the dialog in the DialogFragment
			errorFragment.setDialog(errorDialog);

			// Show the error dialog in the DialogFragment
			errorFragment.show(getSupportFragmentManager(),
					LocationUtils.APPTAG);
		}
	}
*/
	/**
	 * Define a DialogFragment to display the error dialog generated in
	 * showErrorDialog.
	 */
	/*public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		*//**
		 * Default constructor. Sets the dialog field to null
		 *//*
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		*//**
		 * Set the dialog to display
		 * 
		 * @param dialog
		 *            An error dialog
		 *//*
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		
		 //This method must return a Dialog to the DialogFragment.
		 
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}*/

	/*@Override
	public void onConnected(Bundle arg0) {
	}

	@Override
	public void onDisconnected() {
	}*/

	/**
     * An AsyncTask that calls getFromLocation() in the background.
     * The class uses the following generic types:
     * Location - A {@link android.location.Location} object containing the current location,
     *            passed as the input parameter to doInBackground()
     * Void     - indicates that progress units are not used by this subclass
     * String   - An address passed to onPostExecute()
     */
  /*protected class GetAddressTask extends AsyncTask<Location, Void, String> {

        // Store the context passed to the AsyncTask when the system instantiates it.
        Context localContext;

        // Constructor called by the system to instantiate the task
        public GetAddressTask(Context context) {

            // Required by the semantics of AsyncTask
            super();

            // Set a Context for the background task
            localContext = context;
        }

        
         * Get a geocoding service instance, pass latitude and longitude to it, format the returned
         * address, and return the address to the UI thread.
         
        @Override
        protected String doInBackground(Location... params) {
            
             Get a new geocoding service instance, set for localized addresses. This example uses
             android.location.Geocoder, but other geocoders that conform to address standards
             can also be used.
             
            Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());

            // Get the current location from the input parameter list
            Location location = params[0];

            // Create a list to contain the result address
            List <Address> addresses = null;

            // Try to get an address for the current location. Catch IO or network problems.
            try {
                 Call the synchronous getFromLocation() method with the latitude and
                 longitude of the current location. Return at most 1 address.
            	
            	LocalModel localModel = AppEventsController.getInstance().getModelFacade().getLocalModel();
            	localModel.setCurrentlocation_latitude(location.getLatitude());
            	localModel.setCurrentlocation_longitude(location.getLongitude());
                 
                addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1
                );

                // Catch network or other I/O problems.
                } catch (IOException exception1) {

                    // Log an error and return an error message
                    Log.e(LocationUtils.APPTAG, getString(R.string.IO_Exception_getFromLocation));

                    // print the stack trace
                    exception1.printStackTrace();

                    // Return an error message
                    return (getString(R.string.IO_Exception_getFromLocation));

                // Catch incorrect latitude or longitude values
                } catch (IllegalArgumentException exception2) {

                    // Construct a message containing the invalid arguments
                    String errorString = getString(
                            R.string.illegal_argument_exception,
                            location.getLatitude(),
                            location.getLongitude()
                    );
                    // Log the error and print the stack trace
                    Log.e(LocationUtils.APPTAG, errorString);
                    exception2.printStackTrace();

                    //
                    return errorString;
                }
                // If the reverse geocode returned an address
                if (addresses != null && addresses.size() > 0) {

                    // Get the first address
                    Address address = addresses.get(0);

                    // Format the first line of address
                    String addressText = getString(R.string.address_output_string,

                            // If there's a street address, add it
                            address.getMaxAddressLineIndex() > 0 ?
                                    address.getAddressLine(0) : "",

                            // Locality is usually a city
                            address.getLocality(),

                            // The country of the address
                            address.getCountryName()
                    );

                    // Return the text
                    return address.getLocality();

                // If there aren't any addresses, post a message
                } else {
                  return getString(R.string.no_address_found);
                }
        	}
        
         * A method that's called once doInBackground() completes. Set the text of the
         * UI element that displays the address. This method runs on the UI thread.
         
        @Override
        protected void onPostExecute(String address) {

            // Set the address in the UI
            setTitle(address);
        }
    }*/
}
