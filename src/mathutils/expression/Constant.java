package mathutils.expression;

public class Constant extends Expression {

	private double value;
	
	public Constant(double value) {
		this.value = value;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Expression evaluate(VariableList vars) {
		return null;
	}

	@Override
	public double evaluate() {
		return value;
	}

	@Override
	public String toString(VariableList vars) {
		return String.valueOf(value);
	}

}
