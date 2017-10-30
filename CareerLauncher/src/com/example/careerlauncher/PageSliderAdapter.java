package com.example.careerlauncher;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageSliderAdapter extends FragmentPagerAdapter {

	ArrayList<QuestionClass> list;

	public PageSliderAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public PageSliderAdapter(FragmentManager fm, ArrayList<QuestionClass> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		if (pos == 0) {
			return new TestPage(list.get(0));
		} else if (pos == 1) {
			return new TestPage(list.get(1));
		} else if (pos == 2) {
			return new TestPage(list.get(2));
		} else {
			return null;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			return "1";
		case 1:
			return "2";
		case 2:
			return "3";
		default:
			break;
		}
		return null;
	}

}
