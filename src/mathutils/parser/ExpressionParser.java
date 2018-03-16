package mathutils.parser;

import java.util.ArrayList;
import java.util.Stack;

import mathutils.logic.BoolInput;
import mathutils.logic.LogicExpression;
import mathutils.logic.operators.And;
import mathutils.logic.operators.Implies;
import mathutils.logic.operators.Or;

@Deprecated
public abstract class ExpressionParser<T> {

	private ArrayList<ParserOperator<T>> operators;
	private ArrayList<ParserFunction<T>> functions;

	public void useOperator(ParserOperator<T> operator) {
		operators.add(operator);
	}

	public void useFunction(ParserFunction<T> function) {
		functions.add(function);
	}

	private boolean containsOperator(String c) {
		for (ParserOperator<T> po : operators) {
			if (po.getOp().equals(c)) {
				return true;
			}
		}
		return false;
	}

	private ParserOperator<T> getOperator(String c) {
		for (ParserOperator<T> po : operators) {
			if (po.getOp().equals(c)) {
				return po;
			}
		}
		return null;
	}

	/*
	 * public T parse(String str) { System.out.println(" --- BEGIN PARSE --- ");
	 * 
	 * // get rid of extra spaces str = str.replace(" ", "");
	 * 
	 * char[] charstr = str.toCharArray(); char cur = 0;
	 * 
	 * Stack<String> stack = new Stack<String>(); Stack<T> exs = new Stack<T>(); int
	 * mode = 0; // 0 - null, 1, variable/function, 2 number String identifier = "";
	 * 
	 * // loop through each character for (int i = 0; i < charstr.length; i++) { cur
	 * = charstr[i]; if (cur >= 'a' && cur <= 'z') { if (mode != 0) { identifier =
	 * ""; mode = 1; } identifier += cur; // exs.push(popUOps(stack, new
	 * BoolInput(cur))); } else if (containsOperator(identifier)) {
	 * stack.push(identifier); } else if (cur == '-' && i < charstr.length - 1 &&
	 * charstr[i + 1] == '>') { stack.push("->"); } else if (cur == ')') {
	 * popOps(stack, exs); } } popOps(stack, exs); T ex = exs.pop();
	 * System.out.println("-> " + ex);
	 * 
	 * System.out.println(" ---- END PARSE ---- ");
	 * 
	 * return ex; }
	 * 
	 * private void pushIdentifier(String identifier, int mode, Stack<String> stack,
	 * Stack<T> exs) { switch (mode) { case 1: stack.push(identifier); break; case
	 * 2: try { int i = Integer.parseInt(identifier); // exs. } catch
	 * (NumberFormatException e) {
	 * 
	 * } } }
	 * 
	 * 
	 * private void popOps(Stack<String> stack, Stack<T> exs) { T left = null; T
	 * right = null; System.out.println("Current stack: " + stack); while
	 * (stack.size() > 0 && !stack.peek().equals("(")) { String op = stack.pop();
	 * System.out.println("Popped operator " + op); T ex = null; right = exs.pop();
	 * left = exs.pop(); System.out.println("Left expression: " + left +
	 * ", Right expression: " + right); ex = getOperator(op).assemble(left, right);
	 * System.out.println("New expression: " + ex); exs.push(ex); }
	 * 
	 * // check for extra opening parenthesis and remove it if (stack.size() > 0 &&
	 * stack.peek().equals("(")) { stack.pop(); System.out.println("Current stack: "
	 * + stack); }
	 * 
	 * // apply final unary operators exs.push(popUOps(stack, exs.pop())); }
	 * 
	 * private T popUOps(Stack<String> stack, T ex) {
	 * System.out.println(" --- BEGIN PUO --- ");
	 * System.out.println("Current stack: " + stack); while (stack.size() > 0 &&
	 * !stack.peek().equals("(") && containsOperator(stack.peek())) {
	 * System.out.println("Current stack: " + stack); String op = stack.pop();
	 * System.out.println("Popped unary operator " + op); switch (op) { case "~":
	 * System.out.println(ex + " is now " + ex.not()); ex = ex.not(); break; } }
	 * System.out.println(" ---- END PUO ---- "); return ex; }
	 */

}
