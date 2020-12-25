package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;
import ch.epfl.moocprog.random.NormalDistribution;
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
	private Time rotationDelay;

	public abstract double getSpeed();


	public Animal(ToricPosition t,int hitpoints, Time life) 
	{
		super(t);
		this.hitpoints=hitpoints;
		this.lifespan=life;
		this.angleDeDirectionDeDeplacement = UniformDistribution.getValue(0, 2*Math.PI);
		this.rotationDelay = Time.ZERO;
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
	
	
	
	
	
	private void rotate()
	{
		double gamma = Utils.pickValue(computeRotationProbs().getAngles(), computeRotationProbs().getProbabilities());
		this.setDirection(getDirection()+ gamma);
	}
	
	protected final void move(Time dt)
	{
		
		final Time threshold = Context.getConfig().getTime(Config.ANIMAL_NEXT_ROTATION_DELAY) ; 
		double deplacement = dt.toSeconds()*(getSpeed());
		
		
		this.rotationDelay = this.rotationDelay.plus(dt);
		while (this.rotationDelay.compareTo(threshold)>=0.0)
		{
			this.rotationDelay = this.rotationDelay.minus(threshold);			
			rotate();
		}	
		
		
		
		
		Vec2d vecDir = Vec2d.fromAngle(angleDeDirectionDeDeplacement).scalarProduct(deplacement);
		this.setPosition(this.getPosition().add(vecDir));
		
	}
	
	public String toString() 
	{
		return super.toString()+"\n"+String.format("Speed : %.1f", this.getSpeed())+"\n"
				+String.format("Hitpoints : %d", hitpoints)+"\n"
				+String.format("LifeSpan : %.1f", lifespan.toSeconds())+"\n";
	}
	
	
	
	protected RotationProbability computeRotationProbs()
	{
		
		double[] ang =  { -180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180};
		double[] angRadian = { Math.toRadians(-180), Math.toRadians(-100), Math.toRadians(-55), 
				Math.toRadians(-25), Math.toRadians(-10),
				Math.toRadians(0), Math.toRadians(10),
				Math.toRadians( 25),
				Math.toRadians(55),
				Math.toRadians(100), Math.toRadians(180)};
		double[] prob = { 0.0000, 0.0000, 0.0005, 0.0010, 0.0050,
				0.9870,
				0.0050, 0.0010, 0.0005, 0.0000, 0.0000};		
		return new RotationProbability(angRadian,prob);
	
	}
	
	
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);


}
