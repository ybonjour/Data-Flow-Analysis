package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;

public class AvailableExpressionStrategy extends ForwardStrategy{
	
	public Set<String> getInitializationOutgoing(ControlflowGraph graph){
		return graph.allExpressions();
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
		//Do not add the expressions if it contains a free variable,
		//that the expression is assigned to.
		//(allows for independent ordering between gen and kill operations)
		if(node.getFreeVariablesRhs().contains(node.getLhs())) return;
		outgoing.add(node.getRhs());
	}

	@Override
	public void gen(ConditionalNode node, Set<String> outgoing, Set<String> incoming) {
		outgoing.add(node.getCondition());
	}

	@Override
	public void calculateIncoming(Node node, Set<String> incomings, Map<Node, Set<String>> outgoings,
			ControlflowGraph graph) {
		incomings.clear();
		incomings.addAll(graph.allExpressions());
		
		Set<Node> parents = graph.getParents(node);
		for (Node parent : parents) {
			incomings.retainAll(outgoings.get(parent));
		}
	}
}
