package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Vec2d;

public class Positionable 
{
	private ToricPosition tp;
	
	public Positionable()
	{
		tp = new ToricPosition(0.0,0.0);		
	}
	
	public Positionable(ToricPosition t)
	{
		this.tp = t;
		
	}
	
	public ToricPosition getPosition()
	{
		return tp;
	}
	
	protected final void setPosition(ToricPosition position1)
	{
		tp  = position1;
	}
	public String toString()
	{
		return tp.toString();
	}
	


	
}
