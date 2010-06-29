package com.stickycoding.rokon_test;

import com.stickycoding.rokon.RokonActivity;

public class Test extends RokonActivity {

	public static final float GAME_WIDTH = 8f;
	public static final float GAME_HEIGHT = 4.8f;
		
	public static TestScene testScene;

	@Override
	public void onCreate() {
		debugMode();
		forceFullscreen();
		setGameSize(GAME_WIDTH, GAME_HEIGHT);
		setGraphicsPath("textures/");
		createEngine();
	}

	@Override
	public void onLoadComplete() {
		Textures.load();
		setScene(testScene = new TestScene());	
	}
    
}