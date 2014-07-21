package com.adspitcher.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adspitcher.fragments.OffersInfoFragment;
import com.adspitcher.fragments.ReviewsFragment;

public class OffersPagerAdapter extends FragmentPagerAdapter{

	public OffersPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
            return new OffersInfoFragment();
        case 1:
        	return new ReviewsFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
