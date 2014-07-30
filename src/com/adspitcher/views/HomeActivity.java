package com.adspitcher.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.adspitcher.R;
import com.adspitcher.adapters.TabsPagerAdapter;

public class HomeActivity extends BaseActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
    private View activityView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// don’t set any content view here, since its already set in DrawerActivity
		   FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
		    // inflate the custom activity layout
		    LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    activityView = layoutInflater.inflate(R.layout.activity_home, null,false);
		    // add the custom layout of this activity to frame layout.
		    frameLayout.addView(activityView);

		// Initilization
		viewPager = (ViewPager) activityView.findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Resources itemTexts = getResources();
		String[] tabs = itemTexts.getStringArray(R.array.home_tabs_array);

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
        inflater.inflate(R.menu.activity_home_menu, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (super.onOptionsItemSelected(item)) {
           return true;
       }
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search: {
			Intent screenChangeIntent = null;
			screenChangeIntent = new Intent(HomeActivity.this,
					SearchActivity.class);
			HomeActivity.this.startActivity(screenChangeIntent);
			return true;
		}
		case R.id.action_filter: {
			Intent screenChangeIntent = null;
			screenChangeIntent = new Intent(HomeActivity.this,
					FiltersActivity.class);
			HomeActivity.this.startActivity(screenChangeIntent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
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
