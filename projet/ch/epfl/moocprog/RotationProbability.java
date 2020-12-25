package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Utils;

public class RotationProbability {
	
	private double[] angles;
	private double[] probabilities;
	
	public RotationProbability(double[] ang, double[] proba)
	{
		Utils.requireNonNull(ang);
		Utils.requireNonNull(proba);
		Utils.require(ang.length == proba.length);
		
		this.angles = ang.clone();
		this.probabilities = proba.clone();
		
		
	}
	double[] getAngles()
	{
		return this.angles.clone();
	}
	double[] getProbabilities()
	{
		return this.probabilities.clone();
	}
	


}
