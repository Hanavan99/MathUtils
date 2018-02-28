package mathutils.logic;

import mathutils.logic.Deduction.DedRule;

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

	/**
	 * Generates a string that has all of the deduction steps required to prove the
	 * conclusion using the premises. The outputted format is in Logika format, so
	 * taking the output directly into Logika requires no modification. By default,
	 * this string only uses ASCII characters, but if the unicode symbols are
	 * desired, use {@link LogicExpression#toUnicode(String)}.
	 * 
	 * @return the proof as a string
	 */
	public String printProof() {
		StringBuilder sb = new StringBuilder();
		Proof proof = new Proof();

		// print all sequents and conclusion, try to break them apart
		for (LogicExpression premise : premises) {
			sb.append(premise + ", ");
			proof.addStep(premise, new Deduction(DedRule.PREMISE));
		}
		for (LogicExpression premise : premises) {
			LogicExpression current = premise.clone();
			breakDown(proof, current);
		}
		solve(proof, conclusion);
		sb.setLength(sb.length() - 2);
		sb.append(" " + LogicExpression.ASCII_TURNSTILE + " " + conclusion + "\n{\n");

		// iterate through steps and print them
		sb.append(proof.toString(proof.toMap(), 2));
		sb.append("}\n");
		return sb.toString();
	}

	/**
	 * Takes some expression and tries to construct new knowledge given previous
	 * knowledge.
	 * 
	 * @param proof
	 *            the current proof object keeping track of all the deduction steps
	 * @param current
	 *            the logic expression to be used in finding new knowledge
	 * @return whether or not it was successful in determining new knowledge
	 */
	private boolean solve(Proof proof, LogicExpression current) {
		if (!(current instanceof Input)) {
			for (LogicExpression ex : current) {
				buildUp(proof, ex);
			}
		}
		return buildUp(proof, current);
	}

	private boolean buildUp(Proof proof, LogicExpression ex) {
		if (!(ex instanceof Input)) {
			if (ex instanceof BinaryOperator) {
				BinaryOperator bo = (BinaryOperator) ex;
				if (bo instanceof And && proof.contains(bo.getLeft()) && proof.contains(bo.getRight())) {
					LogicExpression step = new And(bo.getLeft(), bo.getRight());
					if (!proof.contains(step)) {
						proof.addStep(step, new Deduction(DedRule.AND_INTRO, bo.getLeft(), bo.getRight()));
					}
					boolean success = false;
					for (LogicExpression premise : premises) {
						success |= breakDown(proof, premise);
					}
					return success;
				} else if (bo instanceof Or) {
					if (proof.contains(bo.getLeft())) {
						LogicExpression step = new Or(bo.getLeft(), bo.getRight());
						if (!proof.contains(step)) {
							proof.addStep(step, new Deduction(DedRule.OR_INTRO_1, bo.getLeft()));
						}
					} else if (proof.contains(bo.getRight())) {
						LogicExpression step = new Or(bo.getLeft(), bo.getRight());
						if (!proof.contains(step)) {
							proof.addStep(step, new Deduction(DedRule.OR_INTRO_2, bo.getRight()));
						}
					} else {
						Proof subProof1 = new Proof();
						subProof1.addStep(bo.getLeft(), new Deduction(DedRule.ASSUME));
						solve(subProof1, bo.getLeft());
						proof.addProof(subProof1);
						Proof subProof2 = new Proof();
						subProof2.addStep(bo.getRight(), new Deduction(DedRule.ASSUME));
						// 913-333-6604
						solve(subProof2, bo.getRight());
						proof.addProof(subProof2);
						LogicExpression last1 = subProof1.getLastProof().getStep();
						LogicExpression last2 = subProof2.getLastProof().getStep();
						if (last1.equals(last2)) {
							proof.addStep(last1, new Deduction(DedRule.OR_ELIM, bo));
						}
					}
				}
			}

		} else {
			for (LogicExpression ex2 : premises) {
				if (!(ex2 instanceof Input)) {
					for (LogicExpression ex3 : ex2) {
						buildUp2(proof, ex, ex3);
					}
				} else {
					buildUp2(proof, ex, ex2);
				}
			}
		}
		return false;
	}

	public boolean buildUp2(Proof proof, LogicExpression a, LogicExpression b) {
		if (proof.contains(a) && proof.contains(b) && !a.equals(b)) {
			LogicExpression newle = new And(a, b);
			proof.addProof(new Proof(newle, new Deduction(DedRule.AND_INTRO, a, b)));
			solve(proof, newle);
		}
		return true;
	}

	/**
	 * Breaks down the current logic expression into as small of pieces as possible.
	 * 
	 * @param proof
	 *            the current proof object keeping track of all the deduction steps
	 * @param current
	 *            the logic expression to break down
	 * @return whether or not any new information was deduced
	 */
	public boolean breakDown(Proof proof, LogicExpression current) {
		if (current instanceof Input) {
			return true;
		}
		if (current instanceof BinaryOperator) {
			BinaryOperator bo = (BinaryOperator) current;
			if (current instanceof And) {
				proof.addStep(bo.getLeft(), new Deduction(DedRule.AND_ELIM1, current));
				proof.addStep(bo.getRight(), new Deduction(DedRule.AND_ELIM2, current));
				return breakDown(proof, bo.getLeft()) && breakDown(proof, bo.getRight());
			} else if (current instanceof Implies && proof.contains(bo.getLeft())) {
				proof.addStep(bo.getRight(), new Deduction(DedRule.IMPLIES_ELIM, current, bo.getLeft()));
				return breakDown(proof, bo.getRight());
			}
		}
		return false;
	}

}
