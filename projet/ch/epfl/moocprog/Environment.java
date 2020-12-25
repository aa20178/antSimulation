package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView
{
	
	private FoodGenerator fg; 
	private List<Food> foodlist;
	private List<Animal> animals	;



	public  Environment() 
	{
		fg = new FoodGenerator();
		foodlist = new LinkedList<Food>();
		animals = new LinkedList<Animal>();

	}
	
	@Override
	public void addFood(Food food) 
	{
		Utils.requireNonNull(food);			
		foodlist.add(food);
	}
	
	public List<ToricPosition> getAnimalsPosition() {
		List<ToricPosition> positions = new ArrayList<>();
		this.animals.forEach(a -> positions.add(a.getPosition()));
		return positions;
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
		
		Iterator<Animal> iterateur = animals.iterator();
		
		while(iterateur.hasNext()) 
		{
			Animal instanceDeUneClasse = iterateur.next();
			if(instanceDeUneClasse.isDead() ) 
			{
				iterateur.remove();
			}
			else
				instanceDeUneClasse.update(this, dt);
		}
		
		
		foodlist.removeIf(food -> food.getQuantity() <= 0);
	}
	
	public void renderEntities(EnvironmentRenderer environmentRenderer)
	{
		foodlist.forEach(environmentRenderer::renderFood);
		animals.forEach(environmentRenderer::renderAnimal);
}
	public void addAnthill(Anthill anthill)	{}
	public void addAnimal(Animal animal)	
	{
		Utils.requireNonNull(animal);	
		
		this.animals.add(animal);
		
	}
	
	
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
