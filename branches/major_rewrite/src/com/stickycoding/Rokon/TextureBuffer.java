package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Build;

/**
 * A holder for the a Texture buffer, easier way of handling the ByteBuffer
 * @author Richard
 */
public class TextureBuffer {
	
	private ByteBuffer _buffer;
	private Texture _texture;
	private int _tileX, _tileY;
	private VBO _vbo;
	
	public TextureBuffer(Texture texture) {
		this(texture, 1, 1);
	}	
	
	public TextureBuffer(Texture texture, int tileCol, int tileRow) {
		setTexture(texture);
		if(Build.VERSION.SDK == "3")
			_buffer = ByteBuffer.allocate(8*4);
		else
			_buffer = ByteBuffer.allocateDirect(8*4);
		_buffer.order(ByteOrder.nativeOrder());
		_tileX = tileCol;
		_tileY = tileRow;
		update();
	}
	
	public TextureBuffer(Texture texture, int tileIndex) {
		setTexture(texture);
		if(Build.VERSION.SDK == "3")
			_buffer = ByteBuffer.allocate(8*4);
		else
			_buffer = ByteBuffer.allocateDirect(8*4);
		_buffer.order(ByteOrder.nativeOrder());
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		update();
	}
	
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
	}
	
	public ByteBuffer getBuffer() {
		return _buffer;
	}

	public Texture getTexture() {
		return _texture;
	}
	
	public void setTile(int tileIndex) {
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		tileIndex += 1;
		update();
	}
	
	public void setTile(int col, int row) {
		_tileX = col;
		_tileY = row;
		update();
	}
	
	private float _x1, _y1, _x2, _y2, xs, ys;
	public void update() {
		if(_texture.getTextureAtlas() == null)
			return;
		
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
		
		if(Rokon.usingVBO()) {
			_vbo = new VBO(_buffer);
			VBOManager.add(_vbo);
		}
	}

}
