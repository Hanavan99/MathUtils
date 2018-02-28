package mathutils.test;

import java.math.BigDecimal;

import mathutils.math.bigdecimal.BigDecimalMath;
import mathutils.math.context.ComputeContext;

public class Test {

    private static final ComputeContext CONTEXT = new ComputeContext(10, 20, 50, BigDecimal.ROUND_HALF_UP, 0);

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	// Matrix a = new Matrix(2, 2);
	// a.set(0, 0, 3);
	// a.set(1, 1, 2);
	// Matrix b = new Matrix(2, 2);
	// b.set(0, 0, 5);
	// b.set(1, 1, -3);
	// Matrix c = MatrixHelper.multiply(a, b);
	// for (MatrixElement e : c) {
	// System.out.println(e.getValue());
	// }
	// System.out.println("e = ");
	// BigDecimalMath.printNumber(BigDecimalMath.E, 200, 2000);
	// System.out.println("pi = ");
	// BigDecimalMath.printNumber(BigDecimalMath.PI, 200, 2000);
	// for (int i = 0; i < 1000000; i += 1000) {
	// long time = System.currentTimeMillis();
	// BigDecimal fact = BigDecimalMath.factorial(new BigDecimal(i));
	// long newTime = System.currentTimeMillis();
	// System.out.println("Calculated " + i + "! in " + (newTime - time) + "ms");
	// // BigDecimal num = dfactorial(new BigDecimal(2 * i - 1), false);
	// // // System.out.println();
	// // BigDecimal den = dfactorial(new BigDecimal(2 * i + 1), true);
	// // // System.out.println();
	// // System.out.println(num + "/" + den);
	//
	// }
	// printNumber(exp(new BigDecimal(10), MC), 200);

	// System.out.println("sin(1)");
	// printNumber(sin(BigDecimal.ONE, MC), 200);
	// System.out.println("arctan(1)*4");
	// printNumber(arctan(new BigDecimal(1), MC).multiply(new BigDecimal(4)), 200);
	// System.out.println("pi");
	// printNumber(PI, 200, 2000);
	// printNumber(clamp(BigDecimalMath.PI.multiply(new BigDecimal(1.5)),
	// BigDecimalMath.PI.negate(), BigDecimalMath.PI), 200);
	// System.out.println("cos(20pi)");
	// System.out.println("sin(3pi/2)");
	// BigDecimalMath.printNumber(BigDecimalMath.sin(BigDecimalMath.PI, CONTEXT),
	// 200, 1000);

	// System.out.println("cos(1)");
	// printNumber(cos(BigDecimal.ONE, MC), 200);
	// System.out.println("tan(1)");
	// printNumber(tan(BigDecimal.ONE, MC), 200);
	// System.out.println("sin(Math.PI)");
	// printNumber(sin(new BigDecimal(Math.PI), MC), 200);

    }

}
