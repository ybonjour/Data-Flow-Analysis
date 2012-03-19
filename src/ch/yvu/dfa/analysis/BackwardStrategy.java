package ch.yvu.dfa.analysis;

import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;

public abstract class BackwardStrategy implements AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return graph.getMaxId();
	}
	
	@Override
	public Set<String> selectEntry(Set<String> outgoing,
			Set<String> incoming) {
		return outgoing;
	}

	@Override
	public Set<String> selectExit(Set<String> outgoing,
			Set<String> incoming) {
		return incoming;
	}

	@Override
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph) {
		return graph.getParents(changedNode);
	}
}
