package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;
import ch.epfl.moocprog.random.UniformDistribution;

import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.app.Context;

import static ch.epfl.moocprog.config.Config.*;
import static ch.epfl.moocprog.app.Context.*;

public abstract class Animal extends Positionable 
{
	private double angleDeDirectionDeDeplacement; 
	private int hitpoints;
	private Time lifespan;
	
	public abstract double getSpeed();


	public Animal(ToricPosition t,int hitpoints, Time life) 
	{
		super(t);
		this.hitpoints=hitpoints;
		this.lifespan=life;
		this.angleDeDirectionDeDeplacement = UniformDistribution.getValue(0, 2*Math.PI);
	}
	
	public final int getHitpoints() {
		return hitpoints;
	}
	
	public final Time getLifespan() {
		return lifespan;
	}
	
	final public double getDirection()
	{
		return angleDeDirectionDeDeplacement;//angle;
	}
	
	
	public final void setDirection(double angle)
	{
		this.angleDeDirectionDeDeplacement = angle;
	}
	
	public final boolean isDead() 
	{
		boolean state = false;
		if((hitpoints <= 0.0) || !(lifespan.isPositive())) 
			{state = true;}
		return state;
	}
	
	public void update(AnimalEnvironmentView env, Time dt)
	{
		lifespan = lifespan.minus(dt.times(getConfig().getDouble(ANIMAL_LIFESPAN_DECREASE_FACTOR)));
		if(!isDead())
		{
			move(dt);
		}

	}
	
	protected final void move(Time dt)
	{
		double deplacement = dt.toSeconds()*(getSpeed());
		Vec2d vecDir = Vec2d.fromAngle(angleDeDirectionDeDeplacement).scalarProduct(deplacement);
		this.setPosition(this.getPosition().add(vecDir));
		
	}
	
	public String toString() 
	{
		return super.toString()+"\n"+String.format("Speed : %.1f", this.getSpeed())+"\n"
				+String.format("Hitpoints : %d", hitpoints)+"\n"
				+String.format("LifeSpan : %.1f", lifespan.toSeconds())+"\n";
	}
	
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);


}
