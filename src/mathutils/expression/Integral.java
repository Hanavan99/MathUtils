package mathutils.expression;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import mathutils.core.DrawProperties;
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
	public MathExpression simplify() {
		MathExpression ex = getExpression().simplify();
		if (ex instanceof Add) {
			// break the integral apart
			Add add = (Add) ex;
			return new Add(new Integral(add.getLeft(), varname), new Integral(add.getRight(), varname));
		} else if (ex instanceof Constant) {
			// if the inside is a constant, it will integrate to constant * varname
			return new Multiply(ex, new Variable(varname));
		} else if (ex instanceof Variable && ((Variable) ex).getName() == varname) {
			// if the inside is just a variable, it will integrate to varname ^ 2 / 2
			return new Divide(new Exponent(ex, new Constant(2)), new Constant(2));
		} else if (ex instanceof Exponent) {
			// if the inside is varname ^ n, it will integrate to varname ^ (n + 1) / (n +
			// 1)
			Exponent exp = (Exponent) ex;
			if (ex instanceof Variable && exp.getRight() instanceof Constant
					&& ((Variable) exp.getLeft()).getName() == varname) {
				Constant pow = new Constant(((Constant) exp.getRight()).evaluate(null).add(new WholeNumber(1)));
				return new Divide(new Exponent(new Variable(varname), pow), pow);
			}
		}
		return this;
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "integral(" + getExpression() + " d" + varname + ")";
	}
	
	@Override
	public Rectangle draw(Graphics2D g, DrawProperties props, int x, int y) {
		g.setFont(props.getDefaultFont());
		String intSymbol = "\u222B";
		int symbolWidth = g.getFontMetrics().stringWidth(intSymbol);
		g.drawString(intSymbol, x, y);
		Rectangle r = getExpression().draw(g, props, x + symbolWidth, y);
		String dvSymbol = "d" + varname;
		int dvWidth = g.getFontMetrics().stringWidth(dvSymbol);
		g.drawString(dvSymbol, x + symbolWidth + r.width, y);
		return new Rectangle(x, y, r.width + symbolWidth + dvWidth, r.height);
	}

}
