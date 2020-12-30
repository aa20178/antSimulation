package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

import ch.epfl.moocprog.utils.Time;
public final class Termite extends Animal {



	public Termite(ToricPosition t) {
		super(t,getConfig().getInt(TERMITE_HP), getConfig().getTime(TERMITE_LIFESPAN));		
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) 
	{
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {

		return getConfig().getDouble(TERMITE_SPEED);
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterMoveDispatch(AnimalEnvironmentView env, Time dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env) {
		// TODO Auto-generated method stub
		return null;
	}

}
