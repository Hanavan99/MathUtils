package mathutils.logic;

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

	/**
	 * Generates a string that has all of the deduction steps required to prove the
	 * conclusion using the premises. The outputted format is in Logika format, so
	 * taking the output directly into Logika requires no modification. By default,
	 * this string only uses ASCII characters, but if the unicode symbols are
	 * desired, use {@link LogicExpression#toUnicode(String)}.
	 * 
	 * @return the proof as a string
	 */
	@Deprecated
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

		// remove duplicates
		proof.removeDuplicates();

		// convert proof to string and return
		sb.append(proof.toString(proof.toMap(), 2) + "}");
		return sb.toString();
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
			subProof.addProof(proof.getProof(end).createSimilar());
			return true;
		} else if (subProof.contains(end)) {
			subProof.addProof(subProof.getProof(end).createSimilar());
			return true;
		} else if (start.equals(end)) {
			return true;
		}

		// first try to break down the assumption
		if (start instanceof BinaryOperator) {
			BinaryOperator bo = (BinaryOperator) start;
			if (bo instanceof And) { // try and break apart the ^
				subProof.addProof(new Proof(bo.getLeft(), new Deduction(DedRule.AND_ELIM1, bo)));
				subProof.addProof(new Proof(bo.getRight(), new Deduction(DedRule.AND_ELIM2, bo)));
				return (prove(proof, subProof, bo.getLeft(), end) || prove(proof, subProof, bo.getRight(), end));
			} else if (bo instanceof Or) {
				Proof sub1 = new Proof();
				sub1.addProof(new Proof(bo.getLeft(), new Deduction(DedRule.ASSUME)).createSimilar());

				Proof sub2 = new Proof();
				sub2.addProof(new Proof(bo.getRight(), new Deduction(DedRule.ASSUME)).createSimilar());

				if (prove(proof, sub1, bo.getLeft(), end) && prove(proof, sub2, bo.getLeft(), end)) {
					subProof.addProof(sub1);
					subProof.addProof(sub2);
					subProof.addProof(new Proof(end, new Deduction(DedRule.OR_ELIM, proof.getProof(bo), sub1, sub2)));
					return true;
				}
			} else if (bo instanceof Implies && prove(proof, subProof, bo.getLeft(), bo.getLeft())) {
				subProof.addProof(new Proof(bo.getRight(), new Deduction(DedRule.IMPLIES_ELIM, bo, bo.getLeft())));
				return prove(proof, subProof, bo.getRight(), end);
			}
		} else if (start instanceof UnaryOperator) {
			UnaryOperator uo = (UnaryOperator) start;
			if (uo instanceof Not && (proof.contains(uo.getExpression()) || subProof.contains(uo.getExpression()))) {
				subProof.addProof(new Proof(new Bottom(), new Deduction(DedRule.NOT_ELIM, uo.getExpression(), uo)));
				return prove(proof, subProof, new Bottom(), end);
			}
		} else if (start instanceof BoolInput) {
			// try to see if there is anything we can break down from here

			for (Proof p : proof) {
				LogicExpression ex = p != null ? p.getStep() : null;
				if (ex != null) {
					if (ex instanceof BinaryOperator) {
						BinaryOperator bo2 = (BinaryOperator) ex;
						if (bo2 instanceof Implies && (proof.contains(bo2.getLeft()) || subProof.contains(bo2.getLeft())
								|| start.equals(bo2.getLeft()))) {
							subProof.addProof(
									new Proof(bo2.getRight(), new Deduction(DedRule.IMPLIES_ELIM, bo2, bo2.getLeft())));
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
					}
				}

			}

		} else if (start instanceof Bottom) {
			subProof.addProof(new Proof(end, new Deduction(DedRule.BOTTOM_ELIM, new Bottom())));
			return true;
		}

		// now try to build up the conclusion
		if (end instanceof BinaryOperator) {
			BinaryOperator bo2 = (BinaryOperator) end;
			if (bo2 instanceof And) {
				if (prove(proof, subProof, bo2.getLeft(), bo2.getLeft())
						&& prove(proof, subProof, bo2.getRight(), bo2.getRight())) {
					subProof.addProof(new Proof(bo2, new Deduction(DedRule.AND_INTRO, bo2.getLeft(), bo2.getRight())));
					return prove(proof, subProof, bo2, end);
				}
			} else if (bo2 instanceof Or) {
				if (proof.contains(bo2.getLeft()) || subProof.contains(bo2.getLeft())
						|| prove(proof, subProof, bo2.getLeft(), bo2.getLeft())) {
					subProof.addProof(new Proof(bo2, new Deduction(DedRule.OR_INTRO_1, bo2.getLeft())));
					return prove(proof, subProof, bo2.getLeft(), end);
				} else if (proof.contains(bo2.getRight()) || subProof.contains(bo2.getRight())
						|| prove(proof, subProof, bo2.getRight(), bo2.getRight())) {
					subProof.addProof(new Proof(bo2, new Deduction(DedRule.OR_INTRO_2, bo2.getRight())));
					return prove(proof, subProof, bo2.getRight(), end);
				}
			} else if (bo2 instanceof Implies) {
				Proof sub = new Proof();
				sub.addProof(new Proof(bo2.getLeft(), new Deduction(DedRule.ASSUME)).createSimilar());
				if (prove(proof, sub, bo2.getLeft(), bo2.getRight())) {
					subProof.addProof(sub);
					subProof.addProof(new Proof(bo2, new Deduction(DedRule.IMPLIES_INTRO, sub)));
					return prove(proof, subProof, bo2, end);
				}
			}
		} else if (end instanceof UnaryOperator) {
			UnaryOperator uo2 = (UnaryOperator) end;
			if (uo2 instanceof Not) {
				Proof sub = new Proof();
				sub.addProof(new Proof(uo2.getExpression(), new Deduction(DedRule.ASSUME)));
				if (prove(proof, sub, uo2.getExpression(), new Bottom())) {
					subProof.addProof(sub);
					subProof.addProof(new Proof(uo2, new Deduction(DedRule.NOT_INTRO, sub)));
					return prove(proof, subProof, uo2, end);
				}
			}
		}

		return false;
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
	@Deprecated
	private boolean solve(Proof proof, LogicExpression current) {
		if (!(current instanceof BoolInput)) {
			for (LogicExpression ex : current) {
				buildUp(proof, ex);
			}
		}
		return buildUp(proof, current);
	}

	@Deprecated
	private boolean buildUp(Proof proof, LogicExpression ex) {
		if (!(ex instanceof BoolInput)) {
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
				if (!(ex2 instanceof BoolInput)) {
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

	@Deprecated
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
	@Deprecated
	public boolean breakDown(Proof proof, LogicExpression current) {
		if (current instanceof Not && proof.contains(((Not) current).getExpression())) {
			proof.addProof(
					new Proof(new Bottom(), new Deduction(DedRule.NOT_ELIM, current, ((Not) current).getExpression())));
			proof.addProof(new Proof(conclusion, new Deduction(DedRule.BOTTOM_ELIM, new Bottom())));
			return true;
		}
		if (current instanceof BoolInput) {
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
