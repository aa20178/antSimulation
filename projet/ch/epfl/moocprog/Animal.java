package ch.epfl.moocprog;

public abstract class Animal extends Positionable 
{
	private double angle; 

	/*
	 * public Animal() 
	{
		super();
		angle=0.0;
	}
	*/

	public Animal(ToricPosition t) 
	{
		super(t);
		angle = 0.0;
	}
	
	final public double getDirection()
	{
		return angle;
	}
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);


}
