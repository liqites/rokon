package com.stickycoding.Rokon;

/**
 * Sprite GridContainer for Grid Control Implementation
 * @author Jeremy
 */

public class GridContainer {

	private Sprite _sprite;
	private int _col, _row; 

	public GridContainer(Sprite sprite, int col, int row)
	{
		_sprite = sprite;
		_col = col;
		_row = row;
	}
	
	public Sprite getSprite()
	{
		return _sprite;
	}

	public int getCol()
	{
		return _col;
	}

	public int getRow()
	{
		return _row;
	}

}