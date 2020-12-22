package ch.epfl.moocprog;

public final class AntSoldier extends Ant {


	public AntSoldier(ToricPosition t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);

	}

}
