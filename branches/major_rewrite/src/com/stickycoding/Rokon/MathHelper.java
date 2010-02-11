package com.stickycoding.Rokon;

public class MathHelper {
	
	public static boolean coordInRect(int x, int y, int rectX, int rectY, int rectWidth, int rectHeight) {
		if(x < rectX)
			return false;
		if(y < rectY)
			return false;
		if(x > rectX + rectWidth)
			return false;
		if(y > rectY + rectHeight)
			return false;
		return true;
	}

}
