package ch.yvu.dfa.expressions;

import java.util.HashSet;
import java.util.Set;


public class Number extends Expression {

	private int number;
	
	public Number(int number){
		this.number = number;
	}
	
	@Override
	public boolean containsVariable(Variable name) {
		return false;
	}

	@Override
	public String getExpression() {
		return String.valueOf(this.number);
	}

	@Override
	public Set<Variable> getFreeVariables() {
		return new HashSet<Variable>();
	}

	@Override
	public boolean isComposed() {
		return false;
	}

}
