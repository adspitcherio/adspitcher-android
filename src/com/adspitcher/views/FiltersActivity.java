package com.adspitcher.views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.adspitcher.R;
import com.adspitcher.adapters.FiltersAdapter;

public class FiltersActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_filter);

		ListView listView_location_filters = (ListView) findViewById(R.id.listView_filters_locations);

		String[] location_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			location_filters[i] = new String("Lorem Ipsum");
		}

		FiltersAdapter adapter = new FiltersAdapter(this,
				R.layout.activity_filter, location_filters);
		listView_location_filters.setAdapter(adapter);

		ListView listView_brands_filters = (ListView) findViewById(R.id.listView_filters_brands);

		String[] brands_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			brands_filters[i] = new String("Lorem Ipsum");
		}

		FiltersAdapter brands_adapter = new FiltersAdapter(this,
				R.layout.activity_filter, brands_filters);
		listView_brands_filters.setAdapter(brands_adapter);

		ListView listView_categories_filters = (ListView) findViewById(R.id.listView_filters_categories);

		String[] categories_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			categories_filters[i] = new String("Lorem Ipsum");
		}

		FiltersAdapter categories_adapter = new FiltersAdapter(this,
				R.layout.activity_filter, categories_filters);
		listView_categories_filters.setAdapter(categories_adapter);
	}
}
