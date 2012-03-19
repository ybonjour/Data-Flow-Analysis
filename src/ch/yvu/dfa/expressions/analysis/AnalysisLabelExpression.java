package ch.yvu.dfa.expressions.analysis;

import ch.yvu.dfa.expressions.statements.Variable;

public class AnalysisLabelExpression extends AnalysisExpression{

	private int id;
	private Variable variable;
	
	public AnalysisLabelExpression(int id, Variable variable){
		this.id = id;
		this.variable = variable;
	}

	@Override
	public boolean containsVariable(Variable variable) {
		return this.variable.containsVariable(variable.getExpression());
	}

	@Override
	public String getExpression() {
		return "(" + this.variable.getExpression() + ", " + this.id + ")"; 
	}
}
