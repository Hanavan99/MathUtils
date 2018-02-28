package mathutils.logic;

import java.util.HashMap;

public class Implies extends BinaryOperator {

	public Implies(LogicExpression left, LogicExpression right) {
		super(left, right);
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> vars) {
		return !getLeft().evaluate(vars) || getRight().evaluate(vars);
	}

	@Override
	public String toString(HashMap<Character, Boolean> vars) {
		return "(" + getLeft().toString(vars) + padSymbol(ASCII_IMPLIES) + getRight().toString(vars) + ")";
	}

}
