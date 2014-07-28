package com.adspitcher.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.models.UserModel;

public class ProfileInfoFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profileinfo, container, false);
		
		UserModel userModel = AppEventsController.getInstance().getModelFacade().getUserModel();
		
		TextView txtView_name = (TextView)view.findViewById(R.id.textview_name_value);
		txtView_name.setText(userModel.getUser_name());
		
		TextView txtView_email = (TextView)view.findViewById(R.id.textview_email_value);
		txtView_email.setText(userModel.getUser_email());
		
		TextView txtView_dob = (TextView)view.findViewById(R.id.textview_dob_value);
		txtView_dob.setText(userModel.getUser_dob());
		
		TextView txtView_number = (TextView)view.findViewById(R.id.textview_phonenumber_value);
		txtView_number.setText(""+userModel.getUser_phonenumber());
		
		TextView txtView_credits = (TextView)view.findViewById(R.id.textview_credits_value);
		txtView_credits.setText(""+userModel.getUser_credits());
		
		TextView txtView_city = (TextView)view.findViewById(R.id.textview_currentlocation_value);
		txtView_city.setText(userModel.getUser_currentCity());
		
		return view;
	}
}