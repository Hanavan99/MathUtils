package com.mathutils.math.matrix;

/**
 * Performs functions such as matrix math and matrix creation.
 * 
 * @author Hanavan Kuhn
 *
 */
public class MatrixHelper {

    /**
     * Adds two matrices together.
     * 
     * {@link }
     * 
     * @param a
     *            matrix A
     * @param b
     *            matrix B
     * @return the resulting matrix
     */
    public static Matrix add(Matrix a, Matrix b) {
	if (compareSize(a, b)) {
	    for (MatrixElement e : a) {
		a.set(e.getPosition(), a.get(e.getPosition()) + b.get(e.getPosition()));
	    }
	    return a;
	} else {
	    return null;
	}
    }

    /**
     * Multiplies the entire matrix A by a scalar quantity.
     * 
     * @param a
     *            matrix A
     * @param b
     *            the scalar quantity
     * @return the resulting matrix
     */
    public static Matrix multiply(Matrix a, double b) {
	for (MatrixElement e : a) {
	    a.set(e.getPosition(), a.get(e.getPosition()) * b);
	}
	return a;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
	if (a.getColumns() == b.getRows()) {
	    Matrix c = new Matrix(a.getRows(), b.getColumns());
	    for (MatrixElement e : c) {
		double value = 0;
		double[] row = a.getRowAsArray(e.getPosition().getY());
		double[] col = b.getColumnAsArray(e.getPosition().getX());
		for (int i = 0; i < row.length; i++) {
		    value += row[i] * col[i];
		}
		c.set(e.getPosition(), value);
	    }
	    return c;
	} else {
	    return null;
	}
    }

    public static Matrix createSquareMatrix(int size) {
	return new Matrix(size, size);
    }

    public static Matrix createIdentityMatrix(int size) {
	Matrix identity = createSquareMatrix(size);
	for (int i = 0; i < size; i++) {
	    identity.set(i, i, 1);
	}
	return identity;
    }

    public static boolean compareSize(Matrix a, Matrix b) {
	return a.getRows() == b.getRows() && a.getColumns() == b.getColumns();
    }

}
