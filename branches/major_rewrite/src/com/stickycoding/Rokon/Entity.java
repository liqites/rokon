package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

public class Entity {
	
	private boolean _dead;
	
	private int _x, _y, _width, _height;
	private int _velX, _velY, _accX, _accY;
	private int _rotationAngle, _rotationPivotX, _rotationPivotY;
	private boolean _rotateAboutCentre = true;
	
	private float _xf, _yf, _widthf, _heightf;
	private float _velXf, _velYf, _accXf, _accYf;
	private float _rotationAnglef, _rotationPivotXf, _rotationPivotYf;
	
	private boolean _requiresPositionUpdate;
	
	public void remove() {
		_dead = true;
	}
	
	public boolean isDead() {
		return _dead;
	}

	protected void makeAlive() {
		_dead = false;
	}
	
	public void setRotationPivot(int rotationPivotX, int rotationPivotY) {
		_rotationPivotX = rotationPivotX;
		_rotationPivotY = rotationPivotY;
		_rotateAboutCentre = false;
	}
	
	public void setRotationPivotAboutCentre() {
		_rotateAboutCentre = true; 
	}
	
	public void setRotation(int rotationAngle) {
		_rotationAngle = rotationAngle;
	}
	
	public void setRotationf(float rotationAngle) {
		_rotationAnglef = rotationAngle;
	}
	
	public void rotate(int rotationAngle) {
		_rotationAngle += rotationAngle;
	}
	
	public void rotate(float rotationAngle) {
		_rotateionAnglef += rotationAngle;
	}
	
	public void setX(int x) {
		_x = x;
		_requiresPositionUpdate = true;
	}
	
	public void setXf(float x) {
		_xf = x;
		_requiresPositionUpdate = true;
	}
	
	public void setY(int y) {
		_y = y;
		_requiresPositionUpdate = true;
	}
	
	public void setYf(float y) {
		_yf = y;
		_requiresPositionUpdate = true;
	}
	
	public void setPos(int x, int y) {
		_x = x;
		_y = y;
		_requiresPositionUpdate = true;
	}
	
	public void setPosf(float x, float y) {
		_xf = x;
		_yf = y;
		_requiresPositionUpdate = true;
	}
	
	public int getX() {
		return _x;
	}
	
	public float getXf() {
		return _xf;
	}
	
	public int getY() {
		return _y;
	}
	
	public float getYf() {
		return _yf;
	}
	
	public void setWidth(int width) {
		_width = width;
		_requiresPositionUpdate = true;
	}
	
	public void setWidth(float width) {
		_widthf = width;
		_requiresPositionUpdate = true;
	}
	
	public void setHeight(float height) {
		_heightf = height;
		_requiresPositionUpdate = true;
	}
	
	public void setHeight(int height) {
		_height = height;
		_requiresPositionUpdate = true;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public float getHeightf() {
		return _heightf;
	}
	
	public int getWidth() {
		return _width;
	}
	
	public float getWidthf() {
		return _widthf;
	}
	
	public void setSize(int width, int height) {
		_width = width;
		_height = height;
		_requiresPositionUpdate = true;
	}
	
	public void setSizef(float width, float height) {
		_widthf = width;
		_heightf = height;
		_requiresPositionUpdate = true;
	}
	
	public void setVelX(int velX) {
		_velX = velX;
	}
	
	public void setVelXf(float velX) {
		_velXf = velX;
	}
	
	public void setVelY(int velY) {
		_velY = velY;
	}
	
	public void setVelYf(float velY) {
		_velYf = velY;
	}
	
	public int getVelX() {
		return _velX;
	}
	
	public float getVelXf() {
		return _velXf;
	}
	
	public int getVelY() {
		return _velY;
	}
	
	public float getVelYf() {
		return _velYf;
	}
	
	public void setVel(int velX, int velY) {
		_velX = velX;
		_velY = velY;
	}
	
	public void setVelf(float velX, float velY) {
		_velXf = velX;
		_velYf = velY;
	}
	
	public void setVelRad(int velocity, int angle) {
		//TODO set velocity from angle, 0 to 0x10000
	}
	
	public void setVelRadf(float velocity, float angle) {
		//TODO set velocity from angle, 0 to 1
	}
	
	public void setAccX(int accX) {
		_accX = accX;
	}
	
	public void setAccXf(float accX) {
		_accXf = accX;
	}
	
	public void setAccY(int accY) {
		_accY = accY;
	}
	
	public void setAccYf(float accY) {
		_accYf = accY;
	}
	
	public int getAccX() {
		return _accX;
	}
	
	public float getAccXf() {
		return _accXf;
	}
	
	public int getAccY() {
		return _accY;
	}
	
	public float getAccYf() {
		return _accYf;
	}
	
	public void setAcc(int accX, int accY) {
		_accX = accX;
		_accY = accY;
	}
	
	public void setAccf(float accX, float accY) {
		_accXf = accX;
		_accYf = accY;
	}
	
	public void setAccRad(int acceleration, int angle) {
		//TODO set acceleration from angle, 0 to 0x10000
	}
	
	public void setAccRad(float acceleration, float angle) {
		//TODO set acceleration from angle, 0 to 1
	}
	
	protected void onDraw(GL10 gl) {
		
	}
	
	protected void onUpdate() {
		if(Rokon.isFixedPoints()) {
			if(_accX != 0)
				_velX += _accX * Rokon.timeModifier;
			if(_velX != 0) {
				_x += _velX * Rokon.timeModifier;
				_requiresPositionUpdate = true;
			}
			if(_accY != 0)
				_velY += _accY * Rokon.timeModifierf;
			if(_velY!= 0) {
				_y += _velY * Rokon.timeModifier;
				_requiresPositionUpdate = true;
			}
		} else {
			if(_accXf != 0)
				_velXf += _accXf * Rokon.timeModifierf;
			if(_velXf != 0) {
				_xf += _velXf * Rokon.timeModifierf;
				_requiresPositionUpdate = true;
			}
			if(_accYf != 0)
				_velYf += _accYf * Rokon.timeModifierf;
			if(_velYf != 0) {
				_yf += _velYf * Rokon.timeModifierf;
				_requiresPositionUpdate = true;
			}
		}
	}
	
	

}
