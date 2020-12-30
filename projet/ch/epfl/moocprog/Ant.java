package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;
public abstract class Ant extends Animal 
{
	private Uid antHillId;
	private ToricPosition lastPheroPos; 
	private final double densite = getConfig().getDouble(ANT_PHEROMONE_DENSITY);
	
	private final double energy = getConfig().getDouble(ANT_PHEROMONE_ENERGY);
	
	protected final RotationProbability computeRotationProbs(AntEnvironmentView env)
	{
		return this.computeDefaultRotationProbs();
	}
	
	@Override
	protected final RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env)
	{
		return env.selectComputeRotationProbsDispatch(this);
		
	}
	
	
	
	
	
	
	protected final void afterMoveDispatch(AnimalEnvironmentView env, Time dt)
	{
		env.selectAfterMoveDispatch(this, dt);
	}
	
	final private void spreadPheromones(AntEnvironmentView env)
	{
		
		ToricPosition currentpos = this.getPosition();
		double d = currentpos.toricDistance(lastPheroPos);
		double nombredInstancesDePhero = d*densite;

		Vec2d v = lastPheroPos.toricVector(currentpos);
		double distanceEntreChaquePhero = d/nombredInstancesDePhero;

		for (int i = 0 ;i < Math.floor(nombredInstancesDePhero) ; i = i+1 )
		{
			
			
		}
		
	
	}

	protected final void afterMoveAnt(AntEnvironmentView env, Time dt)
	{
		spreadPheromones(env);
	}
	
	public Ant(ToricPosition t,int hitpoints, Time life, Uid nAntHillId) 
	{
		super(t,hitpoints,life);
		this.lastPheroPos = t;

		antHillId = nAntHillId;
	}
	
	final public Uid getAnthillId()
	{
		return this.antHillId;
	}
	


	





	
}
