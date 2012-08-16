package ch.yvu.dfa.expressions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.yvu.dfa.analysis.State;

public class StateExpression extends Expression {
	private List<VariableToValue> state;

	public StateExpression(){
		this.state = new ArrayList<VariableToValue>();
	}
	
	public StateExpression(List<VariableToValue> state){
		this.state = state;
	}
	
	public static StateExpression invalidSateExpression(){
		StateExpression state = new StateExpression(null);
		return state;
	}
	
	@Override
	public boolean containsVariable(Variable name) {
		if(this.state == null) return false;
		
		for(VariableToValue v : this.state){
			if(v.containsVariable(name)) return true;
		}
		
		return false;
	}

	@Override
	public String getExpression() {
		if(this.state == null) return "B";
		
		String expression = "";
		
		boolean first = true;
		for(VariableToValue v : this.state){
			if(!first){
				expression += ",";
			}
			first = false;
			
			expression += v.getExpression();
		}
		
		return expression;
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
	
	public boolean isInvalid(){
		return this.state == null;
	}
	
	public List<VariableToValue> getExpressions(){
		ArrayList<VariableToValue> list = new ArrayList<VariableToValue>(); 
		if(this.state != null){
			list.addAll(this.state);
		}
		return list;
	}
}