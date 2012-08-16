package ch.yvu.dfa.expressions;

import java.util.HashSet;
import java.util.Set;

import ch.yvu.dfa.analysis.State;

public class LabeledVariable extends Expression{

	private int id;
	private Variable variable;
	
	public LabeledVariable(int id, Variable variable){
		this.id = id;
		this.variable = variable;
	}

	@Override
	public boolean containsVariable(Variable variable) {
		return this.variable.containsVariable(variable);
	}

	@Override
	public String getExpression() {
		return "(" + this.variable.getExpression() + ", " + this.id + ")"; 
	}

	@Override
	public Set<Variable> getFreeVariables() {
		Set<Variable> freeVariables = new HashSet<Variable>();
		freeVariables.add(this.variable);
		return freeVariables;
	}

	@Override
	public boolean isComposed() {
		return true;
	}

	@Override
	public NumberTop evaluate(State state) {
		throw new RuntimeException();
	}
}
