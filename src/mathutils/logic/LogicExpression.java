package mathutils.logic;

import java.util.HashMap;

/**
 * Represents a logic expression using standard logical operators.
 * 
 * @author Hanavan Kuhn
 *
 */
public abstract class LogicExpression implements Cloneable, Iterable<LogicExpression> {

	public static final String ASCII_AND = "^";
	public static final String ASCII_OR = "V";
	public static final String ASCII_IMPLIES = "->";
	public static final String ASCII_NOT = "~";
	public static final String ASCII_TURNSTILE = "|-";

	public static final String UNI_AND = "\u2227";
	public static final String UNI_OR = "\u2228";
	public static final String UNI_IMPLIES = "\u2192";
	public static final String UNI_NOT = "\u00AC";
	public static final String UNI_TURNSTILE = "\u22A2";

	/**
	 * Evaluates a logic expression given actual true/false values for each
	 * variable.
	 * 
	 * @param vars
	 *            the variable values
	 * @return the result of evaluation
	 * @throws IllegalArgumentException
	 *             if a variable is defined but no matching value associated with
	 *             that variable is contained in {@code vars}.
	 */
	public abstract boolean evaluate(HashMap<Character, Boolean> vars) throws IllegalArgumentException;

	/**
	 * Returns the string representation of this logic expression in human readable
	 * infix format.
	 */
	@Override
	public abstract String toString();

	/**
	 * Returns a string that should be identical in format to
	 * {@link LogicExpression#toString()}, but replaces inputs with literal values.
	 * If not matching literal value is found in {@code vars}, the name of the
	 * variable should be used instead.
	 * 
	 * @param vars
	 *            the variables to substitute
	 * @return the resulting string
	 */
	public abstract String toString(HashMap<Character, Boolean> vars);

	@Override
	public LogicExpression clone() {
		try {
			return (LogicExpression) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Determines whether or not two {@code LogicExpression} objects are equal.
	 */
	@Override
	public abstract boolean equals(Object other);

	/**
	 * Returns a logic expression which equals {@code new Not(this)}.
	 * 
	 * @return !this
	 */
	public LogicExpression not() {
		return new Not(this);
	}

	/**
	 * Returns a logic expression which equals this && right.
	 * 
	 * @param right
	 *            the right side of the and
	 * @return {@code new And(this, right)}
	 */
	public LogicExpression and(LogicExpression right) {
		return new And(this, right);
	}

	public LogicExpression or(LogicExpression right) {
		return new Or(this, right);
	}

	public LogicExpression implies(LogicExpression right) {
		return new Implies(this, right);
	}

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

	/**
	 * Replaces unicode characters corresponding to logic symbols with their ASCII
	 * equivalents. Logic symbols that are already ASCII are not affected.
	 * 
	 * @param str
	 *            the string with which to replace ASCII symbols
	 * @return the formatted string
	 */
	public static String toASCII(String str) {
		return str.replace(UNI_AND, ASCII_AND).replace(UNI_OR, ASCII_OR).replace(UNI_IMPLIES, ASCII_IMPLIES)
				.replace(UNI_NOT, ASCII_NOT).replace(UNI_TURNSTILE, ASCII_TURNSTILE);
	}

	/**
	 * Replaces ASCII characters corresponding to logic symbols with their unicode
	 * equivalents. Logic symbols that are already unicode are not affected.
	 * 
	 * @param str
	 *            the string with which to replace unicode symbols
	 * @return the formatted string
	 */
	public static String toUnicode(String str) {
		return str.replace(ASCII_AND, UNI_AND).replace(ASCII_OR, UNI_OR).replace(ASCII_IMPLIES, UNI_IMPLIES)
				.replace(ASCII_NOT, UNI_NOT).replace(ASCII_TURNSTILE, UNI_TURNSTILE);
	}

}
