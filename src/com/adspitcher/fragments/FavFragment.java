package com.adspitcher.fragments;

import com.adspitcher.R;
import com.adspitcher.adapters.OffersFragmentAdapter;
import com.adspitcher.dataobjects.OffersItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FavFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_fav, container, false);
		
		ListView listView_fav_offers_items = (ListView)view.findViewById(R.id.listview_favoffers);
		
		OffersItem[] offersDataItems = new OffersItem[10];
		for (int i = 0; i < 10; i++) {
			offersDataItems[i] = new OffersItem("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "Pantaloons", "Gurgaon | Mumbai", 10, 10, 10, 10);
		}
		
		OffersFragmentAdapter adapter = new OffersFragmentAdapter(view.getContext(), R.layout.fragment_fav, offersDataItems);
		listView_fav_offers_items.setAdapter(adapter);
		
		return view;
	}
}
