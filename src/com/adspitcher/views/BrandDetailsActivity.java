package com.adspitcher.views;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adspitcher.R;
import com.adspitcher.adapters.BrandsPagerAdapter;

public class BrandDetailsActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private BrandsPagerAdapter mAdapter;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offersdetails);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// get the parent view of home (app icon) imageview
			ViewGroup home = (ViewGroup) findViewById(android.R.id.home)
					.getParent();
			// get the first child (up imageview)
			ImageView upImgView = (ImageView) home.getChildAt(0);
			// change the icon according to your needs
			upImgView.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_action_navigation_previous_item));
		} else {
			// get the up imageview directly with R.id.up
			((ImageView) findViewById(R.id.up))
					.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_navigation_previous_item));
		}

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.offersdetails_pager);
		actionBar = getSupportActionBar();
		mAdapter = new BrandsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Resources itemTexts = getResources();
		String[] tabs = itemTexts.getStringArray(R.array.offers_tabs_array);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_offer_details, menu);
        
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}
