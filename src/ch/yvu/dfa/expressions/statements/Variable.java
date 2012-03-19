package ch.yvu.dfa.expressions.statements;

import java.util.HashSet;
import java.util.Set;

public class Variable implements Expression {

	private String name;
	
	public Variable(String name){
		this.name = name;
	}

	@Override
	public boolean containsVariable(String name) {
		return this.name.compareTo(name) == 0;
	}

	@Override
	public String getExpression() {
		return this.name;
	}

	@Override
	public Set<Variable> getFreeVariables() {
		Set<Variable> freeVariables = new HashSet<Variable>();
		freeVariables.add(this);
		return freeVariables;
	}

	@Override
	public boolean isComposed() {
		return false;
	}
}
