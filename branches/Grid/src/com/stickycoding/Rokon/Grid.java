package com.stickycoding.Rokon;

import java.util.ArrayList;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.GridContainer;

/**
 * Grid Control Implementation
 * @author Jeremy
 */

public class Grid {

	private int [] _gridResult;
	ArrayList<GridContainer> _gridContainer = new ArrayList<GridContainer>();
	private int _gridWidth, _gridHeight, _cols, _rows, _caseWidth, _caseHeight, _x, _y;

	public Grid(int caseWidth, int caseHeight, int cols, int rows) {
		_x = 0;
		_y = 0;
		_caseWidth = caseWidth;
		_caseHeight = caseHeight;
		_cols = cols;
		_rows = rows;
		_gridWidth = _cols * _caseWidth;
		_gridHeight = _rows * _caseHeight;
	}

	public Grid(int caseWidth, int caseHeight, int cols, int rows, int x, int y) {
		_x = x;
		_y = y;
		_caseWidth = caseWidth;
		_caseHeight = caseHeight;
		_cols = cols;
		_rows = rows;
		_gridWidth = _cols * _caseWidth;
		_gridHeight = _rows * _caseHeight;
		moveGrid(_x, _y);
	}

	// Todo
	public void gridCreate(int caseWidth, int caseHeight, int cols, int rows, int alpha, int visible) {
	}
	
	public void moveGrid(int x, int y) {
	    int i = 0;
	    int j = _gridContainer.size();
	    while (i < j) {
	    	_gridContainer.get(i).getSprite().setXY(
	    			(_gridContainer.get(i).getSprite().getX() + (x - _x)),
	    			(_gridContainer.get(i).getSprite().getY() + (y - _y))
	    			);
	    	i++;
	    }
		_x = x;
		_y = y;
	}
	
	// Todo
	public void gridScaleX(int x, int withSprite) {
	}

	// Todo
	public void gridScaleY(int y) {
	}
	
	// Todo
	public void gridScaleXY(int x, int y) {
	}

	// Todo
	public void rotateGrid(int degree) {
	}

	// Todo
	public void relativeRotateGrid(int x, int y, int degree) {
	}

	// Todo
	public void smoothMoveOnGrid(Sprite sprite, int casePosX, int casePosY, int duration) {
	}

	public void moveOnGrid(Sprite sprite, int casePosX, int casePosY) {
		int _convCasePosX = _x + (casePosX * _caseWidth); 
		int _convCasePosY = _y + (casePosY * _caseHeight);
		sprite.setXY(_convCasePosX,_convCasePosY);
		int _search = spriteBinarySearch(sprite);
		if (_search >= 0) {
			_gridContainer.set(_search, new GridContainer(sprite,casePosX,casePosY));
		}
	}

	public int[] getGrid() {
		_gridResult[0] = _x;
		_gridResult[1] = _y;
		_gridResult[2] = _gridWidth;
		_gridResult[3] = _gridHeight;
		return _gridResult;
	}

	public int getX() {
		return _x;
	}
	public int getY() {
		return _y;
	}

	public int [] getOnGridPosXY(int x, int y) {
		float colfloat =  ( -1 * ( _x - x ) ) / _caseWidth;
		int col = 0;
		if (colfloat < 0 || colfloat > _cols ) { col = -1; }
		else if (colfloat < 0.99) { col = 0; }
		else { col = (int) colfloat; }

		float rowfloat =  ( -1 * ( _y - y ) ) / _caseHeight;
		int row = 0;
		if (rowfloat < 0 || rowfloat > _cols ) { row = -1; }
		else if (rowfloat < 0.99) { row = 0; }
		else { row = (int) rowfloat; }
		
		int [] colRow = { col, row };
		return colRow;
	}

	public int getOnGridPosX(int x) {
		float colfloat =  ( -1 * ( _x - x ) ) / _caseWidth;
		int col = 0;
		if (colfloat < 0 || colfloat > _cols ) { col = -1; }
		else if (colfloat < 0.99) { col = 0; }
		else { col = (int) colfloat; }
		return col;
	}
	
	public int getOnGridPosY(int y) {
		float rowfloat =  ( -1 * ( _y - y ) ) / _caseHeight;
		int row = 0;
		if (rowfloat < 0 || rowfloat > _cols ) { row = -1; }
		else if (rowfloat < 0.99) { row = 0; }
		else { row = (int) rowfloat; }
		return row;
	}

	public int [] getSpriteColRow(Sprite sprite) {
		int _search = spriteBinarySearch(sprite);
		int [] colRow = { _gridContainer.get(_search).getCol(), _gridContainer.get(_search).getRow() };
		return colRow;
	}
	
	public int getSpriteCol(Sprite sprite) {
		int _search = spriteBinarySearch(sprite);
		return _gridContainer.get(_search).getCol();
	}

	public int getSpriteRow(Sprite sprite) {
		int _search = spriteBinarySearch(sprite);
		return _gridContainer.get(_search).getRow();
	}

	public ArrayList<GridContainer> getContainer() {
		return _gridContainer;
	}
	
	public int spriteBinarySearch(Sprite sprite) {
	    int first = 0;
	    int last  = _gridContainer.size();
	    while (first < last) {
	        int mid = (first + last) / 2;
	        if (sprite.compareTo(_gridContainer.get(mid).getSprite()) < 0) {
	            last = mid;
	        } else if (sprite.compareTo(_gridContainer.get(mid).getSprite()) > 0) {
	            first = mid + 1;
	        } else {
	            return mid;
	        }
	    }
	    return -1;
	}

    public static void gridSort( ArrayList<GridContainer> gridContainer ) {
        for( int i = 1; i < gridContainer.size(); i++ ) {
        	GridContainer tmpContainer = gridContainer.get(i);
        	Sprite tmpSprite = gridContainer.get(i).getSprite();
            int j = i;

            for( ; j > 0 && tmpSprite.compareTo( gridContainer.get(j - 1).getSprite() ) < 0; j-- ) {
            	gridContainer.set(j, gridContainer.get(j - 1));
            }
            gridContainer.set(j, tmpContainer);
        }
    }

    public void magnetize(Sprite sprite, int casePosX, int casePosY) {
		_gridContainer.add(new GridContainer(sprite,casePosY,casePosY));
		gridSort(_gridContainer);
		moveOnGrid(sprite, casePosX, casePosY);
	}

}