package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.*;
import static ch.epfl.moocprog.app.Context.getConfig;

import ch.epfl.moocprog.utils.Time;

public final class AntWorker extends Ant 
{
	private double foodQuantity;
	
	public double getFoodQuantity() 
	{
		return this.foodQuantity;
	}

	public AntWorker(ToricPosition t,Uid inputAnthillId) {
		super(t, getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN), inputAnthillId);
		foodQuantity = 0;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() 
	{
		return super.toString()+"\n"+String.format("Quantity : %.2f", this.getFoodQuantity())+"\n";

	}
	

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) 
	{
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return getConfig().getDouble(ANT_WORKER_SPEED);
	}
	
	public void makeUturn()
	{	
		  double angle = this.getDirection(); if(angle + Math.PI > 2*Math.PI) 
		  {
			  this.setDirection(angle-Math.PI);
		  } 
		  else
		  	{ this.setDirection(angle+Math.PI);}
	}
	
	protected void seekForFood(AntWorkerEnvironmentView env, Time dt)
	{
		this.move(dt);
		
		// premier temps
		Food foodRecoltee = env.getClosestFoodForAnt(this) ;
		
		
		if( (this.foodQuantity == 0) && (foodRecoltee!= null   ))
		{
			double antMax = getConfig().getDouble(ANT_MAX_FOOD);
			this.foodQuantity =	foodRecoltee.takeQuantity(antMax);	
			this.makeUturn();
		}
		
		if(env.dropFood(this) ==true && this.foodQuantity != 0.0) 
		{
			this.foodQuantity = 0.0;
			this.makeUturn();
		}
	
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) 
	{
		env.selectSpecificBehaviorDispatch(this,  dt);
	}
	
	


}
