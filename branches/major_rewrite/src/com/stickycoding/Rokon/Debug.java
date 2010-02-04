package com.stickycoding.Rokon;

import android.util.Log;

public class Debug {
	
	public static void print(String message) {
		Log.v("Rokon", message);
	}
	
	public static void warning(String message) {
		Log.w("Rokon", message);
		(new Exception()).printStackTrace();
	}
	
	public static void error(String message) {
		Log.e("Rokon", message);
		(new Exception()).printStackTrace();
		System.exit(0);
	}

}
