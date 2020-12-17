package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Vec2d;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import java.util.ArrayList;


public final class ToricPosition {
	
	private Vec2d position ; 
	static int worldWidth = getConfig().getInt(WORLD_WIDTH) ; 
	static int worldHeight = getConfig().getInt(WORLD_HEIGHT) ; 
	
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

	public ToricPosition add(ToricPosition that)
	{
		
		ToricPosition tpAddition =  new ToricPosition(position.add(that.position));
		tpAddition.position = ToricPosition.clampedPosition(tpAddition.position.getX(), tpAddition.position.getY());
		return tpAddition ;

	}	
	
	public ToricPosition add(Vec2d that)
	{
		ToricPosition tpAddition = new ToricPosition(position.add(that));
		tpAddition.position = ToricPosition.clampedPosition(position.getX(), position.getY());
		return tpAddition;

	}

	public Vec2d toVec2d()
	{
		return this.position;
	}
	
	public Vec2d toricVector(ToricPosition that)
	{	
		ToricPosition TPtable[] = new ToricPosition[9];
		
		TPtable[0] =that;
		
		TPtable[1] = that.add(new Vec2d(0, worldHeight));
		TPtable[2] = that.add(new Vec2d(0, -worldHeight));
		
		TPtable[3] = that.add(new Vec2d(worldWidth, 0));
		TPtable[4] = that.add(new Vec2d(-worldWidth, 0));
		
		TPtable[5] = that.add(new Vec2d(worldWidth, worldHeight));
		
		TPtable[6] = that.add(new Vec2d(worldWidth, -worldHeight));
		TPtable[7] = that.add(new Vec2d(-worldWidth, worldHeight));
		TPtable[8] = that.add(new Vec2d(-worldWidth, -worldHeight));
		
		double distanceTable[] = new double[9];
		
		for (int i = 0; i < distanceTable.length ; ++i)
		{
			distanceTable[i] = this.position.distance(TPtable[i].position);
		}
		
		double minimum = distanceTable[0];
		int minimumIndex = 0;
		
		for (int i = 0; i < distanceTable.length ; ++i)
		{
			if(distanceTable[i] < minimum)
			{
				minimum =distanceTable[i]  ;
				minimumIndex = i ;
			}
			
		}
		return TPtable[minimumIndex].position;
		
	}


}
