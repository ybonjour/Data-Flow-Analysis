package ch.yvu.dfa.expressions.statements;

import java.util.HashSet;
import java.util.Set;

public class Operation implements Expression {

	private Expression leftOperand;
	private Expression rightOperand;
	private String operation;
	
	public Operation(Expression leftOperand, Expression rightOperand, String operation){
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		this.operation = operation;
	}
	
	public boolean containsVariable(String name){		
		return this.leftOperand.containsVariable(name) || this.rightOperand.containsVariable(name);
	}

	@Override
	public String getExpression() {
		return this.leftOperand.getExpression() + " " + this.operation + " " + this.rightOperand.getExpression();
	}

	@Override
	public Set<Variable> getFreeVariables() {
		HashSet<Variable> freeVariables = new HashSet<Variable>();
		freeVariables.addAll(this.leftOperand.getFreeVariables());
		freeVariables.addAll(this.rightOperand.getFreeVariables());
		return freeVariables;
	}

	@Override
	public boolean isComposed() {
		return true;
	}
}
