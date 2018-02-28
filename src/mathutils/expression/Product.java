package mathutils.expression;

import java.util.ArrayList;

public class Product extends Expression {

	private ArrayList<Expression> terms = new ArrayList<Expression>();
	
	public void add(Expression term) {
		terms.add(term);
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Expression evaluate(VariableList vars) {
		return null;
	}

	@Override
	public double evaluate() {
		double result = 1;
		for (Expression e : terms) {
			result *= e.evaluate();
		}
		return result;
	}

	@Override
	public String toString(VariableList vars) {
		StringBuilder result = new StringBuilder();
		for (Expression e : terms) {
			result.append(e instanceof Constant ? e + "*" : e);
		}
		return result.toString();
	}

}
