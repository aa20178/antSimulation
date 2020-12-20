package ch.epfl.moocprog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class Environment implements FoodGeneratorEnvironmentView
{
	
	private FoodGenerator fg; 
	private List<Food> foodlist;

	public  Environment() 
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

}
