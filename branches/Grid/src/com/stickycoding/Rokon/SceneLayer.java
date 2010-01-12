package com.stickycoding.Rokon;

import java.util.ArrayList;

/**
 * Layer Control Implementation
 * @author Jeremy
 */

public class SceneLayer {

	private int [] _layerResult;
	ArrayList<SceneLayerContainer> _sceneLayerContainer = new ArrayList<SceneLayerContainer>();
	private int _x, _y;

	public SceneLayer() {
		_x = 0;
		_y = 0;
	}

	public SceneLayer(int x, int y) {
		_x = x;
		_y = y;
	}

	public void moveXY(int x, int y) {
	    int i = 0;
	    int j = _sceneLayerContainer.size();
	    while (i < j) {
	    	_sceneLayerContainer.get(i).getSprite().setXY(
	    			(_sceneLayerContainer.get(i).getSprite().getX() + (x - _x)),
	    			(_sceneLayerContainer.get(i).getSprite().getY() + (y - _y))
	    			);
	    	i++;
	    }
		_x = x;
		_y = y;
	}

	public void moveX(int x) {
	    int i = 0;
	    int j = _sceneLayerContainer.size();
	    while (i < j) {
	    	_sceneLayerContainer.get(i).getSprite().setX(_sceneLayerContainer.get(i).getSprite().getX() + (x - _x));
	    	i++;
	    }
		_x = x;
	}

	public void moveY(int y) {
	    int i = 0;
	    int j = _sceneLayerContainer.size();
	    while (i < j) {
	    	_sceneLayerContainer.get(i).getSprite().setY(_sceneLayerContainer.get(i).getSprite().getY() + (y - _y));
	    	i++;
	    }
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

	public ArrayList<SceneLayerContainer> getContainer() {
		return _sceneLayerContainer;
	}
	
	public int spriteBinarySearch(Sprite sprite) {
	    int first = 0;
	    int last  = _sceneLayerContainer.size();
	    while (first < last) {
	        int mid = (first + last) / 2;
	        if (sprite.compareTo(_sceneLayerContainer.get(mid).getSprite()) < 0) {
	            last = mid;
	        } else if (sprite.compareTo(_sceneLayerContainer.get(mid).getSprite()) > 0) {
	            first = mid + 1;
	        } else {
	            return mid;
	        }
	    }
	    return -1;
	}

    public static void layerSort( ArrayList<SceneLayerContainer> sceneLayerContainer ) {
        for( int i = 1; i < sceneLayerContainer.size(); i++ ) {
        	SceneLayerContainer tmpContainer = sceneLayerContainer.get(i);
        	Sprite tmpSprite = sceneLayerContainer.get(i).getSprite();
            int j = i;

            for( ; j > 0 && tmpSprite.compareTo( sceneLayerContainer.get(j - 1).getSprite() ) < 0; j-- ) {
            	sceneLayerContainer.set(j, sceneLayerContainer.get(j - 1));
            }
            sceneLayerContainer.set(j, tmpContainer);
        }
    }

    public void magnetize(Sprite sprite) {
		_sceneLayerContainer.add(new SceneLayerContainer(sprite));
		layerSort(_sceneLayerContainer);
	}

}