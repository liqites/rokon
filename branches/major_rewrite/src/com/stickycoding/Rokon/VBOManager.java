package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL11;

public class VBOManager {
	
	public static final int MAX_VBO = 500;
	
	private static VBO[] _vbo = new VBO[MAX_VBO];
	private static boolean _requiresLoading = true;
	
	public static void loadBuffers(GL11 gl) {
		if(!_requiresLoading)
			return;
		int count = 0;
		for(int i = 0; i < MAX_VBO; i++)
			if(_vbo[i] != null && _vbo[i].getIndex() == -1)
				count++;
		if(count == 0)
			return;
		int[] bufferIndex = new int[count];
		gl.glGenBuffers(count, bufferIndex, 0);
		int j = 0;
		for(int i = 0; i < MAX_VBO; i++)
			if(_vbo[i] != null && _vbo[i].getIndex() == -1) {
				gl.glBindBuffer(_vbo[i].getDrawType(), bufferIndex[j]);
				int size = _vbo[i].getBuffer().capacity() * 4;
				gl.glBufferData(_vbo[i].getDrawType(), size, _vbo[i].getBuffer(), _vbo[i].getDrawType());
				_vbo[i].setIndex(bufferIndex[j]);
				j++;
			}
        gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
        _requiresLoading = false;
        Debug.print("Loading " + count + " VBOs");
	}
	
	public static void reload() {
		_requiresLoading = true;
		for(int i = 0; i < MAX_VBO; i++)
			if(_vbo[i] != null)
				_vbo[i].setIndex(-1);
	}
	
	public static void add(VBO vbo) {
		if(vbo.isAdded())
			return;
		for(int i = 0; i < MAX_VBO; i++)
			if(_vbo[i] == null) {
				_vbo[i] = vbo;
				vbo.setAdded();
				_requiresLoading = true;
				return;
			}
		Debug.warning("Not enough room to add VBO");
	}

}
