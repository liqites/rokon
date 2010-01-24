package com.stickycoding.Rokon;

/**
 * The superclass for all drawable objects, represents only a static object, its dimensions, and its rotation
 * @author Richard
 */
public class StaticObject {


	private float _startX, _startY, _startWidth, _startHeight;
	private float _x, _y, _offsetX, _offsetY;
	private float _rotation, _rotationPivotX, _rotationPivotY;
	private boolean _rotationPivotRelative = true;
	private float _width, _height, _scaleX, _scaleY;
	
	public StaticObject(float x, float y, float width, float height) {
		_x = x;
		_y = y;
		_startX = x;
		_startY = y;
		_startWidth = width;
		_startHeight = height;
		_width = width;
		_height = height;
		_scaleX = 1;
		_scaleY = 1;
		_offsetX = 0;
		_offsetY = 0;
		_rotationPivotX = (_width / 2);
		_rotationPivotY = (_height / 2);
	}
	
	protected void onUpdate() { }
	
	/**
	 * Sets the offset at which the sprite is drawn on screen
	 * @param offsetX
	 * @param offsetY
	 */
	public void setOffset(float offsetX, float offsetY) {
		_offsetX = offsetX;
		_offsetY = offsetY;
		onUpdate();
	}
	
	public float getOffsetX() {
		return _offsetX;
	}
	
	public float getOffsetY() {
		return _offsetY;
	}
	/**
	 * @param rotation angle, in degrees, to rotate the Sprite relative to its current angle
	 */
	public void rotate(float rotation) {
		_rotation += rotation;
	}
	
	/**
	 * @param rotation angle, in degrees, to set the Sprite's rotation
	 */
	public void setRotation(float rotation) {
		_rotation = rotation;
	}
	
	/**
	 * @return the current angle, in degrees, at which the Sprite is at
	 */
	public float getRotation() {
		return _rotation;
	}
	
	/**
	 * Sets the rotation pivot x coordinate
	 * @param x
	 */
	public void setRotationPivotX(float x) {
		_rotationPivotX = x;
	}
	
	/**
	 * Get rotation pivot x coordinate
	 */
	public float getRotationPivotX() {
		return _rotationPivotX;
	}
	
	/**
	 * Sets the rotation pivot x coordinate
	 * @param x
	 */
	public void setRotationPivotY(float y) {
		_rotationPivotY = y;
	}
	
	/**
	 * Get rotation pivot y coordinate
	 */
	public float getRotationPivotY() {
		return _rotationPivotY;
	}
	
	/**
	 * Defines rotation pivot coordinates as relative to the sprite. 
	 * This does not change the actual pivot coordinates, but defines how the
	 * pivot coordinates are interpreted when rotating
	 */
	public void setRotationPivotRelative() {
		_rotationPivotRelative = true;
	}
	
	/**
	 * Defines rotation pivot coordinates as absolute/fixed (not relative to sprite). 
	 * This does not change the actual pivot coordinates, but defines how the
	 * pivot coordinates are interpreted when rotating
	 */
	public void setRotationPivotAbsolute() {
		_rotationPivotRelative = false;
	}
	
	/**
	 * @return TRUE if rotation pivot is relative, FALSE if absolute/fixed
	 */
	public boolean getRotationPivotRelative() {
		return _rotationPivotRelative;
	}
	
	/**
	 * @param scaleX a multiplier to scale your Sprite in the X direction when drawing
	 */
	public void setScaleX(float scaleX) {
		_scaleX = scaleX;
		onUpdate();
	}
	
	/**
	 * @return the current scale multiplier in X direction
	 */
	public float getScaleX() {
		return _scaleX;
	}
	
	/**
	 * @param scaleY a multiplier to scale your Sprite in the Y direction when drawing
	 */
	public void setScaleY(float scaleY) {
		_scaleY = scaleY;
		onUpdate();
	}
	
	/**
	 * @return the current scale multiplier in Y direction
	 */
	public float getScaleY() {
		return _scaleY;
	}
	
	/**
	 * Note that scale is not considered in collisions
	 * @param scaleX a multiplier to scale your Sprite in the X direction when drawing
	 * @param scaleY a multiplier to scale your Sprite in the Y direction when drawing
	 */
	public void setScale(float scaleX, float scaleY) {
		_scaleX = scaleX;
		_scaleY = scaleY;
		onUpdate();
	}

	/**
	 * @param x the top left position of your Sprite, in the X direction
	 */
	public void setX(float x) {
		_x = x;
		onUpdate();
	}
	
	/**
	 * @param y the top left position of your Sprite, in the Y direction
	 */
	public void setY(float y) {
		_y = y;
		onUpdate();
	}
	
	/**
	 * Sets the position of the Sprite, in pixels
	 * @param x 
	 * @param y
	 */
	public void setXY(float x, float y) {
		_x = x;
		_y = y;
		onUpdate();
	}
	
	/**
	 * @param x number of pixels to move the Sprite relative to its current position
	 */
	public void moveX(float x) {
		_x += x;
		onUpdate();
	}

	
	/**
	 * @param u number of pixels to move the Sprite relative to its current position
	 */
	public void moveY(float y) {
		_y += y;
		onUpdate();
	}
	
	/**
	 * Moves the Sprite relative to its current position
	 * @param x
	 * @param y
	 */
	public void move(float x, float y) {
		_x += x;
		_y += y;
		onUpdate();
	}
	
	/**
	 * @return the top left X position of the Sprite
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * @return the top left X position of the Sprite
	 */
	public float getY() {
		return _y;
	}
	
	/**
	 * @param width width of the Sprite, used for collisions and multiplied by scale when drawn
	 */
	public void setWidth(float width) {
		_width = width;
	}
	
	/**
	 * @param width width of the Sprite, used for collisions and multiplied by scale when drawn
	 * @param start TRUE if startWidth should be set also
	 */
	public void setWidth(float width, boolean start) {
		_width = width;
		_startWidth = width;
	}

	/**
	 * @param height height of the Sprite, used for collisions and multiplied by scale when drawn
	 */
	public void setHeight(float height) {
		_height = height;
	}
	
	/**
	 * @param height height of the Sprite, used for collisions and multiplied by scale when drawn
	 * @param start TRUE if startHeight should be set also
	 */
	public void setHeight(float height, boolean start) {
		_height = height;
		_startHeight = _height;
	}
	
	/**
	 * @return current width of the Sprite
	 */
	public float getWidth() {
		return _width;
	}
	
	/**
	 * @return current height of the Sprite
	 */
	public float getHeight() {
		return _height;
	}
	
	/**
	 * @return current width of the Sprite, rounded to the nearest Integer
	 */
	public int getScreenWidth() {
		return (int)_width;
	}
	
	/**
	 * @return current height of the Sprite, rounded to the nearest Integer
	 */
	public int getScreenHeight() {
		return (int)_height;
	}
	
	/**
	 * Resets the X, Y, and dimenensions to their original value
	 */
	public void reset() {
		_x = _startX;
		_y = _startY;
		_width = _startWidth;
		_height = _startHeight;
	}
	
	/**
	 * @return TRUE if the StaticObject does not appear to be on-screen
	 */
	public boolean notOnScreen() {
		if(Rokon.getRokon().isForceOffscreenRender())
			return false;
		if(getX() + getWidth() < 0 || getX() > Rokon.getRokon().getWidth())
			return true;
		if(getY() + getHeight() < 0 || getY() > Rokon.getRokon().getHeight())
			return true;
		return false;
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
}
