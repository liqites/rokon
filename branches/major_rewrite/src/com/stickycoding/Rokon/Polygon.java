package com.stickycoding.Rokon;

public class Polygon {
	
	private int _vertexCount;
	private int[] _x, _y;
	
	public Polygon(int vertexCount) {
		_vertexCount = vertexCount;
	}
	
	public Polygon(int[] x, int[] y) {
		if(x.length != y.length) {
			Debug.warning("Creating polygon with un-equal XY coordinates");
			return;
		}
		_vertexCount = x.length;
		_x = x;
		_y = y;
	}
	
	public void set(int x[], int y[]) {
		_x = x;
		_y = y;
	}
	
	public int getX(int index) {
		return _x[index];
	}
	
	public int getY(int index) {
		return _y[index];
	}
	
	public void setX(int index, int x) {
		_x[index] = x;
	}
	
	public void setY(int index, int y) {
		_y[index] = y;
	}
	
	public void set(int index, int x, int y) {
		_x[index] = x;
		_y[index] = y;
	}

}
