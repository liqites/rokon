package com.stickycoding.Rokon;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DeviceScreen {
	
	private static DisplayMetrics _dm;
	
	public static int WIDTH, HEIGHT, HALF_HEIGHT, HALF_WIDTH;

	public static void determine(Activity activity) {
		_dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(_dm);
		WIDTH = FP.fromInt(_dm.widthPixels);
		HALF_WIDTH = FP.div(WIDTH, FP.TWO);
		HEIGHT = FP.fromInt(_dm.heightPixels);
		HALF_HEIGHT = FP.div(HEIGHT, FP.TWO);
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
