package mathutils.expression;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;

import mathutils.core.DrawProperties;
import mathutils.number.Number;
import mathutils.number.RealNumber;
import mathutils.number.WholeNumber;

public class Constant extends MathExpression {

	private Number value;

	public Constant(int n) {
		value = new WholeNumber(n);
	}

	public Constant(double n) {
		value = new RealNumber(n);
	}

	public Constant(Number value) {
		this.value = value;
	}

	@Override
	public Iterator<MathExpression> iterator() {
		return null;
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return value.toString();
	}

	@Override
	public boolean equals(Object other) {
		return value.equals(other);
	}

	@Override
	public MathExpression simplify() {
		return this;
	}

	@Override
	public Rectangle draw(Graphics2D g, DrawProperties props, int x, int y) {
		int strWidth = g.getFontMetrics().stringWidth(this.toString());
		int height = g.getFontMetrics().getHeight();
		g.drawString(this.toString(), x, y);
		return new Rectangle(x, y, strWidth, height);
	}

}
