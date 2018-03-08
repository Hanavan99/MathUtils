package mathutils.logic;

public class Deduction {

	public enum DedRule {

		PREMISE("premise", 0), ASSUME("assume", 0), AND_INTRO("^i", 3), AND_ELIM1("^e1", 1), AND_ELIM2("^e2",
				1), OR_INTRO_1("Vi1", 1), OR_INTRO_2("Vi2", 2), OR_ELIM("Ve",
						3), IMPLIES_INTRO("->i", 1), IMPLIES_ELIM("->e", 1), NOT_ELIM("~e", 2), BOTTOM_ELIM("_|_e", 1);

		private String ruleName;
		private int args;

		private DedRule(String ruleName, int args) {
			this.ruleName = ruleName;
			this.args = args;
		}

		public String getRuleName() {
			return ruleName;
		}

		public int getArgCount() {
			return args;
		}
	}

	private DedRule rule;
	private LogicExpression[] steps;
	private Proof[] psteps;

	public Deduction(DedRule rule) {
		this.rule = rule;
		this.steps = null;
		this.psteps = null;
	}

	public Deduction(DedRule rule, LogicExpression... steps) {
		this.rule = rule;
		this.steps = steps;
		this.psteps = null;
	}

	public Deduction(DedRule rule, Proof... psteps) {
		this.rule = rule;
		this.steps = null;
		this.psteps = psteps;
	}

	public DedRule getRule() {
		return rule;
	}

	public LogicExpression[] getSteps() {
		return steps;
	}

	public Proof[] getProofSteps() {
		return psteps;
	}

}
