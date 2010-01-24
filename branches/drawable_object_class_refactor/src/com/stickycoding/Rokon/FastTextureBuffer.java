package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Build;

/**
 * A holder for the a Texture buffer, easier way of handling the ByteBuffer
 * @author Richard
 */
public class FastTextureBuffer {
	
	private float[] _floatBuffer;
	private Texture _texture;
	private float _clipLeft = 0, _clipRight = 0, _clipTop = 0, _clipBottom = 0;
	private int _tileX, _tileY;
	
	public FastTextureBuffer() {
		_texture = null;
		_floatBuffer = new float[4];
		_tileX = 1;
		_tileY = 1;
		update();
	}
	
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
		update();
	}
	
	public void setTileIndex(int tileIndex) {
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		update();
	}

	public void setTile(int tileX, int tileY) {
		_tileX = tileX;
		_tileY = tileY;
	}
	
	public FastTextureBuffer(Texture texture) {
		_texture = texture;
		_floatBuffer = new float[4];
		_tileX = 1;
		_tileY = 1;
		update();
	}
	
	public FastTextureBuffer(Texture texture, int tileIndex) {
		_texture = texture;
		_floatBuffer = new float[4];
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		update();
	}
	
	public float[] getFloatBuffer() {
		return _floatBuffer;
	}
	
	public void clip(float left, float right, float top, float bottom) {
        _clipLeft = left;
        _clipTop = top;
        _clipRight = right;
        _clipBottom = bottom;
        update();
}
	
	public Texture getTexture() {
		return _texture;
	}
	
	public void update() {
		if(_texture == null || _texture.getTextureAtlas() == null)
			return;

		_floatBuffer[0] = getTexture().getAtlasX() + (getTexture().getWidth() * _clipLeft);
		_floatBuffer[1] = getTexture().getAtlasY() + getTexture().getHeight() - (getTexture().getHeight() * _clipBottom);
		_floatBuffer[2] = getTexture().getWidth() - (getTexture().getWidth() * _clipLeft) - (getTexture().getWidth() * _clipRight);
		_floatBuffer[3] = - getTexture().getHeight() + (getTexture().getHeight() * _clipBottom) + (getTexture().getHeight() * _clipTop);
	}

}
