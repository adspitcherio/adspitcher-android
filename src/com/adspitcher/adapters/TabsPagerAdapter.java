package com.adspitcher.adapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adspitcher.fragments.FavFragment;
import com.adspitcher.fragments.FeedsFragment;
import com.adspitcher.fragments.LatestFragment;
import com.adspitcher.fragments.NearbyFragment;
import com.adspitcher.fragments.NewRangeFragment;
import com.adspitcher.fragments.ProfileFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter{

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
            //Search fragment activity
            return new LatestFragment();
        case 1:
        	return new NearbyFragment();
        case 2:
        	return new FavFragment();
        case 3:
        	return new NewRangeFragment();
        case 4:
        	return new FeedsFragment();
        case 5:
        	return new ProfileFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		return 6;
	}

}
