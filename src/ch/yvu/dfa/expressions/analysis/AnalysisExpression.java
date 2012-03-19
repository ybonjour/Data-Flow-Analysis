package ch.yvu.dfa.expressions.analysis;

import ch.yvu.dfa.expressions.statements.Variable;

public abstract class AnalysisExpression {

	public abstract boolean containsVariable(Variable variable);
	
	public abstract String getExpression();
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof AnalysisExpression)) return false;
		AnalysisExpression expression = (AnalysisExpression) obj;
		return this.getExpression().equals(expression.getExpression());
	}
	
	@Override
	public int hashCode() {
		return getExpression().hashCode();
	}
}
