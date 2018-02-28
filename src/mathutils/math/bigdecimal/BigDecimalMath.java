package mathutils.math.bigdecimal;

import java.math.BigDecimal;
import java.math.MathContext;

import mathutils.math.context.ComputeContext;

/**
 * This class adds functionality to the {@code BigDecimal} class. Specifically
 * it includes the triginometric functions (sine, cosine, tangent, arcsine,
 * arccosine, arctangent), as well as some other useful functions (factorial,
 * double factorial, clamping). <br />
 * <br />
 * All of the trigonometric functions use a Taylor Series to calculate. Input
 * values to periodic functions are clamped to prevent precision loss with large
 * values. The {@code ComputeContext} class provides a way to finely control the
 * computation of these functions.
 * 
 * @author Hanavan Kuhn
 *
 */
public class BigDecimalMath {

    /**
     * Default context used for methods that do not require a {@code ComputeContext}
     * object.
     */
    public static final ComputeContext DEFAULT_CONTEXT = new ComputeContext();
    /**
     * The constant &pi; with several hundred digits of accuracy. It is calculated
     * using this library using the function &pi;=6*arcsin(1/2).
     */
    public static final BigDecimal PI;
    /**
     * The constant e calculated with the function e=exp(1).
     */
    public static final BigDecimal E;

    static {
	System.out.println("Computing pi...");
	PI = arcsin(new BigDecimal(0.5), DEFAULT_CONTEXT).multiply(new BigDecimal(6));
	System.out.println("Computing e...");
	E = exp(BigDecimal.ONE, DEFAULT_CONTEXT);
	System.out.println("Done.");
    }

    @Deprecated
    /**
     * Function NYI. This function is designed to calculate &pi; to any desired
     * precision using a digit-extraction method. Currently it is not functional so
     * be patient.
     * 
     * @param cc
     *            The context to use in the computation of this function
     * @return &pi; to the desired accuracy
     */
    public static BigDecimal pi(ComputeContext cc) {
	BigDecimal result = BigDecimal.ZERO;
	MathContext mc = cc.getAsMathContext();
	for (int i = 0; i < 1000; i++) {
	    // result = result
	    // .add(new BigDecimal(-1).pow(i).multiply(factorial(new
	    // BigDecimal(6).multiply(new BigDecimal(i))))
	    // .multiply(new BigDecimal(13591409).add(new BigDecimal(545140134).multiply(new
	    // BigDecimal(i)))));

	    BigDecimal term0 = new BigDecimal(Math.pow(2, 5)).divide(new BigDecimal(4 * i + 1), mc).negate();
	    BigDecimal term1 = new BigDecimal(Math.pow(2, 0)).divide(new BigDecimal(4 * i + 3), mc).negate();
	    BigDecimal term2 = new BigDecimal(Math.pow(2, 8)).divide(new BigDecimal(10 * i + 1), mc);
	    BigDecimal term3 = new BigDecimal(Math.pow(2, 6)).divide(new BigDecimal(10 * i + 3), mc).negate();
	    BigDecimal term4 = new BigDecimal(Math.pow(2, 2)).divide(new BigDecimal(10 * i + 5), mc).negate();
	    BigDecimal term5 = new BigDecimal(Math.pow(2, 2)).divide(new BigDecimal(10 * i + 7), mc).negate();
	    BigDecimal term6 = new BigDecimal(Math.pow(2, 0)).divide(new BigDecimal(10 * i + 9), mc);
	    result = result.add(new BigDecimal(-1).pow(i).divide(new BigDecimal(2).pow(10 * i))
		    .multiply(term0.add(term1).add(term2).add(term3).add(term4).add(term5).add(term6)));
	}
	return new BigDecimal(1d / 256).multiply(result);
    }

    /**
     * Computes the sine of the number theta in radians. Uses the default
     * {@code ComputeContext} for calculation.
     * 
     * @param theta
     *            The angle
     * @return sin(theta)
     */
    public static BigDecimal sin(BigDecimal theta) {
	return sin(theta, DEFAULT_CONTEXT);
    }

    /**
     * Computes the sine of the number theta in radians. Uses the specified
     * {@code MathContext} object to define how the number is calculated.
     * 
     * @param theta
     *            The angle
     * @param cc
     *            The context to use in calculation
     * @return sin(theta)
     */
    public static BigDecimal sin(BigDecimal theta, ComputeContext cc) {
	BigDecimal result = BigDecimal.ZERO;
	MathContext mc = cc.getAsMathContext();
	theta = clamp(theta, PI.negate(), PI);
	for (int i = 0; i < cc.getIterations(); i++) {
	    result = result.add((i % 2 == 0 ? BigDecimal.ONE : BigDecimal.ONE.negate()).multiply(theta.pow(2 * i + 1))
		    .divide(factorial(new BigDecimal(2 * i + 1)), mc));
	}
	return result;
    }

