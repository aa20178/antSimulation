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
		ToricPosition tpAddition =  new ToricPosition(position.add(that.toVec2d()));
		return tpAddition ;

	}	
	
	public ToricPosition add(Vec2d that)
	{
		ToricPosition tpAddition =  new ToricPosition(position.add(that));
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
		Vec2d vecTable[] = new Vec2d[9];
		vecTable[0] = that.toVec2d();
		vecTable[1] = that.toVec2d().add(new Vec2d(0, worldHeight));		
		vecTable[2]=that.toVec2d().add(new Vec2d(0, -worldHeight));
		vecTable[3]=that.toVec2d().add(new Vec2d( worldHeight,0));
		vecTable[4]=that.toVec2d().add(new Vec2d( -worldHeight,0));
		vecTable[5]=that.toVec2d().add(new Vec2d( worldWidth, worldHeight));
		vecTable[6]=that.toVec2d().add(new Vec2d( worldWidth, -worldHeight));

		vecTable[7]=that.toVec2d().add(new Vec2d(- worldWidth, worldHeight));
		vecTable[8]=that.toVec2d().add(new Vec2d( -worldWidth, -worldHeight));
		
		// liste des distances aux points candidats
		
		Vec2d vmin = vecTable[0];		
		for(int i =0; i < vecTable.length; ++i)
		{
			if(this.toVec2d().distance(vecTable[i]) < this.toVec2d().distance(vmin))
			{
				vmin = vecTable[i];
			}
			
		}
		return vmin.minus(position);

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
