package mathutils.math.matrix;

import mathutils.number.Number;

public interface MatrixFunction {

	/**
	 * Applies an action to the given element in a matrix.
	 * 
	 * @param row
	 *            the row the element is in
	 * @param col
	 *            the column the element is in
	 * @param number
	 *            the old value
	 * @return the new value
	 */
	public Number apply(int row, int col, Number number);

}
