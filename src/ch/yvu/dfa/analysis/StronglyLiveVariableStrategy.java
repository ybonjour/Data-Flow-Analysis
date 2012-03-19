package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;
import ch.yvu.dfa.expressions.analysis.AnalysisNodeExpression;
import ch.yvu.dfa.expressions.statements.Variable;

public class StronglyLiveVariableStrategy extends BackwardStrategy {

	@Override
	public Set<AnalysisExpression> getInitializationOutgoing(ControlflowGraph graph) {
		return new HashSet<AnalysisExpression>();
	}

	@Override
	public Set<AnalysisExpression> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<AnalysisExpression>();
	}

	@Override
	public void kill(AssignmentNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		outgoing.remove(node.getLhs());
	}

	@Override
	public void kill(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression	> incoming) {
		//nothing to kill
		
	}

	@Override
	public void gen(AssignmentNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		//strongly live means, we do not consider reads that are assigned to a variable
		//which is later on never read (-> which is not live at the exit point
		// (exit point = incoming because it is a backward analysis))
		if(!incoming.contains(node.getLhs())) return;
		
		for(Variable freeVariable : node.getFreeVariablesRhs()){
			outgoing.add(new AnalysisNodeExpression(freeVariable));
		}
	}

	@Override
	public void gen(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		for(Variable freeVariable : node.getFreeVariables()){
			outgoing.add(new AnalysisNodeExpression(freeVariable));
		}
	}

	@Override
	public void calculateIncoming(Node node, Set<AnalysisExpression> expression2,
			Map<Node, Set<AnalysisExpression>> expressions1, ControlflowGraph graph) {
		//Start with empty set
		expression2.clear();
		Iterator<Node> itChildren = node.childrenIterator();
		while(itChildren.hasNext()){
			Node child = itChildren.next();
			expression2.addAll(expressions1.get(child));
		}
	}
}
