package mathutils.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import mathutils.util.UnhashMap;

/**
 * Stores all of the steps and deduction rules used to prove that two sequents
 * are logically equivalent. Proof objects represent trees that can have
 * sub-proofs.
 * 
 * @author Hanavan Kuhn
 *
 */
public class Proof implements Iterable<Proof>, Cloneable {

	private ArrayList<Proof> proofs = new ArrayList<Proof>();
	private LogicExpression step;
	private Deduction rule;
	private int id = 0;

	/**
	 * Constructs a new proof. Proofs constructed this way act as tree nodes.
	 */
	public Proof() {
		step = null;
		rule = null;
	}

	/**
	 * Constructs a new proof. Proofs constructed this way act as tree leaves,
	 * meaning they do not have any children.
	 * 
	 * @param step
	 *            The logic expression used in this step
	 * @param rule
	 *            The deduction rule (reasoning) used to deduce this step
	 */
	public Proof(LogicExpression step, Deduction rule) {
		this.step = step;
		this.rule = rule;
	}
	
	private Proof(LogicExpression step, Deduction rule, int id) {
		this(step, rule);
		this.id = id;
	}
	
	public Proof createSimilar() {
		return new Proof(step, rule, id + 1);
	}

	/**
	 * Adds a step to this proof. Short for {@code addProof(new Proof(step, rule))}.
	 * 
	 * @param step
	 *            the logic expression to add as the step
	 * @param rule
	 *            the rule used to deduce this step
	 */
	public void addStep(LogicExpression step, Deduction rule) {
		if (!contains(step)) {
			proofs.add(new Proof(step, rule));
		}
	}

