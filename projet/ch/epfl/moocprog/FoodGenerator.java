package ch.epfl.moocprog;


import static ch.epfl.moocprog.config.Config.*;
import static ch.epfl.moocprog.app.Context.getConfig;

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
	

	public void update(FoodGeneratorEnvironmentView env, Time dt)
	{
		final Time threshold = getConfig().getTime(FOOD_GENERATOR_DELAY) ; 
		final double min = getConfig().getDouble(NEW_FOOD_QUANTITY_MIN);
		final double max = getConfig().getDouble(NEW_FOOD_QUANTITY_MAX);
		
		final int taillex =getConfig().getInt(WORLD_WIDTH);
		final int tailley =getConfig().getInt(WORLD_WIDTH);

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
