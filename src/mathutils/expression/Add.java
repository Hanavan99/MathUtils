package mathutils.expression;

import java.util.HashMap;

import mathutils.number.Number;

public class Add extends BinaryOperator {

	public Add(MathExpression left, MathExpression right) {
		super(left, right);
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return getLeft().evaluate(vars).add(getRight().evaluate(vars));
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "(" + getLeft().toString(vars) + " + " + getRight().toString(vars) + ")";
	}

	@Override
	public char getOperatorChar() {
		return '+';
	}

}
