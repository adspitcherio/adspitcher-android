package com.adspitcher.views;

import com.adspitcher.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SignupActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		Intent screenChangeIntent = null;
		screenChangeIntent = new Intent(SignupActivity.this,
				HomeActivity.class);
		SignupActivity.this.startActivity(screenChangeIntent);
		SignupActivity.this.finish();
	}

}
