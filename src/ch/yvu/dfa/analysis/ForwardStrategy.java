package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;

public abstract class ForwardStrategy implements AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return 1;
	}
	
	@Override
	public Set<String> selectEntry(Set<String> outgoing,
			Set<String> incoming) {
		return incoming;
	}

	@Override
	public Set<String> selectExit(Set<String> outgoing,
			Set<String> incoming) {
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
