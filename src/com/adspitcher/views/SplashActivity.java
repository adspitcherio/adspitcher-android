package com.adspitcher.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.adspitcher.R;

public class SplashActivity extends Activity {

	// Time in Milliseconds
	private int SPLASH_TIMER = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);

		// New Handler to start new Activity and close this Splash after few
		// seconds
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(SplashActivity.this,
						LaunchActivity.class);
				SplashActivity.this.startActivity(screenChangeIntent);
				SplashActivity.this.finish();
			}
		}, SPLASH_TIMER);
	}

}
