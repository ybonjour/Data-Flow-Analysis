package ch.yvu.dfa.analysis;

import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;

public abstract class BackwardStrategy implements AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return graph.getMaxId();
	}
	
	@Override
	public Set<Expression> selectEntry(Set<Expression> outgoing,
			Set<Expression> incoming) {
		return outgoing;
	}

	@Override
	public Set<Expression> selectExit(Set<Expression> outgoing,
			Set<Expression> incoming) {
		return incoming;
	}

	@Override
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph) {
		return graph.getParents(changedNode);
	}
}
