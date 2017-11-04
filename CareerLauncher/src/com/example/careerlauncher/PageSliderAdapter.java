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
			return new TestPage(list.get(0), 0);
		} else if (pos == 1) {
			return new TestPage(list.get(1), 1);
		} else if (pos == 2) {
			return new TestPage(list.get(2), 2);
		} else if (pos == 3) {
			return new TestPage(list.get(3), 3);
		} else if (pos == 4) {
			return new TestPage(list.get(4), 4);
		} else if (pos == 5) {
			return new TestPage(list.get(5), 5);
		} else if (pos == 6) {
			return new TestPage(list.get(6), 6);
		} else if (pos == 7) {
			return new TestPage(list.get(7), 7);
		} else if (pos == 8) {
			return new TestPage(list.get(8), 8);
		} else if (pos == 9) {
			return new TestPage(list.get(9), 9);
		} else if (pos == 10) {
			return new TestPage(list.get(10), 10);
		} else if (pos == 11) {
			return new TestPage(list.get(11), 11);
		} else if (pos == 12) {
			return new TestPage(list.get(12), 12);
		} else if (pos == 13) {
			return new TestPage(list.get(13), 13);
		} else if (pos == 14) {
			return new TestPage(list.get(14), 14);
		} else {
			return null;
		}
	}

	@Override
	public int getCount() {
		return 15;
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
		case 3:
			return "4";
		case 4:
			return "5";
		case 5:
			return "6";
		case 6:
			return "7";
		case 7:
			return "8";
		case 8:
			return "9";
		case 9:
			return "10";
		case 10:
			return "11";
		case 11:
			return "12";
		case 12:
			return "13";
		case 13:
			return "14";
		case 14:
			return "15";
		default:
			break;
		}
		return null;
	}

}
