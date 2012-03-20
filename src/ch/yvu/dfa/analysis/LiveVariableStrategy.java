package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.Variable;

public class LiveVariableStrategy extends BackwardStrategy {

	@Override
	public Set<Expression> getInitializationOutgoing(ControlflowGraph graph) {
		return new HashSet<Expression>();
	}

	@Override
	public Set<Expression> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<Expression>();
	}

	@Override
	public void kill(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		outgoing.remove(node.getLhs());
	}

	@Override
	public void kill(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//nothing to kill
		
	}

	@Override
	public void gen(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		for(Variable freeVariable : node.getFreeVariablesRhs()){
			outgoing.add(freeVariable);
		}
	}

	@Override
	public void gen(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		for(Variable freeVariable : node.getFreeVariables()){
			outgoing.add(freeVariable);
		}
	}

	@Override
	public void calculateIncoming(Node node, Set<Expression> incoming,
			Map<Node, Set<Expression>> outgoing, ControlflowGraph graph) {
		//Start with empty set
		incoming.clear();
		Iterator<Node> itChildren = node.childrenIterator();
		while(itChildren.hasNext()){
			Node child = itChildren.next();
			incoming.addAll(outgoing.get(child));
		}
	}
}
