package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

public class Entity {
	
	public static final int NORMAL = 0, DRAWTEX = 1, VBO = 2;
	
	private int _drawType;
	
	private int _x, _y, _width, _height;
	private int _offsetX, _offsetY;
	private float _red, _green, _blue, _alpha;
	private boolean _visible;
	
	private Texture _texture;
	private TextureBuffer _textureBuffer;
	private VertexBuffer _vertexBuffer;
	
	private boolean _hasTexture;
	private boolean _refreshVertices, _refreshTexture;
	
	private int _drawX, _drawY, _drawWidth, _drawHeight;
	
	private int _tileX, _tileY;
	
	public Entity(int x, int y, int width, int height, Texture texture, int drawType) {
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_texture = texture;
		_drawType = drawType;
		if(texture != null) {
			_hasTexture = true;
			_textureBuffer = new TextureBuffer(texture, drawType);
		} else
			_hasTexture = false;
		switch(_drawType) {
			case NORMAL:
				_vertexBuffer = new VertexBuffer(_x, _y, _x + _width, _y + _height);
				break;
			case DRAWTEX:
				
				break;
			case VBO:
				break;
			default:
				Debug.warning("Entity created with invalid drawType [" + drawType + "]");
		}
	}
	
	public Entity(int x, int y, int width, int height, Texture texture) {
		this(x, y, width, height, texture, Constants.DEFAULT_DRAW_TYPE);
	}
	
	public Entity(int x, int y, int width, int height, int drawType) {
		this(x, y, width, height, null, drawType);
	}
	
	public Entity(int x, int y, int width, int height) {
		this(x, y, width, height, null, Constants.DEFAULT_DRAW_TYPE);
	}
	
	public Entity(int x, int y, Texture texture, int drawType) {
		this(x, y, texture.getWidth(), texture.getHeight(), texture, drawType);
	}
	
	public Entity(int x, int y, Texture texture) {
		this(x, y, texture.getWidth(), texture.getHeight(), texture, Constants.DEFAULT_DRAW_TYPE);
	}
	
	protected void onBeforeDraw() {
		if(_refreshVertices) {
			onUpdateVertices();
			_refreshVertices = false;
		}
		if(_refreshTexture) {
			onUpdateTexture();
			_refreshTexture = false;
		}
	}
	
	protected void onDraw(GL10 gl) {
		if(!_visible)
			return;
		
		if(!_hasTexture)
			GLHelper.disableTextures(gl);
		else
			GLHelper.selectTexture(gl, _texture);
		
		switch(_drawType) {
			case NORMAL:
				GLHelper.glColor4f(gl, _red, _green, _blue, _alpha);
				gl.glLoadIdentity();
				GLHelper.vertexPointer(gl, _vertexBuffer.get());
				gl.glVertexPointer(2, GL10.GL_FLOAT, 0, _vertexBuffer.get());
				if(_hasTexture)
					GLHelper.texCoordPointer(gl, _textureBuffer.getBuffer());
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
				break;
			case DRAWTEX:
				if(!_hasTexture)
					break;
				GLHelper.glColor4f(gl, _red, _green, _blue, _alpha);
				GLHelper.drawTexCrop((GL11Ext)gl, getTextureBuffer().getFloatBuffer());
				((GL11Ext)gl).glDrawTexiOES(_drawX, Rokon.screenHeight - _drawY - _drawHeight, 0, _drawWidth, _drawHeight);
				break;
			case VBO:
				break;
		}
		
		if(!_hasTexture)
			GLHelper.enableTextures(gl);
	}
	
	public int roundUp(float val) {
		return (int)(val + 0.499);
	}
	
	protected void onUpdateVertices() {
		switch(_drawType) {
			case DRAWTEX:
				_drawWidth = roundUp(Rokon.toScreenX(_width));
				_drawHeight = roundUp(Rokon.toScreenY(_height));
				_drawX = roundUp(Rokon.toScreenX(_x + _offsetX));
				_drawY = Rokon.screenHeight - roundUp(Rokon.toScreenY(_y + _offsetY)) + _drawHeight;
				
				break;
			case NORMAL:
				_vertexBuffer.update(_x + _offsetX, _y + _offsetY, _x + _offsetX + _width, _y + _offsetY + _height);
				break;
			case VBO:
				break;
		}
	}
	
	protected void onUpdateTexture() {
		if(_hasTexture) {
			_textureBuffer.update();
		}
	}
	
	protected void refreshVertices() {
		_refreshVertices = true;
	}
	
	protected void refreshTexture() {
		_refreshTexture = true;
	}
	
	public boolean hasTexture() {
		return _hasTexture;
	}
	
	public void setX(int x) {
		_x = x;
		refreshVertices();
	}
	
	public int getX() {
		return _x;
	}
	
	public void setY(int y) {
		_y = y;
		refreshVertices();
	}
	
	public int getY() {
		return _y;
	}
	
	public void setXY(int x, int y) {
		_x = x;
		_y = y;
		refreshVertices();
	}
	
	public void move(int x, int y) {
		_x += x;
		_y += y;
		refreshVertices();
	}
	
	public void moveX(int x) {
		_x += x;
		refreshVertices();
	}
	
	public void moveY(int y) {
		_y += y;
		refreshVertices();
	}

	public void setWidth(int width) {
		_width = width;
		refreshVertices();
	}

	public int getWidth() {
		return _width;
	}

	public void setHeight(int height) {
		_height = height;
		refreshVertices();
	}

	public int getHeight() {
		return _height;
	}

	public void setRed(float red) {
		_red = red;
	}

	public float getRed() {
		return _red;
	}

	public void setGreen(float green) {
		_green = green;
	}

	public float getGreen() {
		return _green;
	}

	public void setBlue(float blue) {
		_blue = blue;
	}

	public float getBlue() {
		return _blue;
	}

	public void setAlpha(float alpha) {
		_alpha = alpha;
	}

	public float getAlpha() {
		return _alpha;
	}

	public void setTexture(Texture texture) {
		_texture = texture;
		if(texture != null) {
			refreshTexture();
			_hasTexture = true;
		} else
			_hasTexture = false;
	}

	public Texture getTexture() {
		return _texture;
	}

	public VertexBuffer getVertexBuffer() {
		return _vertexBuffer;
	}

	public TextureBuffer getTextureBuffer() {
		return _textureBuffer;
	}
	
	public void setTile(int x, int y) {
		_tileX = x;
		_tileY = y;
		if(_hasTexture)
			_textureBuffer.setTile(_tileX, _tileY);
		refreshTexture();
	}
	
	public void setTileIndex(int tileIndex) {
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		tileIndex += 1;
		if(_hasTexture)
			_textureBuffer.setTile(_tileX, _tileY);
		refreshTexture();
	}
	
	public int getTileX() {
		return _tileX;
	}
	
	public int getTileY() {
		return _tileY;
	}
	
	public int getTileIndex() {
		return _tileX + ((_tileY - 1) * _texture.getTileColumnCount());
	}

	public void setOffsetX(int offsetX) {
		_offsetX = offsetX;
	}

	public int getOffsetX() {
		return _offsetX;
	}

	public void setOffsetY(int offsetY) {
		_offsetY = offsetY;
	}

	public int getOffsetY() {
		return _offsetY;
	}

	public void setVisible(boolean visible) {
		_visible = visible;
	}

	public boolean isVisible() {
		return _visible;
	}
	
	

}
