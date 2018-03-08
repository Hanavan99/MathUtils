package mathutils.number;

public abstract class Number {

	public abstract double getValue();

	public abstract Number pow(Number n);
	
	public abstract Number add(Number b);

	public abstract Number multiply(Number b);
	
	public abstract Number divide(Number b);

	public abstract Number negate();

	@Override
	public abstract String toString();

}
