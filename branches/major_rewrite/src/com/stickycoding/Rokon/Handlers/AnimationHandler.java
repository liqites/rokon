package com.stickycoding.Rokon.Handlers;

import com.stickycoding.Rokon.Entity;

/**
 * AnimationHandler allows you to be notified at the end of each animation loop, or final loop
 */
public class AnimationHandler {
	
	public void finished(Entity sprite) { }
	
	public void endOfLoop(int remainingLoops) { }
	
}
