package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;

public abstract class ForwardStrategy extends AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return 1;
	}
	
	@Override
	public Set<Expression> selectEntry(Set<Expression> outgoing, Set<Expression> incoming) {
		return incoming;
	}

	@Override
	public Set<Expression> selectExit(Set<Expression> outgoing, Set<Expression> incoming) {
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