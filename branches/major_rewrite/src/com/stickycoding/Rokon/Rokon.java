package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class Rokon extends Activity {
	
	public static final int DRAW_PRIORITY_NORMAL = 0;
	public static final int DRAW_PRIORITY_VBO = 1;
	public static final int DRAW_PRIORITY_DRAWTEX_NORMAL = 2;
	public static final int DRAW_PRIORITY_DRAWTEX_VBO = 3;
	
	public static long realTime, time, previousTime, pauseTime, timeDifference;
	public static int timeModifier;
	
	protected static Rokon rokon;
	
	public static int lastTouchX, lastTouchY;

	public static long prevLoopTime, prevDrawTime, loopTime, drawTime;
	private static boolean _paused;
	private static long _pauseOnTime;
	
	private long _fpsStart, _loopStart;
	private int _fpsCount, _loopCount;
	
	private boolean _endGameThread, _hasGameThread;
	
	private RokonSurfaceView _surfaceView;
	
	private Scene _scene;
	
	private static boolean _supportVBO, _supportDrawTex;
	private static boolean _useVBO, _useDrawTex;
	
	private static boolean _hasLoadingScreen;
	private static String _loadingScreenPath;	
	private static boolean _isLoading;	
	private static String _texturePath, _audioPath;
	
	private static int _drawPriority;
	
	private static boolean _threadedGameLoop = false, _screenSpecificDirectories, _landscape, _showFps;
	private static float _gameWidth, _gameHeight;
	private static int _defaultLayerCount = 1, _defaultMaxEntityCount = 10;
	
	private static BlendFunction _blendFunction;
	
	private static boolean _frozen;
	
	public void onCreate() { }
	public void onLoad() { }
	public void onLoadComplete() { }
	
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		rokon = this;
		Debug.print("Rokon Activity Created");
		onCreate();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		rokon = this;
		startGameLoop();
		if(_scene != null)
			_scene.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		_endGameThread = true;
		pauseGame();
		if(_scene != null)
			_scene.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(_scene != null)
			_scene.onDestroy();
	}
	
	public static void pauseGame() {
		_paused = true;
		_pauseOnTime = time;
	}
	
	public static void unpauseGame() {
		_paused = false;
		pauseTime = realTime - _pauseOnTime;
	}
	
	public static boolean isPaused() {
		return _paused;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(_scene != null) {
			lastTouchX = FP.fromFloat((event.getX() / DeviceScreen.getDisplayMetrics().widthPixels) * _gameWidth);
			lastTouchY = FP.fromFloat((event.getY() / DeviceScreen.getDisplayMetrics().heightPixels) * _gameHeight);
			_scene.handleTouch(lastTouchX, lastTouchY, event);
		}
		try {
			Thread.sleep(16);
		} catch (Exception e) { }
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(_scene != null)
			_scene.handleKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}	
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(_scene != null)
			_scene.handleKeyUp(keyCode, event);
		return super.onKeyUp(keyCode, event);
	}
	
	public void setGameSize(int gameWidth, int gameHeight) {
		_gameWidth = gameWidth;
		_gameHeight = gameHeight;
	}
	
	public void setDefaultLayerCount(int defaultLayerCount) {
		_defaultLayerCount = defaultLayerCount;
	}
	
	public void setDefaultMaxEntityCount(int defaultMaxEntityCount) {
		_defaultMaxEntityCount = defaultMaxEntityCount;
	}
	
	public void setScene(Scene scene) {
		Rokon.freeze();
		if(_scene != null) {
			_scene.destroyScene();
			_scene.onSceneExit();
			scene.loadScene(_scene.destroyScene());
		} else
			scene.loadScene(new Bundle());
		_scene = scene;
		Rokon.unfreeze();
	}
	
	public Scene getScene() {
		return _scene;
	}
	
	public void removeScene() {
		setScene(null);
	}
	
	public void useThreadedGameLoop() {
		_threadedGameLoop = true;
	}
	
	public static boolean isThreadedGameLoop() {
		return _threadedGameLoop;
	}
	
	public void setAudioPath(String audioPath) {
		_audioPath = audioPath;
	}
	
	public static String getAudioPath() {
		if(_audioPath == null)
			return "";
		return _audioPath;
	}
	
	public void setTexturePath(String texturePath) {
		_texturePath = texturePath;
	}
	
	public static String getTexturePath() {
		if(_texturePath == null)
			return "";
		return _texturePath;
	}
	
	public void setLoadingScreen(String loadingScreenPath) {
		_loadingScreenPath = loadingScreenPath;
		_hasLoadingScreen = true;
	}
	
	public static String getLoadingScreenPath() {
		return _loadingScreenPath;
	}
	
	public static boolean hasLoadingScreen() {
		return _hasLoadingScreen;
	}
	
	public void useScreenSpecificDirectories() {
		_screenSpecificDirectories = true;
	}
	
	public static boolean isScreenSpecificDirectories() {
		return _screenSpecificDirectories;
	}
	
	public void setLandscape() {
		_landscape = true;
	}
	
	public static boolean isLandscape() {
		return _landscape;
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
	
	public static float getGameWidth() {
		return _gameWidth;
	}
	
	public static float getGameHeight() {
		return _gameHeight;
	}
	
	public void createEngine() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(_landscape)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		DeviceScreen.determine(this);		
		TextureManager.prepare();
		_surfaceView = new RokonSurfaceView(this);
		setContentView(_surfaceView);
		if(!_hasLoadingScreen) {
			onLoad();
			prepareEngine();
			onLoadComplete();
		} else {
			_isLoading = true;
		}
	}
	
	protected void prepareEngine() {
		VBOManager.reload();
		TextureManager.reload();
		_blendFunction = new BlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		System.gc();
	}
	
	protected static boolean isLoading() {
		return _isLoading;
	}
	
	protected static void setLoading(boolean isLoading) {
		_isLoading = isLoading;
	}
	
	protected static BlendFunction getDefaultBlendFunction() {
		return _blendFunction;
	}
	
	protected void onDraw(GL10 gl) {
		if(_scene != null) {
			while(_frozen);
			
			TextureManager.updateTextures(gl);
			
			if(_useVBO)
				VBOManager.loadBuffers((GL11)gl);

			previousTime = time;
			realTime = System.currentTimeMillis();
			
			if(!_paused) {
				time = realTime - pauseTime;
				timeDifference = time - previousTime;
				timeModifier = FP.fromFloat((float)timeDifference / 1000f);
			}
			
			if(!_threadedGameLoop)
				_scene.handleGameLoop();

			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        gl.glMatrixMode(GL10.GL_MODELVIEW);
	        gl.glLoadIdentity();
			
			_scene.onDraw(gl);
			
			prevDrawTime = drawTime;
			drawTime = System.currentTimeMillis();
			_fpsCount++;
			if(drawTime - _fpsStart >= 1000) {
				_fpsStart = drawTime;
				if(_showFps)
					Debug.print("Frame Rate = " + _fpsCount);
				_fpsCount = 0;
			}
			loopTime = System.currentTimeMillis();
		}
	}
	
	protected void startGameLoop() {
		if(_hasGameThread)
			return;
		if(_threadedGameLoop) {
			Debug.print("Starting Threaded Game Loop");
			_hasGameThread = true;
			_endGameThread = false;
			new Thread(new Runnable() {
				public void run() {
					while(!_endGameThread) {
						if(_scene != null) {
							_scene.handleGameLoop();
							prevLoopTime = loopTime;
							loopTime = System.currentTimeMillis();
							_loopCount++;
							if(loopTime - _loopStart >= 1000) {
								_loopStart = loopTime;
								if(_showFps)
									Debug.print("Loop Rate = " + _loopCount);
								_loopCount = 0;
							}
							loopTime = System.currentTimeMillis();
						}
					}
					_hasGameThread = false;
				}
			}).start();
		}
	}

	public void setDrawPriority(int priority) {
		_drawPriority = priority;
		switch(priority) {
		case DRAW_PRIORITY_DRAWTEX_NORMAL:
			_useDrawTex = true;
			_useVBO = false;
			break;
		case DRAW_PRIORITY_DRAWTEX_VBO:
			_useDrawTex = true;
			_useVBO = true;
			break;
		case DRAW_PRIORITY_NORMAL:
			_useVBO = false;
			_useDrawTex = false;
			break;
		case DRAW_PRIORITY_VBO:
			_useVBO = true;
			_useDrawTex = false;
			break;
		}
	}
	
	public static int getDrawPriority() {
		return _drawPriority;
	}
	
	protected static void setSupport(boolean vbos, boolean drawtex) {
		_supportVBO = vbos;
		_supportDrawTex = drawtex;
	}
	
	public static boolean supportVBO() {
		return _supportVBO;
	}
	
	public static boolean supportDrawTex() {
		return _supportDrawTex;
	}
	
	public static boolean usingVBO() {
		return _useVBO;
	}
	
	public static boolean usingDrawTex() {
		return _useDrawTex;
	}
	
	public static int getDefaultLayerCount() {
		return _defaultLayerCount;
	}
	
	public static int getDefaultMaxEntityCount() {
		return _defaultMaxEntityCount;
	}
	
	public static void freeze() {
		_frozen = true;
	}
	
	public static void unfreeze() {
		_frozen = false;
	}
	
	public static boolean isFrozen() {
		return _frozen;
	}
}
