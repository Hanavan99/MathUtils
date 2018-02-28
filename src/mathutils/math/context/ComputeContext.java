package mathutils.math.context;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * The {@code ComputeContext} class defines characteristics that are to be used
 * when performing a calculation.
 * 
 * @author Hanavan Kuhn
 *
 */
public class ComputeContext {

    private int iterations = 1000;
    private int precision = 5000;
    private int finalPrecision = 10000;
    private int roundingMode = BigDecimal.ROUND_HALF_UP;
    private int threads = 0;

    /**
     * Dummy constructor
     */
    public ComputeContext() {

    }

    public ComputeContext(int iterations) {
	this.iterations = iterations;
    }

    public ComputeContext(int iterations, int precision) {
	this.iterations = iterations;
	this.precision = precision;
    }
    
    public ComputeContext(int iterations, int precision, int finalPrecision) {
	this.iterations = iterations;
	this.precision = precision;
	this.finalPrecision = finalPrecision;
    }

    public ComputeContext(int iterations, int precision, int finalPrecision, int roundingMode, int threads) {
	this.iterations = iterations;
	this.precision = precision;
	this.finalPrecision = finalPrecision;
	this.roundingMode = roundingMode;
	this.threads = threads;
    }

    public int getIterations() {
	return iterations;
    }

    public int getPrecision() {
	return precision;
    }
    
    public int getFinalPrecision() {
	return finalPrecision;
    }

    public int getRoundingMode() {
	return roundingMode;
    }
    
    public boolean shouldMultithread() {
	return threads > 0;
    }
    
    public int getThreads() {
	return threads;
    }

    public MathContext getAsMathContext() {
	RoundingMode mode = null;
	switch (roundingMode) {
	case BigDecimal.ROUND_UNNECESSARY:
	    mode = RoundingMode.UNNECESSARY;
	    break;
	case BigDecimal.ROUND_UP:
	    mode = RoundingMode.UP;
	    break;
	case BigDecimal.ROUND_HALF_UP:
	    mode = RoundingMode.HALF_UP;
	    break;
	}
	return new MathContext(precision, mode);
    }

}
