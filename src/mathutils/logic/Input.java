package mathutils.logic;

import java.util.HashMap;
import java.util.Iterator;

public class Input extends LogicExpression {

	private char name;

	public Input(char name) {
		this.name = name;
	}

	@Override
	public boolean evaluate(HashMap<Character, Boolean> vars) {
		if (vars.containsKey(name)) {
			return vars.get(name);
		}
		throw new IllegalArgumentException("No value associated with '" + name + "'.");
	}

	@Override
	public String toString(HashMap<Character, Boolean> vars) {
		if (vars != null && vars.containsKey(name)) {
			return vars.get(name) ? "T" : "F";
		}
		return String.valueOf(name);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Input && ((Input) other).name == name;
	}

	@Override
	public Iterator<LogicExpression> iterator() {
		return null;
	}

	@Override
	public String toString() {
		return String.valueOf(name);
	}

}
