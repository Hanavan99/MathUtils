package mathutils.expression;

public class Exponent extends Expression {

	private Expression base;
	private Expression exponent;
	
	public Exponent(Expression base, Expression exponent) {
		this.base = base;
		this.exponent = exponent;
	}
	
	public Exponent(double base, double exponent) {
		this(new Constant(base), new Constant(exponent));
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Expression evaluate(VariableList vars) {
		return null;
	}

	@Override
	public double evaluate() {
		return Math.pow(base.evaluate(), exponent.evaluate());
	}

	@Override
	public String toString(VariableList vars) {
		return base + "^" + (exponent.evaluate(null) != null ? "(" + exponent + ")" : exponent);
	}

}