    /**
     * Computes the cosine of the number theta in radians. Uses the default
     * {@code ComputeContext} for calculation.
     * 
     * @param theta
     *            The angle
     * @return cos(theta)
     */
    public static BigDecimal cos(BigDecimal theta) {
	return cos(theta, DEFAULT_CONTEXT);
    }

    /**
     * Computes the cosine of the number theta in radians. Uses the specified
     * {@code MathContext} object to define how the number is calculated.
     * 
     * @param theta
     *            The angle
     * @param cc
     *            The context to use in calculation
     * @return cos(theta)
     */
    public static BigDecimal cos(BigDecimal theta, ComputeContext cc) {
	BigDecimal result = BigDecimal.ZERO;
	MathContext mc = cc.getAsMathContext();
	theta = clamp(theta, PI.negate(), PI);
	for (int i = 0; i < cc.getIterations(); i++) {
	    result = result.add(
		    new BigDecimal(-1).pow(i).multiply(theta.pow(2 * i)).divide(factorial(new BigDecimal(2 * i)), mc));
	}
	return result;
    }

    /**
     * Computes the tangent of the number theta in radians. Uses the default
     * {@code ComputeContext} for calculation.
     * 
     * @param theta
     *            The angle
     * @return tan(theta)
     */
    public static BigDecimal tan(BigDecimal theta) {
	return tan(theta, DEFAULT_CONTEXT);
    }

    /**
     * Computes the tangent of the number theta in radians. Uses the specified
     * {@code MathContext} object to define how the number is calculated.
     * 
     * @param theta
     *            The angle
     * @param cc
     *            The context to use in calculation
     * @return tan(theta)
     */
    public static BigDecimal tan(BigDecimal theta, ComputeContext cc) {
	MathContext mc = cc.getAsMathContext();
	return sin(theta, cc).divide(cos(theta, cc), mc);
    }

    /**
     * Computes the arcsine of the number a, and the result is in radians. Uses the
     * specified {@code MathContext} object to define how the number is calculated.
     * 
     * @param a
     *            The number
     * @param cc
     *            The context to use in calculation
     * @return arcsin(a)
     */
    public static BigDecimal arcsin(BigDecimal a, ComputeContext cc) {
	if (a.abs().compareTo(BigDecimal.ONE) > 0) {
	    throw new ArithmeticException("arcsin() is not defined at " + a.doubleValue());
	}
	BigDecimal result = BigDecimal.ZERO;
	MathContext mc = cc.getAsMathContext();
	for (int i = 0; i < cc.getIterations(); i++) {
	    result = result.add(doubleFactorial(new BigDecimal(2 * i - 1), false)
		    .divide(doubleFactorial(new BigDecimal(2 * i + 1), true), mc).multiply(a.pow(2 * i + 1)));
	    // result = result.add(
	    // a.pow(2 * i + 1).multiply(new BigDecimal(0.5)/* ??? */).divide(factorial(new
	    // BigDecimal(i)).add(
	    // new BigDecimal(2).multiply(new BigDecimal(i).multiply(factorial(new
	    // BigDecimal(i))))), mc));
	}
	return result;
    }

    /**
     * Computes the arctangent of the number a, and the result is in radians. Uses
     * the specified {@code MathContext} object to define how the number is
     * calculated.
     * 
     * @param a
     *            The number
     * @param cc
     *            The context to use in calculation
     * @return arctan(a)
     */
    public static BigDecimal arctan(BigDecimal a, ComputeContext cc) {
	BigDecimal result = BigDecimal.ZERO;
	MathContext mc = cc.getAsMathContext();
	for (int i = 0; i < cc.getIterations(); i++) {
	    result = result
		    .add(new BigDecimal(-1).pow(i).multiply(a.pow(2 * i + 1)).divide(new BigDecimal(2 * i + 1), mc));
	}
	return result;
    }

    /**
     * NYI. Computes a to the power n.
     * 
     * @param a
     *            The number used as the base
     * @param n
     *            The number used as the exponent
     * @return a^n
     * @throws ArithmeticException
     *             if a and n are both zero, or 0^0
     */
    public static BigDecimal pow(BigDecimal a, BigDecimal n) {
	if (a.compareTo(BigDecimal.ZERO) == 0 && n.compareTo(BigDecimal.ZERO) == 0) {
	    throw new ArithmeticException("0^0 is undefined");
	}
	BigDecimal result = a;
	for (BigDecimal i = new BigDecimal(1); i.compareTo(n) <= 0; i = i.add(BigDecimal.ONE)) {
	    result = result.multiply(result);
	}
	return result;
    }

