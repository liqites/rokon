/**
 * @author Jeremy
 * SceneLayer Control
 * SceneLayer has 3 level
 * SceneLayer
 *  |
 *  - LayerContainer
 *       |
 *       - Sprite
 *       
 * SceneLayer class :
 * 
 * SceneLayer(Pos X, Pos Y)
 *   
 * SceneLayer allow you to lock some Sprites together.
 *       
 * LayerContainer is an ArrayList automatically created when you create a SceneLayer,
 * it contain sprites.
 * it's useful if you want to know what sprites are in the SceneLayer
 * they are sorted by sprites hashCodes
 * 
 * getSprite() : return Sprite
 * 
 * 
 */
package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.SceneLayer;
import com.stickycoding.Rokon.Debug;

public class Example22 extends RokonActivity {
	
	public TextureAtlas atlas;
	public FixedBackground background;
	
	public Texture [] narutoTexture = new Texture[2];
	public Sprite [] narutoSprite = new Sprite[10];

	public int x = 150, y = 150, sensX = 1, sensY = 1;
	public SceneLayer testSceneLayer1; 

    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		// Creation of SceneLayer
		// SceneLayer = new SceneLayer(Start Pos X, Start Pos Y)
		testSceneLayer1 = new SceneLayer(x,y);
		//
		atlas = new TextureAtlas(512, 1024);
		atlas.insert(narutoTexture[0] = new Texture("graphics/sprites/naruto.png"));
		atlas.insert(narutoTexture[1] = new Texture("graphics/backgrounds/beach.png"));
		narutoTexture[0].setTileSize(40, 40);
		TextureManager.load(atlas);

		narutoSprite[0] = new Sprite(100, 120, 40, 40, narutoTexture[0]);
    	narutoSprite[1] = new Sprite(130, 110, 40, 40, narutoTexture[0]);
    	narutoSprite[2] = new Sprite(100, 150, 40, 40, narutoTexture[0]);
    	narutoSprite[3] = new Sprite(120, 140, 40, 40, narutoTexture[0]);
    	narutoSprite[4] = new Sprite(180, 120, 40, 40, narutoTexture[0]);
        rokon.addSprite(narutoSprite[0]);
        rokon.addSprite(narutoSprite[1]);
        rokon.addSprite(narutoSprite[2]);
        rokon.addSprite(narutoSprite[3]);
        rokon.addSprite(narutoSprite[4]);

        // magnetize sprite to the SceneLayer
        // SceneLayer.magnetize(Sprite)
		testSceneLayer1.magnetize(narutoSprite[0]);
		testSceneLayer1.magnetize(narutoSprite[1]);
		testSceneLayer1.magnetize(narutoSprite[2]);
		testSceneLayer1.magnetize(narutoSprite[3]);
		testSceneLayer1.magnetize(narutoSprite[4]);
		//
        
        int animeNaruto0[] = {2,13,2,24};
        int animeNaruto1[] = {6,17};
        int animeNaruto2[] = {37,48,37,59};
        int animeNaruto3[] = {9,20,9,31};
        int animeNaruto4[] = {41,42,43,44};

        narutoSprite[0].animateCustom(animeNaruto3, 200);
        narutoSprite[1].animateCustom(animeNaruto4, 200);
        narutoSprite[2].animateCustom(animeNaruto0, 200);
        narutoSprite[3].animateCustom(animeNaruto1, 200);
        narutoSprite[4].animateCustom(animeNaruto2, 200);
        
        background = new FixedBackground(narutoTexture[1]);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
	}

	@Override
	public void onGameLoop() {
		//
		// Example 1 : move the grid 1 with all the objects hung. 
		//
		// Grid.moveGrid(x, y)
		if (x > 250) { sensX = -1; } else if (testSceneLayer1.getX() < 150) { sensX = 1; }
		if (y > 225) { sensY = -1; } else if (testSceneLayer1.getY() < 150) { sensY = 1; }
		if (sensX > 0) { x += 2; } else { x -= 2; }
		if (sensY > 0) { y += 2; } else { y -= 2; }
		testSceneLayer1.moveXY(x, y);
		
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}