package mathutils.expression;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;

import mathutils.core.DrawProperties;
import mathutils.number.Number;

public class Variable extends MathExpression {

	private char name;

	public Variable(char name) {
		this.name = name;
	}

	public char getName() {
		return name;
	}

	@Override
	public Iterator<MathExpression> iterator() {
		return null;
	}

	@Override
	public Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException {
		return vars != null && vars.containsKey(name) ? vars.get(name) : null;
	}

	@Override
	public String toString() {
		return String.valueOf(name);
	}

	@Override
	public String toString(HashMap<Character, Number> vars) {
		return vars != null && vars.containsKey(name) ? vars.get(name).toString() : String.valueOf(name);
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof Variable && ((Variable) other).name == name;
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
