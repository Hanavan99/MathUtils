package mathutils.expression;

import java.util.HashMap;
import java.util.Iterator;
import mathutils.number.Number;

public abstract class BinaryOperator extends MathExpression {

	private MathExpression left;
	private MathExpression right;

	public BinaryOperator(MathExpression left, MathExpression right) {
		this.left = left;
		this.right = right;
	}

	public MathExpression getLeft() {
		return left;
	}

	public MathExpression getRight() {
		return right;
	}

	public Iterator<MathExpression> iterator() {
		return new Iterator<MathExpression>() {

			private Iterator<MathExpression> left = BinaryOperator.this.left.iterator();
			private Iterator<MathExpression> right = BinaryOperator.this.right.iterator();
			private boolean meNext = true;
			private boolean leftNext = left != null;
			private boolean rightNext = right != null;

			@Override
			public boolean hasNext() {
				return meNext || leftNext || rightNext;
			}

			@Override
			public MathExpression next() {
				if (left != null && leftNext) {
					leftNext = false;
					return left.next();
					// leftNext = left.hasNext();
					// return result;
				}
				if (right != null && rightNext) {
					rightNext = false;
					return right.next();
					// rightNext = right.hasNext();
					// return result;
				}
				if (meNext) {
					meNext = false;
					return BinaryOperator.this;
				}
				return null;
			}

		};
	}

	@Override
	public final boolean equals(Object other) {
		return other instanceof BinaryOperator && ((BinaryOperator) other).getClass().equals(this.getClass())
				&& ((BinaryOperator) other).getLeft() != null && ((BinaryOperator) other).getLeft().equals(left)
				&& ((BinaryOperator) other).getLeft() != null && ((BinaryOperator) other).getRight().equals(right);
	}

	public final String toString() {
		return toString(null);
	}

	@Override
	public final MathExpression simplify(HashMap<Character, Number> vars) {
		MathExpression sleft = left.simplify(vars);
		MathExpression sright = right.simplify(vars);
		if (sleft instanceof Constant && sright instanceof Constant) {
			return new Constant(evaluate(vars));
		}
		return this;
	}

}
