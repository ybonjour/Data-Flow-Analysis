package ch.yvu.dfa.expressions;

import java.util.HashSet;
import java.util.Set;

import ch.yvu.dfa.analysis.State;
import ch.yvu.dfa.expressions.Expression;

public class VariableToValue extends Expression {
	private Variable variable;
	private NumberTop value;
	
	public VariableToValue(Variable variable, NumberTop value){
		this.variable = variable;
		this.value = value;
	}

	@Override
	public boolean containsVariable(Variable name) {
		return variable.containsVariable(name);
	}

	@Override
	public String getExpression() {
		return this.variable.getExpression() + " -> " + this.value.getExpression();
	}

	@Override
	public Set<Variable> getFreeVariables() {
		return new HashSet<Variable>();
	}

	@Override
	public boolean isComposed() {
		return true;
	}

	@Override
	public NumberTop evaluate(State state){
		throw new RuntimeException();
	}
	
	public Variable getVariable(){
		return this.variable;
	}
	
	public NumberTop getValue(){
		return this.value;
	}
}