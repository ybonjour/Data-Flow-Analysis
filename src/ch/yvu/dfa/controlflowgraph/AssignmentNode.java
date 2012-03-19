package ch.yvu.dfa.controlflowgraph;

import java.util.HashSet;
import java.util.Set;
import ch.yvu.dfa.analysis.AnalysisStrategy;

public class AssignmentNode extends Node {

	private String lhs;
	private String rhs;
	private Set<String> freeVariablesRhs;
	
	public AssignmentNode(int id, String lhs, String rhs, Set<String> freeVariablesRhs){
		super(id);
		this.lhs = lhs;
		this.rhs = rhs;
		this.freeVariablesRhs = freeVariablesRhs;
	}
	
	public void kill(Set<String> outgoing, Set<String> incoming, AnalysisStrategy strategy){
		strategy.kill(this, outgoing, incoming);
	}
	
	public void gen(Set<String> outgoing, Set<String> incoming, AnalysisStrategy strategy){
		strategy.gen(this, outgoing, incoming);
	}

	@Override
	public String getExpression() {
		return rhs;
	}

	public String getLhs() {
		return lhs;
	}

	public String getRhs() {
		return rhs;
	}

	@Override
	public String getStatement() {
		return this.lhs + ":=" + this.rhs;
	}
	
	public Set<String> getFreeVariablesRhs(){
		return new HashSet<String>(this.freeVariablesRhs);
	}
}
