package ch.yvu.dfa.expressions.analysis;

import ch.yvu.dfa.expressions.statements.Expression;
import ch.yvu.dfa.expressions.statements.Variable;

public class AnalysisNodeExpression extends AnalysisExpression {
	
	private Expression expression;
	
	public AnalysisNodeExpression(Expression expression){
		this.expression = expression;
	}

	@Override
	public boolean containsVariable(Variable variable) {
		return this.expression.containsVariable(variable.getExpression());
	}

	@Override
	public String getExpression() {
		return this.expression.getExpression();
	}

}
