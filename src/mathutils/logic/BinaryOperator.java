package mathutils.logic;

import java.util.Iterator;

public abstract class BinaryOperator extends LogicExpression {

	private LogicExpression left;
	private LogicExpression right;

	public BinaryOperator(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}

	public LogicExpression getLeft() {
		return left;
	}

	public LogicExpression getRight() {
		return right;
	}

	public Iterator<LogicExpression> iterator() {
		return new Iterator<LogicExpression>() {

			private Iterator<LogicExpression> left = BinaryOperator.this.left.iterator();
			private Iterator<LogicExpression> right = BinaryOperator.this.right.iterator();
			private boolean meNext = true;
			private boolean leftNext = left != null;
			private boolean rightNext = right != null;

			@Override
			public boolean hasNext() {
				return meNext || leftNext || rightNext;
			}

			@Override
			public LogicExpression next() {
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
		return other instanceof BinaryOperator && ((BinaryOperator) other).getClass().equals(this.getClass()) && ((BinaryOperator) other).getLeft() != null && ((BinaryOperator) other).getLeft().equals(left)
				&& ((BinaryOperator) other).getLeft() != null && ((BinaryOperator) other).getRight().equals(right);
	}
	
	@Override
	public final String toString() {
		return toString(null);
	}

}
