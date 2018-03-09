package mathutils.parser;

public abstract class ParserOperator<T> {

	private char op;
	
	public ParserOperator(char op) {
		this.op = op;
	}
	
	public char getOp() {
		return op;
	}
	
	public abstract T assemble(T left, T right);
	
}