    /**
     * Clamps a value between two bounds. If the number exceeds the upper bound, it
     * is wrapped back to the lower bound. If the number exceeds the lower bound, it
     * is wrapped back to the upper bound.
     * 
     * @param a
     *            The number to be clamped
     * @param lower
     *            The lower bound that the number cannot exceed
     * @param upper
     *            The upper bound that the number cannot exceed
     * @return The clamped number
     */
    public static BigDecimal clamp(BigDecimal a, BigDecimal lower, BigDecimal upper) {
	return a.remainder(upper.subtract(lower)).add(lower);
    }

    /**
     * Raises e to the power of the number.
     * 
     * @param a
     *            The number
     * @param cc
     *            The context to use in calculation
     * @return e^a
     */
    public static BigDecimal exp(BigDecimal a, ComputeContext cc) {
	BigDecimal result = BigDecimal.ZERO;
	MathContext mc = cc.getAsMathContext();
	for (int i = 0; i < cc.getIterations(); i++) {
	    result = result.add(a.pow(i).divide(factorial(new BigDecimal(i)), mc));
	}
	return result;
    }

    /**
     * Multiplies all numbers between 1 and n together. Only works for integer
     * values. NOTE: 0! = 1, and 1! = 1. This function also is not defined for
     * negative numbers. To evaluate the factorial function with any real number,
     * use the gamma function.
     * 
     * @param n
     *            The number to factorial
     * @return n!
     * @throws ArithmeticException
     *             if n is not an integer, or if n < 0
     */
    public static BigDecimal factorial(BigDecimal n) {
	if (n.scale() != 0) {
	    throw new ArithmeticException("Factorial function does not accept decimals");
	}
	if (n.compareTo(BigDecimal.ZERO) < 0) {
	    throw new ArithmeticException("Factorial function is not defined for numbers < 0");
	}
	BigDecimal result = BigDecimal.ONE;
	for (BigDecimal i = new BigDecimal(2); i.compareTo(n) <= 0; i = i.add(BigDecimal.ONE)) {
	    result = result.multiply(i);
	}
	return result;
    }

    /**
     * If n is even, multiplies all even numbers between 2 and n together. If n is
     * odd, multiplies all odd numbers between 1 and n together. If special is true,
     * it will multiply all the even numbers between 2 and n together, and if n is
     * odd, it will multiply by n as well. Some examples of results are shown below.
     * <br />
     * <br />
     * <table border width=150>
     * <tr>
     * <th>n</th>
     * <th>special</th>
     * <th>result</th>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>false</td>
     * <td>3*1=3</td>
     * </tr>
     * <tr>
     * <td>6</td>
     * <td>false</td>
     * <td>6*4*2=48</td>
     * </tr>
     * <tr>
     * <td>4</td>
     * <td>true</td>
     * <td>4*2=8</td>
     * </tr>
     * <tr>
     * <td>7</td>
     * <td>true</td>
     * <td>7*6*4*2=336</td>
     * </tr>
     * </table>
     * 
     * @param n
     *            The number to take the double factorial of
     * @param special
     *            Whether or not to force the use of even numbers
     * @return n!!
     */
    public static BigDecimal doubleFactorial(BigDecimal n, boolean special) {
	BigDecimal result = BigDecimal.ONE;
	if (special) {
	    for (BigDecimal i = new BigDecimal(2); i.compareTo(n) <= 0; i = i.add(new BigDecimal(2))) {
		result = result.multiply(i);
	    }
	    result = result.multiply(n);
	} else if (n.remainder(new BigDecimal(2)).equals(BigDecimal.ZERO)) {
	    for (BigDecimal i = new BigDecimal(2); i.compareTo(n) <= 0; i = i.add(new BigDecimal(2))) {
		result = result.multiply(i);
	    }
	} else {
	    for (BigDecimal i = new BigDecimal(3); i.compareTo(n) <= 0; i = i.add(new BigDecimal(2))) {
		result = result.multiply(i);
	    }

	}
	return result;
    }

    /**
     * Prints the number to the console with a specified number of digits per line.
     * 
     * @param d
     *            The number
     * @param digitsPerLine
     *            The number of digits per line
     */
    public static void printNumber(BigDecimal d, int digitsPerLine) {
	String str = d.toPlainString();
	for (int i = 0; i < str.length(); i += digitsPerLine) {
	    int end = i + digitsPerLine;
	    System.out.println(str.substring(i, end > str.length() ? str.length() : end));
	}
    }

    /**
     * Prints the number to the console with a specified number of digits per line,
     * as well as a limit on how many digits are printed.
     * 
     * @param d
     *            The number
     * @param digitsPerLine
     *            The number of digits per line
     * @param digits
     *            The maximum number of digits that will print
     */
    public static void printNumber(BigDecimal d, int digitsPerLine, int digits) {
	String str = d.toPlainString();
	for (int i = 0; i < Math.min(str.length(), digits); i += digitsPerLine) {
	    int end = i + digitsPerLine;
	    System.out.println(str.substring(i, end > str.length() ? str.length() : end));
	}
    }

}
