package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Variable;
import ch.yvu.dfa.expressions.Expression;

public class StronglyLiveVariableStrategy extends BackwardStrategy {

	@Override
	public Set<Expression> getInitializationOutgoing(ControlflowGraph graph) {
		return new HashSet<Expression>();
	}

	@Override
	public Set<Expression> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<Expression>();
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
		outgoing.remove(node.getLhs());
	}

	private void kill(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//nothing to kill
		
	}

	private void gen(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//strongly live means, we do not consider reads that are assigned to a variable
		//which is later on never read (-> which is not live at the exit point
		// (exit point = incoming because it is a backward analysis))
		if(!incoming.contains(node.getLhs())) return;
		
		for(Variable freeVariable : node.getFreeVariablesRhs()){
			outgoing.add(freeVariable);
		}
	}

	private void gen(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		for(Variable freeVariable : node.getFreeVariables()){
			outgoing.add(freeVariable);
		}
	}
}
