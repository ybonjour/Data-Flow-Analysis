package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.LabeledVariable;
import ch.yvu.dfa.expressions.Expression;

public class ReachingDefinitionsStrategy extends ForwardStrategy {
	
	public Set<Expression> getInitializationOutgoing(ControlflowGraph graph){
		return new HashSet<Expression>();
	}
	
	@Override
	public Set<Expression> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<Expression>();
	}

	@Override
	public void calculateIncoming(Node node, Set<Expression> incoming,
			Map<Node, Set<Expression>> outgoings, ControlflowGraph graph) {
		incoming.clear();
		Set<Node> parents = graph.getParents(node);
		for (Node parent : parents) {
			incoming.addAll(outgoings.get(parent));
		}
	}
	
	@Override
	public void applyStatement(AssignmentNode node, Set<Expression> incoming,
			Set<Expression> outgoing) {
		outgoing.clear();
		outgoing.addAll(incoming);
		kill(node, outgoing, incoming);
		gen(node, outgoing, incoming);
	}
	
	@Override
	public void applyStatement(ConditionalNode node, Set<Expression> incoming,
			Set<Expression> outgoing) {
		outgoing.clear();
		outgoing.addAll(incoming);
		kill(node, outgoing, incoming);
		gen(node, outgoing, incoming);	
	}
	
	private void kill(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		Set<Expression> killExpressions = new HashSet<Expression>();
		for (Expression expression : outgoing) {
			if(expression.containsVariable(node.getLhs())){
				killExpressions.add(expression);
			}
		}
		outgoing.removeAll(killExpressions);
	}

	private void kill(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//Nothing to kill
	}

	private void gen(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		outgoing.add(new LabeledVariable(node.getId(), node.getLhs()));
	}

	private void gen(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//Nothing to generate
	}
}