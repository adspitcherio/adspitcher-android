package com.adspitcher.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.adspitcher.R;

public class LoginActivity extends Activity{
	
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
		
		//Action on click of Create An Account Button
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

}
