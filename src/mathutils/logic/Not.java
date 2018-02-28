package mathutils.logic;

import java.util.HashMap;

public class Not extends UnaryOperator {

	public Not(LogicExpression expression) {
		super(expression);
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> vars) throws IllegalArgumentException {
		return !getExpression().evaluate(vars);
	}

	@Override
	public String toString(HashMap<Character, Boolean> vars) {
		return ASCII_NOT + getExpression().toString(vars);
	}

}
