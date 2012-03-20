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
	public void kill(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		outgoing.remove(node.getLhs());
	}

	@Override
	public void kill(ConditionalNode node, Set<Expression> outgoing, Set<Expression	> incoming) {
		//nothing to kill
		
	}

	@Override
	public void gen(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//strongly live means, we do not consider reads that are assigned to a variable
		//which is later on never read (-> which is not live at the exit point
		// (exit point = incoming because it is a backward analysis))
		if(!incoming.contains(node.getLhs())) return;
		
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
	public void calculateIncoming(Node node, Set<Expression> expression2,
			Map<Node, Set<Expression>> expressions1, ControlflowGraph graph) {
		//Start with empty set
		expression2.clear();
		Iterator<Node> itChildren = node.childrenIterator();
		while(itChildren.hasNext()){
			Node child = itChildren.next();
			expression2.addAll(expressions1.get(child));
		}
	}
}
