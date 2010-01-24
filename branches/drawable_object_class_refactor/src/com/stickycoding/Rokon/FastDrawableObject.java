package com.stickycoding.Rokon;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

public class FastDrawableObject extends DynamicObject {

	private boolean _killMe = false;
	
	public FastDrawableObject(float x, float y, float width, float height) {
		super(x, y, width, height);	
	}

	public void resetModifiers() { }
	
	/**
	 * Revives the Sprite, so that it can be used again.
	 */
	public void revive() {
		_killMe = false;
	}

	/**
	 * @return TRUE if the Sprite is marked for removal from the Layer after the next frame.
	 */
	public boolean isDead() {
		return _killMe;
	}
	
	/**
	 * Marks the Sprite for removal, it will be taken off the Layer at the end of the current frame 
	 */
	public void markForRemoval() {
		_killMe = true;
	}
	
	public void drawFrame(GL10 gl) { }
	
	/**
	 * Updates the vertex buffer, should be done if you modify any variables manually
	 */
	protected void onUpdate() {
		super.onUpdate();
	}
	
}
