package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Vec2d;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;


public final class ToricPosition {
	
	private Vec2d position ; 
	private static Vec2d clampedPosition(double x, double y)
	{
		int worldWidth = getConfig().getInt(WORLD_WIDTH) ; 
		
		while(x <0 )
		{
			x = x + worldWidth ; 
			
		}
		while(x > worldWidth  )
		{
			x = x - worldWidth ; 
			
		}
	}
	

	public ToricPosition()
	{
		
	}

}
