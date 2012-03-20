package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;

public class AvailableExpressionStrategy extends ForwardStrategy{
	
	public Set<Expression> getInitializationOutgoing(ControlflowGraph graph){
		return getAllExpressions(graph);
	}

	@Override
	public Set<Expression> getInitializationIncoming(ControlflowGraph graph) {
		return new HashSet<Expression>();
	}

	@Override
	public void kill(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		Set<Expression> killExpressions = new HashSet<Expression>();
		for (Expression expression : outgoing) {
			if(expression.containsVariable(node.getLhs())){
				killExpressions.add(expression);
			}
		}
		outgoing.removeAll(killExpressions);
	}

	@Override
	public void kill(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//Nothing to kill
	}

	@Override
	public void gen(AssignmentNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		//Do not add the expressions if it contains a free variable,
		//that the expression is assigned to.
		//(allows for independent ordering between gen and kill operations)
		if(node.containsRightHandSideVariableInLeftHandSide()) return;
		
		if(!node.isComposedExpression()) return;
		
		outgoing.add(node.getRhs());
	}

	@Override
	public void gen(ConditionalNode node, Set<Expression> outgoing, Set<Expression> incoming) {
		outgoing.add(node.getExpression());
	}

	@Override
	public void calculateIncoming(Node node, Set<Expression> incomings, Map<Node, Set<Expression>> outgoings,
			ControlflowGraph graph) {
		incomings.clear();
		incomings.addAll(getAllExpressions(graph));
		
		Set<Node> parents = graph.getParents(node);
		for (Node parent : parents) {
			incomings.retainAll(outgoings.get(parent));
		}
	}
	
	private Set<Expression> getAllExpressions(ControlflowGraph graph){
		Set<Expression> expressions = new HashSet<Expression>();
		for(Node node : graph.getNodes()){
			expressions.add(node.getExpression());
		}
		return expressions;
	}
}
