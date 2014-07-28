package com.adspitcher.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adspitcher.R;
import com.adspitcher.adapters.FiltersAdapter;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.models.LocalModel;

public class PreferencesFragment extends Fragment{
	private ListView listView_location_filters, listView_brands_filters,
	listView_categories_filters;
private FiltersAdapter adapter, brands_adapter, categories_adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_preferences, container, false);
		
		setHasOptionsMenu(true);
		LocalModel localModel = AppEventsController.getInstance().getModelFacade().getLocalModel();
		
		listView_location_filters = (ListView) view.findViewById(R.id.listView_filters_locations);
		String[] location_filters = localModel.getLocations();

		adapter = new FiltersAdapter(getActivity(), R.layout.activity_filter,
				location_filters);
		listView_location_filters.setAdapter(adapter);

		listView_brands_filters = (ListView) view.findViewById(R.id.listView_filters_brands);
		String[] brands_filters = localModel.getBrands();

		brands_adapter = new FiltersAdapter(getActivity(), R.layout.activity_filter,
				brands_filters);
		listView_brands_filters.setAdapter(brands_adapter);

		listView_categories_filters = (ListView) view.findViewById(R.id.listView_filters_categories);
		String[] categories_filters = localModel.getCategories();

		categories_adapter = new FiltersAdapter(getActivity(), R.layout.activity_filter,
				categories_filters);
		listView_categories_filters.setAdapter(categories_adapter);
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_profile_save, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_savepreferences: {
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
