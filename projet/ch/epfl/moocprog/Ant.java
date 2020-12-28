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
	

	final private void spreadPheromones(AntEnvironmentView env)
	{
		ToricPosition currentpos = this.getPosition();
		double d =currentpos.toricDistance(lastPheroPos);
		double nombredInstancesDePhero = d*densite;
		double nipPartieEntiere = Math.floor(d*densite);

		Vec2d v = lastPheroPos.toricVector(currentpos);

		for (int i = 0 ;i < nipPartieEntiere ; i = i+1 )
		{
			ToricPosition pos_i = lastPheroPos.add(v.scalarProduct(i/(nombredInstancesDePhero)));
			
			Pheromone pheromone = new Pheromone(pos_i, this.energy);
			env.addPheromone(pheromone);
			this.lastPheroPos = currentpos;
			
		}
		
	
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
