package com.adspitcher.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.listeners.ConnListener;
import com.adspitcher.models.ConnectionModel;

public class LoginActivity extends Activity implements ConnListener {

	private Button btn_signin;
	private String username, password;
	private boolean keepMeLoggedInBool;
	private ConnectionModel connModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_login);

		EditText password = (EditText) findViewById(R.id.edittext_password);
		password.setTypeface(Typeface.SERIF);
		password.setTransformationMethod(new PasswordTransformationMethod());

		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.setConnectionStatus(ConnectionModel.START_CONN);
		connModel.setListener(this);
		connModel.registerView(AppEventsController.getInstance()
				.getActivityUpdateListener());

		// Settings Messages
		Constants.ERROR_NETWORK_PROBLEM = getResources().getString(
				R.string.error_network_problem);
		Constants.ERROR_READING_DATA_FROM_SERVER_PROBLEM = getResources()
				.getString(R.string.error_reading_data_from_server);

		btn_signin = (Button) findViewById(R.id.btn_login);
		btn_signin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				requestConnection(view);
			}
		});

		// Action on click of Create An Account Button
		TextView textview_createaccount = (TextView) findViewById(R.id.textview_createaccount);
		textview_createaccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LoginActivity.this,
						SignupActivity.class);
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
			eventData.putString(Constants.TEXT_USERNAME, username);
			eventData.putString(Constants.TEXT_PASSWORD, password);
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
	protected void onResume() {
		super.onResume();
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.setListener(this);
		connModel.setConnectionStatus(ConnectionModel.START_CONN);
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
	public void onConnection() {
		switch (connModel.getConnectionStatus()) {
		case ConnectionModel.LOGGED_IN: {
			Intent screenChangeIntent = null;
			screenChangeIntent = new Intent(LoginActivity.this,
					HomeActivity.class);
			LoginActivity.this.startActivity(screenChangeIntent);
			LoginActivity.this.finish();
			/*if (keepMeLoggedInBool) {
				SharedPreferences sharedPref = getSharedPreferences(
						Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(Constants.TEXT_USERNAME, username);
				editor.putString(Constants.TEXT_PASSWORD, password);
				editor.commit();
			}
			// Hit to fetch families data
			AppEventsController.getInstance()
					.handleEvent(NetworkEvents.EVENT_ID_GET_LATEST_OFFERS, null,
							this.btn_signin);*/
		}
			break;
		case ConnectionModel.GOT_ERROR: {
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
