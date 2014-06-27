package com.adspitcher.views;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.adspitcher.R;
import com.adspitcher.adapters.FiltersAdapter;

public class FiltersActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_filter);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
		    // get the parent view of home (app icon) imageview
		    ViewGroup home = (ViewGroup) findViewById(android.R.id.home).getParent();
		    // get the first child (up imageview)
		    ( (ImageView) home.getChildAt(0) )
		        // change the icon according to your needs
		        .setImageDrawable(getResources().getDrawable(R.drawable.ic_action_content_remove));
		} else {
		    // get the up imageview directly with R.id.up
		    ( (ImageView) findViewById(R.id.up) )
		        .setImageDrawable(getResources().getDrawable(R.drawable.ic_action_content_remove));
		} 

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_filter_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_apply:{
	            return true;
	        }
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
