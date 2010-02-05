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
	public static final int DRAW_PRIORITY_VBO_NORMAL = 1;
	public static final int DRAW_PRIORITY_VBO_DRAWTEX_NORMAL = 2;
	public static final int DRAW_PRIORITY_DRAWTEX_NORMAL = 3;
	public static final int DRAW_PRIORITY_DRAWTEX_VBO_NORMAL = 4;
	
	public static long time, previousTime, pauseTime, timeDifference;
	public static int timeModifier;
	public static float timeModifierf;
	
	protected static Rokon rokon;
	
	public static int fixedPointUnit = 0x10000;
	
	public static long prevLoopTime, prevDrawTime, loopTime, drawTime;
	
	private long _fpsStart, _loopStart;
	private int _fpsCount, _loopCount;
	
	private boolean _endGameThread, _hasGameThread;
	
	private RokonSurfaceView _surfaceView;
	
	private Scene _scene;
	private boolean _hasScene;
	
	private static boolean _supportVBO, _supportDrawTex;
	private static boolean _useVBO, _useDrawTex;
	
	private static boolean _hasLoadingScreen;
	private static String _loadingScreenPath;	
	private static boolean _isLoading;	
	private static String _texturePath, _audioPath;
	
	private static int _drawPriority;
	
	private static boolean _threadedGameLoop = false, _screenSpecificDirectories, _landscape, _fixedPoints, _showFps;
	private static int _gameWidth, _gameHeight;
	private int _defaultLayerCount = 1, _defaultMaxEntityCount = 10;
	
	private static boolean _waitingForTextures, _waitingForVBO;
	
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
		startGameLoop();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		_endGameThread = true;
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
	
	public void useFixedPoints(boolean useHelper) {
		_fixedPoints = true;
	}
	
	public static boolean isFixedPoints() {
		return _fixedPoints;
	}
	
	public void useFloatingPoints() {
		_fixedPoints = false;
	}
	
	public static boolean isFloatingPoints() {
		return !_fixedPoints;
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
	
	public static int getGameWidth() {
		return _gameWidth;
	}
	
	public static int getGameHeight() {
		return _gameHeight;
	}
	
	public void createEngine() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(_landscape)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		DeviceScreen.determine(this);		
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
	}
	
	protected static boolean isLoading() {
		return _isLoading;
	}
	
	protected static void setLoading(boolean isLoading) {
		_isLoading = isLoading;
	}
	
	protected void onDraw(GL10 gl) {
		TextureManager.updateTextures(gl);
		
		if(_useVBO)
			VBOManager.loadBuffers((GL11)gl);

		previousTime = time;
		time = System.currentTimeMillis() - pauseTime;
		timeDifference = time - previousTime;
		
		if(_fixedPoints)
			timeModifier = (int)(fixedPointUnit * (timeDifference / 1000));
		else
			timeModifierf = timeDifference / 1000f;
		
		if(_hasScene) {
			if(!_threadedGameLoop)
				_scene.onGameLoop();

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
			_hasGameThread = true;
			_endGameThread = false;
			new Thread(new Runnable() {
				public void run() {
					while(!_endGameThread) {
						if(_hasScene) {
							_scene.onGameLoop();
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
		case DRAW_PRIORITY_DRAWTEX_VBO_NORMAL:
			_useDrawTex = true;
			_useVBO = true;
			break;
		case DRAW_PRIORITY_VBO_DRAWTEX_NORMAL:
			_useVBO = true;
			_useDrawTex = true;
			break;
		case DRAW_PRIORITY_VBO_NORMAL:
			_useVBO = true;
			_useDrawTex =false;
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
	
	public static void setFixedPointUnit(int fixedPointUnit) {
		Rokon.fixedPointUnit = fixedPointUnit;
	}
	
	protected static void vboLoadComplete() {
		_waitingForVBO = false;
	}
	
	protected static void textureLoadComplete() {
		_waitingForTextures = false;
	}
	
}
