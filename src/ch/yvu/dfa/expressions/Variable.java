package ch.yvu.dfa.expressions;

import java.util.HashSet;
import java.util.Set;

public class Variable extends Expression {

	private String name;
	
	public Variable(String name){
		this.name = name;
	}

	@Override
	public boolean containsVariable(Variable variable) {
		return this.name.compareTo(variable.name) == 0;
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
