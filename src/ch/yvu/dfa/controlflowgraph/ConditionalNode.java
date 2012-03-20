package ch.yvu.dfa.controlflowgraph;

import java.util.Set;

import ch.yvu.dfa.analysis.AnalysisStrategy;
import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.Variable;

public class ConditionalNode extends Node{

	private Expression condition;
	
	public ConditionalNode(int id, Expression condition){
		super(id);
		this.condition = condition;
	}
	
	@Override
	public void kill(Set<Expression> outgoing, Set<Expression> incoming, AnalysisStrategy strategy){
		strategy.kill(this, outgoing, incoming);
	}
	
	@Override
	public void gen(Set<Expression> outgoing, Set<Expression> incoming, AnalysisStrategy strategy){
		strategy.gen(this, outgoing, incoming);
	}

	@Override
	public Expression getExpression() {
		return condition;
	}

	@Override
	public String getStatement() {
		return condition.getExpression();
	}
	
	public Set<Variable> getFreeVariables(){
		return this.condition.getFreeVariables();
	}
}
