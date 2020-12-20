package ch.epfl.moocprog;

final public class Food extends Positionable
{
	private double quantity;
	
	public Food(ToricPosition tp, double d)
	{
		super(tp);
		if(d<0)
		{quantity = 0.0;}
		else quantity = d;
	}
	

	public double getQuantity()
	{
		return quantity;
	}

	public double takeQuantity(double takenQuantity) 
	{
		double newquantity = 0;
		
		if(takenQuantity<0)
		{
			throw new IllegalArgumentException();
		}
		
		if(takenQuantity <= quantity)
		{
			quantity = quantity - takenQuantity;
			newquantity =  takenQuantity;
			
		}
		else if(takenQuantity > quantity)
		{
			newquantity = quantity;
			quantity = 0;
		}
		
		return newquantity;		
	}
	
	public String toString()
	{
		return super.toString() + String.format("\nQuantity : %.2f", getQuantity());
	}
	
}
