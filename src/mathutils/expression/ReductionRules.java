package mathutils.expression;

public class ReductionRules {

	private boolean reduceToDecimal = false;

	public ReductionRules() {
		
	}
	
	public ReductionRules(boolean reduceToDecimal) {
		this.reduceToDecimal = reduceToDecimal;
	}
	
	public boolean isReduceToDecimal() {
		return reduceToDecimal;
	}

	public void setReduceToDecimal(boolean reduceToDecimal) {
		this.reduceToDecimal = reduceToDecimal;
	}

}
