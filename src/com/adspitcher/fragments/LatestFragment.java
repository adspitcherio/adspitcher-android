package com.adspitcher.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.adspitcher.R;
import com.adspitcher.adapters.OffersFragmentAdapter;
import com.adspitcher.constants.Constants;
import com.adspitcher.dataobjects.OffersItem;
import com.adspitcher.views.LaunchActivity;
import com.adspitcher.views.OffersDetailsActivity;
import com.adspitcher.views.SplashActivity;

public class LatestFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_latest, container, false);
		
		ListView listView_latest_offers_items = (ListView)view.findViewById(R.id.listview_latestoffers);
		
		OffersItem[] offersDataItems = new OffersItem[10];
		for (int i = 0; i < 10; i++) {
			offersDataItems[i] = new OffersItem("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "Pantaloons", "Gurgaon | Mumbai", 10, 10, 10, 10);
		}
		
		OffersFragmentAdapter adapter = new OffersFragmentAdapter(view.getContext(), R.layout.fragment_latest, offersDataItems);
		listView_latest_offers_items.setAdapter(adapter);
		
		listView_latest_offers_items.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent screenChangeIntent = null;
				screenChangeIntent = new Intent(getActivity(),
						OffersDetailsActivity.class);
				startActivity(screenChangeIntent);
			}
		});
		
		return view;
	}

}

