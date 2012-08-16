package ch.yvu.dfa.expressions;

import java.util.HashSet;
import java.util.Set;

import ch.yvu.dfa.analysis.State;

public class NumberTop extends Expression {

	//If number is null -> Expression is Top
	private Number number;
	
	private NumberTop(){
	}
	
	public static NumberTop number(int number){
		NumberTop nt = new NumberTop();
		nt.number = new Number(number);
		return nt;
	}
	
	public static NumberTop top(){
		NumberTop nt = new NumberTop();
		nt.number = null;
		return nt;
	}
	
	public static NumberTop booleanValue(boolean value){
		return NumberTop.number(value ? 1 : 0);
	}
	
	public int number(){
		if(this.number == null) throw new RuntimeException("invalid state");
		return this.number.value();
	}
	
	public boolean isTop(){
		return this.number == null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof NumberTop)) return false;
		NumberTop nt = (NumberTop) obj;
		
		if(this.number == null || nt.number == null){
			return this.number == nt.number;
		}
		
		return this.number.equals(nt.number);
	}
	
	@Override
	public int hashCode() {
		return this.number.hashCode();
	}

	@Override
	public boolean containsVariable(Variable name) {
		return false;
	}

	@Override
	public String getExpression() {
		return this.number != null ? this.number.getExpression() : "T";
	}

	@Override
	public Set<Variable> getFreeVariables() {
		return new HashSet<Variable>();
	}

	@Override
	public boolean isComposed() {
		return false;
	}

	@Override
	public NumberTop evaluate(State state) {
		return this;
	}
	
	public NumberTop add(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		
		return NumberTop.number(this.number() + nt.number());
	}
	
	public NumberTop subtract(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		
		return NumberTop.number(this.number() - nt.number());
	}
	
	public NumberTop multiply(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		
		return NumberTop.number(this.number() * nt.number());
	}
	
	public NumberTop greaterThan(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		return NumberTop.booleanValue(this.number() > nt.number());
	}
	
	public NumberTop greaterOrEqualThan(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		return NumberTop.booleanValue(this.number() >= nt.number());
	}
	
	public NumberTop smallerThan(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		return NumberTop.booleanValue(this.number() < nt.number());
	}
	
	public NumberTop smallerOrEqualThan(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		return NumberTop.booleanValue(this.number() <= nt.number());
	}
	
	public NumberTop equalTo(NumberTop nt){
		if(this.isTop() || nt.isTop()) return NumberTop.top();
		return NumberTop.booleanValue(this.number() == nt.number());
	}
}