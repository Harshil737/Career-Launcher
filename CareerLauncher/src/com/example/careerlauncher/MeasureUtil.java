package com.example.careerlauncher;

import android.content.Context;

public class MeasureUtil {

	public static int getSizeOfItem(Context mContext) {
		int ScreenWidth = mContext.getResources().getConfiguration().screenWidthDp;
		float scale = mContext.getResources().getDisplayMetrics().density;
		int widthToUse = ((int) (ScreenWidth * scale + 0.5f)) - 8;
		int size = widthToUse / 2;
		return size;
	}
}