package com.stickycoding.Rokon;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Scene {
	
	private ArrayList<TextureAtlas> _textureAtlas;
	
	public Rokon rokon;
	private Background _background;
	private boolean _hasBackground;
	private int _layerCount, _maxEntityCount;
	private Entity[][] _entity;
	
	public Bundle lastBundle, thisBundle;
	
	public void onCreate() { }
	public void onDestroy() { }
	public void onPause() { }
	public void onResume() { }
	public void onGameLoop() { }
	public void onSceneLoad(Bundle incomingBundle) { }
	public void onSceneExit() { }
	
	public void onPreDraw(GL10 gl) { }
	public void onPostBackgroundDraw(GL10 gl) { }
	public void onPreDraw(GL10 gl, int layer) { }
	public void onPostDraw(GL10 gl) { }
	public void onPostDraw(GL10 gl, int layer) { }

	public void onIncomingCall() { }
	public void onNotification() { }

	public void onTouch(int x, int y, MotionEvent event) { }
	public void onTouchDown(int x, int y, MotionEvent event) { }
	public void onTouchMove(int x, int y, MotionEvent event) { }
	public void onTouchUp(int x, int y, MotionEvent event) { }
	
	public void onKey(int keyCode, KeyEvent event) { }
	public void onKeyDown(int keyCode, KeyEvent event) { }
	public void onKeyPress(int keyCode, KeyEvent event) { }
	public void onKeyUp(int keyCode, KeyEvent event) { }
	
	protected Scene(int layerCount, int maxEntityCount) {
		rokon = Rokon.rokon;
		_layerCount = layerCount;
		_maxEntityCount = maxEntityCount;
		_entity = new Entity[_layerCount][];
		for(int i = 0; i < _layerCount; i++)
			_entity[i] = new Entity[_maxEntityCount];
		_textureAtlas = new ArrayList<TextureAtlas>();
	}
	
	public Scene() {
		this(Rokon.getDefaultLayerCount(), Rokon.getDefaultMaxEntityCount());
	}
	
	public int getMaxEntityCount() { 
		return _maxEntityCount;
	}
	
	public int getLayerCount() {
		return _layerCount;
	}
	
	public Rokon getRokon() {
		return rokon;
	}
	
	public void add(Entity entity, int layer) {
		for(int i = 0; i < _maxEntityCount; i++)
			if(_entity[layer][i] == null) {
				_entity[layer][i] = entity;
				return;
			}
		Debug.warning("Not enough room for Entity, layer=" + layer);
	}
	
	public void add(Entity entity) {
		add(entity, 0);
	}
	
	public void remove(Entity entity) {
		entity.remove();
	}
	
	public void clear() {
		for(int i = 0; i < _layerCount; i++)
			clear(i);
	}
	
	public void clear(int layer) {
		for(int i = 0; i < _maxEntityCount; i++)
			if(_entity[layer][i] != null) 
				_entity[layer][i] = null;
	}
	
	public void setBackground(Background background) { 
		_background = background;
		_hasBackground = (background != null);
	}
	
	public Background getBackground() {
		return _background;
	}
	
	public boolean hasBackground() {
		return _hasBackground;
	}
	
	public void removeBackground() {
		_background = null;
		_hasBackground = false;
	}
	
	protected void onDraw(GL10 gl) {
		onPreDraw(gl);
		if(_hasBackground) {
			_background.onDraw(gl);
			onPostBackgroundDraw(gl);
		}
		for(int i = 0; i < _layerCount; i++) {
			onPreDraw(gl, i);
			for(int j = 0; j < _maxEntityCount; j++)
				if(_entity[i][j] != null) {
					_entity[i][j].onUpdate();
					_entity[i][j].onDraw(gl);
				}
			onPostDraw(gl, i);
		}
		onPostDraw(gl);
	}
	
	protected void loadScene(Bundle incomingBundle) {
		lastBundle = incomingBundle;
		thisBundle = new Bundle();
		TextureManager.removeAll();
		for (Iterator<TextureAtlas> it = _textureAtlas.iterator(); it.hasNext(); )
			TextureManager.load(it.next());
		System.gc();
	}
	
	protected Bundle destroyScene() {
		TextureManager.removeAll();
		return thisBundle;
	}
	
	public void addTextureAtlas(TextureAtlas atlas) {
		_textureAtlas.add(atlas);
	}
	
	public void removeTextureAtlas(TextureAtlas atlas) {
		_textureAtlas.remove(atlas);
	}
	
	public void clearTextureAtlas() {
		_textureAtlas.clear();
	}
	

}
