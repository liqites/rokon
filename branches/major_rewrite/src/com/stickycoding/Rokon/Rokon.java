package com.stickycoding.Rokon;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Rokon extends Activity {
	
	private Scene _scene;
	private boolean _hasScene;
	
	private boolean _hasLoadingScreen;
	private String _loadingScreenPath;
	
	private String _texturePath, _audioPath;
	
	private boolean _threadedGameLoop = false, _screenSpecificDirectories, _landscape, _fixedPoints, _showFps;
	private int _gameWidth, _gameHeight;
	private int _defaultLayerCount = 1, _defaultMaxEntityCount = 10;
	
	public void onCreate() { }
	
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		Debug.print("Rokon Activity Created");
		onCreate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(_hasScene) {
			
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}
	
	public void setGameSize(int gameWidth, int gameHeight) {
		_gameWidth = gameWidth;
		_gameHeight = gameHeight;
	}
	
	public Scene createScene() {
		return new Scene(this, _defaultLayerCount, _defaultMaxEntityCount);
	}
	
	public Scene createScene(int layerCount, int maxEntityCount) {
		return new Scene(this, layerCount, maxEntityCount);
	}
	
	public void setDefaultLayerCount(int defaultLayerCount) {
		_defaultLayerCount = defaultLayerCount;
	}
	
	public void setDefaultMaxEntityCount(int defaultMaxEntityCount) {
		_defaultMaxEntityCount = defaultMaxEntityCount;
	}
	
	public void setScene(Scene scene) {
		_scene = scene;
		_hasScene = (scene != null);
	}
	
	public Scene getScene() {
		return _scene;
	}
	
	public boolean hasScene() {
		return _hasScene;
	}
	
	public void removeScene() {
		setScene(null);
	}
	
	public void useThreadedGameLoop() {
		_threadedGameLoop = true;
	}
	
	public void setAudioPath(String audioPath) {
		_audioPath = audioPath;
	}
	
	public void setTexturePath(String texturePath) {
		_texturePath = texturePath;
	}
	
	public void setLoadingScreen(String loadingScreenPath) {
		_loadingScreenPath = loadingScreenPath;
		_hasLoadingScreen = true;
	}
	
	public void useScreenSpecificDirectories() {
		_screenSpecificDirectories = true;
	}
	
	public void setLandscape() {
		_landscape = true;
	}
	
	public void useFixedPoints() {
		_fixedPoints = true;
	}
	
	public void useFloatingPoints() {
		_fixedPoints = false;
	}
	
	public void showFps() {
		_showFps = true;
	}
	
	public void hideFps() {
		_showFps = false;
	}
	
	public void toggleFps() {
		_showFps = !_showFps;
	}
	
	
}
