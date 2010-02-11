package com.stickycoding.Rokon.Entities;

import android.view.MotionEvent;

import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;

public class Button extends Sprite {
	
	private Texture _upTexture, _onTexture, _pressedTexture;
	
	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void setTexture(Texture upTexture, Texture onTexture, Texture pressedTexture) {
		setTexture(upTexture);
		_upTexture = upTexture;
		_onTexture = onTexture;
		_pressedTexture = pressedTexture;
	}
	
	@Override
	public void onTouchDown(int x, int y, MotionEvent event) {
		if(_onTexture != null)
			setTexture(_onTexture);
	}
	
	@Override
	public void onTouchExit() {
		if(_upTexture != null)
			setTexture(_upTexture);
	}
	
	@Override
	public void onTouchUp(int x, int y, MotionEvent event) {
		if(_pressedTexture != null)
			setTexture(_pressedTexture);
	}

}
