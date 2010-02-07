package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.FP;
import com.stickycoding.Rokon.Scene;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;

public class MainMenu extends Scene {
	
	TextureAtlas mainMenuAtlas;
	Texture target;
	Sprite sprite;
	
	public MainMenu() {
		super();
		
		mainMenuAtlas = new TextureAtlas(512, 512);
		mainMenuAtlas.insert(target = new Texture("target.png"));
		
		addTextureAtlas(mainMenuAtlas);
		
		sprite = new Sprite(FP.fromInt(200), FP.fromInt(100), FP.fromInt(100), FP.fromInt(100));
		add(sprite);
		sprite.setTexture(target);
	}
	
	
	

}
