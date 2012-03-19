package ch.yvu.dfa.expressions.statements;

import java.util.Set;

public interface Expression {

	public boolean containsVariable(String name);
	
	public String getExpression();
	
	public Set<Variable> getFreeVariables();
	
	public boolean isComposed();
}