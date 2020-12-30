package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.*;
import static ch.epfl.moocprog.app.Context.getConfig;

import ch.epfl.moocprog.utils.Time;

public final class AntSoldier extends Ant {


	public AntSoldier(ToricPosition t,Uid inputAnthillId) 
	{
		super(t, getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), inputAnthillId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) 
	{	
	}


	
	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return getConfig().getDouble(ANT_SOLDIER_SPEED);
	}
	
	protected void seekForEnemies(AntEnvironmentView env, Time dt) 
	{
		this.move(env,dt);

	}


	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) 
	{
		env.selectSpecificBehaviorDispatch(this,  dt);
	}
	
}
