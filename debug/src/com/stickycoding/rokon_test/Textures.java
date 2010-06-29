package com.stickycoding.rokon_test;

import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;

public class Textures {

	public static TextureAtlas atlas;	public static Texture background, box;
	
	public static void load() {
		atlas = new TextureAtlas();
		atlas.complete();
	}
	
}
