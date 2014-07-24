package com.adspitcher.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.adspitcher.R;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.models.UserModel;

public class SplashActivity extends FragmentActivity{

	// Time in Milliseconds
	private int SPLASH_TIMER = 2000;
	private String accessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setContentView(R.layout.activity_splash);

		SharedPreferences sharedPref = getSharedPreferences(
				Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
		accessToken = sharedPref.getString(Constants.TEXT_ACCESSTOKEN,
				Constants.TEXT_DATABASE_ACCESS_VALUE_DEFAULT);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!accessToken.equals(Constants.TEXT_DATABASE_ACCESS_VALUE_DEFAULT)) {
					UserModel userModel = AppEventsController
							.getInstance().getModelFacade()
							.getUserModel();
					userModel.setUserLoggedIn(true);
					Intent screenChangeIntent = null;
					screenChangeIntent = new Intent(SplashActivity.this,
							HomeActivity.class);
					SplashActivity.this.startActivity(screenChangeIntent);
					SplashActivity.this.finish();
				} else {
					UserModel userModel = AppEventsController
							.getInstance().getModelFacade()
							.getUserModel();
					userModel.setUserLoggedIn(false);
					Intent screenChangeIntent = null;
					screenChangeIntent = new Intent(SplashActivity.this,
							LaunchActivity.class);
					SplashActivity.this.startActivity(screenChangeIntent);
					SplashActivity.this.finish();
				}
			}
		}, SPLASH_TIMER);

	}
}
