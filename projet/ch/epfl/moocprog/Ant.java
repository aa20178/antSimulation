package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

public abstract class Ant extends Animal 
{
	private Uid antHillId;


	
	public Ant(ToricPosition t,int hitpoints, Time life, Uid nAntHillId) 
	{
		super(t,hitpoints,life);
		antHillId = nAntHillId;
	}
	
	final public Uid getAnthillId()
	{
		return this.antHillId;
	}
	
	
	





	
}
