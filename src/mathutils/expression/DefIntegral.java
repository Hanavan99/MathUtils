package mathutils.expression;

import java.util.HashMap;

public class DefIntegral extends Integral {

	private Number lower;
	private Number upper;

	public DefIntegral(MathExpression expression, char varname, Number lower, Number upper) {
		super(expression, varname);
		this.lower = lower;
		this.upper = upper;
	}

	@Override
	public mathutils.number.Number evaluate(HashMap<Character, mathutils.number.Number> vars)
			throws IllegalArgumentException {
		return super.evaluate(vars);
	}

	@Override
	public MathExpression simplify(HashMap<Character, mathutils.number.Number> vars) {
		return super.simplify(vars);
	}

	@Override
	public String toString(HashMap<Character, mathutils.number.Number> vars) {
		return super.toString(vars);
	}

}
