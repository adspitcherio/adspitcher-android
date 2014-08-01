package com.adspitcher.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adspitcher.R;

public class OffersInfoFragment extends Fragment{
	
	private TextView thumbsupView;
	private TextView thumbsdownView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_offerinfo, container, false);
		RelativeLayout thumbsupLayout = (RelativeLayout)view.findViewById(R.id.relativelayout_thumbsup);
		thumbsupView = (TextView)view.findViewById(R.id.textview_thumbsup);
		thumbsupLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				int count = Integer.parseInt(thumbsupView.getText().toString());
				count++;
				thumbsupView.setText(""+count);
			}
		});
		RelativeLayout thumbsdownLayout = (RelativeLayout)view.findViewById(R.id.relativelayout_thumbsdown);
		thumbsdownView = (TextView)view.findViewById(R.id.textview_thumbsdown);
		thumbsdownLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				int count = Integer.parseInt(thumbsdownView.getText().toString());
				count--;
				thumbsdownView.setText(""+count);
			}
		});
		return view;
	}
}