	/**
	 * Determines whether or not a given expression is contained in this proof. This
	 * method recursively searches through every sub-proof to find a match, not just
	 * the top level. This method only looks for the equivalence of two expressions
	 * only, ignoring the deduction type used to deduce the expression.
	 * 
	 * @param step
	 *            the logic expression to look for
	 * @return whether or not this proof contains this expression
	 */
	public boolean contains(LogicExpression step) {
		Map<Proof, Integer> map = toMap();
		for (Proof p : map.keySet()) {
			if (p != null && p.isLeaf() && p.getStep().equals(step)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tries to find a sub-proof that has the specified start and end expressions.
	 * 
	 * @param start
	 *            the starting expression
	 * @param end
	 *            the ending expression
	 * @return the sub proof, or {@code null} if none was found
	 */
	public Proof getProof(LogicExpression start, LogicExpression end) {
		Map<Proof, Integer> map = toMap();
		for (Proof p : map.keySet()) {
			if (p != null && !p.isLeaf() && p.getFirstProof().getStep().equals(start)
					&& p.getLastProof().getStep().equals(end)) {
				return p;
			}
		}
		return null;
	}

	public Proof getProof(LogicExpression step) {
		Map<Proof, Integer> map = toMap();
		for (Proof p : map.keySet()) {
			if (p != null && p.isLeaf() && p.getStep().equals(step)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Adds a proof to this {@code Proof} object.
	 * 
	 * @param p
	 *            the proof to add
	 */
	public void addProof(Proof p) {
		proofs.add(p);
	}

	/**
	 * If this {@code Proof} is a leaf, this method returns the logic expression in
	 * the proof.
	 * 
	 * @return the logic expression of this proof
	 */
	public LogicExpression getStep() {
		return step;
	}

	/**
	 * If this {@code Proof} is a leaf, this method returns the deduction used to
	 * determine the logic expression.
	 * 
	 * @return the deduction rule of this proof
	 */
	public Deduction getRule() {
		return rule;
	}

	/**
	 * Whether or not this proof contains only one step, which is just considered a
	 * single entity, rather than a sub-proof. This is done by comparing
	 * {@code step != null}.
	 * 
	 * @return whether or not this proof is a leaf (meaning it is not a sub-proof)
	 */
	public boolean isLeaf() {
		return step != null;
	}

	@Override
	public Iterator<Proof> iterator() {
		return isLeaf() ? new Iterator<Proof>() {

			boolean done = false;

			@Override
			public boolean hasNext() {
				return !done;
			}

			@Override
			public Proof next() {
				done = true;
				return Proof.this;
			}

		} : new Iterator<Proof>() {

			private boolean meDone = false;
			private int index = 0;
			private Iterator<Proof> curItr;

			@Override
			public boolean hasNext() {
				return index < proofs.size() || isLeaf();
			}

			@Override
			public Proof next() {
				if (!meDone) {
					meDone = true;
					return Proof.this;
				}
				while (hasNext()) {
					if (curItr == null) {
						curItr = proofs.get(index).iterator();
					}
					if (curItr.hasNext()) {
						return curItr.next();
					} else {
						curItr = null;
						index++;
					}
				}
				return null;
			}

		};
	}

	/**
	 * Recursively converts the entire proof into a {@code UnhashMap} object that
	 * contains every proof contained by this {@code Proof} object. The proofs are
	 * numbered for use when converting the proofs into text form.
	 * 
	 * @return the map containing all of the steps
	 */
	public Map<Proof, Integer> toMap() {
		UnhashMap<Proof, Integer> map = new UnhashMap<Proof, Integer>();
		int index = 0;
		for (Proof p : this) {
			if (p != null && !map.containsKey(p)) {
				map.put(p, index++);
			}
		}
		return map;
	}

	/**
	 * Gets the first {@code Proof} object in this proof. If it is a leaf,
	 * {@code this} is returned, and if not, the first {@code Proof} contained by
	 * this proof is returned.
	 * 
	 * @return the first proof in this {@code Proof}
	 */
	public Proof getFirstProof() {
		return isLeaf() ? this : proofs.get(0);
	}

	/**
	 * Gets the last {@code Proof} object in this proof. If it is a leaf,
	 * {@code this} is returned, and if not, the last {@code Proof} contained by
	 * this proof is returned.
	 * 
	 * @return the last proof in this {@code Proof}
	 */
	public Proof getLastProof() {
		return isLeaf() ? this : proofs.get(proofs.size() - 1);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Proof) {
			if (isLeaf()) {
				return ((Proof) other).isLeaf() && ((Proof) other).step.equals(step) && ((Proof) other).id == id;
			} else {
				return ((Proof) other).proofs != null && ((Proof) other).proofs.equals(proofs);
			}
		}
		return false;
	}

	/**
	 * Converts this {@code Proof} object into a string format. This format is
	 * directly usable in Logika. <b>Note:</b> the default {@code toString()} method
	 * is not overridden. This is because converting a sub-proof to a string would
	 * not be possible without knowledge of its parent's proofs.
	 * 
	 * @param map
	 *            the map that stores the numbered proof steps
	 * @param indent
	 *            the starting indent of the numbered proofs
	 * @return a string representation of this {@code Proof} object.
	 */
	public String toString(Map<Proof, Integer> map, int indent) {
		if (isLeaf()) {
			String str = spacer(indent) + map.get(this) + ". " + step;
			String rstr = rule.getRule().getRuleName();
			if (rule.getSteps() != null) {
				for (LogicExpression s : rule.getSteps()) {
					rstr += " " + map.get(new Proof(s, null));
				}
			} else if (rule.getProofSteps() != null) {
				for (Proof p : rule.getProofSteps()) {
					rstr += " " + map.get(p);
				}
			}
			return str + spacer(50 - str.length()) + rstr;
		}
		StringBuilder sb = new StringBuilder();
		for (Proof p : proofs) {
			if (p.isLeaf()) {
				sb.append(p.toString(map, indent) + "\n");
			} else {
				sb.append(spacer(indent) + map.get(p) + ". {\n");
				sb.append(p.toString(map, indent + 5) + "\n");
				sb.setLength(sb.length() - 1);
				sb.append(spacer(indent + 3) + "}\n");
			}
		}
		return sb.toString();
	}

	public void removeDuplicates() {

	}

	/**
	 * Recursively creates a string with {@code length} number of spaces in it.
	 * 
	 * @param length
	 *            the number of spaces to create the string with
	 * @return the string
	 */
	private String spacer(int length) {
		if (length <= 0)
			return "";
		return " " + spacer(length - 1);
	}

}
