package ch.yvu.dfa.analysis;

import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;

public interface AnalysisStrategy {
	
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception;
	public Set<AnalysisExpression> getInitializationOutgoing(ControlflowGraph graph);
	public Set<AnalysisExpression> getInitializationIncoming(ControlflowGraph graph);

	public void kill(AssignmentNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming);
	public void kill(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming);
	
	public void gen(AssignmentNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming);
	public void gen(ConditionalNode node, Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming);
	
	public void calculateIncoming(Node node, Set<AnalysisExpression> incoming, Map<Node, Set<AnalysisExpression>> outgoing, ControlflowGraph graph);
	
	public Set<AnalysisExpression> selectEntry(Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming);
	public Set<AnalysisExpression> selectExit(Set<AnalysisExpression> outgoing, Set<AnalysisExpression> incoming);
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph);
	
}
