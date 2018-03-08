package mathutils.number;

public class RealNumber extends Number {

	private double n;

	public RealNumber(double n) {
		this.n = n;
	}

	@Override
	public double getValue() {
		return n;
	}

	@Override
	public Number pow(Number n) {
		return new RealNumber(Math.pow(this.n, n.getValue()));
	}

	@Override
	public Number add(Number b) {
		return new RealNumber(n + b.getValue());
	}

	@Override
	public Number multiply(Number b) {
		return new RealNumber(n * b.getValue());
	}

	@Override
	public Number divide(Number b) {
		return new RealNumber(n / b.getValue());
	}

	@Override
	public Number negate() {
		return new RealNumber(-n);
	}

	@Override
	public String toString() {
		return String.valueOf(n);
	}

}
