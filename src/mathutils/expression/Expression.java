package mathutils.expression;

public abstract class Expression {
	
	public abstract boolean isLeaf();
	
	public abstract Expression evaluate(VariableList vars);
	
	public abstract double evaluate();
	
	public abstract String toString(VariableList vars);
	
	@Override
	public final String toString() {
		return "Expression";
	}
	
}
