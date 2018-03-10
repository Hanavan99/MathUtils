package mathutils.expression;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import mathutils.core.DrawProperties;
import mathutils.number.Number;
import mathutils.util.RectangleMath;

public class Exponent extends BinaryOperator {

	public Exponent(MathExpression left, MathExpression right) {
		super(left, right);
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return getLeft().evaluate(vars).pow(getRight().evaluate(vars));
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "(" + getLeft().toString(vars) + " ^ " + getRight().toString(vars) + ")";
	}

	@Override
	public char getOperatorChar() {
		return '^';
	}

	@Override
	public Rectangle draw(Graphics2D g, DrawProperties props, int x, int y) {
		Rectangle baseBounds = getLeft().draw(g, props, x, y);
		g.setFont(props.getSmallFont());
		Rectangle expBounds = getRight().draw(g, props, x + baseBounds.width, y - baseBounds.height / 3);
		g.setFont(props.getDefaultFont());
		return RectangleMath.findBounds(baseBounds, expBounds);
	}
	
	

}
