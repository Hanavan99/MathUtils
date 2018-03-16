package mathutils.parser;

public abstract class ParserOperator<T> {

	private String op;
	
	public ParserOperator(String op) {
		this.op = op;
	}
	
	public String getOp() {
		return op;
	}
	
	public abstract T assemble(T left, T right);
	
}
