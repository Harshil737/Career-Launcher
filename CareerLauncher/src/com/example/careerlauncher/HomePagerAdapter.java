package com.example.careerlauncher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

	private UserProfile userProfile;
	private Test test;
	private Home home;

	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			home = new Home();
			return home;
		case 1:
			test = new Test();
			return test;
		case 2:
			userProfile = new UserProfile();
			return userProfile;
		default:
			home = new Home();
			return home;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "HOME";
		case 1:
			return "TEST";
		case 2:
			return "PROFILE";
		default:
			return "HOME";
		}
	}
}