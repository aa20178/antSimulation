package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Vec2d;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import java.util.ArrayList;


public final class ToricPosition {
	
	final private Vec2d position ; 
		
	private static Vec2d clampedPosition(double x, double y)
	{
		 final int worldWidth = getConfig().getInt(WORLD_WIDTH) ; 
		 final int worldHeight = getConfig().getInt(WORLD_HEIGHT) ; 

		while(x <0 )	
		{
			x = x + worldWidth ; 
			
		}
		while(x >= worldWidth  )
		{
			x = x - worldWidth ; 
			
		}		
		
		
		while(y <0 )
		{
			y = y + worldHeight ; 
			
		}
		while(y>= worldHeight  )
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
		Vec2d v = position.add(that.position);
		ToricPosition tpAddition =  new ToricPosition(v);
		return tpAddition ;

	}	
	
	public ToricPosition add(Vec2d that)
	{
		Vec2d v = position.add(that);
		ToricPosition tpAddition = new ToricPosition(v);
		return tpAddition;

	}

	public Vec2d toVec2d()
	{
		return this.position;
	}
	
	public Vec2d toricVector(ToricPosition that)
	{	
		final int worldWidth = getConfig().getInt(WORLD_WIDTH) ; 
		 final int worldHeight = getConfig().getInt(WORLD_HEIGHT) ; 
		
		//liste des points candidats
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
		
		// liste des distances aux points candidats
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
		
		Vec2d shortestDistanceVector = new Vec2d( TPtable[minimumIndex].position.getX() - this.position.getX() , TPtable[minimumIndex].position.getY()- this.position.getY());
		
		// il faut retourner le vecteur qui correspond à la distance la plus courte 
		return shortestDistanceVector ;//TPtable[minimumIndex].position;
	}
	
	public double toricDistance(ToricPosition that)
	{
		return toricVector(that).length();
	}
	
	public String toString()
	{
		return this.position.toString();
	}
	
	
	


}
