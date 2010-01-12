package com.stickycoding.Rokon;

import java.util.ArrayList;

/**
 * Layer Control Implementation
 * @author Jeremy
 */

public class SceneLayer {

	private int [] _layerResult;
	ArrayList<LayerContainer> _layerContainer = new ArrayList<LayerContainer>();
	private int _x, _y;

	public SceneLayer() {
		_x = 0;
		_y = 0;
	}

	public SceneLayer(int x, int y) {
		_x = x;
		_y = y;
	}

	public void moveLayer(int x, int y) {
	    int i = 0;
	    int j = _layerContainer.size();
	    while (i < j) {
	    	_layerContainer.get(i).getSprite().setXY(
	    			(_layerContainer.get(i).getSprite().getX() + (x - _x)),
	    			(_layerContainer.get(i).getSprite().getY() + (y - _y))
	    			);
	    	i++;
	    }
		_x = x;
		_y = y;
	}
	
	// Todo
	public void layerScaleX(int x, int withSprite) {
	}

	// Todo
	public void layerScaleY(int y) {
	}
	
	// Todo
	public void layerScaleXY(int x, int y) {
	}

	// Todo
	public void rotateLayer(int degree) {
	}

	// Todo
	public void relativeRotateLayer(int x, int y, int degree) {
	}

	// Todo
	public void smoothMoveOnLayer(Sprite sprite, int casePosX, int casePosY, int duration) {
	}

	public int[] getLayer() {
		_layerResult[0] = _x;
		_layerResult[1] = _y;
		return _layerResult;
	}

	public int getX() {
		return _x;
	}
	public int getY() {
		return _y;
	}

	public ArrayList<LayerContainer> getContainer() {
		return _layerContainer;
	}
	
	public int spriteBinarySearch(Sprite sprite) {
	    int first = 0;
	    int last  = _layerContainer.size();
	    while (first < last) {
	        int mid = (first + last) / 2;
	        if (sprite.compareTo(_layerContainer.get(mid).getSprite()) < 0) {
	            last = mid;
	        } else if (sprite.compareTo(_layerContainer.get(mid).getSprite()) > 0) {
	            first = mid + 1;
	        } else {
	            return mid;
	        }
	    }
	    return -1;
	}

    public static void layerSort( ArrayList<LayerContainer> layerContainer ) {
        for( int i = 1; i < layerContainer.size(); i++ ) {
        	LayerContainer tmpContainer = layerContainer.get(i);
        	Sprite tmpSprite = layerContainer.get(i).getSprite();
            int j = i;

            for( ; j > 0 && tmpSprite.compareTo( layerContainer.get(j - 1).getSprite() ) < 0; j-- ) {
            	layerContainer.set(j, layerContainer.get(j - 1));
            }
            layerContainer.set(j, tmpContainer);
        }
    }

    public void magnetize(Sprite sprite) {
		_layerContainer.add(new LayerContainer(sprite));
		layerSort(_layerContainer);
	}

}