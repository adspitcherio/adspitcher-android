package com.adspitcher.views;

import com.adspitcher.R;
import com.adspitcher.adapters.OffersFragmentAdapter;
import com.adspitcher.dataobjects.OffersItem;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

public class SearchActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
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
		
		ListView listView_search_offers_items = (ListView)findViewById(R.id.listview_searchoffers);
		
		OffersItem[] offersDataItems = new OffersItem[10];
		for (int i = 0; i < 10; i++) {
			offersDataItems[i] = new OffersItem("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "Pantaloons", "Gurgaon | Mumbai", 10, 10, 10, 10);
		}
		
		OffersFragmentAdapter adapter = new OffersFragmentAdapter(this, R.layout.activity_search, offersDataItems);
		listView_search_offers_items.setAdapter(adapter);
	}

}
