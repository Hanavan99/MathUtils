package mathutils.logic.operators;

import java.util.HashMap;

import mathutils.logic.BinaryOperator;
import mathutils.logic.LogicExpression;

public class Or extends BinaryOperator {

	public Or(LogicExpression left, LogicExpression right) {
		super(left, right);
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> vars) {
		return getLeft().evaluate(vars) || getRight().evaluate(vars);
	}

	@Override
	public String toString(HashMap<Character, Boolean> vars) {
		return "(" + getLeft().toString(vars) + padSymbol(ASCII_OR) + getRight().toString(vars) + ")";
	}
}
