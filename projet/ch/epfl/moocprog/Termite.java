package ch.epfl.moocprog;

public final class Termite extends Animal {



	public Termite(ToricPosition t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) 
	{
		visitor.visit(this, s);
	}

}
