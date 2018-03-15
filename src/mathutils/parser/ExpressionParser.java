package mathutils.parser;

import java.util.ArrayList;

public abstract class ExpressionParser<T> {

	private ArrayList<ParserOperator<T>> operators;
	private ArrayList<ParserFunction<T>> functions;

	public void useOperator(ParserOperator<T> operator) {
		operators.add(operator);
	}

	public void useFunction(ParserFunction<T> function) {
		functions.add(function);
	}

	public T parse(String str) {
		int mode = 0;
		String curex = "";
		char[] charstr = str.toCharArray();
		char last = 0;
		char cur = 0;
		T root = null;
		for (int i = 0; i < charstr.length; i++) {
			// get current character
			cur = charstr[i];

			// check if we just started
			if (i == 0) {
				curex += cur;
				last = cur;
				break;
			}

			// see if the next character is different than the ones in curex
			switch (mode) {
			case 0:
				// we are reading nothing
				break;
			case 1:
				// we are reading a number
			}
		}
		return null;
	}

}
