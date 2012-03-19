package ch.yvu.dfa.controlflowgraph;

import java.util.Set;
import ch.yvu.dfa.analysis.AnalysisStrategy;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;
import ch.yvu.dfa.expressions.statements.Expression;
import ch.yvu.dfa.expressions.statements.Variable;

public class AssignmentNode extends Node {

	private Variable lhs;
	private Expression rhs;
	
	public AssignmentNode(int id, Variable lhs, Expression rhs){
		super(id);
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	@Override
	public void kill(Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming, AnalysisStrategy strategy){
		strategy.kill(this, outgoing, incoming);
	}
	
	@Override
	public void gen(Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming, AnalysisStrategy strategy){
		strategy.gen(this, outgoing, incoming);
	}

	@Override
	public Expression getExpression() {
		return this.rhs;
	}

	public Variable getLhs() {
		return lhs;
	}

	public Expression getRhs() {
		return rhs;
	}

	public boolean isComposedExpression(){
		return this.rhs.isComposed();
	}
	
	@Override
	public String getStatement() {
		return this.lhs.getExpression() + ":=" + this.rhs.getExpression();
	}
	
	public Set<Variable> getFreeVariablesRhs(){
		return this.rhs.getFreeVariables();
	}
	
	public boolean containsRightHandSideVariableInLeftHandSide(){
		return this.rhs.containsVariable(this.lhs.getExpression());
	}
}
