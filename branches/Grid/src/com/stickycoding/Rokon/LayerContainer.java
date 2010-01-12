package com.stickycoding.Rokon;

/**
 * Sprite GridContainer for Grid Control Implementation
 * @author Jeremy
 */

public class LayerContainer {

	private Sprite _sprite;
	private int _x, _y; 

	public LayerContainer(Sprite sprite)
	{
		_sprite = sprite;
	}
	
	public Sprite getSprite()
	{
		return _sprite;
	}

}