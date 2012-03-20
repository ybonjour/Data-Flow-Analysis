package ch.yvu.dfa.analysis;

import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;

public interface AnalysisStrategy {
	
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception;
	public Set<Expression> getInitializationOutgoing(ControlflowGraph graph);
	public Set<Expression> getInitializationIncoming(ControlflowGraph graph);

	public void kill(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming);
	public void kill(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming);
	
	public void gen(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming);
	public void gen(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming);
	
	public void calculateIncoming(Node node, Set<Expression> incoming, Map<Node, Set<Expression>> outgoing, ControlflowGraph graph);
	
	public Set<Expression> selectEntry(Set<Expression> outgoing, Set<Expression> incoming);
	public Set<Expression> selectExit(Set<Expression> outgoing, Set<Expression> incoming);
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph);
	
}
