package com.stickycoding.Rokon;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Build;

public class RokonRenderer implements GLSurfaceView.Renderer {
	
	private boolean _hasLoaded;
	private Rokon _rokon;
	
	public RokonRenderer(Rokon rokon) {
		_rokon = rokon;
	}

	public void onDrawFrame(GL10 gl) {
		GLHelper.setGL(gl);
		if(Rokon.isLoading()) {
			
			if(_hasLoaded) {
				GLHelper.enableTextures();
				GLHelper.enableTexCoordArray();
				GLHelper.enableVertexArray();
				gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glScalef(Rokon.getGameWidth(), Rokon.getGameHeight(), 0);
				gl.glColor4f(1, 1, 1, 1);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
				return;
			}
			
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.enableVertexArray();

			Debug.print("Loading has begun, showing screen");
			
			_hasLoaded = true;
			
			try {
				Bitmap tbitmap = BitmapFactory.decodeStream(Rokon.rokon.getAssets().open(Rokon.getTexturePath() + Rokon.getLoadingScreenPath()));
				Bitmap bitmap;
				if(tbitmap.getWidth() > 512 || tbitmap.getHeight() > 512)
					bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);
				else
					bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				canvas.drawBitmap(tbitmap, 0, 0, new Paint());

				int[] tmp_tex = new int[1];
				gl.glGenTextures(1, tmp_tex, 0);
				GLHelper.bindTexture(tmp_tex[0]);
	            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
	            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
	            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
	            gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
	            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	            
	            ByteBuffer texBuffer;
                if(Build.VERSION.SDK == "3")
                        texBuffer = ByteBuffer.allocate(8*4);
                else
                        texBuffer = ByteBuffer.allocateDirect(8*4);
                texBuffer.order(ByteOrder.nativeOrder());
                
                texBuffer.position(0);                
                texBuffer.putFloat(0);
                texBuffer.putFloat(0);                
                float height = bitmap.getHeight();
                float width = bitmap.getWidth();
                if(Rokon.isLandscape()) {
                    texBuffer.putFloat(tbitmap.getWidth() / width);
                    texBuffer.putFloat(0);
                    texBuffer.putFloat(0);
                    texBuffer.putFloat(tbitmap.getHeight() / height);
                    texBuffer.putFloat(tbitmap.getWidth() / width);
                        texBuffer.putFloat(tbitmap.getHeight() / height);
                } else {
                    texBuffer.putFloat(tbitmap.getHeight() / height);
                    texBuffer.putFloat(0);
                    texBuffer.putFloat(0);
                    texBuffer.putFloat(tbitmap.getWidth() / width);
                    texBuffer.putFloat(tbitmap.getHeight() / height);
                    texBuffer.putFloat(tbitmap.getWidth() / width);
                }
                texBuffer.position(0);
                gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);

                ByteBuffer vertexBuffer;
                if(Build.VERSION.SDK == "3")
                	vertexBuffer = ByteBuffer.allocate(8*4);
                else
                        vertexBuffer = ByteBuffer.allocateDirect(8*4);
                vertexBuffer.order(ByteOrder.nativeOrder());
                vertexBuffer.position(0);
                vertexBuffer.putFloat(0); vertexBuffer.putFloat(0);
                vertexBuffer.putFloat(1); vertexBuffer.putFloat(0);
                vertexBuffer.putFloat(0); vertexBuffer.putFloat(1);
                vertexBuffer.putFloat(1); vertexBuffer.putFloat(1);
                vertexBuffer.position(0);
                gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);    
                
				gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glScalef(Rokon.getGameWidth(), Rokon.getGameHeight(), 0);
				gl.glColor4f(1, 1, 1, 1);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				
			} catch (Exception e) {
				e.printStackTrace();
				Debug.error("Error with loading screen... " + e.getMessage());
			}
			
			
			
			
			new Thread(new Runnable() {
				public void run() {
					_rokon.onLoad();
					_rokon.prepareEngine();
					_rokon.onLoadComplete();
					_rokon.startGameLoop();
					Rokon.setLoading(false);
					Debug.print("Loading over");
				}
			}).start();
		} else {
			_rokon.onDraw(gl);
		}
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Debug.print("onSurfaceChanged");
		GLHelper.setGL(gl);
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
        GLU.gluOrtho2D(gl, 0, Rokon.getGameWidth(), Rokon.getGameHeight(), 0);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Debug.print("onSurfaceCreated");
		
		String extensions = gl.glGetString(GL10.GL_EXTENSIONS);
		boolean vbo = extensions.contains("GL_OES_draw_texture");
		boolean drawTex = (gl instanceof GL11);
			
		Debug.print("Device GPU vbo=" + vbo + " drawtex=" + drawTex);
		Rokon.setSupport(vbo, drawTex);
		
		GLHelper.setGL(gl);
		gl.glViewport(0, 0, Rokon.getGameWidth(), Rokon.getGameHeight());
		gl.glClearColor(0, 0, 0, 1);
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		GLHelper.blendMode(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        GLU.gluOrtho2D(gl, 0, Rokon.getGameWidth(), Rokon.getGameHeight(), 0);
	}
        

}
