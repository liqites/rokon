package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Scene;

public class GameActivity extends Rokon {
	
	public void onCreate() {
		setGameSize(480, 320);
		setLandscape();
		setTexturePath("graphics/");
		setAudioPath("audio/");
		setLoadingScreen("loading_screen.png");
		setDrawPriority(DRAW_PRIORITY_VBO_NORMAL);
		useThreadedGameLoop();
		createEngine();
	}
	
	public void onLoad() {
		Debug.print("Loading all the goodies");
		setScene(MyScene = createScene());
	}
	
	public Scene MyScene;

}
