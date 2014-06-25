package com.adspitcher.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.adspitcher.R;

public class LaunchActivity extends Activity {

	// Time in Milliseconds
	private int BENEFITS_TIMER = 2000;
	private int benefitCounter = 0;
	private TextView txtView_benefits;
	private String[] benefits;
	private int[] benefitsDrawableList = new int[] {
			R.drawable.ic_notification_white, R.drawable.ic_rewards_icon,
			R.drawable.ic_follow_white };
	private Resources res;
	private Runnable benefitsRunnable;
	private long mStartTime;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_launch);

		txtView_benefits = (TextView) findViewById(R.id.textview_benefits);

		res = getResources();
		benefits = res.getStringArray(R.array.benefits_array);
		txtView_benefits.setText(benefits[benefitCounter]);
		txtView_benefits.setCompoundDrawablesWithIntrinsicBounds(
				res.getDrawable(benefitsDrawableList[benefitCounter]), null,
				null, null);

		benefitsRunnable = new Runnable() {

			@Override
			public void run() {
				Log.d("Launch Activity", "Inside Counter");
				final long start = mStartTime;
				long millis = SystemClock.uptimeMillis() - start;
				int seconds = (int) (millis / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;
				txtView_benefits.setText(benefits[benefitCounter]);
				txtView_benefits.setCompoundDrawablesWithIntrinsicBounds(
						res.getDrawable(benefitsDrawableList[benefitCounter]),
						null, null, null);
				mStartTime = start + (((minutes * 60) + seconds + 5) * 1000);
				mHandler.postAtTime(this, mStartTime);
				if (benefitCounter < 3) {
					benefitCounter++;
					if (benefitCounter == 3)
						benefitCounter = 0;
				}
			}
		};

		// New Handler to switch signup benefits
		mHandler.postDelayed(benefitsRunnable, BENEFITS_TIMER);

		// Action on click of Create An Account Button
		TextView textview_createaccount = (TextView) findViewById(R.id.textview_createaccount);
		textview_createaccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LaunchActivity.this,
						SignupActivity.class);
				LaunchActivity.this.startActivity(screenChangeIntent);
			}
		});
		
		// Action on click of Create An Account Button
		TextView textview_login = (TextView) findViewById(R.id.textview_login);
		textview_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(LaunchActivity.this,
						LoginActivity.class);
				LaunchActivity.this.startActivity(screenChangeIntent);
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
}
