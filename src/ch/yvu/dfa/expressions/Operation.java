package ch.yvu.dfa.expressions;

import java.util.HashSet;
import java.util.Set;

import ch.yvu.dfa.analysis.State;

public class Operation extends Expression {

	private Expression leftOperand;
	private Expression rightOperand;
	private String operation;
	
	public Operation(Expression leftOperand, Expression rightOperand, String operation){
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		this.operation = operation;
	}
	
	public boolean containsVariable(Variable variable){		
		return this.leftOperand.containsVariable(variable) || this.rightOperand.containsVariable(variable);
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

	@Override
	public NumberTop evaluate(State state){		
		NumberTop rightNumber = this.rightOperand.evaluate(state);
		NumberTop leftNumber =	this.leftOperand.evaluate(state);
		
		if(rightNumber == null || leftNumber == null){
			return null;
		}
		
		if(this.operation.equals("+")){
			return rightNumber.add(leftNumber);
		} else if(this.operation.equals("-")){
			return rightNumber.subtract(leftNumber);
		} else if(this.operation.equals("*")){
			return rightNumber.multiply(leftNumber);
		} else if(this.operation.equals(">")){
			return rightNumber.greaterThan(leftNumber);
		} else if(this.operation.equals(">=")){
			return rightNumber.greaterOrEqualThan(leftNumber);
		} else if(this.operation.equals("<")){
			return rightNumber.smallerThan(leftNumber);
		} else if(this.operation.equals("<=")){
			return rightNumber.smallerOrEqualThan(leftNumber);
		} else if(this.operation.equals("==")){
			return rightNumber.equalTo(leftNumber);
		}
		
		throw new RuntimeException();
	}
}
