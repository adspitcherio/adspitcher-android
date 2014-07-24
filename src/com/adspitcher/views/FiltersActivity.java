package com.adspitcher.views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.listeners.ConnListener;
import com.adspitcher.models.ConnectionModel;

public class FiltersActivity extends ActionBarActivity implements ConnListener {

	private ListView listView_location_filters, listView_brands_filters,
			listView_categories_filters;
	private FiltersAdapter adapter, brands_adapter, categories_adapter;
	private ConnectionModel connModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_filter);
		
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.setListener(this);
		connModel.registerView(AppEventsController.getInstance()
				.getActivityUpdateListener());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// get the parent view of home (app icon) imageview
			ViewGroup home = (ViewGroup) findViewById(android.R.id.home)
					.getParent();
			// get the first child (up imageview)
			((ImageView) home.getChildAt(0))
			// change the icon according to your needs
					.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_remove));
		} else {
			// get the up imageview directly with R.id.up
			((ImageView) findViewById(R.id.up)).setImageDrawable(getResources()
					.getDrawable(R.drawable.ic_action_content_remove));
		}

		listView_location_filters = (ListView) findViewById(R.id.listView_filters_locations);

		String[] location_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			location_filters[i] = new String("Lorem Ipsum");
		}

		adapter = new FiltersAdapter(this, R.layout.activity_filter,
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

		listView_brands_filters = (ListView) findViewById(R.id.listView_filters_brands);

		String[] brands_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			brands_filters[i] = new String("Lorem Ipsum");
		}

		brands_adapter = new FiltersAdapter(this, R.layout.activity_filter,
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

		listView_categories_filters = (ListView) findViewById(R.id.listView_filters_categories);

		String[] categories_filters = new String[5];
		for (int i = 0; i < 5; i++) {
			categories_filters[i] = new String("Lorem Ipsum");
		}

		categories_adapter = new FiltersAdapter(this, R.layout.activity_filter,
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
		case R.id.action_apply: {
			HashMap<String, String> locations = adapter.getCheckedItems();
			HashMap<String, String> businesses = brands_adapter.getCheckedItems();
			HashMap<String, String> categories = categories_adapter.getCheckedItems();
			Bundle eventData = new Bundle();
			String[] tempData = new String[locations.size()];
			int i = 0;
			Set<String> keys = locations.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				tempData[i] = (String) iterator.next();
				i++;
			}
			eventData.putStringArray(Constants.TEXT_LOCATIONS, tempData);
			
			tempData = new String[businesses.size()];
			i = 0;
			keys = businesses.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				tempData[i] = (String) iterator.next();
				i++;
			}
			eventData.putStringArray(Constants.TEXT_BUSINESSES, tempData);
			
			tempData = new String[categories.size()];
			i = 0;
			keys = businesses.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				tempData[i] = (String) iterator.next();
				i++;
			}
			eventData.putStringArray(Constants.TEXT_CATEGORIES, tempData);
			
			/*AppEventsController.getInstance().handleEvent(
					NetworkEvents.EVENT_ID_GET_FILTERED_OFFERS, eventData, listView_location_filters);*/

			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConnection() {
		switch (connModel.getConnectionStatus()) {
		case ConnectionModel.SUCCESS: {
			FiltersActivity.this.finish();
		}
			break;
		case ConnectionModel.ERROR: {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					FiltersActivity.this);
			builder.setTitle(getResources().getString(R.string.text_error));
			builder.setMessage(connModel.getConnectionErrorMessage());
			builder.setCancelable(false);
			builder.setPositiveButton(getResources().getString(R.string.OK),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
			break;
		}
	}
}
