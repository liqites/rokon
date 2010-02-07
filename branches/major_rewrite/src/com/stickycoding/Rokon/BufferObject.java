package com.stickycoding.Rokon;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import android.os.Build;

public class BufferObject {
	
	private ByteBuffer _byteBuffer;
	private IntBuffer _intBuffer;
	private boolean _useIntBuffer;
	
	public BufferObject() {
		if(Build.VERSION.SDK == "3")
			_byteBuffer = ByteBuffer.allocate(8*4);
		else
			_byteBuffer = ByteBuffer.allocateDirect(8*4);
		_byteBuffer.order(ByteOrder.nativeOrder());		
	}
	
	public BufferObject(boolean useIntBuffer) {
		if(useIntBuffer) {
			_useIntBuffer = true;

			_byteBuffer = ByteBuffer.allocateDirect(8*4);
			_byteBuffer.order(ByteOrder.nativeOrder());
			_intBuffer = _byteBuffer.asIntBuffer();
		} else {
			if(Build.VERSION.SDK == "3")
				_byteBuffer = ByteBuffer.allocate(8*4);
			else
				_byteBuffer = ByteBuffer.allocateDirect(8*4);
			_byteBuffer.order(ByteOrder.nativeOrder());	
		}
	}
	
	public void update(int x, int y, int width, int height) {
		if(_useIntBuffer) {
			_intBuffer.position(0);
			_intBuffer.put(x);
			_intBuffer.put(y);
			_intBuffer.put(x + width);
			_intBuffer.put(y);
			_intBuffer.put(x);
			_intBuffer.put(y + height);
			_intBuffer.put(x + width);
			_intBuffer.put(y + height);
			_intBuffer.position(0);
		} else {
			_byteBuffer.position(0);
			_byteBuffer.putInt(x);
			_byteBuffer.putInt(y);
			_byteBuffer.putInt(x + width);
			_byteBuffer.putInt(y);
			_byteBuffer.putInt(x);
			_byteBuffer.putInt(y + height);
			_byteBuffer.putInt(x + width);
			_byteBuffer.putInt(y + height);
			_byteBuffer.position(0);
		}
	}
	
	public IntBuffer getIntBuffer() {
		return _intBuffer;
	}
	
	public ByteBuffer get() {
		return _byteBuffer;
	}

}
