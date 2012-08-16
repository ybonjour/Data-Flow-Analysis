package ch.yvu.dfa.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.yvu.dfa.expressions.Variable;
import ch.yvu.dfa.expressions.VariableToValue;
import ch.yvu.dfa.expressions.NumberTop;
import ch.yvu.dfa.expressions.StateExpression;

public class State {

	//Expression must be a number or Bottom / Top
	private Map<Variable, NumberTop> variables;
	
	public State(){
		this.variables = new HashMap<Variable, NumberTop>();
	}
	
	public State(StateExpression stateExpression){
		this();
		for(VariableToValue expression : stateExpression.getExpressions()){
			if(this.variables.containsKey(expression.getVariable())) throw new IllegalArgumentException();		
			this.variables.put(expression.getVariable(), expression.getValue());
		}
	}
	
	public boolean containsVariable(Variable variable){
		return this.variables.containsKey(variable);
	}
	
	public void setVariable(Variable variable, NumberTop number){
		this.variables.put(variable, number);
	}
	
	public NumberTop getValue(Variable variable){
		return this.variables.get(variable);
	}
	
	public Iterator<Variable> variableIterator(){
		return this.variables.keySet().iterator();
	}
	
	public StateExpression createStateExpression(){
		List<VariableToValue> variableList = new ArrayList<VariableToValue>();
		for(Variable var : this.variables.keySet()){
			NumberTop nt = this.variables.get(var);
			if(nt == null){
				throw new RuntimeException();
			}
			VariableToValue varToVal =  new VariableToValue(var, nt);
			variableList.add(varToVal);
		}
		
		return new StateExpression(variableList);
	}
}