package com.adspitcher.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;

public class LoginActivity extends ActionBarActivity implements ActivityUpdateListener {

	private TextView btn_signin;
	private String username, password;
	private boolean keepMeLoggedInBool;
	private ConnectionModel connModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		EditText editText_password = (EditText) findViewById(R.id.edittext_password);
		editText_password.setTypeface(Typeface.SERIF);
		editText_password
				.setTransformationMethod(new PasswordTransformationMethod());

		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		//connModel.setListener(this);
		//connModel.registerView(AppEventsController.getInstance().getActivityUpdateListener());
		connModel.registerView(this);

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
	}

	private void requestConnection(View view) {
		username = ((EditText) findViewById(R.id.edittext_username)).getText()
				.toString();
		password = ((EditText) findViewById(R.id.edittext_password)).getText()
				.toString();

		if (validateEnteredData(username, password)) {
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

	private boolean validateEnteredData(String name, String pwd) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if ((name == null || name.equals(""))
				|| (pwd == null || pwd.equals(""))) {
			builder.setTitle(R.string.text_error);
			builder.setMessage(R.string.text_valid_credentials);
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
			return false;
		}
		return true;
	}
	
	@Override
	protected void onPause() {
		Log.d("LoginActivity", "Inside onPause");
		connModel.unregisterView(this);
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("LoginActivity", "Inside onDestroy");
		connModel.unregisterView(this);
		super.onDestroy();
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
			Intent screenChangeIntent = null;
			screenChangeIntent = new Intent(LoginActivity.this,
					LaunchActivity.class);
			LoginActivity.this.startActivity(screenChangeIntent);
			LoginActivity.this.finish();
		}
			break;
		case ConnectionModel.ERROR: {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					LoginActivity.this);
			builder.setTitle(getResources().getString(R.string.text_error));
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
