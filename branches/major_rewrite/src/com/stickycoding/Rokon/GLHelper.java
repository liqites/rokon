package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class GLHelper {
	
	private static GL10 _gl;
	private static boolean _glVertexArray, _glTexCoordArray, _glTexture2D;
	private static int _textureIndex = -1, _arrayBuffer = -1, _elementBuffer = -1, _srcBlendMode = -1, _dstBlendMode = -1;
	
	protected static void setGL(GL10 gl) {
		_gl = gl;
	}
	
	public static void enableVertexArray() {
		if(!_glVertexArray) {
			_gl.glEnable(GL10.GL_VERTEX_ARRAY);
			_glVertexArray = true;
		}
	}
	
	public static void disableVertexArray() {
		if(_glVertexArray) {
			_gl.glDisable(GL10.GL_VERTEX_ARRAY);
			_glVertexArray = false;
		}
	}
	
	public static void enableTexCoordArray() {
		if(!_glTexCoordArray) {
			_gl.glEnable(GL10.GL_TEXTURE_COORD_ARRAY);
			_glTexCoordArray = true;
		}
	}
	
	public static void disableTexCoordArray() {
		if(_glTexCoordArray) {
			_gl.glDisable(GL10.GL_TEXTURE_COORD_ARRAY);
			_glTexCoordArray = false;
		}
	}
	
	public static void enableTextures() {
		if(!_glTexture2D) {
			_gl.glEnable(GL10.GL_TEXTURE_2D);
			_glTexture2D = true;
		}
	}
	
	public static void disableTextures() {
		if(_glTexture2D) {
			_gl.glDisable(GL10.GL_TEXTURE_2D);
			_glTexture2D = false;
		}
	}
	
	public static void bindBuffer(int bufferIndex) {
		if(bufferIndex != _arrayBuffer) {
			((GL11)_gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferIndex);
			_arrayBuffer = bufferIndex;
		}
	}
	
	public static void bindElementBuffer(int elementBufferIndex) {
		if(elementBufferIndex != _elementBuffer) {
			((GL11)_gl).glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, elementBufferIndex);
			_elementBuffer = elementBufferIndex;
		}
	}
	
	public static void bindTexture(int textureIndex) {
		if(textureIndex != _textureIndex) {
			_gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex);
			_textureIndex = textureIndex;
		}
	}
	
	public static void blendMode(int srcBlendMode, int dstBlendMode) {
		if(srcBlendMode != _srcBlendMode || dstBlendMode != _dstBlendMode) {
			_gl.glBlendFunc(srcBlendMode, dstBlendMode);
			_srcBlendMode = srcBlendMode;
			_dstBlendMode = dstBlendMode;
		}
	}

}
