package com.stickycoding.Rokon;

public class FastTexturedObject extends DrawableObject {

	private Texture _texture;
	private FastTextureBuffer _textureBuffer;
	
	private int _tileX, _tileY;
	
	public FastTexturedObject(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height);
		_textureBuffer = new FastTextureBuffer(texture);
		setTexture(texture);
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
		
	}
	
	public FastTextureBuffer getTextureBuffer() {
		return _textureBuffer;
	}
	
	/**
	 * @param texture applies a Texture to the Sprite
	 */
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
		_textureBuffer.setTexture(texture);
		onTextureUpdate();
	}
	
	/**
	 * Removes the Texture that has been applied to the Sprite
	 */
	public void resetTexture() {
		_texture = null;
	}
	
	/**
	 * @return the current Texture tile index that is being used by the Sprite
	 */
	public int getTileIndex() {
		int tileIndex = 0;
		tileIndex += _tileX;
		tileIndex += (_tileY - 1) * _texture.getTileColumnCount();
		return tileIndex;
	}	
	
	/**
	 * Sets the Texture tile index to be used by the Sprite by columns and rows, rather than index
	 * @param tileX column
	 * @param tileY row
	 */
	public void setTile(int tileX, int tileY) {
		_tileX = tileX;
		_tileY = tileY;
		_textureBuffer.setTile(tileX, tileY);
		onTextureUpdate();
	}
	
	/**
	 * @return the current Texture applied to the Sprite
	 */
	public Texture getTexture() {
		return _texture;
	}
	
	/**
	 * @param tileIndex the index of the Texture tile to be used by the Sprite, 1-based
	 */
	public void setTileIndex(int tileIndex) {
		if(_texture == null) {
			Debug.print("Error - Tried setting tileIndex of null texture");
			return;			
		}
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		tileIndex += 1;
		onTextureUpdate();
	}
	
	protected void onTextureUpdate() { 
		if(_texture == null)
			return;
		
		if(_texture.getTextureAtlas() == null)
			return;
		
		_textureBuffer.update();
	}

}
