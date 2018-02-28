package mathutils.core;

/**
 * Represents an expression of any type.
 * 
 * @author Hanavan Kuhn
 *
 * @param <T>
 *            the type of value returned when {@code evaluate()} is called
 * @param <E>
 *            the type of values substituted in for a given input
 */
public abstract class Expression<T, E> {

	/**
	 * Returns a new expression given values for the inputs.
	 * 
	 * @param inputs
	 *            the known inputs to work with
	 * @return the final result
	 */
	public abstract T evaluate(InputList<T, E> inputs);

	/**
	 * Determines whether two Expressions are equal. This method should not check
	 * for arithmetic or logical equivalency, but rather
	 */
	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract String toString();

	public abstract String toString(InputList<T, E> inputs);

}
