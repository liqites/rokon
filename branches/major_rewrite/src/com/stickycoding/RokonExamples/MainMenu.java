package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.FP;
import com.stickycoding.Rokon.Scene;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.Entities.Button;

public class MainMenu extends Scene {
	
	TextureAtlas mainMenuAtlas;
	Texture target;
	Button sprite;
	
	public MainMenu() {
		super();
		
		mainMenuAtlas = new TextureAtlas(512, 512);
		mainMenuAtlas.insert(target = new Texture("target.png"));
		
		addTextureAtlas(mainMenuAtlas);
		
		sprite = new Button(FP.fromInt(200), FP.fromInt(100), FP.fromInt(100), FP.fromInt(100));
		//sprite.setTexture(target, target, target);
		sprite.setTexture(target);
		add(sprite);
		addTouchable(sprite);

		checkForTouchables();
	}
	
	
	

}
