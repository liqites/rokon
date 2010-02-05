package com.stickycoding.Rokon;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL11;

public class VBO {
	
	private int _index = -1;
	private int _drawType = GL11.GL_STATIC_DRAW;
	private boolean _added;
	
	protected void setIndex(int index) {
		_index = index;
	}
	
	public int getIndex() {
		return _index;
	}
	
	public void setDrawType(int bufferType) {
		_drawType = bufferType;
	}
	
	public int getDrawType() {
		return _drawType;
	}
	
	protected boolean isAdded() {
		return _added;
	}
	
	protected void setAdded() {
		_added = true;
	}
	
	protected void removed() {
		_added = false;
	}
	
	public Buffer getBuffer() { return null; }
	public IntBuffer getIntBuffer() { return null; }
	public ByteBuffer getByteBuffer() { return null; }
	public FloatBuffer getFloatBuffer() { return null; }
	public int getSize() { return 0; }

}
