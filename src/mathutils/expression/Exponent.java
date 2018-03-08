package mathutils.expression;

import java.util.HashMap;

import mathutils.number.Number;

public class Exponent extends BinaryOperator {

	public Exponent(MathExpression left, MathExpression right) {
		super(left, right);
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return getLeft().evaluate(vars).pow(getRight().evaluate(vars));
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "(" + getLeft().toString(vars) + " ^ " + getRight().toString(vars) + ")";
	}

}
