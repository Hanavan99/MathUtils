package mathutils.expression;

import java.util.HashMap;

import mathutils.number.Number;

public abstract class MathExpression implements Cloneable, Iterable<MathExpression> {

	/**
	 * Evaluates a math expression given actual numeric values for each variable.
	 * 
	 * @param vars
	 *            the variable values
	 * @return the result of evaluation
	 * @throws IllegalArgumentException
	 *             if a variable is defined but no matching value associated with
	 *             that variable is contained in {@code vars}.
	 */
	public abstract Number evaluate(HashMap<Character, Number> vars) throws IllegalArgumentException;

	public abstract MathExpression simplify(HashMap<Character, Number> vars);
	
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
