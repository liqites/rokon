package com.stickycoding.Rokon;

public class Sprite extends Entity {
	
	private int _drawPriority = Rokon.getDrawPriority();
	
	private boolean _hasTexture;
	private Texture _texture;
	
	public int getDrawPriority() {
		return _drawPriority;
	}
	
	public void setDrawPriority(int drawPriority) {
		_drawPriority = drawPriority;
	}
	
	public void setTexture(Texture texture) {
		_texture = texture;
		_hasTexture = (_texture != null);
	}
	
	public Texture getTexture() {
		return _texture;
	}
	
	public boolean hasTexture() {
		return _hasTexture;
	}
	
	protected void onDraw() {
		
	}
	
	protected void onUpdate() {
		super.onUpdate();
		//TODO Handle animations
	}

}
