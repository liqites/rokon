package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

public class Entity {
	
	private boolean _dead;
	
	public void remove() {
		_dead = true;
	}
	
	public boolean isDead() {
		return _dead;
	}

	protected void makeAlive() {
		_dead = false;
	}
	
	protected void onDraw(GL10 gl) {
		
	}

}
