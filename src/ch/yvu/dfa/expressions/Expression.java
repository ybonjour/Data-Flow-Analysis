package ch.yvu.dfa.expressions;

import java.util.Set;

public abstract class Expression {

	public abstract boolean containsVariable(Variable name);
	
	public abstract String getExpression();
	
	public abstract Set<Variable> getFreeVariables();
	
	public abstract boolean isComposed();
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Expression)) return false;
		Expression expression = (Expression) obj;
		return this.getExpression().compareTo(expression.getExpression()) == 0;
	}
	
	@Override
	public int hashCode() {
		return this.getExpression().hashCode();
	}
}