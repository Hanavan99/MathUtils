package com.mathutils.math.matrix;

import java.util.Iterator;

import com.mathutils.math.vertex.Vertex2i;

/**
 * Represents a mathematical matrix.
 * 
 * <a href=
 * "https://en.wikipedia.org/wiki/Matrix_(mathematics)">https://en.wikipedia.org/wiki/Matrix_(mathematics)</a>
 * 
 * @author Hanavan Kuhn
 *
 */
public class Matrix implements Iterable<MatrixElement> {

    private final int cols;
    private final int rows;
    private double[][] matrix;

    public Matrix(int rows, int cols) {
	if (cols < 0 || rows < 0) {
	    throw new IllegalStateException("Matrix cannot have negative columns or rows!");
	}
	this.cols = cols;
	this.rows = rows;
	matrix = new double[rows][cols];
    }
    
    public Matrix(double[]... matrix) {
	cols = matrix[0].length;
	rows = matrix.length;
	this.matrix = matrix;
    }

    public double get(int row, int col) {
	if (col >= 0 && row >= 0 && col < cols && row < rows) {
	    return matrix[row][col];
	} else {
	    throw new ArrayIndexOutOfBoundsException("Invalid col or row position");
	}
    }

    public double get(Vertex2i pos) {
	return get(pos.getY(), pos.getX());
    }

    public void set(int row, int col, double value) {
	if (col >= 0 && row >= 0 && col < cols && row < rows) {
	    matrix[row][col] = value;
	}
    }

    public void set(Vertex2i pos, double value) {
	set(pos.getY(), pos.getX(), value);
    }

    public double[] getColumnAsArray(int col) {
	if (col >= 0 && col < cols) {
	    double[] column = new double[rows];
	    for (int row = 0; row < rows; row++) {
		column[row] = matrix[row][col];
	    }
	    return column;
	} else {
	    throw new ArrayIndexOutOfBoundsException("Invalid col or row position");
	}
    }

    public double[] getRowAsArray(int row) {
	if (row >= 0 && row < rows) {
	    return matrix[row];
	} else {
	    throw new ArrayIndexOutOfBoundsException("Invalid col or row position");
	}
    }

    public boolean isSquare() {
	return cols == rows;
    }

    public int getColumns() {
	return cols;
    }

    public int getRows() {
	return rows;
    }

    @Override
    public Iterator<MatrixElement> iterator() {
	MatrixElement[] matrixArray = new MatrixElement[rows * cols];
	for (int row = 0; row < rows; row++) {
	    for (int col = 0; col < cols; col++) {
		matrixArray[row * rows + col] = new MatrixElement(matrix[row][col], new Vertex2i(row, col));
	    }
	}
	return new Iterator<MatrixElement>() {

	    private int currentIndex = 0;
	    private MatrixElement[] array = matrixArray;

	    @Override
	    public boolean hasNext() {
		return currentIndex != array.length;
	    }

	    @Override
	    public MatrixElement next() {
		return array[currentIndex++];
	    }

	};
    }

}
