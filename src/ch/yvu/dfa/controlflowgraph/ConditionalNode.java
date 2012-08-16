package ch.yvu.dfa.controlflowgraph;

import java.util.HashSet;
import java.util.Set;

import ch.yvu.dfa.analysis.AnalysisStrategy;
import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.Variable;
import ch.yvu.dfa.expressions.Operation;

public class ConditionalNode extends Node{

	private Expression rhs;
	private Expression lhs;
	private String operator;
	
	
	public ConditionalNode(int id, Expression lhs, Expression rhs, String operator){
		super(id);
		this.rhs = rhs;
		this.lhs = lhs;
		this.operator = operator;
	}
	
	@Override
	public void applyStatement(Set<Expression> incoming,
			Set<Expression> outgoing, AnalysisStrategy strategy) {
		strategy.applyStatement(this, incoming, outgoing);
	}

	@Override
	public Expression getExpression() {
		return new Operation(lhs, rhs, this.operator);
	}

	@Override
	public String getStatement() {
		return lhs.getExpression() + " " + operator + " " + rhs.getExpression();
	}
	
	public Set<Variable> getFreeVariables(){
		Set<Variable> freeVariables = new HashSet<Variable>();
		freeVariables.addAll(this.rhs.getFreeVariables());
		freeVariables.addAll(this.lhs.getFreeVariables());
		return freeVariables;
	}
}