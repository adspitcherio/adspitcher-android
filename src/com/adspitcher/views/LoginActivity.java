package com.adspitcher.views;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

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
	}

}
