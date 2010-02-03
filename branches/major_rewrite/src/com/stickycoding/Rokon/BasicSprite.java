package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import android.os.Build;

import com.stickycoding.Rokon.Handlers.AnimationHandler;
import com.stickycoding.Rokon.Handlers.CollisionHandler;


/**
 * Sprite handles both visual and physical
 * parts of in game objects. Basic dynamics can be applied.
 *
 * @author Richard
 */
public class BasicSprite extends TexturedObject {

	private boolean _visible;
	
	private float _red;
	private float _green;
	private float _blue;
	private float _alpha;
	
	public int intVar1, intVar2, intVar3;
	
	public BasicSprite(float x, float y, float width, float height) {
		this(x, y, width, height, null);
	}
		
	public BasicSprite(float x, float y, float width, float height, Texture texture) {
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
	
	public BasicSprite(float x, float y, Texture texture) {
		this(x, y, 0, 0, texture);
		setWidth(texture.getWidth() / texture.getTileColumnCount(), true);
		setHeight(texture.getHeight() / texture.getTileRowCount(), true);
		onUpdate();
	}
	
	public BasicSprite(Texture texture) {
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
	private boolean hasTexture;
	public void drawFrame(GL10 gl) {
		if(!_visible)
			return;
		
		if(notOnScreen())
			return;
		
		if(getTexture() == null)
			hasTexture = false;
		else
			hasTexture = true;
		
		if(!hasTexture) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		} else {
			getTexture().select(gl);
		}
		
		gl.glLoadIdentity();
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, getVertexBuffer());

		if(getRotation() != 0) {
			if (getRotationPivotRelative()) {
				gl.glTranslatef(getX() + (getScaleX() * getRotationPivotX()), getY() + (getScaleY() * getRotationPivotY()), 0);
				gl.glRotatef(getRotation(), 0, 0, 1);
				gl.glTranslatef(-1 * (getX() + (getScaleX() * getRotationPivotX())), -1 * (getY() + (getScaleY() * getRotationPivotY())), 0);
			} else {
				gl.glTranslatef(getRotationPivotX(), getRotationPivotY(), 0);
				gl.glRotatef(getRotation(), 0, 0, 1);
				gl.glTranslatef(-1 * getRotationPivotX(), -1 * getRotationPivotY(), 0);
			}
		}

		gl.glColor4f(_red, _green, _blue, _alpha);
		if(hasTexture)
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, getTextureBuffer().getBuffer());	

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		//((GL11Ext)gl).glTexParameterfv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, getTextureBuffer().getFloatBuffer(), 0);
		//((GL11Ext)gl).glDrawTexiOES((int)(getX() + getOffsetX()), (int)(getY() + getOffsetY()), 0, (int)(getWidth() * getScaleX()), (int)(getHeight() * getScaleY()));
		//((GL11Ext)gl).glDrawTexiOES(5, Rokon.screenHeight - 5, 0, 100, 100);
		
		if(!hasTexture) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnable(GL10.GL_TEXTURE_2D);
		}
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
}