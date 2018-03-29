package mathutils.logic;

import java.util.Stack;

import mathutils.logic.Deduction.DedRule;
import mathutils.logic.operators.And;
import mathutils.logic.operators.Implies;
import mathutils.logic.operators.Not;
import mathutils.logic.operators.Or;

/**
 * This class uses natural deduction to determine if either two sequents are
 * logically equivalent, or if given enough premises, a result can be deduced
 * given the information.
 * 
 * @author Hanavan Kuhn
 *
 */
public class SequentProver {

	private LogicExpression[] premises;
	private LogicExpression conclusion;
	private int proofMode = Proof.MODE_TEXT;

	/**
	 * Creates a new {@code SequentProver} object with no premises or conclusion.
	 */
	public SequentProver() {
		premises = null;
		conclusion = null;
	}

	/**
	 * Constructs a new {@code SequentProver} object with the given premises and
	 * conclusion.
	 * 
	 * @param premises
	 *            the premises used in the proof
	 * @param conclusion
	 *            the conclusion to be proven using the premises
	 */
	public SequentProver(LogicExpression[] premises, LogicExpression conclusion) {
		this.premises = premises;
		this.conclusion = conclusion;
	}

	/**
	 * Sets the premises to be used in the proof.
	 * 
	 * @param premises
	 *            the premises
	 */
	public void setPremises(LogicExpression... premises) {
		this.premises = premises;
	}

	/**
	 * Sets the conclusion to be proven using the premises.
	 * 
	 * @param conclusion
	 *            the conclusion
	 */
	public void setConclusion(LogicExpression conclusion) {
		this.conclusion = conclusion;
	}

	public void setMode(int proofMode) {
		this.proofMode = proofMode;
	}

	/**
	 * Quick way to set the premises and conclusion in one method call.
	 * 
	 * @param conclusion
	 *            the conclusion
	 * @param premises
	 *            the premises
	 */
	public void set(LogicExpression conclusion, LogicExpression... premises) {
		setConclusion(conclusion);
		setPremises(premises);
	}

	public boolean hasProof() {
		Proof proof = new Proof();
		for (LogicExpression premise : premises) {
			proof.addStep(premise, new Deduction(DedRule.PREMISE));
		}
		for (LogicExpression premise : premises) {
			if (prove(proof, proof, premise, conclusion)) {
				return true;
			}
		}
		return false;
	}

	public String getProof() {
		StringBuilder sb = new StringBuilder();
		Proof proof = new Proof();

		// go through each premise and print it
		for (LogicExpression premise : premises) {
			sb.append(premise + ", ");
			proof.addStep(premise, new Deduction(DedRule.PREMISE));
		}
		sb.setLength(sb.length() - 2);
		sb.append(" |- " + conclusion + "\n{\n");

		// go through each premise and try to break it down
		for (LogicExpression premise : premises) {
			if (prove(proof, proof, premise, conclusion)) {
				break;
			}
		}

		// convert proof to string and return
		sb.append(proof.toString(proof.toMap(), 2, proofMode) + "}");
		String result = sb.toString();
		if (proofMode == Proof.MODE_HTML) {
			result = result.replaceAll(" ", "&nbsp;").replaceAll("\n", "\n<br />");
		}
		return result;
	}

	public boolean prove(LogicExpression start, LogicExpression end) {
		Proof proof = new Proof();
		return prove(proof, proof, start, end);
	}

