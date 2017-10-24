package com.example.careerlauncher;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

	private UserProfile userProfile;
	private Test test;

	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			test = new Test();
			return test;
		case 1:
			userProfile = new UserProfile();
			return userProfile;
		default:
			test = new Test();
			return test;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "TEST";
		case 1:
			return "PROFILE";
		default:
			return "TEST";
		}
	}

}
