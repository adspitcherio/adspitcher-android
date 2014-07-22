package com.adspitcher.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.defines.NetworkEvents;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;

public class SignupActivity extends ActionBarActivity implements ActivityUpdateListener {

	private String username, email, password;
	private ConnectionModel connModel;
	private TextView btn_submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		//connModel.setListener(this);
		//connModel.registerView(AppEventsController.getInstance().getActivityUpdateListener());
		connModel.registerView(this);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		btn_submit = (TextView) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				requestConnection(view);
			}
		});

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

	private void requestConnection(View view) {
		username = ((EditText) findViewById(R.id.edittext_name)).getText()
				.toString();
		password = ((EditText) findViewById(R.id.edittext_signup_password))
				.getText().toString();
		email = ((EditText) findViewById(R.id.edittext_email)).getText()
				.toString();

		if (validateEnteredData(username, password, email)) {
			Bundle eventData = new Bundle();
			eventData.putString(Constants.TEXT_USERNAME, username);
			eventData.putString(Constants.TEXT_PASSWORD, password);
			eventData.putString(Constants.TEXT_EMAIL, email);

			AppEventsController.getInstance().handleEvent(
					NetworkEvents.EVENT_ID_AUTHORIZE_USER, eventData, view);
		}
	}

	private boolean validateEnteredData(String name, String pwd, String email) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if ((name == null || name.equals(""))
				|| (pwd == null || pwd.equals(""))
				|| (email == null || email.equals(""))) {
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
		Log.d("SignupActivity", "Inside onDestroy");
		connModel.unregisterView(this);
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("SignupActivity", "Inside onDestroy");
		connModel.unregisterView(this);
		super.onDestroy();
	}

	@Override
	public void updateActivity() {
		switch (connModel.getConnectionStatus()) {
		case ConnectionModel.SUCCESS: {
			Log.d("SignupActivity", "Inside onConnection");
			SharedPreferences sharedPref = getSharedPreferences(
					Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(Constants.TEXT_ACCESSTOKEN, AppEventsController
					.getInstance().getModelFacade().getUserModel()
					.getAccessToken());
			editor.commit();
			Intent screenChangeIntent = null;
			screenChangeIntent = new Intent(SignupActivity.this,
					LaunchActivity.class);
			SignupActivity.this.startActivity(screenChangeIntent);
			SignupActivity.this.finish();
		}
			break;
		case ConnectionModel.ERROR: {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SignupActivity.this);
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