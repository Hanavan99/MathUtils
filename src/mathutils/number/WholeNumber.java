package mathutils.number;

public class WholeNumber extends Number {

	private int n;

	public WholeNumber(int n) {
		this.n = n;
	}

	@Override
	public double getValue() {
		return n;
	}

	@Override
	public Number pow(Number n) {
		return new WholeNumber((int) Math.pow(this.n, (int) n.getValue()));
	}

	@Override
	public Number add(Number b) {
		return new WholeNumber(n + (int) b.getValue());
	}

	@Override
	public Number multiply(Number b) {
		if (b instanceof WholeNumber) {
			return new WholeNumber(n * (int) b.getValue());
		}
		return new RealNumber(n * b.getValue());
	}

	@Override
	public Number divide(Number b) {
		return new RealNumber(n / b.getValue());
	}

	@Override
	public Number negate() {
		return new WholeNumber(-n);
	}

	@Override
	public String toString() {
		return String.valueOf(n);
	}

}
