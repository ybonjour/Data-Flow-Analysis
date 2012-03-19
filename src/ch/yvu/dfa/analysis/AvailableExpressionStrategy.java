package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;
import ch.yvu.dfa.expressions.analysis.AnalysisNodeExpression;

public class AvailableExpressionStrategy extends ForwardStrategy{
	
	public Set<AnalysisExpression> getInitializationOutgoing(ControlflowGraph graph){
		return getAllExpressions(graph);
	}

	@Override
	public Set<AnalysisExpression> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<AnalysisExpression>();
	}

	@Override
	public void kill(AssignmentNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		Set<AnalysisExpression> killExpressions = new HashSet<AnalysisExpression>();
		for (AnalysisExpression expression : outgoing) {
			if(expression.containsVariable(node.getLhs())){
				killExpressions.add(expression);
			}
		}
		outgoing.removeAll(killExpressions);
	}

	@Override
	public void kill(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		//Nothing to kill
	}

	@Override
	public void gen(AssignmentNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		//Do not add the expressions if it contains a free variable,
		//that the expression is assigned to.
		//(allows for independent ordering between gen and kill operations)
		if(node.containsRightHandSideVariableInLeftHandSide()) return;
		
		if(!node.isComposedExpression()) return;
		
		outgoing.add(new AnalysisNodeExpression(node.getRhs()));
	}

	@Override
	public void gen(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		outgoing.add(new AnalysisNodeExpression(node.getExpression()));
	}

	@Override
	public void calculateIncoming(Node node, Set<AnalysisExpression> incomings, Map<Node, Set<AnalysisExpression>> outgoings,
			ControlflowGraph graph) {
		incomings.clear();
		incomings.addAll(getAllExpressions(graph));
		
		Set<Node> parents = graph.getParents(node);
		for (Node parent : parents) {
			incomings.retainAll(outgoings.get(parent));
		}
	}
	
	private Set<AnalysisExpression> getAllExpressions(ControlflowGraph graph){
		Set<AnalysisExpression> expressions = new HashSet<AnalysisExpression>();
		for(Node node : graph.getNodes()){
			expressions.add(new AnalysisNodeExpression(node.getExpression()));
		}
		return expressions;
	}
}
