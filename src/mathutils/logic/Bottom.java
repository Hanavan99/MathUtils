package mathutils.logic;

import java.util.HashMap;
import java.util.Iterator;

public class Bottom extends LogicExpression {

	@Override
	public Iterator<LogicExpression> iterator() {
		return null;
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> vars) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return "_|_";
	}

	@Override
	public String toString(HashMap<Character, Boolean> vars) {
		return "_|_";
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Bottom;
	}

}
