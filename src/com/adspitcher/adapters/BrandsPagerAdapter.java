package com.adspitcher.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adspitcher.fragments.BrandsInfoFragment;
import com.adspitcher.fragments.ReviewsFragment;

public class BrandsPagerAdapter extends FragmentPagerAdapter{

	public BrandsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
            return new BrandsInfoFragment();
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
