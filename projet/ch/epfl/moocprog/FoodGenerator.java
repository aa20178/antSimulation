package ch.epfl.moocprog;


import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;

import ch.epfl.moocprog.random.NormalDistribution;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;



final public class FoodGenerator 
{
	private Time time;
	
	public FoodGenerator()
	{
		time = time.ZERO;
	}
	

	void update(FoodGeneratorEnvironmentView env, Time dt)
	{
		final Time threshold = Context.getConfig().getTime(Config.FOOD_GENERATOR_DELAY) ; 
		final double min = Context.getConfig().getDouble(Config.NEW_FOOD_QUANTITY_MIN);
		final double max = Context.getConfig().getDouble(Config.NEW_FOOD_QUANTITY_MAX);
		
		final int taillex =Context.getConfig().getInt(Config.WORLD_WIDTH);
		final int tailley =Context.getConfig().getInt(Config.WORLD_WIDTH);

		this.time = this.time.plus(dt);
		
		while (this.time.compareTo(threshold)>=0.0)
		{
			this.time = this.time.minus(threshold);
			double quantity =UniformDistribution.getValue(min, max);			
			double coordinateX =NormalDistribution.getValue(taillex/2.0,taillex*taillex/16.0);
			double coordinateY =NormalDistribution.getValue(tailley/2.0,tailley*tailley/16.0);
			
			
			ToricPosition tp = new ToricPosition(coordinateX, coordinateY);
			env.addFood(new Food(tp, quantity));

		}
		
	}


}
