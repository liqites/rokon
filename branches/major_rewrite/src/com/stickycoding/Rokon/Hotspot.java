package com.stickycoding.Rokon;

/**
 * Hotspot class is used as a way of simplying the detection of touches on
 * the screen, and triggers onHotspotTouched
 *
 */
public class Hotspot {
	
	public float x;
	public float y;
	public float width;
	public float height;
	private int _id;
	
	private Entity _entity;
	
	/**
	 * Creates a Hotspot based on coodinates, with no ID
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Hotspot(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		_id = -1;
		_entity = null;
	}
	
	/**
	 * Creates a Hotspot based on coordinates, with a specified ID
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param id
	 */
	public Hotspot(float x, float y, float width, float height, int id) {
		this(x, y, width, height);
		_id = id;
	}
	
	/**
	 * Defines a hotspot by a sprite, with no ID number
	 * @param _dynamicObject
	 */
	public Hotspot(Entity entity) {
		_entity = entity;
	}
	
	/**
	 * Defines a hotspot by a sprite, with a specified ID
	 * @param _sprite
	 * @param id
	 */
	public Hotspot(Entity entity, int id) {
		this(entity);
		_id = id;
	}
	
	/**
	 * @return the ID of the Hotspot, for easier identification
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * Sets the ID of this particular Hotspot
	 * @param id
	 */
	public void setId(int id) {
		_id = id;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void update(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		_entity = null;
	}
	
	/**
	 * @param _dynamicObject
	 */
	public void update(Entity entity) {
		_entity = entity;
	}
	
	/**
	 * Returns the DynamicObject to which the Hotspot is bound
	 * @return NULL if not set
	 */
	public Entity getObject() {
		return _entity;
	}
}
