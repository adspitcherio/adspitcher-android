package com.adspitcher.fragments;

import com.adspitcher.R;
import com.adspitcher.adapters.FiltersAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PreferencesFragment extends Fragment{
	private ListView listView_location_filters, listView_brands_filters,
	listView_categories_filters;
private FiltersAdapter adapter, brands_adapter, categories_adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_preferences, container, false);
		listView_location_filters = (ListView) view.findViewById(R.id.listView_filters_locations);

		String[] location_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			location_filters[i] = new String("Lorem Ipsum");
		}

		adapter = new FiltersAdapter(getActivity(), R.layout.activity_filter,
				location_filters);
		listView_location_filters.setAdapter(adapter);

		/*listView_location_filters
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						FiltersAdapter ad = (FiltersAdapter) parent
								.getAdapter();
						ad.setCheckedItem(view,position);
					}
				});*/

		listView_brands_filters = (ListView) view.findViewById(R.id.listView_filters_brands);

		String[] brands_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			brands_filters[i] = new String("Lorem Ipsum");
		}

		brands_adapter = new FiltersAdapter(getActivity(), R.layout.activity_filter,
				brands_filters);
		listView_brands_filters.setAdapter(brands_adapter);

		/*listView_brands_filters
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						FiltersAdapter ad = (FiltersAdapter) parent
								.getAdapter();
						ad.setCheckedItem(view,position);
					}
				});*/

		listView_categories_filters = (ListView) view.findViewById(R.id.listView_filters_categories);

		String[] categories_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			categories_filters[i] = new String("Lorem Ipsum");
		}

		categories_adapter = new FiltersAdapter(getActivity(), R.layout.activity_filter,
				categories_filters);
		listView_categories_filters.setAdapter(categories_adapter);

		/*listView_categories_filters
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						FiltersAdapter ad = (FiltersAdapter) parent
								.getAdapter();
						ad.setCheckedItem(view,position);
					}
				});*/
		return view;
	}

}
