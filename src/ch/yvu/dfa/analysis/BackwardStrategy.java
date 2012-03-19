package ch.yvu.dfa.analysis;

import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;

public abstract class BackwardStrategy implements AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return graph.getMaxId();
	}
	
	@Override
	public Set<AnalysisExpression> selectEntry(Set<AnalysisExpression> outgoing,
			Set<AnalysisExpression> incoming) {
		return outgoing;
	}

	@Override
	public Set<AnalysisExpression> selectExit(Set<AnalysisExpression> outgoing,
			Set<AnalysisExpression> incoming) {
		return incoming;
	}

	@Override
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph) {
		return graph.getParents(changedNode);
	}
}
