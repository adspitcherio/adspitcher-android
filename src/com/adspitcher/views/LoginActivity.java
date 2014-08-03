package com.adspitcher.views;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adspitcher.R;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;
import com.adspitcher.models.UserModel;
import com.adspitcher.utils.TextValidator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphLocation;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class LoginActivity extends ActionBarActivity implements ActivityUpdateListener{//, ConnectionCallbacks, OnConnectionFailedListener {

	//Variables for Native Login
	private TextView btn_signin;
	private String username, password;
	private boolean keepMeLoggedInBool;
	private ConnectionModel connModel;
	private EditText editText_username, editText_password;
	private boolean isEmailValid, isPasswordValid;
	String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private UserModel userModel;
	
	//Variables for FB Login
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	
	//Variables for Google Plus Login
    /*private GoogleApiClient mGoogleApiClient;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private static final int RC_SIGN_IN = 0;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		editText_username = (EditText) findViewById(R.id.edittext_username);
		editText_password = (EditText) findViewById(R.id.edittext_password);
		editText_password.setTypeface(Typeface.SANS_SERIF);
		editText_password
				.setTransformationMethod(new PasswordTransformationMethod());
		
		editText_password.addTextChangedListener(new TextValidator(
				editText_password) {
			@Override
			public void validate(TextView textView, String text) {
				if (text != null && text.length() >= 6) {
					textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getResources().getDrawable(
									R.drawable.ic_device_access_accounts_valid),
							null);
					isPasswordValid = true;
				} else if (text != null && text.length() < 6) {
					textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getResources().getDrawable(
									R.drawable.ic_device_access_accounts_error),
							null);
					isPasswordValid = false;
				} else {
					textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getResources().getDrawable(
									R.drawable.ic_device_access_accounts), null);
					isPasswordValid = false;
				}
			}
		});

		editText_username
				.addTextChangedListener(new TextValidator(editText_username) {
					@Override
					public void validate(TextView textView, String text) {
						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(text);
						if (text != null && matcher.matches()) {
							textView.setCompoundDrawablesWithIntrinsicBounds(
									null,
									null,
									getResources().getDrawable(
											R.drawable.ic_content_email_valid),
									null);
							isEmailValid = true;
						} else if (text != null && !matcher.matches()) {
							textView.setCompoundDrawablesWithIntrinsicBounds(
									null,
									null,
									getResources().getDrawable(
											R.drawable.ic_content_email_error),
									null);
							isEmailValid = false;
						} else {
							textView.setCompoundDrawablesWithIntrinsicBounds(
									null,
									null,
									getResources().getDrawable(
											R.drawable.ic_content_email), null);
							isEmailValid = false;
						}
					}
				});
		
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.registerView(this);
		userModel = AppEventsController.getInstance().getModelFacade().getUserModel();

		// Settings Messages
		Constants.ERROR_NETWORK_PROBLEM = getResources().getString(
				R.string.error_network_problem);
		Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM = getResources()
				.getString(R.string.error_reading_data_from_server);

		btn_signin = (TextView) findViewById(R.id.btn_login);
		btn_signin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				requestConnection(view);
			}
		});

		CheckBox keepMeLoggedIn = (CheckBox) findViewById(R.id.checkbox_rememberme);
		keepMeLoggedIn
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						keepMeLoggedInBool = isChecked;
					}
				});

		// Action on click of Forgot Password Button
		TextView textview_forgotpwd = (TextView) findViewById(R.id.btn_forgotpassword);
		textview_forgotpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LoginActivity.this,
						ForgotPwdActivity.class);
				LoginActivity.this.startActivity(screenChangeIntent);
			}
		});
		
		//Facebook Login Session Requirements
		
		ImageView fb_image_button = (ImageView)findViewById(R.id.imageview_fb_login);
		fb_image_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				onClickLogin();
			}
		});
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
            	session.openForRead(new Session.OpenRequest(this)
                .setPermissions(Arrays.asList("public_profile","email","user_location","user_birthday"))
                .setCallback(statusCallback));
            }
        }
		
		/*ImageView gplus_login_button = (ImageView)findViewById(R.id.imageview_gplus_login);
		gplus_login_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Signin button clicked
	            signInWithGplus();
			}
		});
		
		// Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();*/
	}

	private void requestConnection(View view) {
		username = editText_username.getText()
				.toString();
		password = editText_password.getText()
				.toString();

		if (isEmailValid && isPasswordValid) {
			Bundle eventData = new Bundle();
			eventData.putString(Constants.TEXT_GRANT_TYPE, Constants.TEXT_PASSWORD);
			eventData.putString(Constants.TEXT_USERNAME, username);
			eventData.putString(Constants.TEXT_PASSWORD, password);
			eventData.putString(Constants.TEXT_CLIENT_ID, Constants.OAUTH_CLIENT_ID);
			eventData.putString(Constants.TEXT_CLIENT_SECRET, Constants.OAUTH_CLIENT_SECRET);
			AppEventsController.getInstance().handleEvent(
					NetworkEvents.EVENT_ID_AUTHENTICATE_USER, eventData, view);
		}
	}
	
	@Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
        //mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
        /*if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }*/
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Login Activity", "I am inside onActivityResult");
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        /*else if( isGooglePlusLogin ){
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }
     
            mIntentInProgress = false;
     
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Login Activity", "I am inside onSaveInstanceState");
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }
    
    /*@Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
        	isGooglePlusLogin = false;
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
     
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
     
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
     
    }*/
	
	@Override
	protected void onDestroy() {
		Log.d("LoginActivity", "Inside onDestroy");
		connModel.unregisterView(this);
		super.onDestroy();
	}
	
	/*@Override
	public void onConnected(Bundle arg0) {
	    mSignInClicked = false;
	    Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	 
	    // Get user's information
	    getProfileInformation();
	 
	    // Update the UI after signin
	    updateUI(true);
	 
	}
	 
	@Override
	public void onConnectionSuspended(int arg0) {
	    mGoogleApiClient.connect();
	    updateUI(false);
	}
	 
	*//**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * *//*
	private void updateUI(boolean isSignedIn) {
	    if (isSignedIn) {
	    } else {
	    	isGooglePlusLogin = false;
	    }
	}
	
	*//**
	 * Fetching user's information name, email, profile pic
	 * *//*
	private void getProfileInformation() {
	    try {
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	            Person currentPerson = Plus.PeopleApi
	                    .getCurrentPerson(mGoogleApiClient);
	            String personName = currentPerson.getDisplayName();
	            String personPhotoUrl = currentPerson.getImage().getUrl();
	            String personGooglePlusProfile = currentPerson.getUrl();
	            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	 
	            Log.e("Login Google Plus", "Name: " + personName + ", plusProfile: "
	                    + personGooglePlusProfile + ", email: " + email
	                    + ", Image: " + personPhotoUrl);
	 
	            // by default the profile url gives 50x50 px image only
	            // we can replace the value with whatever dimension we want by
	            // replacing sz=X
	            personPhotoUrl = personPhotoUrl.substring(0,
	                    personPhotoUrl.length() - 2)
	                    + PROFILE_PIC_SIZE;
	 
	            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
	 
	        } else {
	            Toast.makeText(getApplicationContext(),
	                    "Person information is null", Toast.LENGTH_LONG).show();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	*//**
	 * Sign-in into google
	 * *//*
	private void signInWithGplus() {
	    if (!mGoogleApiClient.isConnecting()) {
	        mSignInClicked = true;
	        resolveSignInError();
	    }
	}
	 
	*//**
	 * Method to resolve any signin errors
	 * *//*
	private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	        try {
	            mIntentInProgress = true;
	            mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	        } catch (SendIntentException e) {
	            mIntentInProgress = false;
	            mGoogleApiClient.connect();
	        }
	    }
	}
*/
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
	
	private void updateView(Session session, SessionState state, Exception exception) {
        if (session.isOpened()) {
        	Log.d("Login Activity", "Login successull");
        	final String accessToken = session.getAccessToken();
        	Request.newMeRequest(session, new Request.GraphUserCallback()
            {
                @Override
                public void onCompleted(GraphUser user, Response response)
                {
                    if (response != null)
                    {
                        try
                        {
                            String name = user.getName();
                            String birthdate = user.getBirthday();
                            String city = (String) user.getLocation().getProperty("name");
                            String email = (String) user.getProperty("email");
                            userModel.setUser_name(name);
                            userModel.setUser_email(email);
                            userModel.setUser_currentCity(city);
                            userModel.setUser_dob(birthdate);
                            SharedPreferences sharedPref = getSharedPreferences(
            						Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
            				SharedPreferences.Editor editor = sharedPref.edit();
            				editor.putString(Constants.TEXT_ACCESSTOKEN, accessToken);
            				editor.putInt(Constants.TEXT_USER_LOGGEDIN_STATUS, UserModel.USER_FB_LOGGEDIN);
            				editor.commit();
            				connModel.unregisterView(LoginActivity.this);
            				LoginActivity.this.finish();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Log.d("Facebook Exception", e.toString());
                        }

                    }
                }
            }).executeAsync();
        } else {
            Log.d("Login Activity", "Logout successfull");
        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
        	session.openForRead(new Session.OpenRequest(this)
            .setPermissions(Arrays.asList("public_profile","email","user_location","user_birthday"))
            .setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView(session, state, exception);
        }
    }

	@Override
	public void updateActivity() {
		switch (connModel.getConnectionStatus()) {
		case ConnectionModel.SUCCESS: {
			Log.d("LoginActivity", "Inside onConnection");

			if (keepMeLoggedInBool) {
				SharedPreferences sharedPref = getSharedPreferences(
						Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(Constants.TEXT_ACCESSTOKEN, AppEventsController
						.getInstance().getModelFacade().getUserModel()
						.getAccessToken());
				editor.commit();
			}
			connModel.unregisterView(this);
			LoginActivity.this.finish();
		}
			break;
		case ConnectionModel.ERROR: {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					LoginActivity.this);
			builder.setTitle(getResources().getString(R.string.error));
			builder.setMessage(connModel.getConnectionErrorMessage());
			builder.setCancelable(false);
			builder.setPositiveButton(getResources().getString(R.string.OK),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
			break;
		}
	}

}
