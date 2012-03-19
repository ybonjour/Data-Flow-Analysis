package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.analysis.AnalysisExpression;

public abstract class ForwardStrategy implements AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return 1;
	}
	
	@Override
	public Set<AnalysisExpression> selectEntry(Set<AnalysisExpression> outgoing,
			Set<AnalysisExpression> incoming) {
		return incoming;
	}

	@Override
	public Set<AnalysisExpression> selectExit(Set<AnalysisExpression> outgoing,
			Set<AnalysisExpression> incoming) {
		return outgoing;
	}

	@Override
	public Set<Node> getNodesToRecalculate(Node changedNode, ControlflowGraph graph) {
		Set<Node> set = new HashSet<Node>(); 
		Iterator<Node> children = changedNode.childrenIterator();
		while(children.hasNext()){
			Node child = children.next();
			set.add(child);
		}
		
		return set;
	}

}
