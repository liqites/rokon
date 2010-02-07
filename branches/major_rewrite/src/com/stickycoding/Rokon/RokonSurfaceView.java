package com.stickycoding.Rokon;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class RokonSurfaceView extends GLSurfaceView {
        
	RokonRenderer renderer;
	Rokon _rokon;
	
	public RokonSurfaceView(Rokon rokon) {
	    super(rokon);
	    _rokon  = rokon;
	    renderer  = new RokonRenderer(rokon);
	    setRenderer(renderer);
	}

    public boolean onTouchEvent(final MotionEvent event) {
        //queueEvent(new Runnable() {
		//	public void run() {
				_rokon.onTouchEvent(event);
		//	}
        //});
        try {
        	Thread.sleep(16);
        } catch (Exception e) { }
        return true;
    }
}