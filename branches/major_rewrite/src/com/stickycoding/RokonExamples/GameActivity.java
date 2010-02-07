package com.stickycoding.RokonExamples;

import android.view.MotionEvent;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Entity;
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
			super();
			sprite = new Sprite(100 * Rokon.fixedPointUnit, 100 * Rokon.fixedPointUnit, 50 * Rokon.fixedPointUnit, 50 * Rokon.fixedPointUnit);
			//Sprite sprite = new Sprite(10, 10, 50, 50);
			
			atlas = new TextureAtlas(512, 512);
			atlas.insert(texture = new Texture("face.png"));
			
			addTextureAtlas(atlas);
			
			sprite.setTexture(texture);
			
			checkForTouchables(true);
			
			add(sprite);
		}
		
		boolean down = false;
		
		public void onTouchDown(Entity entity, int x, int y, MotionEvent event) { 
			//sprite.scaleFromCentre(sprite.getScaleX() + 0.2f, sprite.getScaleY() + 0.1f);
			down = true;
			Debug.print("Pressed down");
			Rokon.pauseGame();
		}
		public void onTouchMove(int x, int y, MotionEvent event) { }
		public void onTouchUp(Entity entity, int x, int y, MotionEvent event) { 
			//sprite.setVelX(0);
			down = false;
			Rokon.unpauseGame();
			Debug.print("Up");
		}
		
		public void onGameLoop() {
			sprite.rotate(1 * Rokon.fixedPointUnit);
			if(down) {
				sprite.scaleFromCentre(sprite.getScaleX() + 0.01f, sprite.getScaleY() + 0.01f);
			}
		}
		
	}

}
