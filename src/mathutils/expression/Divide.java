package mathutils.expression;

import java.util.HashMap;

import mathutils.number.Number;

public class Divide extends BinaryOperator {

	public Divide(MathExpression left, MathExpression right) {
		super(left, right);
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return getLeft().evaluate(vars).divide(getRight().evaluate(vars));
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "(" + getLeft().toString(vars) + " / " + getRight().toString(vars) + ")";
	}

}
