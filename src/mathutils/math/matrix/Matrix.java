package mathutils.math.matrix;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

import mathutils.math.vertex.Vertex2i;
import mathutils.number.Number;
import mathutils.number.WholeNumber;

/**
 * Represents a generic matrix. This matrix can be used to store numerical data
 * as if it was a 2D array.
 * 
 * @author Hanavan Kuhn
 *
 * @param <Number>
 *            the type of data stored in the matrix
 */
public class Matrix implements Iterable<Number> {

	private class MatrixIterator implements Iterator<Number> {

		private int row = 0;
		private int col = 0;

		@Override
		public boolean hasNext() {
			return row < Matrix.this.rows && col < Matrix.this.cols;
		}

		@Override
		public Number next() {
			Number value = Matrix.this.get(row, col);
			col++;
			if (col > Matrix.this.cols) {
				col = 0;
				row++;
			}
			return value;
		}

	}

	private class MatrixInternalIterator implements Iterator<MatrixElement<Number>> {

		private int row = 0;
		private int col = 0;

		@Override
		public boolean hasNext() {
			return row < Matrix.this.rows && col < Matrix.this.cols;
		}

		@Override
		public MatrixElement<Number> next() {
			MatrixElement<Number> value = new MatrixElement<Number>(Matrix.this.get(row, col), new Vertex2i(col, row));
			col++;
			if (col > Matrix.this.cols) {
				col = 0;
				row++;
			}
			return value;
		}

	}

	private final int cols;
	private final int rows;
	private Object[][] matrix;

	/**
	 * Creates a new {@code Matrix} object with the specified number of rows and
	 * columns.
	 * 
	 * @param rows
	 *            the number of rows
	 * @param cols
	 *            the number of columns
	 */
	public Matrix(int rows, int cols) {
		if (cols < 0 || rows < 0) {
			throw new IllegalArgumentException("Matrix cannot have negative columns or rows!");
		}
		this.cols = cols;
		this.rows = rows;
		matrix = new Object[rows][cols];
		fill(new WholeNumber(0));
	}

	/**
	 * Gets the value at the specified row and column position.
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @return the value stored at that position
	 * @throws IllegalArgumentException
	 *             if either the row or column position is outside the size of the
	 *             matrix
	 */
	@SuppressWarnings("unchecked")
	public Number get(int row, int col) {
		checkBounds(row, col);
		return (Number) matrix[row][col];
	}

	/**
	 * Same as {@code get(int row, int col)}, but takes in a {@code Vertex2i} object
	 * rather than explicit row and column positions.
	 * 
	 * @param pos
	 *            the position to get
	 * @return the value stored at that position
	 */
	public Number get(Vertex2i pos) {
		return get(pos.getY(), pos.getX());
	}

	/**
	 * Sets the value at the specified row and column position to the provided
	 * value.
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @param value
	 *            the new value
	 */
	public void set(int row, int col, Number value) {
		checkBounds(row, col);
		matrix[row][col] = value;
	}

	/**
	 * Same as {@code set(int row, int col, Number value)}, but takes in a
	 * {@code Vertex2i} object rather than explicit row and column positions.
	 * 
	 * @param pos
	 *            the position to set
	 * @param value
	 *            the new value
	 */
	public void set(Vertex2i pos, Number value) {
		set(pos.getY(), pos.getX(), value);
	}

	/**
	 * Fills the matrix with the specified value.
	 * 
	 * @param value
	 *            the value to fill it with
	 */
	public void fill(Number value) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				matrix[row][col] = value;
			}
		}
	}

	/**
	 * Checks whether or not the specified row and column are valid positions in
	 * this {@code Matrix}. If the provided parameters fall outside the bounds of
	 * the matrix, an exception is thrown, otherwise the method returns normally. If
	 * you don't want to catch an exception, the {@code isInside(int row, int col)}
	 * should be used instead.
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 */
	public void checkBounds(int row, int col) {
		if (!isInside(row, col)) {
			throw new IllegalArgumentException("Invalid column or row position");
		}
	}

	/**
	 * Checks whether or not the specified row and column are valid positions in
	 * this {@code Matrix}.
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @return whether or not the row and column fall inside the matrix bounds
	 */
	public boolean isInside(int row, int col) {
		return col >= 0 && row >= 0 && col < cols && row < rows;
	}

	public boolean isNumberic() {
		return matrix[0][0].getClass().equals(Number.class);
	}

	public Number[] getRowAsArray(int row) {
		if (row >= 0 && row < rows) {
			return (Number[]) matrix[row];
		} else {
			throw new ArrayIndexOutOfBoundsException("Invalid col or row position");
		}
	}

	/**
	 * Returns whether or not this matrix is square, or if it has the same amount of
	 * rows and columns.
	 * 
	 * @return if the matrix is square
	 */
	public boolean isSquare() {
		return cols == rows;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return cols;
	}

	@Override
	public void forEach(Consumer<? super Number> action) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				action.accept(get(row, col));
			}
		}
	}

	public void doFunc(MatrixFunction action) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				set(row, col, action.apply(row, col, get(row, col)));
			}
		}
	}

	public void add(Matrix m) {
		doFunc((row, col, e) -> e.add(m.get(row, col)));
	}

	public void multiply(Number number) {
		doFunc((row, col, e) -> e.multiply(number));
	}

	public void multiply(Matrix m) {
		if (rows == m.cols && cols == m.rows) {
			doFunc((int row, int col, Number e) -> {
				Number sum = new WholeNumber(0);
				for (int i = 0; i < m.rows; i++) {
					//sum = sum.add(b)
				}
				return sum;
			});
		} else {
			throw new IllegalArgumentException("Matrix must have same dimensions as this matrix transposed");
		}
	}

	@Override
	public Iterator<Number> iterator() {
		return new MatrixIterator();
	}

	public Iterator<MatrixElement<Number>> internalIterator() {
		return new MatrixInternalIterator();
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		for (int col = 0; col < cols; col++) {
			sb.append("[");
			for (int row = 0; row < rows; row++) {
				sb.append(get(row, col).toString());
				sb.append(", ");
			}
			sb.setLength(sb.length() - 2);
			sb.append("]\n");
		}
		return sb.toString();
	}

}
