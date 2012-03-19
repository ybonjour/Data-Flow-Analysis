package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;

public class ReachingDefinitionsStrategy extends ForwardStrategy {
	
	public Set<String> getInitializationOutgoing(ControlflowGraph graph){
		return new HashSet<String>();
	}
	
	@Override
	public Set<String> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<String>();
	}

	@Override
	public void kill(AssignmentNode node, Set<String> outgoing, Set<String> incoming) {
		Set<String> killExpressions = new HashSet<String>();
		for (String expression : outgoing) {
			if(expression.contains(node.getLhs())){
				killExpressions.add(expression);
			}
		}
		outgoing.removeAll(killExpressions);
	}

	@Override
	public void kill(ConditionalNode node, Set<String> outgoing, Set<String> incoming) {
		//Nothing to kill
	}

	@Override
	public void gen(AssignmentNode node, Set<String> outgoing, Set<String> incoming) {
		outgoing.add(createLabelVariableString(node));
	}

	@Override
	public void gen(ConditionalNode node, Set<String> outgoing, Set<String> incoming) {
		//Nothing to generate
	}

	@Override
	public void calculateIncoming(Node node, Set<String> incoming,
			Map<Node, Set<String>> outgoings, ControlflowGraph graph) {
		incoming.clear();
		Set<Node> parents = graph.getParents(node);
		for (Node parent : parents) {
			incoming.addAll(outgoings.get(parent));
		}
	}
	
	private String createLabelVariableString(AssignmentNode node){
		return "(" + node.getLhs() + ", " + node.getId() + ")";
	}
}