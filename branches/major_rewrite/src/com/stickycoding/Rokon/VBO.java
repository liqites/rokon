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
	private Buffer _buffer;
	
	public VBO(ByteBuffer byteBuffer) {
		setByteBuffer(byteBuffer);
	}
	
	public VBO(IntBuffer intBuffer) {
		setIntBuffer(intBuffer);
	}
	
	public void setIntBuffer(IntBuffer intBuffer) {
		_buffer = intBuffer;
	}
	
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
	
	public void setByteBuffer(ByteBuffer byteBuffer) {
		_buffer = byteBuffer;
	}
	
	public Buffer getBuffer() { return _buffer; }
	public IntBuffer getIntBuffer() { return (IntBuffer)_buffer; }
	public ByteBuffer getByteBuffer() { return (ByteBuffer)_buffer; }
	public FloatBuffer getFloatBuffer() { return (FloatBuffer)_buffer; }
	public int getSize() { return _buffer.capacity(); }

}
