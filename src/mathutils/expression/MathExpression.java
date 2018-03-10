package mathutils.expression;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import mathutils.core.DrawProperties;
import mathutils.core.Drawable;
import mathutils.number.Number;

/**
 * Represents a mathematical expression.
 * 
 * @author Hanavan Kuhn
 *
 */
public abstract class MathExpression implements Cloneable, Iterable<MathExpression>, Drawable {

	/**
	 * Tries to simplify the expression to as simple as possible. This function does
	 * not substitute in variable values, only simplifies in order to preserve the
	 * semantics of each operator. For example, having the expression
	 * {@code integral(x ^ 2 dx)} would not integrate correctly if {@code x} were
	 * substituted to be {@code 3} before integration.
	 * 
	 * @return the simplified expression
	 */
	public abstract MathExpression simplify();

	/**
	 * Evaluates a math expression given actual numeric values for each variable.
	 * 
	 * @param vars
	 *            the variable values
	 * @return the result of evaluation
	 * @throws IllegalArgumentException
	 *             if a variable is defined but no matching value associated with
	 *             that variable is contained in {@code vars}, or the expression
	 *             contains {@code MathExpression} objects that cannot be evaluated
	 *             at their current state. This suggests that the expression either
	 *             needs to be simplified or cannot be simplified enough.
	 */
	public abstract Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException;

	/**
	 * Replaces any variables with their corresponding values. This function does
	 * not simplify, only substitutes. For example, the expression {@code x + 5}
	 * with {@code x} being {@code 5} would produce {@code 5 + 5}.
	 * 
	 * @param vars
	 *            the map containing the character/number pairs to replace
	 * @return the updated expression
	 */
	public MathExpression replace(HashMap<Character, Number> vars) {
		return this;
	}

	/**
	 * Returns the string representation of this math expression in human readable
	 * infix format.
	 */
	@Override
	public abstract String toString();

	/**
	 * Returns a string that should be identical in format to
	 * {@link MathExpression#toString()}, but replaces inputs with literal values.
	 * If not matching literal value is found in {@code vars}, the name of the
	 * variable should be used instead.
	 * 
	 * @param vars
	 *            the variables to substitute
	 * @return the resulting string
	 */
	public abstract String toString(HashMap<Character, Number> vars);

	/**
	 * Creates a copy of this {@code MathExpression} such that
	 * {@code a.equals(a.clone())} will always return {@code true}.
	 */
	@Override
	public MathExpression clone() {
		try {
			return (MathExpression) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Determines whether or not two {@code MathExpression} objects are equal.
	 */
	@Override
	public abstract boolean equals(Object other);

	// TODO add plus(), minus(), times(), divide(), integrate(), ...

	/**
	 * Pads a symbol (or any string) with a single space on the beginning and end of
	 * the string. For example, {@code "a"} would be converted to {@code " a "}.
	 * 
	 * @param symbol
	 *            the symbol to pad
	 * @return the padded symbol
	 */
	protected String padSymbol(String symbol) {
		return " " + symbol + " ";
	}

}
