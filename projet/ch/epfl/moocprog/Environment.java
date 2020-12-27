package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView, AnthillEnvironmentView, AntEnvironmentView, AntWorkerEnvironmentView
{
	
	private FoodGenerator fg; 
	private List<Food> foodlist;
	private List<Animal> animals	;
	private List<Anthill> anthills	;

	public  Environment() 
	{
		fg = new FoodGenerator();
		foodlist = new LinkedList<Food>();
		animals = new LinkedList<Animal>();
		anthills = new LinkedList<Anthill>();
	}
	
	
	public void  addAnthill(Anthill anthill)
	{
		Utils.requireNonNull(anthill);			
		anthills.add(anthill);
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
		
		
		// Gestion des fourmilieres
		for (Anthill anthill : anthills) {
			anthill.update(this, dt);
		}
		
		
		
		
		
		
		//nettoyage animaux morts
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


	@Override
	public void addAnt(Ant ant) throws IllegalArgumentException
	{
		Utils.requireNonNull(ant);	
		this.addAnimal(ant);
	}


	@Override
	public Food getClosestFoodForAnt(AntWorker antWorker) 
	{
		Food closeFood = Utils.closestFromPoint(antWorker, this.foodlist);
		
		double rayonPerception = getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE);
		
		if (closeFood == null  ) 
		{
			return null;
		}
		
		else if (closeFood.getPosition().toricDistance(antWorker.getPosition()) > rayonPerception)
			return null;
		
		return closeFood;
	}
	
	
	protected Anthill getAnthillById(Uid selectedUid)
	{
		Utils.requireNonNull(selectedUid);
		
		Anthill fourmillierePresumee = null; 
		
		for (Anthill anth : this.anthills)
		{
			if (anth.getAnthillId().equals(selectedUid)) 
			{
				fourmillierePresumee = anth;
			}
		}
		return fourmillierePresumee;
		
	}

	
	

	@Override
	public boolean dropFood(AntWorker antWorker)
	{
		Utils.requireNonNull(antWorker);	
		double rayonPerception = getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE);
		Anthill ahill = getAnthillById(antWorker.getAnthillId());
	
		if ( ahill==null)
		{
			return false ;	
		}
		
		else if(ahill.getPosition().toricDistance(antWorker.getPosition()) > rayonPerception )
		{
			return false;
		}
		
		ahill.dropFood(antWorker.getFoodQuantity());
		return true;
	}


	@Override
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt) {
		antWorker.seekForFood(this, dt);
		
	}


	@Override
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt) {
		antSoldier.seekForEnemies(this, dt);

	}

}