	/**
	 * Tries to prove that the logic expression {@code assume} is logically
	 * equivalent to {@code conclusion}.
	 * 
	 * @param proof
	 *            the main proof that contains all of the deduction steps
	 * @param subProof
	 *            the current subProof to add steps to, or if proving the main proof
	 *            pass in the same value for {@code proof}
	 * @param start
	 *            the assumption to be made, premise, or current knowledge
	 * @param end
	 *            the end result we want
	 * @return whether or not the logical equivalence between the assumption and
	 *         conclusion was provable
	 */
	public boolean prove(Proof proof, Proof subProof, LogicExpression start, LogicExpression end) {
		// check if we already have that knowledge
		if (proof.contains(end)) {
			if (!subProof.contains(end)) {
				subProof.addProof(proof.getProof(end).createSimilar());
			}
			return true;
		} else if (subProof.contains(end)) {
			return true;
		} else if (start != null && start.equals(end)) {
			return true;
		}

		// first try to break down the assumption
		if (start instanceof BinaryOperator) {
			BinaryOperator bo = (BinaryOperator) start;
			if (bo instanceof And) {

				// break apart the end and try to prove the conclusion with them
				subProof.addProof(subProof.createProofWithID(bo.getLeft(), DedRule.AND_ELIM1, bo));
				subProof.addProof(subProof.createProofWithID(bo.getRight(), DedRule.AND_ELIM2, bo));
				return (prove(proof, subProof, bo.getLeft(), end) || prove(proof, subProof, bo.getRight(), end));
			} else if (bo instanceof Or) {

				// try to prove end with the left and right sides
				Proof sub1 = Proof.createSubProof();
				sub1.addProof(sub1.createProofWithID(bo.getLeft(), DedRule.ASSUME));
				Proof sub2 = Proof.createSubProof();
				sub2.addProof(sub2.createProofWithID(bo.getRight(), DedRule.ASSUME));

				// attempt to prove them and if successful then add them to the final proof
				if (prove(proof, sub1, bo.getLeft(), end) && prove(proof, sub2, bo.getRight(), end)) {
					subProof.addProof(sub1);
					subProof.addProof(sub2);
					subProof.addProof(
							new Proof(end, new Deduction(DedRule.OR_ELIM, subProof.getProof(bo), sub1, sub2)));
					return true;
				}
			} else if (bo instanceof Implies && prove(proof, subProof, null, bo.getLeft())) {
				subProof.addProof(subProof.createProofWithID(bo.getRight(), DedRule.IMPLIES_ELIM, bo, bo.getLeft()));
				return prove(proof, subProof, bo.getRight(), end);
			}
		} else if (start instanceof UnaryOperator) {
			UnaryOperator uo = (UnaryOperator) start;
			if (uo instanceof Not) {
				if (proof.contains(uo.getExpression()) || subProof.contains(uo.getExpression())) {
				subProof.addProof(subProof.createProofWithID(new Bottom(), DedRule.NOT_ELIM, uo.getExpression(), uo));
				return prove(proof, subProof, new Bottom(), end);
				}
			}
		} else if (start instanceof Bottom) {

			// eliminate bottom by proving the end
			subProof.addProof(new Proof(end, new Deduction(DedRule.BOTTOM_ELIM, new Bottom())));
			return true;
		}

		// now try to build up the conclusion
		if (end instanceof BinaryOperator) {
			BinaryOperator bo2 = (BinaryOperator) end;
			if (bo2 instanceof And) {
				if (prove(proof, subProof, start, bo2.getLeft()) && prove(proof, subProof, start, bo2.getRight())) {
					subProof.addProof(
							subProof.createProofWithID(bo2, DedRule.AND_INTRO, bo2.getLeft(), bo2.getRight()));
					return prove(proof, subProof, bo2, end);
				}
			} else if (bo2 instanceof Or) {
				if (prove(proof, subProof, start, bo2.getLeft())) {
					subProof.addProof(subProof.createProofWithID(bo2, DedRule.OR_INTRO_1, bo2.getLeft()));
					return prove(proof, subProof, bo2.getLeft(), end);
				} else if (prove(proof, subProof, start, bo2.getRight())) {
					subProof.addProof(subProof.createProofWithID(bo2, DedRule.OR_INTRO_2, bo2.getRight()));
					return prove(proof, subProof, bo2.getRight(), end);
				}
			} else if (bo2 instanceof Implies) {
				Proof sub = Proof.createSubProof();
				sub.addProof(sub.createProofWithID(bo2.getLeft(), DedRule.ASSUME));
				if (prove(proof, sub, bo2.getLeft(), bo2.getRight())) {
					subProof.addProof(sub);
					subProof.addProof(new Proof(bo2, new Deduction(DedRule.IMPLIES_INTRO, sub)));
					return prove(proof, subProof, bo2, end);
				}
			}
		} /*
			 * else if (end instanceof UnaryOperator) { UnaryOperator uo2 = (UnaryOperator)
			 * end; if (uo2 instanceof Not) { Proof sub = new Proof(); sub.addProof(new
			 * Proof(uo2.getExpression(), new Deduction(DedRule.ASSUME))); if (prove(proof,
			 * sub, uo2.getExpression(), new Bottom())) { subProof.addProof(sub);
			 * subProof.addProof(new Proof(uo2, new Deduction(DedRule.NOT_INTRO, sub)));
			 * return prove(proof, subProof, uo2, end); } } }
			 */

		// try this as a last resort
		if (start instanceof BoolInput) {
			// see if there is anything we can do
			if (subProof.contains(start.not())) {
				subProof.addProof(subProof.createProofWithID(new Bottom(), DedRule.NOT_ELIM, start, start.not()));
				return prove(proof, subProof, new Bottom(), end);
			}

			// as a last resort, try to see if there is anything we can break down from here
			for (Proof p : proof) {
				LogicExpression ex = p != null ? p.getStep() : null;
				if (ex != null) {
					if (ex instanceof BinaryOperator) {
						BinaryOperator bo2 = (BinaryOperator) ex;
						if (bo2 instanceof Implies && prove(proof, subProof, null, bo2.getLeft())
								&& !subProof.contains(bo2.getRight())) {
							subProof.addProof(subProof.createProofWithID(bo2.getRight(), DedRule.IMPLIES_ELIM, bo2,
									bo2.getLeft()));
							return prove(proof, subProof, bo2.getRight(), end);
						}
					} else if (ex instanceof UnaryOperator) {
						UnaryOperator uo2 = (UnaryOperator) ex;
						if (uo2 instanceof Not
								&& (proof.contains(uo2.getExpression()) || subProof.contains(uo2.getExpression()))) {
							subProof.addProof(
									new Proof(new Bottom(), new Deduction(DedRule.NOT_ELIM, uo2.getExpression(), uo2)));
							return prove(proof, subProof, new Bottom(), end);
						}
					} else if (ex instanceof BoolInput) {
						Proof sub = Proof.createSubProof();
						sub.addProof(sub.createProofWithID(ex.not(), DedRule.ASSUME));
						if (prove(proof, sub, ex.not(), new Bottom())) {
							subProof.addProof(sub);
							subProof.addProof(new Proof(ex, new Deduction(DedRule.PBC, sub)));
						}
						return prove(proof, subProof, ex, end);
					}
				}

			}

		}

		return false;
	}

