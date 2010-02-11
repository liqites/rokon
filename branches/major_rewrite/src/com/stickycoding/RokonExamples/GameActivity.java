package com.stickycoding.RokonExamples;

import android.view.MotionEvent;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Entity;
import com.stickycoding.Rokon.FP;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Scene;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;

public class GameActivity extends Rokon {
	
	public void onCreate() {
		setGameSize(480, 320);
		setLandscape();
		setTexturePath("graphics/");
		setAudioPath("audio/");
		showFps();
		setLoadingScreen("loading_screen.png");
		setDrawPriority(DRAW_PRIORITY_NORMAL);
		createEngine();
	}
	
	public void onLoad() {
		Debug.print("Loading all the goodies");
		setScene(myScene = new MyScene());
	}
	
	MyScene myScene;
	
	public class MyScene extends Scene {
		
		Sprite sprite;
		TextureAtlas atlas;
		Texture texture;

		public MyScene() {
			super(1, 500);

			
			atlas = new TextureAtlas(512, 512);
			atlas.insert(texture = new Texture("face.png"));
			
			for(int i = 0; i < 100; i++) {
				Sprite sprite = new Sprite(FP.fromDouble(Math.random() * 400), FP.fromDouble(Math.random() * 300), FP.fromInt(100), FP.fromInt(100));
				sprite.setTexture(texture);
				add(sprite);
			}
			
			addTextureAtlas(atlas);
			
			
			//checkForTouchables(true);
			
			
			setChildScene(new MainMenu(), false);
		}
		
		boolean down = false;
		
		public void onTouchDown(int x, int y, MotionEvent event) { 
			//sprite.scaleFromCentre(sprite.getScaleX() + 0.2f, sprite.getScaleY() + 0.1f);
			down = true;
		}
		public void onTouchMove(int x, int y, MotionEvent event) { }
		public void onTouchUp(int x, int y, MotionEvent event) { 
			down = false;
			//sprite.scale(FP.ONE, FP.ONE);
		}
		
		public void onGameLoop() {
			if(down) {
				//sprite.rotateAboutCentre(FP.fromInt(1));
				////sprite.scaleFromCentre(sprite.getScaleX() + FP.div(1, 100), sprite.getScaleY() +  + FP.div(1, 100));
			}
		}
		
	}

}
