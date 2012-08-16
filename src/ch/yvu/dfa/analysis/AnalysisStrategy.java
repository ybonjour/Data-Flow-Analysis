package ch.yvu.dfa.analysis;

import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;

public abstract class AnalysisStrategy {
	
	public void applyStatement(Node node, Set<Expression> incoming, Set<Expression> outgoing){
		node.applyStatement(incoming, outgoing, this);
	}
	
	public abstract int getIdOfInitialNode(ControlflowGraph graph) throws Exception;
	public abstract Set<Expression> getInitializationOutgoing(ControlflowGraph graph);
	public abstract Set<Expression> getInitializationIncoming(ControlflowGraph graph);
	
	public abstract void calculateIncoming(Node node, Set<Expression> incoming, Map<Node, Set<Expression>> outgoing, ControlflowGraph graph);
	
	public abstract Set<Expression> selectEntry(Set<Expression> outgoing, Set<Expression> incoming);
	public abstract Set<Expression> selectExit(Set<Expression> outgoing, Set<Expression> incoming);
	public abstract Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph);
	
	public abstract void applyStatement(AssignmentNode node, Set<Expression> incoming, Set<Expression> outgoing);
	public abstract void applyStatement(ConditionalNode node, Set<Expression> incoming, Set<Expression> outgoing);
}
