package ch.yvu.dfa.controlflowgraph;

import java.util.HashSet;
import java.util.Set;
import ch.yvu.dfa.analysis.AnalysisStrategy;

public class ConditionalNode extends Node{

	private String condition;
	private Set<String> freeVariables;
	
	public ConditionalNode(int id, String condition, Set<String> freeVariables){
		super(id);
		this.condition = condition;
		this.freeVariables = freeVariables;
	}
	
	public void kill(Set<String> outgoing, Set<String> incoming, AnalysisStrategy strategy){
		strategy.kill(this, outgoing, incoming);
	}
	
	public void gen(Set<String> outgoing, Set<String> incoming, AnalysisStrategy strategy){
		strategy.gen(this, outgoing, incoming);
	}

	@Override
	public String getExpression() {
		return condition;
	}

	@Override
	public String getStatement() {
		return condition;
	}

	public String getCondition() {
		return condition;
	}
	
	public Set<String> getFreeVariables(){
		return new HashSet<String>(this.freeVariables);
	}
}
