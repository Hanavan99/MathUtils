package mathutils.test;

import java.util.ArrayList;
import java.util.HashMap;

import mathutils.expression.Add;
import mathutils.expression.Constant;
import mathutils.expression.Integral;
import mathutils.expression.MathExpression;
import mathutils.expression.Multiply;
import mathutils.expression.Variable;
import mathutils.logic.BoolInput;
import mathutils.logic.SequentProver;
import mathutils.number.Number;
import mathutils.number.WholeNumber;

public class Test {

	public static void main(String[] args) {
		testLogic();
		// testMath();
	}

	public static void testLogic() {
		BoolInput p = new BoolInput('p');
		BoolInput q = new BoolInput('q');
		BoolInput r = new BoolInput('r');
		BoolInput s = new BoolInput('s');

		SequentProver sp = new SequentProver();
		sp.setPremises(p.and(q).and(r).or(s));
		sp.setConclusion(p.or(s).and(q.or(s)).and(r.or(s)));
		System.out.println(sp.getProof());
	}

	public static void testMath() {
		HashMap<Character, Number> vars = new HashMap<Character, Number>();
		vars.put('x', new WholeNumber(5));

		MathExpression sum = new Add(new Constant(3), new Multiply(new Constant(2), new Variable('x')));
		System.out.println(sum);
		System.out.println(sum.toString(vars));
		sum = new Integral(new Variable('x'), 'x');
		System.out.println(sum);
		ArrayList<MathExpression> simplify = reduce(sum, vars);
		int i = 1;
		for (MathExpression ex : simplify) {
			System.out.println(i++ + ": " + ex);
		}
	}

	public static ArrayList<MathExpression> reduce(MathExpression expression, HashMap<Character, Number> vars) {
		ArrayList<MathExpression> steps = new ArrayList<MathExpression>();
		steps.add(expression);
		MathExpression last = null;
		MathExpression current = expression.clone();
		while (current != null && !current.equals(last) && !(current instanceof Constant)) {
			current = current.simplify(vars);
			steps.add(current);
			last = current;
		}
		return steps;
	}

}
