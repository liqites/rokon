package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;


/**
 * Sprite handles both visual and physical
 * parts of in game objects. Basic dynamics can be applied.
 *
 * @author Richard
 */
public class FastSprite extends FastTexturedObject {

	private boolean _visible;
	
	private float _red;
	private float _green;
	private float _blue;
	private float _alpha;
	
	public FastSprite(float x, float y, float width, float height) {
		this(x, y, width, height, null);
	}
		
	public FastSprite(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height, texture);
		_red = 1;
		_green = 1;
		_blue = 1;
		_alpha = 1;
		_visible = true;
		revive();
		resetDynamics();
		onUpdate();
	}
	
	public FastSprite(float x, float y, Texture texture) {
		this(x, y, 0, 0, texture);
		setWidth(texture.getWidth() / texture.getTileColumnCount(), true);
		setHeight(texture.getHeight() / texture.getTileRowCount(), true);
		onUpdate();
	}
	
	public FastSprite(Texture texture) {
		this(0, 0, 0, 0, texture);
		setWidth(texture.getWidth() / texture.getTileColumnCount(), true);
		setHeight(texture.getHeight() / texture.getTileRowCount(), true);
		onUpdate();
	}
	/**
	 * @param visible TRUE if the Sprite is to be drawn on the Layer, default is TRUE
	 */
	public void setVisible(boolean visible) {
		_visible = visible;
	}
	
	/**
	 * @return TRUE if the Sprite is being drawn onto the Layer
	 */
	public boolean isVisible() {
		return _visible;
	}
		
	/**
	 * Updates the texture buffers used by OpenGL, there should be no need to call this
	 */
	public void updateBuffers() {
		getTextureBuffer().update();
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setRed(float red) {
		_red = red;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setGreen(float green) {
		_green = green;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setBlue(float blue) {
		_blue = blue;
	}

	/**
	 * @param red 0.0 to 1.0
	 */
	public void setAlpha(float alpha) {
		_alpha = alpha;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setRedInt(int red) {
		_red = (float)red / 255f;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setGreenInt(int green) {
		_green = (float)green / 255f;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setBlueInt(int blue) {
		_blue = (float)blue / 255f;
	}

	/**
	 * @param red 0 to 255
	 */
	public void setAlphaInt(int alpha) {
		_alpha = (float)alpha / 255f;
	}
	
	/**
	 * @return current alpha value, 0.0 to 1.0
	 */
	public float getAlpha() {
		return _alpha;
	}
	
	/**
	 * Sets the color of the Sprite, this is still applied when a textured. 1,1,1,1 is white and 0,0,0,1 is black 
	 * @param red 0.0 to 1.0
	 * @param green 0.0 to 1.0
	 * @param blue 0.0 to 1.0 
	 * @param alpha 0.0 to 1.0
	 */
	public void setColor(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	/**
	 * @return current red value, 0.0 to 1.0
	 */
	public float getRed() {
		return _red;
	}
	
	/**
	 * @return current green value, 0.0 to 1.0
	 */
	public float getGreen() {
		return _green;
	}

	/**
	 * @return current blue value, 0.0 to 1.0
	 */
	public float getBlue() {
		return _blue;
	}

	/**
	 * @return current red value, 0 to 255
	 */
	public int getRedInt() {
		return Math.round(_red * 255);
	}

	/**
	 * @return current green value, 0 to 255
	 */
	public int getGreenInt() {
		return Math.round(_green * 255);
	}

	/**
	 * @return current blue value, 0 to 255
	 */
	public int getBlueInt() {
		return Math.round(_blue * 255);
	}

	/**
	 * @return current alpha value, 0 to 255
	 */
	public int getAlphaInt() {
		return Math.round(_alpha * 255);
	}

	/**
	 * Draws the Sprite to the OpenGL object, should be no need to call this
	 * @param gl
	 */
	
	GL11Ext gl11;
	float drawX, drawY, drawWidth, drawHeight;
	public void drawFrame(GL10 gl) {
		
		if(!_visible)
			return;
		
		if(_clipLeft >= 1 || _clipRight >= 1 || _clipTop >= 1 && _clipBottom >= 1)
			return;
		
		gl11 = (GL11Ext)gl;

		if(getTexture() != null)
			getTexture().select(gl);
		
		
		GLHelper.glColor4f(gl, _red, _green, _blue, _alpha);
		GLHelper.drawTexCrop(gl11, getTextureBuffer().getFloatBuffer());
		
		//gl11.glDrawTexiOES(roundUp(Rokon.toScreenX(getX() + getOffsetX() + ((getWidth() * getScaleX()) * _clipLeft))), roundUp(Rokon.toScreenY(getY() + getOffsetY() + (getHeight() * getScaleY() * _clipTop)) - Rokon.convertToScreenY((getHeight() * getScaleY()) - (getHeight() * getScaleY() * _clipBottom) - (getHeight() * getScaleY() * _clipTop)   )), 0, roundUp(Rokon.toScreenX(getWidth() * getScaleX() - ((getWidth() + getScaleX()) * _clipRight) - ((getWidth() + getScaleX()) * _clipLeft))), roundUp(Rokon.convertToScreenY(getHeight() * getScaleY() - (getHeight() * getScaleY() * _clipBottom) - (getHeight() * getScaleY() * _clipTop) )));
		gl11.glDrawTexfOES(drawX, Rokon.screenHeight - drawY - drawHeight, 0, drawWidth, drawHeight);
	}
	
	public int roundUp(float val) {
		//return (int)(Math.ceil(val));
		return (int)val;
	}
	
	private float _clipLeft = 0, _clipRight = 0, _clipTop = 0, _clipBottom = 0;
	public void setClip(float left, float right, float top, float bottom) {
		_clipLeft = left;
		_clipRight = right;
		_clipTop = top;
		_clipBottom = bottom;
		if(_clipLeft < 0)
			_clipLeft = 0;
		if(_clipLeft > 1)
			_clipLeft = 1;
		if(_clipRight < 0)
			_clipRight = 0;
		if(_clipRight > 1)
			_clipRight = 1;
		if(_clipBottom < 0)
			_clipBottom = 0;
		if(_clipBottom > 1)
			_clipBottom = 1;
		if(_clipTop < 0)
			_clipTop = 0;
		if(_clipTop > 1)
			_clipTop = 1;
		getTextureBuffer().clip(left, right, top, bottom);
		onUpdate();
	}
	
	/**
	 * Note, this is very basic and does represents only the rectangular Sprite
	 * @param x
	 * @param y
	 * @return TRUE if the Sprite is colliding with the given coordinates
	 */
	public boolean isAt(int x, int y) {
		if(x < getX() || x > getX() + getWidth())
			return false;
		if(y < getY() || y > getY() + getHeight())
			return false;
		return true;
	}
		
	/**
	 * Resets the sprite to the initial conditions
	 */
	public void reset() {
		reset(true);
	}
	
	/**
	 * Resets the sprite to the initial conditions
	 * @param resetTexture TRUE if the texture is to be reset to the first tile
	 */
	public void reset(boolean resetTexture) {
		super.reset();
		stop();
		if(resetTexture)
			setTileIndex(1);
	}
	

	protected void onUpdate() {
		//super.onUpdate();
		drawX = roundUp(Rokon.toScreenX(getX() + getOffsetX() + ((getWidth() * getScaleX()) * _clipLeft)));
		drawY = roundUp(Rokon.toScreenY(getY() + getOffsetY() + ((getHeight() * getScaleY()) * _clipTop)));
		drawWidth = roundUp(Rokon.toScreenX((getWidth() * getScaleX()) - (getWidth() * getScaleX() * _clipRight) - (getWidth() * getScaleX() * _clipLeft)));
		drawHeight = roundUp(Rokon.toScreenY((getHeight() * getScaleY()) - (getHeight() * getScaleY() * _clipBottom) - (getHeight() * getScaleY() * _clipTop)));
		if(_clipRight != 0)
			Debug.print("clip right = " + _clipRight + " dw=" + drawWidth);
		
	}
}