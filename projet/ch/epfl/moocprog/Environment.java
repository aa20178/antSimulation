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
import ch.epfl.moocprog.utils.Vec2d;

public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView, AnthillEnvironmentView, AntEnvironmentView, AntWorkerEnvironmentView
{
	
	private FoodGenerator fg; 
	private List<Food> foodlist;
	private List<Animal> animals	;
	private List<Anthill> anthills	;
	private List<Pheromone> pheros	;

	public  Environment() 
	{
		fg = new FoodGenerator();
		foodlist = new LinkedList<Food>();
		animals = new LinkedList<Animal>();
		anthills = new LinkedList<Anthill>();
		pheros = new LinkedList<Pheromone>();

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
	
	
	void cleanPheromones(List<Pheromone> pheroslist){
		
		Utils.requireNonNull(pheroslist);
	//nettoyage pheros negli
	Iterator<Pheromone> iterateurPheros = pheroslist.iterator();
	while(iterateurPheros.hasNext()) 
	{
		Pheromone instancePhero = iterateurPheros.next();
		if(instancePhero.isNegligible() ) 
		{
			iterateurPheros.remove();
		}
		
	}
	
	return ;
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void update(Time dt) 
	{
		fg.update(this,  dt);
		
		
		// Gestion des fourmilieres
		for (Anthill anthill : anthills) {
			anthill.update(this, dt);
		}
		
		
		//nettoyage pheros negli
		Iterator<Pheromone> iterateurPheros = pheros.iterator();
		while(iterateurPheros.hasNext()) 
		{
			Pheromone instancePhero = iterateurPheros.next();
			if(instancePhero.isNegligible() ) 
			{
				iterateurPheros.remove();
			}
			else
				instancePhero.update(dt);
		}
			
		
		
		
		
		//nettoyage animaux morts
		Iterator<Animal> iterateurAnimaux = animals.iterator();
		while(iterateurAnimaux.hasNext()) 
		{
			Animal instanceAni = iterateurAnimaux.next();
			if(instanceAni.isDead() ) 
			{
				iterateurAnimaux.remove();
			}
			else
				instanceAni.update(this, dt);
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

	List<Double> getPheromonesQuantities()
	{
		List<Double> l = new ArrayList<Double>();
		for ( Pheromone ph : pheros)
		{
			l.add(ph.getQuantity());
		}
		return l;
	}
	
	
	

	@Override
	public void addPheromone(Pheromone pheromone)
	{
		Utils.requireNonNull(pheromone);			
		pheros.add(pheromone);
	}


	@Override
	public double[] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position, double directionAngleRad,
			double[] angles)
	{
		Utils.requireNonNull(position);

		Utils.requireNonNull(angles);

		double[] T = new double[angles.length];
		double distanceSmell = getConfig().getDouble(ANT_SMELL_MAX_DISTANCE);
		
		
		for (Pheromone pheromone : this.pheros) 
		{
			if (!pheromone.isNegligible() && pheromone.getPosition().toricDistance(position) <=distanceSmell )
			{

				Vec2d v = position.toricVector(pheromone.getPosition());
				double beta = v.angle() - directionAngleRad;
				int closestAngleIndex = 0;

				for ( int i = 0 ; i < angles.length ; i ++)
				{
					double ecartAlaBorneI = closestAngleFrom(beta, angles[i]);
					double ecartAlaBorneCourante = closestAngleFrom(beta, angles[closestAngleIndex]);
					
					if(ecartAlaBorneI <ecartAlaBorneCourante)
					{
						closestAngleIndex = i;
					}
				}
				T[closestAngleIndex] = T[closestAngleIndex] + pheromone.getQuantity();
				
			}
		}
		return T;
	}
	
	
	private static double closestAngleFrom(double angle, double target) {
		double diff = normalizedAngle(angle - target);
		return Math.min(diff, 2 * Math.PI - diff);
	}
	
	private static double normalizedAngle(double angle) 
	{
		while (angle < 0)
			angle = angle + 2* Math.PI;
		while (angle >= 2 * Math.PI)
			angle = angle - 2* Math.PI;
		return angle;
	}


	@Override
	public RotationProbability selectComputeRotationProbsDispatch(Ant ant) {
		// TODO Auto-generated method stub
		return ant.computeRotationProbs(this);
	}


	@Override
	public void selectAfterMoveDispatch(Ant ant, Time dt)
	{
		ant.afterMoveAnt(this, dt);
	}

}
