package mathutils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mathutils.math.matrix.Matrix;
import mathutils.math.vertex.Vertex2i;

public class MatrixTest {

    @Test
    public void test() {
	Matrix m = new Matrix(3, 2);
	assertEquals(m.getColumns(), 2);
	assertEquals(m.getRows(), 3);
	assertEquals(m.isSquare(), false);
	m = new Matrix(new double[] { 1, 2, 3 }, new double[] { -3, 2, -1 }, new double[] { 5, 4, 3 });
	assertEquals(m.getColumns(), 3);
	assertEquals(m.getRows(), 3);
	assertEquals(m.get(0, 0), 1, 0);
	assertEquals(m.get(new Vertex2i(1, 1)), 2, 0);
	assertEquals(m.getColumnAsArray(1)[2], 4, 0);
	assertEquals(m.getRowAsArray(1)[1], 2, 0);
	assertEquals(m.isSquare(), true);
    }
}
