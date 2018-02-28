package mathutils.expression;

import java.util.HashMap;

public class VariableList {

	private HashMap<String, Expression> constants = new HashMap<String, Expression>();
	
	public void add(String name, Expression ex) {
		constants.put(name, ex);
	}
	
	public void add(String name, double value) {
		constants.put(name, new Constant(value));
	}
	
}
