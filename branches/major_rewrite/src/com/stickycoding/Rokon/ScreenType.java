package com.stickycoding.Rokon;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenType {
	
	public static final int UNKNOWN = 0;
	public static final int TATTOO = 1;			//320x240
	public static final int G1 = 2;				//480x320
	public static final int NEXUS = 3;			//720x480
	public static final int DROID = 4;			//854x480
	
	private static int _screen;
	private static boolean _widescreen;
	
	public static boolean isWidescreen() {
		return _widescreen;
	}
	
	public static int getDisplay() {
		return _screen;
	}
	
	public static void fetch(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		setScreen(dm.widthPixels, dm.heightPixels);
		Debug.print("Screen Type = " + _screen + " wide=" + _widescreen);
	}
	
	public static void setScreen(int width, int height) {
		if((width == 320 && height == 240) || (height == 320 && width == 240)) {
			_screen = TATTOO;
			_widescreen = false;
			return;
		}
		if((width == 480 && height == 320) || (height == 480 && width == 320)) {
			_screen = G1;
			_widescreen = false;
			return;
		}
		if((width == 720 && height == 480 || height == 720 && width == 480)) {
			_screen = NEXUS;
			_widescreen = false;
			return;
		}
		if((width == 854 && height == 480 || height == 854 && height == 480)) {
			_screen = DROID;
			_widescreen = true;
			return;
		}
		_screen = UNKNOWN;
		if(width > height)
			if(width / height > 1.5d)
				_widescreen = true;
			else
				_widescreen = false;
		else if(height / width > 1.5d)
			_widescreen = true;
		else
			_widescreen = false;
	}

}