	/**
	 * Parses a logic expression from a string format.
	 * 
	 * @param str
	 *            the string to parse
	 * @return the new logic expression
	 */
	public LogicExpression parse(String str) {
		System.out.println(" --- BEGIN PARSE --- ");

		// get rid of extra spaces
		str = str.replace(" ", "");

		char[] charstr = str.toCharArray();
		char cur = 0;

		Stack<String> ustack = new Stack<String>();
		Stack<String> stack = new Stack<String>();
		Stack<LogicExpression> exs = new Stack<LogicExpression>();

		// loop through each character
		for (int i = 0; i < charstr.length; i++) {
			cur = charstr[i];
			if (cur >= 'a' && cur <= 'z') {
				exs.push(popUOps(stack, new BoolInput(cur)));
			} else if (cur == '^' || cur == 'V' || cur == '(') {
				stack.push(String.valueOf(cur));
			} else if (cur == '~') {
				stack.push(String.valueOf(cur));
			} else if (cur == '-' && i < charstr.length - 1 && charstr[i + 1] == '>') {
				stack.push("->");
			} else if (cur == ')') {
				popOps(stack, ustack, exs);
			}
		}
		popOps(stack, ustack, exs);
		LogicExpression ex = exs.pop();
		System.out.println("-> " + ex);

		System.out.println(" ---- END PARSE ---- ");

		return ex;
	}

	private static void popOps(Stack<String> stack, Stack<String> ustack, Stack<LogicExpression> exs) {
		LogicExpression left = null;
		LogicExpression right = null;
		System.out.println("Current stack: " + stack);
		while (stack.size() > 0 && !stack.peek().equals("(")) {
			String op = stack.pop();
			System.out.println("Popped operator " + op);
			LogicExpression ex = null;
			right = exs.pop();
			left = exs.pop();
			System.out.println("Left expression: " + left + ", Right expression: " + right);
			switch (op) {
			case "^":
				ex = new And(left, right);
				break;
			case "V":
				ex = new Or(left, right);
				break;
			case "->":
				ex = new Implies(left, right);
				break;
			}
			System.out.println("New expression: " + ex);
			exs.push(ex);
		}

		// check for extra opening parenthesis and remove it
		if (stack.size() > 0 && stack.peek().equals("(")) {
			stack.pop();
			System.out.println("Current stack: " + stack);
		}

		// apply final unary operators
		exs.push(popUOps(stack, exs.pop()));
	}

	private static LogicExpression popUOps(Stack<String> stack, LogicExpression ex) {
		System.out.println(" --- BEGIN PUO --- ");
		System.out.println("Current stack: " + stack);
		while (stack.size() > 0 && !stack.peek().equals("(") && stack.peek().equals("~")) {
			System.out.println("Current stack: " + stack);
			String op = stack.pop();
			System.out.println("Popped unary operator " + op);
			switch (op) {
			case "~":
				System.out.println(ex + " is now " + ex.not());
				ex = ex.not();
				break;
			}
		}
		System.out.println(" ---- END PUO ---- ");
		return ex;
	}

}
