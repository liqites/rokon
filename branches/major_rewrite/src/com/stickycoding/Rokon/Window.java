package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.KillBall.Game;

public class Window {
	
	public int x, y, width, height;
	public boolean central;
	
	public int windowViewWidth, windowViewHeight;
	public int windowViewMidX, windowViewMidY;
	
	public Sprite sprite;
	
	private int realX, realY;
	
	public Window(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		windowViewWidth = Game.GAME_WIDTH;
		windowViewHeight = Game.GAME_HEIGHT;
		windowViewMidX = (windowViewWidth / 2) - FP.fromInt(32);
		windowViewMidY = (windowViewHeight / 2) - FP.fromInt(32);
	}
	
	protected void onDraw(GL10 gl) {
		
		realX = x;
		realY = y;
		
		if(x - windowViewMidX < 0)
			realX = windowViewMidX;
		if(y - windowViewMidY < 0)
			realY = windowViewMidY;
		if(x > width - windowViewMidX - FP.fromInt(64)) 
			realX = width - windowViewMidX - FP.fromInt(64);
		if(y > height - windowViewMidY - FP.fromInt(64)) 
			realY = height - windowViewMidY - FP.fromInt(64);
		
		if(central) {
			gl.glTranslatex(- realX + windowViewMidX, - realY + windowViewMidY, 0);
		} else {
			gl.glTranslatex(-realX, -realY, 0);
		}
	}
	
	

}
