package mathutils.parser;

public abstract class ParserFunction<T> {

	private String funcName;
	private int paramCount;

	/**
	 * @param funcName
	 * @param paramCount
	 */
	public ParserFunction(String funcName, int paramCount) {
		this.funcName = funcName;
		this.paramCount = paramCount;
	}

	public String getFuncName() {
		return funcName;
	}

	public int getParamCount() {
		return paramCount;
	}

	public abstract T assemble(Object[] args);

}
