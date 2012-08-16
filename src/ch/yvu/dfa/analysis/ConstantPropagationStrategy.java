package ch.yvu.dfa.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.NumberTop;
import ch.yvu.dfa.expressions.StateExpression;
import ch.yvu.dfa.expressions.Variable;
import ch.yvu.dfa.expressions.VariableToValue;

public class ConstantPropagationStrategy extends AnalysisStrategy {

	@Override
	public int getIdOfInitialNode(ControlflowGraph graph) throws Exception {
		return 1;
	}

	@Override
	public Set<Expression> getInitializationOutgoing(ControlflowGraph graph) {
		HashSet<Expression> initial = new HashSet<Expression>();
		initial.add(StateExpression.invalidSateExpression());
		return initial;
	}

	@Override
	public Set<Expression> getInitializationIncoming(ControlflowGraph graph) {
		HashSet<Expression> initial = new HashSet<Expression>();
		initial.add(new StateExpression());
		return initial;
	}

	@Override
	public void calculateIncoming(Node node, Set<Expression> incoming,
			Map<Node, Set<Expression>> outgoing, ControlflowGraph graph) {
		
		State mergedState = new State();
		for(Node predecessor : graph.getParents(node)){
			Set<Expression> predecessorOutgoing = outgoing.get(predecessor);
			if(predecessorOutgoing.size() != 1)  throw new RuntimeException("More than one expression");
			State predecessorState = new State((StateExpression) predecessorOutgoing.toArray()[0]);
			
			Iterator<Variable> varIt = predecessorState.variableIterator();
			while(varIt.hasNext()){
				Variable variable = varIt.next();
				NumberTop nt;
				if(mergedState.containsVariable(variable) && mergedState.getValue(variable) != predecessorState.getValue(variable)){
					nt = NumberTop.top();
				} else {
					nt = predecessorState.getValue(variable);
				}
				mergedState.setVariable(variable, nt);
			}
		}
		
		incoming.clear();
		incoming.add(mergedState.createStateExpression());
	}

	@Override
	public Set<Expression> selectEntry(Set<Expression> outgoing,
			Set<Expression> incoming) {
		return incoming;
	}

	@Override
	public Set<Expression> selectExit(Set<Expression> outgoing,
			Set<Expression> incoming) {
		return outgoing;
	}

	@Override
	public Set<Node> getNodesToRecalculate(Node changedNode,
			ControlflowGraph graph) {
		Set<Node> set = new HashSet<Node>(); 
		Iterator<Node> children = changedNode.childrenIterator();
		while(children.hasNext()){
			Node child = children.next();
			set.add(child);
		}
		
		return set;
	}
	
	//requires: Set only contains VariableToValue Expressions
	public Set<VariableToValue> castExpressionSet(Set<Expression> expressions){
		Set<VariableToValue> resultSet = new HashSet<VariableToValue>();
		for(Expression expression : expressions){
			resultSet.add((VariableToValue) expression);
		}
		
		return resultSet;
	}


	@Override
	public void applyStatement(AssignmentNode node, Set<Expression> incoming,
			Set<Expression> outgoing) {
		
		//for constant propagation only one Expression can be tracked (state)
		if(incoming.size() != 1) throw new RuntimeException("More than one expression");
		
		StateExpression incomingStateExpression = (StateExpression) incoming.toArray()[0];
		StateExpression outgoingStateExpression;
		if(incomingStateExpression.isInvalid()){
			outgoingStateExpression = StateExpression.invalidSateExpression();
		} else {
			State state = new State(incomingStateExpression);
			NumberTop value = node.getRhs().evaluate(state);
			if(value == null)
			{
				outgoingStateExpression = StateExpression.invalidSateExpression();
			} else {
				state.setVariable(node.getLhs(), value);
				outgoingStateExpression = state.createStateExpression();
			}
		}
		
		outgoing.clear();
		outgoing.add(outgoingStateExpression);
	}

	@Override
	public void applyStatement(ConditionalNode node, Set<Expression> incoming,
			Set<Expression> outgoing) {
		outgoing.clear();
		outgoing.addAll(incoming);
	}

}
