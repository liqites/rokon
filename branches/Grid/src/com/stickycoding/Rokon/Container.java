package com.stickycoding.Rokon;

import com.stickycoding.Rokon.Sprite;

/**
 * Sprite Container for Grid Control Implementation
 * @author Jeremy
 */

public class Container {

	private Sprite _sprite;
	private int _x, _y; 

	public Container(Sprite sprite, int x, int y)
	{
		_sprite = sprite;
		_x = x;
		_y = y;
	}
	
	public Sprite getSprite()
	{
		return _sprite;
	}

	public int getCol()
	{
		return _x;
	}

	public int getRow()
	{
		return _y;
	}

}