package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

public class Window {
	
	public int x, y, width, height;
	public boolean central;
	
	public int windowViewWidth, windowViewHeight;
	
	public Sprite sprite;
	
	private int realX, realY;
	
	public Window(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void onDraw(GL10 gl) {
		
		realX = x;
		realY = y;
		
		if(x - DeviceScreen.HALF_WIDTH < 0)
			realX = DeviceScreen.HALF_WIDTH;
		if(y - DeviceScreen.HALF_HEIGHT < 0)
			realY = DeviceScreen.HALF_HEIGHT;
		if(x > width - DeviceScreen.HALF_WIDTH) 
			realX = width - DeviceScreen.HALF_WIDTH;
		if(y > height - DeviceScreen.HALF_HEIGHT) 
			realY = height - DeviceScreen.HALF_HEIGHT;
		
		if(central) {
			gl.glTranslatex(- realX + DeviceScreen.HALF_WIDTH, - realY + DeviceScreen.HALF_HEIGHT, 0);
		} else {
			gl.glTranslatex(-realX, -realY, 0);
		}
	}
	
	

}
