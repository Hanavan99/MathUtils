package mathutils.logic;

import java.util.Iterator;

public abstract class UnaryOperator extends LogicExpression {

	private LogicExpression expression;

	public UnaryOperator(LogicExpression expression) {
		this.expression = expression;
	}

	public LogicExpression getExpression() {
		return expression;
	}

	public Iterator<LogicExpression> iterator() {
		return new Iterator<LogicExpression>() {

			private Iterator<LogicExpression> expression = UnaryOperator.this.expression.iterator();
			private boolean meNext = true;
			private boolean expressionNext = expression != null;

			@Override
			public boolean hasNext() {
				return meNext || expressionNext;
			}

			@Override
			public LogicExpression next() {
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
