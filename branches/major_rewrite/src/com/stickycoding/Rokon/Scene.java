package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class Scene {
	
	private Rokon _rokon;
	private Background _background;
	private boolean _hasBackground;
	private int _layerCount, _maxEntityCount;
	private Entity[][] _entity;
	
	public void onCreate() { }
	public void onDestroy() { }
	public void onPause() { }
	public void onResume() { }
	public void onGameLoop() { }
	
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
	
	protected Scene(Rokon rokon, int layerCount, int maxEntityCount) {
		_rokon = rokon;
		_layerCount = layerCount;
		_maxEntityCount = maxEntityCount;
		_entity = new Entity[_layerCount][];
		for(int i = 0; i < _layerCount; i++)
			_entity[i] = new Entity[_maxEntityCount];
	}
	
	public Scene() {
		Debug.error("Scene must be created through Rokon.createScene");
	}
	
	public int getMaxEntityCount() { 
		return _maxEntityCount;
	}
	
	public int getLayerCount() {
		return _layerCount;
	}
	
	public Rokon getRokon() {
		return _rokon;
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
			onPostBackgroundDraw(gl);
			_background.onDraw(gl);
		}
		for(int i = 0; i < _layerCount; i++) {
			onPreDraw(gl, i);
			for(int j = 0; j < _maxEntityCount; j++)
				if(_entity[i][j] != null)
					_entity[i][j].onDraw(gl);
			onPostDraw(gl, i);
		}
		onPostDraw(gl);
	}
	
	
	

}
