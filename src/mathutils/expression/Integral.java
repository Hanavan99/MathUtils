package mathutils.expression;

import java.util.HashMap;

import mathutils.number.Number;
import mathutils.number.WholeNumber;

public class Integral extends UnaryOperator {

	private char varname;

	public Integral(MathExpression expression, char varname) {
		super(expression);
		this.varname = varname;
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public MathExpression simplify(HashMap<Character, Number> vars) {
		MathExpression ex = getExpression();
		if (ex instanceof Add) {
			return new Add(new Integral(((Add) ex).getLeft(), varname), new Integral(((Add) ex).getRight(), varname));
		} else if (ex instanceof Constant) {
			return new Multiply(ex, new Variable(varname));
		} else if (ex instanceof Variable && ((Variable) ex).getName() == varname) {
			return new Divide(new Exponent(ex, new Constant(2)), new Constant(2));
		} else if (ex instanceof Exponent && ((Exponent) ex).getLeft() instanceof Variable
				&& ((Variable) ex).getName() == varname && ((Exponent) ex).getRight() instanceof Constant) {
			Constant pow = new Constant(((Constant) ex).evaluate(vars).add(new WholeNumber(1)));
			return new Divide(new Exponent(new Variable(varname), pow), pow);
		}
		return null;

	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "integral(" + getExpression() + " d" + varname + ")";
	}

}
