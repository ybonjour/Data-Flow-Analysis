package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;
import ch.yvu.dfa.expressions.analysis.AnalysisLabelExpression;

public class ReachingDefinitionsStrategy extends ForwardStrategy {
	
	public Set<AnalysisExpression> getInitializationOutgoing(ControlflowGraph graph){
		return new HashSet<AnalysisExpression>();
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
		outgoing.add(new AnalysisLabelExpression(node.getId(), node.getLhs()));
	}

	@Override
	public void gen(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming) {
		//Nothing to generate
	}

	@Override
	public void calculateIncoming(Node node, Set<AnalysisExpression> incoming,
			Map<Node, Set<AnalysisExpression>> outgoings, ControlflowGraph graph) {
		incoming.clear();
		Set<Node> parents = graph.getParents(node);
		for (Node parent : parents) {
			incoming.addAll(outgoings.get(parent));
		}
	}
}