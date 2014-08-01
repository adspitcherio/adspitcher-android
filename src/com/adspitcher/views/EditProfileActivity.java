package com.adspitcher.views;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.dialogfragments.ErrorDialog;
import com.adspitcher.dialogfragments.ErrorDialog.NoticeDialogListener;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;

public class EditProfileActivity extends FragmentActivity implements ActivityUpdateListener, NoticeDialogListener {
	
	private ConnectionModel connModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_edit_profile);
		
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.registerView(this);

		// Action on click of Cancel Button
		TextView button_cancel = (TextView) findViewById(R.id.button_cancel);
		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		// Action on click of Save Button
		TextView button_save = (TextView) findViewById(R.id.button_save);
		button_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
			}
		});
	}

	@Override
	public void updateActivity() {
		switch (connModel.getConnectionStatus()) {
		case ConnectionModel.SUCCESS: {
			ErrorDialog dialog = new ErrorDialog(getResources().getString(R.string.success), getResources().getString(R.string.text_editprofile_editsuccess), getResources().getString(R.string.OK), "");
			dialog.show(getSupportFragmentManager(), "Success");
		}
			break;
		case ConnectionModel.ERROR: {
			ErrorDialog dialog = new ErrorDialog(getResources().getString(R.string.error), connModel.getConnectionErrorMessage(), getResources().getString(R.string.OK), "");
			dialog.show(getSupportFragmentManager(), "Error");
		}
			break;
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}
}
