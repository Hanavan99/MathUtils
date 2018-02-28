package mathutils.logic;

public class Deduction {

	public enum DedRule {

		PREMISE("premise", 0), ASSUME("assume", 0), AND_INTRO("^i", 3), AND_ELIM1("^e1", 1), AND_ELIM2("^e2",
				1), OR_INTRO_1("Vi1", 1), OR_INTRO_2("Vi2", 2), OR_ELIM("Ve", 3), IMPLIES_ELIM("->e", 1);

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

	public Deduction(DedRule rule, LogicExpression... steps) {
		this.rule = rule;
		this.steps = steps;
	}

	public DedRule getRule() {
		return rule;
	}

	public LogicExpression[] getSteps() {
		return steps;
	}

}
