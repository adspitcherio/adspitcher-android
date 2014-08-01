package com.adspitcher.views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.adspitcher.utils.TextValidator;

public class SignupActivity extends ActionBarActivity implements
		ActivityUpdateListener {

	private String username, email, password;
	private ConnectionModel connModel;
	private TextView btn_submit;
	private EditText edittext_username, edittext_email, edittext_password;
	private boolean isUsernameValid, isEmailValid, isPasswordValid;
	String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		// connModel.setListener(this);
		// connModel.registerView(AppEventsController.getInstance().getActivityUpdateListener());
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

		edittext_username = (EditText) findViewById(R.id.edittext_name);
		edittext_username.addTextChangedListener(new TextValidator(
				edittext_username) {
			@Override
			public void validate(TextView textView, String text) {
				if (text != null && text.length() > 2) {
					textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getResources().getDrawable(
									R.drawable.ic_social_person_valid), null);
					isUsernameValid = true;
				} else if (text != null && !text.matches("[a-zA-Z ]+")) {
					textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getResources().getDrawable(
									R.drawable.ic_social_person_error), null);
					isUsernameValid = false;
				} else {
					textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							getResources().getDrawable(
									R.drawable.ic_social_person), null);
					isUsernameValid = false;
				}
			}
		});

		edittext_password = (EditText) findViewById(R.id.edittext_signup_password);
		edittext_password.addTextChangedListener(new TextValidator(
				edittext_password) {
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

		edittext_email = (EditText) findViewById(R.id.edittext_email);
		edittext_email
				.addTextChangedListener(new TextValidator(edittext_email) {
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
		 .toString(); password = ((EditText)
		 findViewById(R.id.edittext_signup_password)) .getText().toString();
		 email = ((EditText) findViewById(R.id.edittext_email)).getText()
		 .toString();
		 
		if( isUsernameValid && isPasswordValid && isEmailValid ){
			Bundle eventData = new Bundle();
			eventData.putString(Constants.TEXT_CONSUMER_NAME, username);
			eventData.putString(Constants.TEXT_CONSUMER_PASSWORD, password);
			eventData.putString(Constants.TEXT_CONSUMER_EMAIL, email);
			eventData.putString(Constants.TEXT_CONSUMER_CURRENT_LOCATION_LATITUDE, email);
			eventData.putString(Constants.TEXT_CONSUMER_CURRENT_LOCATION_LONGITUDE, email);
	
			AppEventsController.getInstance().handleEvent(
					NetworkEvents.EVENT_ID_AUTHORIZE_USER, eventData, view);
		}
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
			connModel.unregisterView(this);
			SignupActivity.this.finish();
		}
			break;
		case ConnectionModel.ERROR: {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SignupActivity.this);
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