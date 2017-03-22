package com.mathutils.test;

import com.mathutils.math.matrix.Matrix;
import com.mathutils.math.matrix.MatrixElement;
import com.mathutils.math.matrix.MatrixHelper;

public class Test {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	Matrix a = new Matrix(2, 2);
	a.set(0, 0, 3);
	a.set(1, 1, 2);
	Matrix b = new Matrix(2, 2);
	b.set(0, 0, 5);
	b.set(1, 1, -3);
	Matrix c = MatrixHelper.multiply(a, b);
	for (MatrixElement e : c) {
	    System.out.println(e.getValue());
	}
    }

}
