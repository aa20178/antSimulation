package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Time;


public class Pheromone extends Positionable {
	
	private ToricPosition position;
	private double quantity;
	public Pheromone(ToricPosition tp)
	{
		super(tp);		
	}
	public Pheromone(ToricPosition tp, double qty)
	{
		super(tp);		
		quantity = qty;
	}
	
	public double getQuantity()
	{
		return quantity;
	}
	
	public boolean isNegligible()
	{
		double r = getConfig().getDouble(PHEROMONE_THRESHOLD);
		return (quantity < r);
		
	}
	public void update(Time dt) 
	{
		if(!this.isNegligible())
		{
			double rate = getConfig().getDouble(PHEROMONE_EVAPORATION_RATE);
			this.quantity-=dt.toSeconds()*rate;
			
			
			if(this.getQuantity()<0.0) this.quantity = 0.0;
		}
	}

}
