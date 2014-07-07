package com.adspitcher.adapters;

import com.adspitcher.fragments.PreferencesFragment;
import com.adspitcher.fragments.ReviewsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProfilePagerAdapter extends FragmentPagerAdapter{

	public ProfilePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
            return new ReviewsFragment();
        case 1:
        	return new PreferencesFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
