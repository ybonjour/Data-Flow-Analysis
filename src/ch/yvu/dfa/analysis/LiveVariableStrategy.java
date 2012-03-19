package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;

public class LiveVariableStrategy extends BackwardStrategy {

	@Override
	public Set<String> getInitializationOutgoing(ControlflowGraph graph) {
		return new HashSet<String>();
	}

	@Override
	public Set<String> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<String>();
	}

	@Override
	public void kill(AssignmentNode node, Set<String> outgoing, Set<String> incoming) {
		outgoing.remove(node.getLhs());
	}

	@Override
	public void kill(ConditionalNode node, Set<String> outgoing, Set<String> incoming) {
		//nothing to kill
		
	}

	@Override
	public void gen(AssignmentNode node, Set<String> outgoing, Set<String> incoming) {
		outgoing.addAll(node.getFreeVariablesRhs());
	}

	@Override
	public void gen(ConditionalNode node, Set<String> outgoing, Set<String> incoming) {
		outgoing.addAll(node.getFreeVariables());
	}

	@Override
	public void calculateIncoming(Node node, Set<String> expression2,
			Map<Node, Set<String>> expressions1, ControlflowGraph graph) {
		//Start with empty set
		expression2.clear();
		Iterator<Node> itChildren = node.childrenIterator();
		while(itChildren.hasNext()){
			Node child = itChildren.next();
			expression2.addAll(expressions1.get(child));
		}
	}

	@Override
	public Set<String> selectEntry(Set<String> expressions1,
			Set<String> expressions2) {
		return expressions1;
	}

	@Override
	public Set<String> selectExit(Set<String> expressions1,
			Set<String> expressions2) {
		return expressions2;
	}
}
