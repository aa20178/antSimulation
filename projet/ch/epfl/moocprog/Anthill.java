package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.*;
import static ch.epfl.moocprog.app.Context.getConfig;


import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class Anthill extends Positionable 
{
	
	private double foodQuantity;
	private Uid id;
	private double probability;
	private Time time;
	private final Time antGenerationDelay = getConfig().getTime(ANTHILL_SPAWN_DELAY);

	
	public Anthill(ToricPosition tp)//, double prob)
	{
		super(tp);	
		this.probability = getConfig().getDouble(	ANTHILL_WORKER_PROB_DEFAULT);
		this.foodQuantity = 0;
		this.time = Time.ZERO;

		this.id = Uid.createUid();
	}
	
	public Anthill(ToricPosition tp, double prob)
	{
		super(tp);	
		this.probability = prob;
		this.foodQuantity = 0;
		this.time = Time.ZERO;

		this.id = Uid.createUid();
	}
	
	public void dropFood(double toDrop)
	{
		Utils.require(toDrop>=0);
		this.foodQuantity = this.foodQuantity + toDrop;
	}

	public Uid getAnthillId()
	{
		return this.id;
	}

	public double getFoodQuantity()
	{
		return this.foodQuantity;
	}
	public String toString()
	{
		return super.toString() +"\n"+String.format("Quantity : %.2f", this.getFoodQuantity())+"\n";
	}
	
	
	
	
public void update(AnthillEnvironmentView env, Time dt) {
		
		this.time = this.time.plus(dt);
		
		while(this.time.compareTo(antGenerationDelay) >= 0.0) 
		{
			double r = UniformDistribution.getValue(0.0, 1.0);
			if(r<=this.probability) 
			{
				AntWorker antWorker = new AntWorker(this.getPosition(), this.id);
				env.addAnt(antWorker);
			}
			else 
			{
				AntSoldier antSoldier = new AntSoldier(this.getPosition(), this.id);
				env.addAnt(antSoldier);
			}
			this.time = this.time.minus(antGenerationDelay);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
