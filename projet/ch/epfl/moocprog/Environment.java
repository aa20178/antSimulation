package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class Environment implements FoodGeneratorEnvironmentView
{
	
	private FoodGenerator fg; 
	private List<Food> foodlist;

	public Environment() 
	{
		fg = new FoodGenerator();
		foodlist = new LinkedList<Food>();
	}
	
	@Override
	public void addFood(Food food) 
	{
		Utils.requireNonNull(food);			
		foodlist.add(food);
	}
	
	public List<Double> getFoodQuantities()
	{
		List<Double> l = new ArrayList<Double>();
		for ( Food f : foodlist)
		{
			l.add(f.getQuantity());
		}
		return l;
	}
	
	public void update(Time dt) 
	{
		fg.update(this,  dt);
		foodlist.removeIf(food -> food.getQuantity() <= 0);
	}
	
	
	
	public void renderEntities(EnvironmentRenderer environmentRenderer)
	{foodlist.forEach(environmentRenderer::renderFood);}
	
	public void addAnthill(Anthill anthill)	{}
	public void addAnimal(Animal animal)	{}
	
	
	public int getWidth()
	{
		return getConfig().getInt(WORLD_WIDTH) ; 
	}
	public int getHeight()
	{
		 int worldHeight = getConfig().getInt(WORLD_HEIGHT) ; 

		return worldHeight ; 
	}

}
