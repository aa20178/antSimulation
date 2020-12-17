package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Vec2d;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;


public final class ToricPosition {
	
	private Vec2d position ; 
	private static Vec2d clampedPosition(double x, double y)
	{
		int worldWidth = getConfig().getInt(WORLD_WIDTH) ; 
		int worldHeight = getConfig().getInt(WORLD_HEIGHT) ; 

		while(x <0 )
		{
			x = x + worldWidth ; 
			
		}
		while(x > worldWidth  )
		{
			x = x - worldWidth ; 
			
		}		
		
		
		while(y <0 )
		{
			y = y + worldHeight ; 
			
		}
		while(y> worldWidth  )
		{
			y = y - worldHeight ; 
			
		}
		return new Vec2d(x, y);
	}
	

	public ToricPosition(double newx, double newy)
	{
		position = clampedPosition(newx, newy);
		
	}
	public ToricPosition(Vec2d newposition)
	{
		position = clampedPosition(newposition.getX() , newposition.getY());
		
	}
	
	public ToricPosition()
	{
		position = clampedPosition(0.0 ,0.0);
		
	}


}
