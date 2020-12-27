package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;
import static ch.epfl.moocprog.config.Config.TERMITE_HP;
import static ch.epfl.moocprog.config.Config.TERMITE_LIFESPAN;

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
	
	protected void seekForFood(AntWorkerEnvironmentView env, Time dt)
	{
		this.move(dt);
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) 
	{
		env.selectSpecificBehaviorDispatch(this,  dt);
	}
	
	


}
