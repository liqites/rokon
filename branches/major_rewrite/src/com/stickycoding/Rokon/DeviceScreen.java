package com.stickycoding.Rokon;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DeviceScreen {
	
	private static DisplayMetrics _dm;

	protected static void determine(Activity activity) {
		_dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(_dm);
	}
	
	public static DisplayMetrics getDisplayMetrics() {
		return _dm;
	}
	
	public static int getScreenWidth() {
		return _dm.widthPixels;
	}
	
	public static int getScreenHeight() {
		return _dm.heightPixels;
	}

}
