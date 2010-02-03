package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Build;

/**
 * A holder for the a Texture buffer, easier way of handling the ByteBuffer
 * @author Richard
 */
public class TextureBuffer {

	public static final int NORMAL = 0, DRAWTEX = 1, VBO = 2;
	private int _drawType;
	
	private float[] _floatBuffer;
	private ByteBuffer _buffer;
	private Texture _texture;
	private float _clipLeft = 0, _clipRight = 0, _clipTop = 0, _clipBottom = 0;
	private int _tileX, _tileY;
	
	public TextureBuffer(Texture texture, int drawType) {
		_drawType = drawType;
		_texture = texture;
		switch(drawType) {
			case NORMAL:
				if(Build.VERSION.SDK == "3")
					_buffer = ByteBuffer.allocate(8*4);
				else
					_buffer = ByteBuffer.allocateDirect(8*4);
				_buffer.order(ByteOrder.nativeOrder());
				break;
			case DRAWTEX:
				_floatBuffer = new float[4];
				break;
			case VBO:
				break;
		}
		_tileX = 1;
		_tileY = 1;
	}
	
	public TextureBuffer(Texture texture) {
		this(texture, Constants.DEFAULT_DRAW_TYPE);
	}
	
	public TextureBuffer() {
		this(null, Constants.DEFAULT_DRAW_TYPE);
	}
	
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
	}
	
	public void setTileIndex(int tileIndex) {
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
	}

	public void setTile(int tileX, int tileY) {
		_tileX = tileX;
		_tileY = tileY;
	}
	
	public ByteBuffer getByteBuffer() {
		return _buffer;
	}
	
	public float[] getFloatBuffer() {
		return _floatBuffer;
	}
	
	public void clip(float left, float right, float top, float bottom) {
        _clipLeft = left;
        _clipTop = top;
        _clipRight = right;
        _clipBottom = bottom;
	}
	
	public Texture getTexture() {
		return _texture;
	}
	
	private float _x1, _y1, _x2, _y2, xs, ys;
	public void update() {
		if(_texture == null || _texture.getTextureAtlas() == null)
			return;
		
		switch(_drawType) {
			case NORMAL:	
				_x1 = _texture.getAtlasX();
				_y1 = _texture.getAtlasY();
				_x2 = _texture.getAtlasX() + _texture.getWidth();
				_y2 = _texture.getAtlasY() + _texture.getHeight();
				
				xs = (_x2 - _x1) / _texture.getTileColumnCount();
				ys = (_y2 - _y1) / _texture.getTileRowCount();
	
				_x1 = _texture.getAtlasX() + (xs * (_tileX - 1));
				_y1 = _texture.getAtlasY() + (ys * (_tileY - 1));
				_x2 = _texture.getAtlasX() + (xs * (_tileX - 1)) + xs; 
				_y2 = _texture.getAtlasY() + (ys * (_tileY - 1)) + ys;
				
                _x1 = _x1 / _texture.getTextureAtlas().getWidth();
                _y1 = _y1 / _texture.getTextureAtlas().getHeight();
                _x2 = _x2 / _texture.getTextureAtlas().getWidth();
                _y2 = _y2 / _texture.getTextureAtlas().getHeight();
	
				_buffer.position(0);		
				_buffer.putFloat(_x1); _buffer.putFloat(_y1);
				_buffer.putFloat(_x2); _buffer.putFloat(_y1);
				_buffer.putFloat(_x1); _buffer.putFloat(_y2);
				_buffer.putFloat(_x2); _buffer.putFloat(_y2);		
				_buffer.position(0);
				break;
			case DRAWTEX:
				_floatBuffer[0] = getTexture().getAtlasX();
				_floatBuffer[1] = getTexture().getAtlasY() + getTexture().getHeight();
				_floatBuffer[2] = getTexture().getWidth();
				_floatBuffer[3] = - getTexture().getHeight();
				break;
			case VBO:
				break;
		}
	}

}
