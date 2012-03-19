package ch.yvu.dfa.analysis;

import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;

public interface AnalysisStrategy {
	
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception;
	public Set<String> getInitializationOutgoing(ControlflowGraph graph);
	public Set<String> getInitializationIncoming(ControlflowGraph graph);

	public void kill(AssignmentNode node, Set<String> outgoing, Set<String> incoming);
	public void kill(ConditionalNode node, Set<String> outgoing, Set<String> incoming);
	
	public void gen(AssignmentNode node, Set<String> outgoing, Set<String> incoming);
	public void gen(ConditionalNode node, Set<String> outgoing, Set<String> incoming);
	
	public void calculateIncoming(Node node, Set<String> entry, Map<Node, Set<String>> exits, ControlflowGraph graph);
	
	public Set<String> selectEntry(Set<String> outgoing, Set<String> incoming);
	public Set<String> selectExit(Set<String> outgoing, Set<String> incoming);
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph);
	
}
