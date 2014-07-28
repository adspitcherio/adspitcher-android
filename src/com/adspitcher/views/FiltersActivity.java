package com.adspitcher.views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.adspitcher.R;
import com.adspitcher.adapters.FiltersAdapter;
import com.adspitcher.constants.Constants;
import com.adspitcher.controllers.AppEventsController;
import com.adspitcher.listeners.ActivityUpdateListener;
import com.adspitcher.models.ConnectionModel;
import com.adspitcher.models.LocalModel;

public class FiltersActivity extends ActionBarActivity implements ActivityUpdateListener {

	private ListView listView_location_filters, listView_brands_filters,
			listView_categories_filters;
	private FiltersAdapter adapter, brands_adapter, categories_adapter;
	private ConnectionModel connModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_filter);
		
		connModel = AppEventsController.getInstance().getModelFacade()
				.getConnModel();
		connModel.registerView(this);
		
		LocalModel localModel = AppEventsController.getInstance().getModelFacade().getLocalModel();

		listView_location_filters = (ListView) findViewById(R.id.listView_filters_locations);
		String[] location_filters = localModel.getLocations();

		adapter = new FiltersAdapter(this, R.layout.activity_filter,
				location_filters);
		listView_location_filters.setAdapter(adapter);
		
		listView_brands_filters = (ListView) findViewById(R.id.listView_filters_brands);

		String[] brands_filters = localModel.getBrands();
		brands_adapter = new FiltersAdapter(this, R.layout.activity_filter,
				brands_filters);
		listView_brands_filters.setAdapter(brands_adapter);

		listView_categories_filters = (ListView) findViewById(R.id.listView_filters_categories);

		String[] categories_filters = localModel.getCategories();

		categories_adapter = new FiltersAdapter(this, R.layout.activity_filter,
				categories_filters);
		listView_categories_filters.setAdapter(categories_adapter);
		
		// Action on click of Cancel Button
		TextView button_cancel = (TextView) findViewById(R.id.button_cancel);
		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		// Action on click of Apply Button
		TextView button_apply = (TextView) findViewById(R.id.button_apply);
		button_apply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				HashMap<String, String> locations = adapter.getCheckedItems();
				HashMap<String, String> businesses = brands_adapter.getCheckedItems();
				HashMap<String, String> categories = categories_adapter.getCheckedItems();
				Bundle eventData = new Bundle();
				
				String[] tempData = null;
				int i = 0;
				Set<String> keys = null;
				
				if( locations.size() > 0 ){
					tempData = new String[locations.size()];
					i = 0;
					keys = locations.keySet();
					for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
						tempData[i] = (String) iterator.next();
						i++;
					}
					eventData.putStringArray(Constants.TEXT_LOCATIONS, tempData);
				}
				
				if( businesses.size() > 0 ){
					tempData = new String[businesses.size()];
					i = 0;
					keys = businesses.keySet();
					for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
						tempData[i] = (String) iterator.next();
						i++;
					}
					eventData.putStringArray(Constants.TEXT_BRANDS, tempData);
				}
				
				if( categories.size() > 0 ){
					tempData = new String[categories.size()];
					i = 0;
					keys = businesses.keySet();
					for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
						tempData[i] = (String) iterator.next();
						i++;
					}
					eventData.putStringArray(Constants.TEXT_CATEGORIES, tempData);
				}
				finish();
				/*AppEventsController.getInstance().handleEvent(
						NetworkEvents.EVENT_ID_GET_FILTERED_OFFERS, eventData, listView_location_filters);*/
			}
		});
	}

	@Override
	public void updateActivity() {
		switch (connModel.getConnectionStatus()) {
		case ConnectionModel.SUCCESS: {
			FiltersActivity.this.finish();
		}
			break;
		case ConnectionModel.ERROR: {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					FiltersActivity.this);
			builder.setTitle(getResources().getString(R.string.error));
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
