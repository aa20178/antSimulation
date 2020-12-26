package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.*;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Utils;

public final class Anthill extends Positionable 
{
	
	private double foodQuantity;
	private Uid id;
	private double antSpawnProba;
	
	public Anthill(ToricPosition tp)//, double prob)
	{
		super(tp);	
		this.antSpawnProba = getConfig().getDouble(	ANTHILL_WORKER_PROB_DEFAULT);
		this.foodQuantity = 0;
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
	

	
	
}
