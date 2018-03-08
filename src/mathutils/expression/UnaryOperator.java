package mathutils.expression;

import java.util.Iterator;

public abstract class UnaryOperator extends MathExpression {

	private MathExpression expression;

	public UnaryOperator(MathExpression expression) {
		this.expression = expression;
	}

	public MathExpression getExpression() {
		return expression;
	}

	public Iterator<MathExpression> iterator() {
		return new Iterator<MathExpression>() {

			private Iterator<MathExpression> expression = UnaryOperator.this.expression.iterator();
			private boolean meNext = true;
			private boolean expressionNext = expression != null;

			@Override
			public boolean hasNext() {
				return meNext || expressionNext;
			}

			@Override
			public MathExpression next() {
				if (expression != null && expressionNext) {
					expressionNext = false;
					return expression.next();
				}
				if (meNext) {
					meNext = false;
					return UnaryOperator.this;
				}
				return null;
			}

		};
	}

	@Override
	public final boolean equals(Object other) {
		return other instanceof UnaryOperator && ((UnaryOperator) other).getClass().equals(this.getClass())
				&& ((UnaryOperator) other).getExpression() != null
				&& ((UnaryOperator) other).getExpression().equals(expression);
	}

	@Override
	public final String toString() {
		return toString(null);
	}

}
