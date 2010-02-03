package com.stickycoding.Rokon;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * Helps to minimize GL calls
 * @author Richard
 */
public class GLHelper {
	
	public static boolean USE_HELPER = true;
	
	private static float _glColor4fRed, _glColor4fGreen, _glColor4fBlue, _glColor4fAlpha = -1;
	public static void glColor4f(GL10 gl, float red, float green, float blue, float alpha) {
		if(!USE_HELPER || alpha != _glColor4fAlpha || red != _glColor4fRed || green != _glColor4fGreen || blue != _glColor4fBlue) {
			gl.glColor4f(red, green, blue, alpha);
			_glColor4fRed = red;
			_glColor4fGreen = green;
			_glColor4fBlue = blue;
			_glColor4fAlpha = alpha;
		}
	}
	
	private static ByteBuffer _lastTexCoordPointerBuffer;
	public static void texCoordPointer(GL10 gl, ByteBuffer buffer) {
		if(!USE_HELPER || _lastTexCoordPointerBuffer != buffer) {
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, buffer);
			_lastTexCoordPointerBuffer = buffer;
		}
	}
	
	private static ByteBuffer _lastVertexPointerBuffer;
	public static void vertexPointer(GL10 gl, ByteBuffer buffer) {
		if(!USE_HELPER || _lastVertexPointerBuffer != buffer) {
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, buffer);
			_lastVertexPointerBuffer = buffer;
		}
	}
	
	private static float _drawTexCrop0, _drawTexCrop1, _drawTexCrop2, _drawTexCrop3 = -1;
	public static void drawTexCrop(GL11Ext gl, float[] buffer) {
		if(!USE_HELPER || _drawTexCrop0 != buffer[0] || _drawTexCrop1 != buffer[1] || _drawTexCrop2 != buffer[2] || _drawTexCrop3 != buffer[3]) {
			gl.glTexParameterfv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, buffer, 0);			
			_drawTexCrop0 = buffer[0];
			_drawTexCrop1 = buffer[1];
			_drawTexCrop2 = buffer[2];
			_drawTexCrop3 = buffer[3];
		}
	}
	
	private static boolean _texturesEnabled = true;
	public static void enableTextures(GL10 gl) {
		if(!USE_HELPER || !_texturesEnabled) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
			_texturesEnabled = true;
		}
	}
	
	public static void disableTextures(GL10 gl) {
		if(!USE_HELPER || _texturesEnabled) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			_texturesEnabled = true;
		}
	}
	
	public static void selectTexture(GL10 gl, Texture texture) {
		texture.select(gl);
	}

}
