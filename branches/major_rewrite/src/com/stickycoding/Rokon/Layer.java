package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * A Layer contains your visible objects, Text, Sprites and Emitters.
 * They are useful to layer certain objects above others.
 * @author Richard
 */
public class Layer {
	public static final int MAX_ENTITIES = 85;
	public static final int MAX_TEXTS = 10;
	public static final int MAX_EMITTERS = 10;
	
	private Entity[] entityArr = new Entity[MAX_ENTITIES];
	private Emitter[] emitterArr = new Emitter[MAX_EMITTERS];
	private Text[] textArr = new Text[MAX_TEXTS];
	private int i, j, k, l, m, n, o, p, q;
	
	public Layer() {

	}
	
	/**
	 * Adds Text to the layer
	 * @param text
	 */
	public void addText(Text text) {
		j = -1;
		for(i = 0; i < MAX_TEXTS; i++)
			if(textArr[i] == null)
				j = i;
		if(j == -1) {
			Debug.print("TOO MANY TEXTS");
			return;
		}
		textArr[j] = text;
	}
	
	/**
	 * @param text removes a Text object from the layer
	 */
	public void removeText(Text text) {
		text.markForRemoval();
	}

	
	public void removeEntity(Entity entity) {
		entity.setDead(true);
	}
	
	public void addEntity(Entity entity) {
		m = -1;
		for(n = 0; n < MAX_ENTITIES; n++)
			if(entityArr[n] == null) {
				m = n;
				break;
			}
		if(m == -1) {
			Debug.print("TOO MANY ENTITIES");
			return;
		}
		entity.setDead(false);
		entityArr[m] = entity;
	}
	
	/**
	 * This is currently out of use.
	 * @param emitter
	 * @return
	 */
	private int aa, ab;
	public void addEmitter(Emitter emitter) {
		aa = -1;
		for(ab = 0; ab < MAX_EMITTERS; ab++)
			if(emitterArr[ab] == null) {
				aa = ab;
				ab = MAX_EMITTERS;
				break;
			}
		if(aa == -1) {
			Debug.print("TOO MANY EMITTERS");
			return;
		}
		emitterArr[aa] = emitter;
	}
	
	/**
	 * All layers must update their objects positions before they can be drawn, or tested for collisions
	 */
	public void updateMovement() {
		for(o = 0; o < MAX_ENTITIES; o++)
			if(entityArr[o] != null)
				entityArr[o].onBeforeDraw();
	}
	
	/**
	 * Updates all layers, and draws to the surface. There is no need to call this yourself.
	 * @param gl
	 */
	public void drawFrame(GL10 gl) {
		try {

			for(p = 0; p < MAX_ENTITIES; p++) {
				if(entityArr[p] != null) {
					if(entityArr[p].isDead())
						entityArr[p] = null;
					else
						entityArr[p].onDraw(gl);
				}
			}
			
			for(p = 0; p < MAX_EMITTERS; p++) {
				if(emitterArr[p] != null) {
					emitterArr[p].drawFrame(gl);
					if(emitterArr[p].isDead())
						emitterArr[p] = null;
				}
			}
			
			for(p = 0; p < MAX_TEXTS; p++) {
				if(textArr[p] != null) {
					textArr[p].drawFrame(gl);
					if(textArr[p].isDead())
						textArr[p] = null;
				}
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Removes everything from the layer
	 */
	public void clearLayer() {
		for(q = 0; q < MAX_ENTITIES; q++)
			if(entityArr[q] != null) {
				entityArr[q] = null;
			}
		
		for(q = 0; q < MAX_EMITTERS; q++)
			emitterArr[q] = null;
		
		for(q = 0; q < MAX_TEXTS; q++)
			textArr[q] = null;
	}
}
