package mathutils.parser;

import java.util.ArrayList;

public abstract class ExpressionParser<T> {

	private ArrayList<ParserOperator<T>> operators;
	
	public void useOperator(ParserOperator<T> operator) {
		operators.add(operator);
	}
	
	public T parse() {
		return null;
	}
	
}
