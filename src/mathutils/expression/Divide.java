package mathutils.expression;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import mathutils.core.DrawProperties;
import mathutils.number.Number;

public class Divide extends BinaryOperator {

	public Divide(MathExpression left, MathExpression right) {
		super(left, right);
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return getLeft().evaluate(vars).divide(getRight().evaluate(vars));
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return "(" + getLeft().toString(vars) + " / " + getRight().toString(vars) + ")";
	}

	@Override
	public char getOperatorChar() {
		return '/';
	}

	@Override
	public Rectangle draw(Graphics2D g, DrawProperties props, int x, int y) {
		g.setFont(props.getDefaultFont());
		Rectangle topBounds = getLeft().draw(g, props, x + 5, y - 5);
		g.setStroke(new BasicStroke(props.getDefaultFont().getSize() / 20));
		g.drawLine(x, y, x + topBounds.width + 10, y);
		return topBounds;
	}

}
